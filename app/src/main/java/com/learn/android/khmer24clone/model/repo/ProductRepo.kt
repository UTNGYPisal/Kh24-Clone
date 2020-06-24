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
}