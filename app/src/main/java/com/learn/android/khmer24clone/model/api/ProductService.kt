package com.learn.android.khmer24clone.model.api

import androidx.annotation.WorkerThread
import com.learn.android.khmer24clone.common.C
import com.learn.android.khmer24clone.model.entity.Product
import okhttp3.MultipartBody
import retrofit2.http.*

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


    @WorkerThread
    @POST(C.Endpoints.toggleFavorite)
    suspend fun toggleFavorite(
        @Body() body: HashMap<String, Any>
    ): BaseResponse<Any>

    @WorkerThread
    @POST(C.Endpoints.getFavorites)
    suspend fun getFavorites(
        @Body() body: HashMap<String, Any>
    ): BaseResponse<ArrayList<Product>>

    @WorkerThread
    @POST(C.Endpoints.postProduct)
    suspend fun postProduct(
        @Body() body: HashMap<String, Any>
    ): BaseResponse<Product>

    @WorkerThread
    @POST(C.Endpoints.uploadProductImage)
    @Multipart
    suspend fun uploadProductImage(
        @Part() image: MultipartBody
    ): BaseResponse<Product>
}