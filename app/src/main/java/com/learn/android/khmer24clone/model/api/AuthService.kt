package com.learn.android.khmer24clone.model.api

import androidx.annotation.WorkerThread
import com.learn.android.khmer24clone.common.C
import com.learn.android.khmer24clone.model.entity.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @WorkerThread
    @POST(C.Endpoints.authenticate)
    suspend fun authenticate(@Body() body: HashMap<String, Any>): BaseResponse<User>
}