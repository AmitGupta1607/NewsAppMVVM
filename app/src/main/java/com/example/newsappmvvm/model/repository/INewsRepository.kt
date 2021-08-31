package com.example.newsappmvvm.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.newsappmvvm.model.models.Article
import com.example.newsappmvvm.model.models.ArticlesResponse
import kotlinx.coroutines.flow.Flow

interface INewsRepository{

     fun fetchNews():Flow<PagingData<Article>>
     suspend fun fetchNewsFromDb():List<Article>
}