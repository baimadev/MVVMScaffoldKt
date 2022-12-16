package com.zlingsmart.data.repository

import com.zlingsmart.database.ArticleDataDao
import com.zlingsmart.model.ArticleData
import com.zlingsmart.model.ArticleInfo
import com.zlingsmart.network.response.getResult
import com.zlingsmart.network.service.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
    private val articleDataDao: ArticleDataDao
) : HomeRepository {

    override fun getArticles(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<List<ArticleData>> =
        flow {
            val list = articleDataDao.getAllData()
            if (list.isNotEmpty()){
                Timber.d("data from db!")
                emit(list)
            }else{
                val articleInfo = networkClient.getArticles().getResult()
                emit(articleInfo.datas)
            }
        }.catch { exception ->
            onError.invoke()
            Timber.e(exception.message)
        }
            .onStart { onStart.invoke() }
            .onCompletion { onComplete.invoke() }
            .flowOn(Dispatchers.IO)


    override fun saveArticleData(
        articleData: List<ArticleData>,
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<Boolean> = flow {
        articleDataDao.insert(articleData)
        emit(true)
    }.catch { exception ->
        onError.invoke()
        Timber.e(exception.message)
    }
        .onStart { onStart.invoke() }
        .onCompletion { onComplete.invoke() }
        .flowOn(Dispatchers.IO)

}