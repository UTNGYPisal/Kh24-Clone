package com.learn.android.khmer24clone.ui.home

import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.repo.MetadataRepo
import com.learn.android.khmer24clone.model.repo.ProductRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    fun fetchSlides() = liveData {
        val response = MetadataRepo.getAllSlides()
        emit(response)
    }

    fun fetchCategories() = liveData {
        val response = MetadataRepo.getAllCategories()
        emit(response)
    }

    fun fetchProducts(
        categoryId: Int? = null
    ) = liveData {
        val response = ProductRepo.getProducts(categoryId)
        emit(response)
    }
}