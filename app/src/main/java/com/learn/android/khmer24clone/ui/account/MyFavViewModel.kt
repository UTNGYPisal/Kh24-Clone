package com.learn.android.khmer24clone.ui.account

import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.repo.ProductRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class MyFavViewModel : BaseViewModel(){

    fun getFavorites() = liveData {
        emit(UnhandledResult.Loading)
        val response = ProductRepo.getFavorites()
        emit(response)
    }
}