package com.example.newsappmvvm.retrofit

import com.example.newsappmvvm.model.models.ArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNewsTopHeadlines(
        @Query("country")country:String,
        @Query("apiKey")apiKey:String,
        @Query("pageSize")pageSize:Int,
        @Query("page")page:Int

    ):ArticlesResponse
}