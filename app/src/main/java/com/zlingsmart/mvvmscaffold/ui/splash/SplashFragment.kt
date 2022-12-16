package com.zlingsmart.mvvmscaffold.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zlingsmart.mvvmscaffold.R
import com.zlingsmart.mvvmscaffold.databinding.FragmentFullscreenBinding
import com.zlingsmart.mvvmscaffold.util.nav
import com.zlingsmart.mvvmscaffold.util.navigateAction

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFullscreenBinding>(inflater,
            R.layout.fragment_fullscreen,null,false)
        binding.jump = View.OnClickListener { nav().navigateAction(R.id.action_fullscreenFragment_to_articlesFragment) }
        return binding.root
    }

}