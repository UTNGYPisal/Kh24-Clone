package com.learn.android.khmer24clone.ui.product.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.Category
import com.learn.android.khmer24clone.model.repo.MetadataRepo
import com.learn.android.khmer24clone.model.repo.ProductRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class ProductListViewModel : BaseViewModel(){

    val category = MutableLiveData<Category?>()

    fun fetchProducts(categoryId: Int? = null) = liveData {
        emit(UnhandledResult.Loading)
        val response = ProductRepo.getProducts(categoryId)
        emit(response)
    }

    fun toggleFavorite(productId: Int) = liveData {
        emit(UnhandledResult.Loading)
        val response = ProductRepo.toggleFavorite(productId)
        emit(response)
    }

}