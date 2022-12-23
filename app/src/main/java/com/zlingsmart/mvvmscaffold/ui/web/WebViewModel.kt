package com.zlingsmart.mvvmscaffold.ui.web

import com.zlingsmart.base.viewmodel.BaseViewModel


class WebViewModel : BaseViewModel() {

    //是否收藏
    var collect = false

    //收藏的Id
    var ariticleId = 0

    //标题
    var showTitle: String = ""

    //文章的网络访问路径
    var url: String = ""
}