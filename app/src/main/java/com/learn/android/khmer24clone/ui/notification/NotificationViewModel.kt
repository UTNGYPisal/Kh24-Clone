package com.learn.android.khmer24clone.ui.notification

import androidx.lifecycle.liveData
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.repo.MetadataRepo
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class NotificationViewModel : BaseViewModel(){

    fun fetchNotifications() = liveData {
        emit(UnhandledResult.Loading)
        val response = MetadataRepo.getNotifications()
        emit(response)
    }
}