package com.learn.android.khmer24clone.model.repo

import com.learn.android.khmer24clone.model.api.*
import com.learn.android.khmer24clone.model.entity.Category
import com.learn.android.khmer24clone.model.entity.Notification
import com.learn.android.khmer24clone.model.entity.Product
import com.learn.android.khmer24clone.model.entity.Slide
import org.koin.java.KoinJavaComponent.get

object MetadataRepo {

    private val metadataService = get(MetadataService::class.java)

    suspend fun getAllSlides(): UnhandledResult<BaseResponse<ArrayList<Slide>>> {
        return safeApiCall {
            metadataService.getSlides()
        }
    }

    suspend fun getAllCategories(): UnhandledResult<BaseResponse<ArrayList<Category>>> {
        return safeApiCall {
            metadataService.getCategories()
        }
    }

    suspend fun getNotifications(): UnhandledResult<BaseResponse<ArrayList<Notification>>> {
        return safeApiCall {
            metadataService.getNotifications()
        }
    }
}