package com.okifirsyah.githubmate.utils.interceptor.network

import com.okifirsyah.githubmate.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

class HeaderInterceptor(private val requestHeaders: HashMap<String, String>) : Interceptor {

//    private val storage: StoragePreference by KoinJavaComponent.inject(StoragePreference::class.java)

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.toString()
        Timber.tag("HeaderInterceptor").d("<<<<<<<<< $path")

        if (path.contains("logout")) {
            mapRequestHeaders()
            Timber.tag("HeaderInterceptor").d("<<<<<<<<< $requestHeaders")
        } else {
            mapRequestHeaders()
        }

        val request = mapHeaders(chain)

        return chain.proceed(request)
    }

    private fun mapRequestHeaders() {
        Timber.tag("HeaderInterceptor").d("<<<<<<<<< Before : $requestHeaders")
        val token = BuildConfig.TOKEN
        requestHeaders["Authorization"] = "token $token"
        Timber.tag("HeaderInterceptor").d("Token : $token")

        Timber.tag("HeaderInterceptor").d("<<<<<<<<< After : $requestHeaders")
    }


    private fun mapHeaders(chain: Interceptor.Chain): Request {
        val original = chain.request()

        val requestBuilder = original.newBuilder()

        for ((key, value) in requestHeaders) {
            requestBuilder.addHeader(key, value)
        }
        return requestBuilder.build()
    }
}
