package com.github.topnax.nbadatabasemobile.di

import com.github.topnax.nbadatabasemobile.BuildConfig
import com.github.topnax.nbadatabasemobile.balldontlieapi.BalldontlieApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    single {
        // API Key
        val apiKey = BuildConfig.BALLDONTLIE_API_KEY

        require(apiKey.isNotBlank()) { "balldontlie API key is missing" }

        // Create an interceptor to add the Authorization header
        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithAuth = originalRequest.newBuilder()
                .header("Authorization", apiKey)
                .build()
            chain.proceed(requestWithAuth)
        }

        // Build OkHttpClient with interceptors
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    single {
        // Configure Kotlinx Serialization
        Json {
            ignoreUnknownKeys = true
        }
    }

    single {
        // Build Retrofit instance
        Retrofit.Builder()
            .baseUrl("https://api.balldontlie.io/v1/")
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<BalldontlieApi> {
        // Create BalldontlieApi implementation
        get<Retrofit>().create(BalldontlieApi::class.java)
    }
}