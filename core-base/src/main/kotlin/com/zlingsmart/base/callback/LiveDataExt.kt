package com.zlingsmart.base.callback

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * 添加默认值
 */
class BooleanLiveData : MutableLiveData<Boolean>() {

    override fun getValue(): Boolean {
        return super.getValue() ?: false
    }
}

class ByteLiveData : MutableLiveData<Byte>() {
    override fun getValue(): Byte {
        return super.getValue() ?: 0
    }
}

class DoubleLiveData : MutableLiveData<Double>() {
    override fun getValue(): Double {
        return super.getValue() ?: 0.0
    }
}

class FloatLiveData(value: Float = 0f) : MutableLiveData<Float>(value) {
    override fun getValue(): Float {
        return super.getValue()!!
    }
}

class IntLiveData : MutableLiveData<Int>() {

    override fun getValue(): Int {
        return super.getValue() ?: 0
    }
}

class ShortLiveData : MutableLiveData<Short>() {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}

class StringLiveData : MutableLiveData<String>() {
    override fun getValue(): String {
        return super.getValue() ?: ""
    }
}

/**
 * 非粘性livedata
 * 仅分发 owner observe 后 才新拿到的数据
 * 可避免共享作用域 VM 下 liveData 被 observe 时旧数据倒灌的情况
 */
class NonStickLiveData<T> : MutableLiveData<T>() {
    override fun observe(
        owner: LifecycleOwner,
        observer: Observer<in T>
    ) {
        super.observe(owner, observer)
        hook(observer)
    }

    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        try {
            //获取field private SafeIterableMap<Observer<T>, ObserverWrapper> mObservers
            val mObservers = liveDataClass.getDeclaredField("mObservers")
            mObservers.isAccessible = true
            //获取SafeIterableMap集合mObservers
            val observers = mObservers[this]
            val observersClass: Class<*> = observers.javaClass
            //获取SafeIterableMap的get(Object obj)方法
            val methodGet =
                observersClass.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            //获取到observer在集合中对应的ObserverWrapper对象
            val objectWrapperEntry = methodGet.invoke(observers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("ObserverWrapper can not be null")
            }
            //获取ObserverWrapper的Class对象  LifecycleBoundObserver extends ObserverWrapper
            val wrapperClass: Class<*>? = objectWrapper.javaClass.superclass
            //获取ObserverWrapper的field mLastVersion
            val mLastVersion =
                wrapperClass!!.getDeclaredField("mLastVersion")
            mLastVersion.isAccessible = true
            //获取liveData的field mVersion
            val mVersion = liveDataClass.getDeclaredField("mVersion")
            mVersion.isAccessible = true
            val mV = mVersion[this]
            //把当前ListData的mVersion赋值给 ObserverWrapper的field mLastVersion
            mLastVersion[objectWrapper] = mV
            mObservers.isAccessible = false
            methodGet.isAccessible = false
            mLastVersion.isAccessible = false
            mVersion.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}