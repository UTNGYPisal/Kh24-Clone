package com.learn.android.khmer24clone.model.repo

import com.learn.android.khmer24clone.model.api.*
import com.learn.android.khmer24clone.model.entity.Product
import org.koin.java.KoinJavaComponent.get

object ProductRepo {
    private val productService = get(ProductService::class.java)

    suspend fun getProducts(
        categoryId: Int? = null
    ): UnhandledResult<BaseResponse<ArrayList<Product>>> {
        return safeApiCall {
            productService.getProducts(
                buildParams(
                    "category_id" to (categoryId ?: 0)
                )
            )
        }
    }

    suspend fun searchProductsByKeyword(
        keyword: String? = null
    ): UnhandledResult<BaseResponse<ArrayList<Product>>> {
        return safeApiCall {
            productService.searchProductsByKeyword(
                buildParams(
                    "keyword" to (keyword ?: "")
                )
            )
        }
    }

    suspend fun getProductDetail(
        id: Int
    ): UnhandledResult<BaseResponse<Product>> {
        return safeApiCall {
            productService.getProductDetail(
                id,
                buildParams()
            )
        }
    }

    suspend fun toggleFavorite(
        productId: Int
    ): UnhandledResult<BaseResponse<Any>> {
        return safeApiCall {
            productService.toggleFavorite(
                buildParams(
                    "product_id" to productId
                )
            )
        }
    }

    suspend fun getFavorites(): UnhandledResult<BaseResponse<ArrayList<Product>>> {
        return safeApiCall {
            productService.getFavorites(
                buildParams()
            )
        }
    }

    /**
     * @param images in form of "https://www.kh24.com/images/dlsfgkg.png,https://www.kh24.com/images/dlsfgkg.png"
     */
    suspend fun postProduct(
        categoryId: Int,
        provinceId: Int,
        title: String,
        price: Double,
        shortDescription: String? = null,
        images: String? = null
    ): UnhandledResult<BaseResponse<Product>> {
        return safeApiCall {
            productService.postProduct(
                buildParams(
                    "category_id" to categoryId,
                    "province_id" to provinceId,
                    "title" to title,
                    "price" to price,
                    "short_description" to (shortDescription ?: ""),
                    "images" to (images ?: "")
                )
            )
        }
    }
}