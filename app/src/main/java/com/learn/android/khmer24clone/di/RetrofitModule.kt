package com.learn.android.khmer24clone.di

import com.learn.android.khmer24clone.model.api.MetadataService
import com.learn.android.khmer24clone.model.api.ProductService
import org.koin.dsl.module

val retrofitModule = module {
    factory {
        ServiceFactory.provideHeaderInterceptor()
    }
    single { ServiceFactory.provideCertificatePinner() }
    single { ServiceFactory.provideHttpClient(get(), get()) }
    single { ServiceFactory.provideRetrofit(get()) }
    single {
        ServiceFactory.provideService(get(), MetadataService::class.java)
    }
    single {
        ServiceFactory.provideService(get(), ProductService::class.java)
    }
}