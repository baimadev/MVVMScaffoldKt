package com.zlingsmart.model
import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

data class ArticleInfo(
    val curPage: Int,
    val datas: List<ArticleData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

@Entity
data class ArticleData(
    @PrimaryKey
    var id: Int,
    var audit: Int,
    var author: String,
    var chapterId: Int,
    var chapterName: String,
    var courseId: Int,
    var desc: String,
    var link: String,
    var niceDate: String,
    var niceShareDate: String,
    var tags: List<Tag>,
    var title: String,
)

data class Tag(
    var name: String,
    var url: String
)