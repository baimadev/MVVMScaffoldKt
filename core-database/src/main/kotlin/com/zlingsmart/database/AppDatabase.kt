package com.zlingsmart.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zlingsmart.model.ArticleData


@Database(
    entities = [ArticleData::class],
    version = 2,
    exportSchema = true
)

@TypeConverters(value = [TypeResponseConverter::class])
abstract class AppDatabase:RoomDatabase() {
    abstract fun articleDataDao():ArticleDataDao
}