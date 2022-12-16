package com.zlingsmart.database

import androidx.room.*
import com.zlingsmart.model.ArticleData

@Dao
interface ArticleDataDao {
    @Query("select * from ArticleData")
    suspend fun getAllData() : List<ArticleData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleData: List<ArticleData>)

    @Update
    suspend fun update( articleData: ArticleData)
}