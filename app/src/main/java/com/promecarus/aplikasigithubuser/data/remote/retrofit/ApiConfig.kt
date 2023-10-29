package com.promecarus.aplikasigithubuser.data.remote.retrofit

import com.promecarus.aplikasigithubuser.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(
                OkHttpClient.Builder()

                    .addInterceptor(Interceptor {
                    it.proceed(
                        it.request().newBuilder().addHeader(
                            "Authorization", "token ${BuildConfig.GITHUB_API_KEY}"
                        ).build()
                    )
                })

                    .addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    )
                ).build()
            ).build().create(ApiService::class.java)
    }
}
