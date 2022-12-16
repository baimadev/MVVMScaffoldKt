package com.zlingsmart.network.service

import com.zlingsmart.model.ArticleInfo
import com.zlingsmart.network.response.BaseResponse
import javax.inject.Inject

class NetworkClient @Inject constructor(private val networkService: NetworkService){

    suspend fun getToken():BaseResponse<String> = networkService.getToken("test")

    suspend fun getArticles():BaseResponse<ArticleInfo> = networkService.getArticlesList()

}