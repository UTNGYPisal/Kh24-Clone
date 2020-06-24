package com.learn.android.khmer24clone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.model.api.BaseResponse
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.User
import com.learn.android.khmer24clone.model.repo.AuthRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val isLoggedIn = MutableLiveData<Boolean>().apply {
        value = User.isLoggedIn
    }

    fun authenticate(
        idToken: String,
        name: String? = null,
        imageUrl: String? = null
    ) = liveData {
        emit(UnhandledResult.Loading)
        val response = AuthRepo.authenticate(idToken, name, imageUrl)
        emit(response)
    }

    fun handleServerLoginSuccess(response: BaseResponse<User>) {
        printLog("Login at server success: ${response.result}")
        printLog("Token: ${response.token}")

        User.current = response.result!!.copy(token = response.token)
        isLoggedIn.value = true
    }
}