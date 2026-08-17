package com.example.ch3mxr.di

import android.content.Context
import com.example.ch3mxr.ui.manager.FacebookAuthManager
import com.example.ch3mxr.ui.manager.GoogleAuthManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthManagerModule {

    @Provides
    @Singleton
    fun provideGoogleAuthManager(@ApplicationContext context: Context): GoogleAuthManager {
        return GoogleAuthManager(context)
    }

    @Provides
    @Singleton
    fun provideFacebookAuthManager(): FacebookAuthManager{
        return FacebookAuthManager()
    }
}