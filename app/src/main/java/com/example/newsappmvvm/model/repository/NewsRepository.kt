package com.example.newsappmvvm.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.newsappmvvm.model.NetworkConstants
import com.example.newsappmvvm.model.NetworkConstants.PAGE_SIZE
import com.example.newsappmvvm.model.db.NewsArticlesDao
import com.example.newsappmvvm.model.models.Article
import com.example.newsappmvvm.model.models.ArticlesResponse
import com.example.newsappmvvm.retrofit.NewsApiService
import kotlinx.coroutines.flow.Flow


class NewsRepository(private val apiService: NewsApiService,
                     private val newsDao:NewsArticlesDao):INewsRepository {

    override fun fetchNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {NewsDataSource(apiService,newsDao)}
        ).flow
    }


    override suspend fun fetchNewsFromDb(): List<Article>{
        return newsDao.fetchAllArticles()
    }



}