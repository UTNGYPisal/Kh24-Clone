package com.learn.android.khmer24clone.ui.base

import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.learn.android.khmer24clone.MainViewModel
import com.learn.android.khmer24clone.common.extension.runOnUiThread
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

open class BaseFragment(@LayoutRes val layoutResId: Int) : Fragment(layoutResId){

    val mainViewModel by inject<MainViewModel>()


    fun showLoading() {
        runOnUiThread {
            requireActivity().run {
                blurView.isVisible = true
                mainLoadingView.isVisible = true
            }
        }
    }

    fun hideLoading(){
        runOnUiThread {
            requireActivity().run {
                blurView.isVisible = false
                mainLoadingView.isVisible = false
            }
        }
    }
}