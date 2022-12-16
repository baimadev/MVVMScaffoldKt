package com.zlingsmart.mvvmscaffold.ui.article

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zlingsmart.model.ArticleData
import com.zlingsmart.mvvmscaffold.databinding.ItemArticleBinding

class ArticleListAdapter() : BaseQuickAdapter<ArticleData, ArticleListAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(val vb: ItemArticleBinding): RecyclerView.ViewHolder(vb.root)

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int, item: ArticleData?) {
        item?.let {
            holder.vb.tvAuthor.text = it.author
            holder.vb.tvTitle.text = it.title
            holder.vb.tvDate.text = it.niceShareDate
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val vb = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(vb)
    }
}