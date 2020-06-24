package com.learn.android.khmer24clone.model.repo

import com.learn.android.khmer24clone.model.api.*
import com.learn.android.khmer24clone.model.entity.User
import org.koin.java.KoinJavaComponent.get

object AuthRepo {

    private val authService = get(AuthService::class.java)

    suspend fun authenticate(
        idToken: String,
        name: String? = null,
        imageUrl: String? = null
    ): UnhandledResult<BaseResponse<User>> {
        return safeApiCall {
            authService.authenticate(
                buildParams(
                    "id_token" to idToken,
                    "name" to (name ?: ""),
                    "social_image_url" to (imageUrl ?: "")
                )
            )
        }
    }
}