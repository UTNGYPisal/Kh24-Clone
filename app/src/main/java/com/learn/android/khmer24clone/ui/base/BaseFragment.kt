package com.learn.android.khmer24clone.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.learn.android.khmer24clone.MainViewModel
import org.koin.android.ext.android.inject

open class BaseFragment(@LayoutRes val layoutResId: Int) : Fragment(layoutResId){

    val mainViewModel by inject<MainViewModel>()

}