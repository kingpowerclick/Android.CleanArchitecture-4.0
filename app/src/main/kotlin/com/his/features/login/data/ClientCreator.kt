package com.his.features.login.data

import android.content.Context
import com.his.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientCreator @Inject constructor(private val mContext: Context) {
	open fun createHttpClient(): OkHttpClient {
		val okHttpClientBuilder = OkHttpClient.Builder()

		okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)

		okHttpClientBuilder.addInterceptor { chain ->
			val originalRequest = chain.request()
			val request = originalRequest.newBuilder()
				.header("Accept", "application/json")
				.method(originalRequest.method(), originalRequest.body())

			chain.proceed(request.build())
		}


		// Add Http Logging for debug mode
		if (BuildConfig.DEBUG) {
			val interceptor = HttpLoggingInterceptor()
			interceptor.level = HttpLoggingInterceptor.Level.BODY
			okHttpClientBuilder.addInterceptor(interceptor)
		}

		return okHttpClientBuilder.build()
	}
}