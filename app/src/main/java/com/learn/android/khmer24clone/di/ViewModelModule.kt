package com.learn.android.khmer24clone.di

import com.learn.android.khmer24clone.MainViewModel
import com.learn.android.khmer24clone.ui.account.MyFavViewModel
import com.learn.android.khmer24clone.ui.auth.LoginViewModel
import com.learn.android.khmer24clone.ui.category.sub.SubCategoryViewModel
import com.learn.android.khmer24clone.ui.notification.NotificationViewModel
import com.learn.android.khmer24clone.ui.post.PostViewModel
import com.learn.android.khmer24clone.ui.product.detail.ProductDetailViewModel
import com.learn.android.khmer24clone.ui.product.list.ProductListViewModel
import com.learn.android.khmer24clone.ui.search.SearchViewModel
import org.koin.dsl.module

val viewModelModules = module {
    single { SubCategoryViewModel() }
    single { ProductListViewModel() }
    single { ProductDetailViewModel() }
    single { SearchViewModel() }
    single { NotificationViewModel() }

    single { LoginViewModel() }
    single { MainViewModel() }
    single { MyFavViewModel() }
    single { PostViewModel() }
}