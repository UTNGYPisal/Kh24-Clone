package com.learn.android.khmer24clone.common

import com.learn.android.khmer24clone.BuildConfig

object C {
    object Endpoints {
        const val getSlides = BuildConfig.BASE_URL + BuildConfig.UNAUTH_API_PATH + "/slides"
        const val getCategories = BuildConfig.BASE_URL + BuildConfig.UNAUTH_API_PATH + "/categories"
        const val getProducts = BuildConfig.BASE_URL + BuildConfig.UNAUTH_API_PATH + "/products"
        const val searchProducts = BuildConfig.BASE_URL + BuildConfig.UNAUTH_API_PATH + "/search"
        const val getProductDetail = BuildConfig.BASE_URL + BuildConfig.UNAUTH_API_PATH + "/product-detail/{id}"
        const val getNotifications = BuildConfig.BASE_URL + BuildConfig.UNAUTH_API_PATH + "/notifications"

        const val authenticate = BuildConfig.BASE_URL + BuildConfig.BASIC_API_PATH + "/authenticate"

        const val toggleFavorite = BuildConfig.BASE_URL + BuildConfig.API_PATH + "/toggle-favorite"
    }


    object Security {
        const val sslPinningEnabled = false
    }
}