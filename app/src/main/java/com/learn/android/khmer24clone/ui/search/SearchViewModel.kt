package com.learn.android.khmer24clone.ui.search

import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.repo.ProductRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class SearchViewModel : BaseViewModel(){

    fun searchProductsByKeyword(keyword: String) = liveData {
        emit(UnhandledResult.Loading)
        val response = ProductRepo.searchProductsByKeyword(keyword)
        emit(response)
    }
}