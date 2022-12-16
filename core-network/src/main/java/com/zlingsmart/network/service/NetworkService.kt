package com.zlingsmart.network.service

import com.zlingsmart.model.ArticleInfo
import com.zlingsmart.network.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkService {

    /**
     * 获取token
     */
    @POST("oauth/token")
    suspend fun getToken(@Query("grant_type") grantType : String= "client_credentials",
                         @Query("client_id") clientID : String= "Settings.CLIENT_ID",
                         @Query("client_secret") clientSecret : String= "Settings.CLIENT_SECRET"): BaseResponse<String>


    /**
     * 获取首页文章列表
     */
    @GET("article/list/0/json")
    suspend fun getArticlesList(): BaseResponse<ArticleInfo>
}
