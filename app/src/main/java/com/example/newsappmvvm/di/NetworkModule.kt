package com.example.newsappmvvm.di

import com.example.newsappmvvm.model.NetworkConstants
import com.example.newsappmvvm.model.db.NewsArticlesDao
import com.example.newsappmvvm.model.repository.INewsRepository
import com.example.newsappmvvm.model.repository.NewsRepository
import com.example.newsappmvvm.retrofit.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {



    @Provides
    fun providesRetrofit():NewsApiService{
        return Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    @Provides
    fun providesNewsRepositoryModule(apiService: NewsApiService,newsDao:NewsArticlesDao):INewsRepository{
        return NewsRepository(apiService,newsDao)
    }
}