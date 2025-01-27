package com.spapalamprou.turtlesafe.data.interceptors

import com.spapalamprou.turtlesafe.data.storage.TokenStorage
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * An interceptor for adding an access token to HTTP requests.
 *
 * @property storage An instance of TokenStorage used to retrieve the access token.
 */
class AccessTokenInterceptor @Inject constructor(
    private val storage: TokenStorage
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = storage.getAccessToken()
        val request = chain.request().newBuilder()
        if (!chain.request().url.toString().contains("auth")) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }
        return chain.proceed(request.build())
    }
}