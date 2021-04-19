package com.juan.bravoTest.services

import android.content.Context
import android.util.Log
import com.google.gson.FieldNamingPolicy
import com.google.gson.FieldNamingStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.juan.bravoTest.BuildConfig
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

open class BaseService() {
    private val gsonConfig: Gson
        get() {
            return GsonBuilder()
                .setLenient()
                .disableHtmlEscaping()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd").create()
        }

    protected var retrofit: Retrofit
        protected set

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonConfig))
            .client(getHttpClient())
            .build()

    }

    private fun getHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .followRedirects(false)
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())

        return client.build()
    }
}

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request  = chain.request()
        var token = BuildConfig.API_TOKEN
        var builder: Request.Builder = request.newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization" ,"Bearer $token")

        val authenticatedRequest = builder.build()

        return chain.proceed(authenticatedRequest)
    }
}
