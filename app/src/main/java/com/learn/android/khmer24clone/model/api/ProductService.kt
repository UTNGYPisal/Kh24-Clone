package com.learn.android.khmer24clone.model.api

import androidx.annotation.WorkerThread
import com.learn.android.khmer24clone.common.C
import com.learn.android.khmer24clone.model.entity.Product
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductService {
    @WorkerThread
    @POST(C.Endpoints.getProducts)
    suspend fun getProducts(
        @Body() body: HashMap<String, Any>
    ): BaseResponse<ArrayList<Product>>

    @WorkerThread
    @POST(C.Endpoints.searchProducts)
    suspend fun searchProductsByKeyword(
        @Body() body: HashMap<String, Any>
    ): BaseResponse<ArrayList<Product>>

    @WorkerThread
    @POST(C.Endpoints.getProductDetail)
    suspend fun getProductDetail(
        @Path("id") id: Int,
        @Body() body: HashMap<String, Any>
    ): BaseResponse<Product>
}