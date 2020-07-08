import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.common.C
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.model.entity.User
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ServiceFactory {

    fun provideCertificatePinner(): CertificatePinner {
        val certificatePinnerBuilder: CertificatePinner.Builder = CertificatePinner.Builder()
        certificatePinnerBuilder.add(BuildConfig.DOMAIN_NAME, BuildConfig.CERT_HASH)

        return certificatePinnerBuilder.build()
    }

    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().apply {
                    this.addHeader("Accept", "application/json")
                    printLog("Request Url: ${chain.request().url}")
                    if (User.isLoggedIn) {
                        this.addHeader("Authorization", "Bearer ${User.current!!.token!!}")
                    } else {
                        //this.addHeader("Authorization", "Basic ${Constants.Security.basicHttpToken}")
                    }
                }.build()
            )
        }
    }

    fun provideHttpClient(certificatePinner: CertificatePinner, headerInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(120, TimeUnit.SECONDS)
            readTimeout(120, TimeUnit.SECONDS)
            writeTimeout(120, TimeUnit.SECONDS)
            addInterceptor(headerInterceptor)

            /**
             * DO NOT USE THIS IN PRODUCTION
             */
            if (!C.Security.sslPinningEnabled) {
                ignoreAllSSLErrors()
            } else {
                certificatePinner(certificatePinner)
            }

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
        }.build()

    }

    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun <T> provideService(retrofit: Retrofit, serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }


    private fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier(HostnameVerifier { _, _ -> true })
        return this
    }
}
