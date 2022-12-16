package com.zlingsmart.mvvmscaffold.ui.article

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zlingsmart.base.fragment.BaseVmDbFragment
import com.zlingsmart.mvvmscaffold.R
import com.zlingsmart.mvvmscaffold.databinding.FragmentArticlesBinding
import com.zlingsmart.mvvmscaffold.util.init
import com.zlingsmart.mvvmscaffold.util.nav
import com.zlingsmart.mvvmscaffold.util.navigateAction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.include_toolbar.*

@AndroidEntryPoint
class ArticlesFragment : BaseVmDbFragment<ArticlesViewModel, FragmentArticlesBinding>() {

    companion object {
        fun newInstance() = ArticlesFragment()
    }

    private val adapter: ArticleListAdapter = ArticleListAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        binding {
            rvArticle.layoutManager = LinearLayoutManager(context)
            adapter.setItemAnimation(BaseQuickAdapter.AnimationType.ScaleIn)
            adapter.setOnItemClickListener{adapter, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    //putParcelable("ariticleData", adapter.items[position])
                    //putParcelable("ariticleData", )
                    putString("title",adapter.items[position].title)
                    putString("url",adapter.items[position].link)
                })
            }
            rvArticle.adapter = adapter
            vm = mViewModel
        }
        toolbar.init("文章列表")
    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {
        mViewModel.articleInfoData.observe(this){
            adapter.submitList(it)
        }
    }

    override fun showLoading(isShow: Boolean) {
        mDatabind.progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onStop() {
        super.onStop()
        mViewModel.saveArticleData()
    }
}