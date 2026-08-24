package com.example.ch3mxr.di

import com.example.ch3mxr.data.remote.AuthApiService
import com.example.ch3mxr.data.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

//    private const val BASE_URL = "http://localhost:8080/"
    private const val BASE_URL = "http://192.168.1.42:8080/"

    private const val CLIENT_ID = "chemxr-client"
    private const val CLIENT_SECRET = "bonjour"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(
        retrofit: Retrofit
    ): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(authApiService: AuthApiService): RemoteRepository{
        return RemoteRepository(authApiService,Credentials.basic(
            CLIENT_ID,
            CLIENT_SECRET
        ))
    }
}