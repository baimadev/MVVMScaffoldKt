package com.zlingsmart.network.interceptor

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.GzipSource
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.TimeUnit

class LoggingInterceptor @JvmOverloads constructor(private val logger: Logger = Logger.DEFAULT) :
        Interceptor {

    val DEBUT_SHOW_LOG = "DEBUT_SHOW_LOG"

    @Volatile
    var level: Level = Level.NONE

    @Volatile
    private var headersToRedact = emptySet<String>()

    /**
     * 指定敏感的Header，打印黑块儿而不是真实内容
     *
     * eg:
     * logging.redactHeader("Authorization")
     * logging.redactHeader("Cookie")
     */
    fun redactHeader(name: String) {
        headersToRedact = TreeSet(String.CASE_INSENSITIVE_ORDER).apply {
            addAll(headersToRedact)
            add(name)
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level

        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS

        val requestBody = request.body

        val connection = chain.connection()
        var requestMessage = ("--> "
                + request.method
                + ' '.toString() + request.url
                + if (connection != null) " " + connection.protocol() else "") + SEPARATOR
        if (!logHeaders && requestBody != null) {
            requestMessage += " (" + requestBody.contentLength() + "-byte body)" + SEPARATOR
        }

        if (logHeaders) {
            if (requestBody != null) {
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    requestMessage += "Content-Type: $contentType$SEPARATOR"
                }

                val contentLength = requestBody.contentLength()
                if (contentLength != -1L) {
                    requestMessage += "Content-Length: $contentLength$SEPARATOR"
                }
            }

            val headers = request.headers
            var i = 0
            val count = headers.size
            while (i < count) {
                val name = headers.name(i)
                if (!"Content-Type".equals(name, ignoreCase = true)
                        && !"Content-Length".equals(name, ignoreCase = true)
                ) {
                    requestMessage += headerMessage(headers, i)
                }
                i++
            }

            requestMessage += SEPARATOR
            if (!logBody || requestBody == null) {
                requestMessage += "--> END " + request.method + SEPARATOR
            } else if (bodyHasUnknownEncoding(request.headers)) {
                requestMessage += "--> END " + request.method + " (encoded body omitted)" + SEPARATOR
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)

                val charset = requestBody.contentType()?.charset(UTF8) ?: UTF8

                if (isPlaintext(buffer)) {
                    val requestBodyString = buffer.readString(charset)
                    requestMessage += requestBodyString + SEPARATOR
                    requestMessage += SEPARATOR
                    requestMessage += "--> END " + request.method + " (" + requestBody.contentLength() + "-byte body)" + SEPARATOR
                } else {
                    requestMessage += "--> END " + request.method + " (binary " + requestBody.contentLength() + "-byte body omitted)" + SEPARATOR
                }
            }
        }
        if (SHOW_NETWORK_LOG || request.header(DEBUT_SHOW_LOG) == "true")
            logger.log(requestMessage)

        var responseMessage = ""
        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            responseMessage += "<-- HTTP FAILED: $e$SEPARATOR"
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = requireNotNull(response.body)
        val contentLength = responseBody.contentLength()
        val bodySize =
                if (contentLength != -1L) contentLength.toString() + "-byte" else "unknown-length"
        responseMessage += "<-- " + response.code +
                (if (response.message.isEmpty()) "" else ' ' + response.message) + ' ' +
                response.request
                        .url + " (" + tookMs + "ms" + (if (!logHeaders) ", $bodySize body" else "") + ')' + SEPARATOR

        if (logHeaders) {
            val headers = response.headers
            var i = 0
            val count = headers.size
            while (i < count) {
                responseMessage += headerMessage(headers, i++)
            }

            if (!logBody ) {
                responseMessage += "<-- END HTTP$SEPARATOR"
            } else if (bodyHasUnknownEncoding(response.headers)) {
                responseMessage += "<-- END HTTP (encoded body omitted)$SEPARATOR"
            } else {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source.buffer()

                var gzippedLength: Long? = null
                if ("gzip".equals(headers.get("Content-Encoding"), ignoreCase = true)) {
                    gzippedLength = buffer.size
                    var gzippedResponseBody: GzipSource? = null
                    try {
                        gzippedResponseBody = GzipSource(buffer.clone())
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    } finally {
                        gzippedResponseBody?.close()
                    }
                }

                val charset = responseBody.contentType()?.charset(UTF8) ?: UTF8

                if (!isPlaintext(buffer)) {
                    responseMessage += SEPARATOR
                    responseMessage += "<-- END HTTP (binary " + buffer.size + "-byte body omitted)" + SEPARATOR
                    return response
                }

                if (contentLength != 0L) {
                    responseMessage += SEPARATOR
                    val responseBodyString = buffer.clone().readString(charset)
                    responseMessage += responseBodyString + SEPARATOR
                }

                responseMessage += SEPARATOR
                responseMessage += if (gzippedLength != null) {
                    "<-- END HTTP (" + buffer.size + "-byte, " + gzippedLength + "-gzipped-byte body)" + SEPARATOR
                } else {
                    "<-- END HTTP (" + buffer.size + "-byte body)" + SEPARATOR
                }
            }
        }
        if (SHOW_NETWORK_LOG || request.header(DEBUT_SHOW_LOG) == "true")
            logger.log(responseMessage)

        return response
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return (contentEncoding != null
                && !contentEncoding.equals("identity", ignoreCase = true)
                && !contentEncoding.equals("gzip", ignoreCase = true))
    }

    private fun headerMessage(headers: Headers, i: Int): String {
        val name = headers.name(i)
        val value = if (headersToRedact.contains(name)) "██" else headers.value(i)
        return "$name: $value$SEPARATOR"
    }

    enum class Level {

        /**
         * No logs.
         */
        NONE,

        /**
         * Logs request and response lines.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,

        /**
         * Logs request and response lines and their respective headers.
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,

        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    interface Logger {

        fun log(message: String)

        companion object {

            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    Timber.i(message)
                }
            }
        }
    }

    companion object {
        const val SHOW_NETWORK_LOG = true
        private val UTF8 = Charset.forName("UTF-8")

        private const val SEPARATOR = "\n"

        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false
            }
        }
    }
}
