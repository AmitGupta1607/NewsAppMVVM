package com.example.newsappmvvm.di

import com.example.common.DispatcherProviderImpl
import com.example.common.IDispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DispatchersModule {

    @Provides
    fun dispatcherProvider():IDispatcherProvider{
        return DispatcherProviderImpl()
    }
}