package com.zlingsmart.data.repository

import com.zlingsmart.model.ArticleData
import com.zlingsmart.model.ArticleInfo
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getArticles(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<List<ArticleData>>

    fun saveArticleData(
        articleData: List<ArticleData>,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Boolean>
}