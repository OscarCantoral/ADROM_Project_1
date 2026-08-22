package com.example.ch3mxr.di

import com.example.ch3mxr.data.remote.OAuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Named
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    private const val BASE_URL = "http://10.0.2.2:8080/"
    private const val CLIENT_ID = "chemxr-client"
    private const val CLIENT_SECRET = "bonjour"

    @Provides
    @Singleton
    @Named("OAuthClientId")
    fun provideOAuthClientId(): String {
        return CLIENT_ID
    }

    @Provides
    @Singleton
    @Named("OAuthClientSecret")
    fun provideOAuthClientSecret(): String {
        return CLIENT_SECRET
    }

    @Provides
    @Singleton
    @Named("OAuthOkHttp")
    fun provideOAuthOkHttpClient(
        @Named("OAuthClientId")
        clientId: String,

        @Named("OAuthClientSecret")
        clientSecret: String
    ): OkHttpClient {

        val credentials = Credentials.basic(
            clientId,
            clientSecret
        )

        return OkHttpClient.Builder()
            .addInterceptor { chain ->

                val request = chain.request()
                    .newBuilder()
                    .header(
                        "Authorization",
                        credentials
                    )
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    @Named("OAuthRetrofit")
    fun provideOAuthRetrofit(
        @Named("OAuthOkHttp")
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideOAuthApi(
        @Named("OAuthRetrofit")
        retrofit: Retrofit
    ): OAuthApi {
        return retrofit.create(OAuthApi::class.java)
    }

}