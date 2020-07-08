package com.learn.android.khmer24clone.ui.post

import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.repo.ProductRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class PostViewModel : BaseViewModel(){

    fun postProduct(
        categoryId: Int,
        provinceId: Int,
        title: String,
        price: Double,
        shortDescription: String? = null,
        images: String? = null
    ) = liveData {
        emit(UnhandledResult.Loading)
        val response = ProductRepo.postProduct(
            categoryId,
            provinceId,
            title,
            price,
            shortDescription,
            images
        )
        emit(response)
    }
}