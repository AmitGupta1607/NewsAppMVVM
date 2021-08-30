package com.example.newsappmvvm.model.repository

import androidx.paging.*
import com.example.newsappmvvm.model.NetworkConstants
import com.example.newsappmvvm.model.NetworkConstants.PAGE_SIZE
import com.example.newsappmvvm.model.models.ArticlesResponse
import com.example.newsappmvvm.retrofit.NewsApiService
import kotlinx.coroutines.flow.Flow


class NewsRepository(private val apiService: NewsApiService):INewsRepository {

    override fun fetchNews(): Flow<PagingData<ArticlesResponse.Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {NewsDataSource(apiService)}
        ).flow
    }

    override suspend fun fetchHeadlines(): ArrayList<ArticlesResponse.Article> {
       val response = apiService.getNewsTopHeadlines(apiKey = NetworkConstants.API_KEY,
        pageSize = 20,page = 1,country = "us")
        return response.articles
    }



}