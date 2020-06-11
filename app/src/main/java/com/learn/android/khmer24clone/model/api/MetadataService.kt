package com.learn.android.khmer24clone.model.api

import androidx.annotation.WorkerThread
import com.learn.android.khmer24clone.common.C
import com.learn.android.khmer24clone.model.entity.Category
import com.learn.android.khmer24clone.model.entity.Notification
import com.learn.android.khmer24clone.model.entity.Product
import com.learn.android.khmer24clone.model.entity.Slide
import retrofit2.http.Body
import retrofit2.http.POST

interface MetadataService {

    @WorkerThread
    @POST(C.Endpoints.getSlides)
    suspend fun getSlides(): BaseResponse<ArrayList<Slide>>

    @WorkerThread
    @POST(C.Endpoints.getCategories)
    suspend fun getCategories(): BaseResponse<ArrayList<Category>>

    @WorkerThread
    @POST(C.Endpoints.getNotifications)
    suspend fun getNotifications(): BaseResponse<ArrayList<Notification>>
}