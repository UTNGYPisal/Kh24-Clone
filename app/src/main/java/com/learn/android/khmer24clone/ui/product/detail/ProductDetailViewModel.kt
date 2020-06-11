package com.learn.android.khmer24clone.ui.product.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.Product
import com.learn.android.khmer24clone.model.repo.ProductRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class ProductDetailViewModel: BaseViewModel(){
    val product = MutableLiveData<Product?>()
    val productId = MutableLiveData<Int?>()

    fun getProductDetail(id: Int) = liveData {
        emit(UnhandledResult.Loading)
        val response = ProductRepo.getProductDetail(id)
        emit(response)
    }
}