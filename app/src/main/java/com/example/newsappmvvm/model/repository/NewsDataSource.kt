package com.example.newsappmvvm.model.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappmvvm.model.NetworkConstants
import com.example.newsappmvvm.model.db.NewsArticlesDao
import com.example.newsappmvvm.model.models.Article
import com.example.newsappmvvm.model.models.ArticlesResponse
import com.example.newsappmvvm.retrofit.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


const val PAGE_INITIAL=1

class NewsDataSource(
    private val apiService: NewsApiService,private val newsArticlesDao: NewsArticlesDao):
    PagingSource<Int, Article>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key?:PAGE_INITIAL
        return try {
            val response =
                apiService.getNewsTopHeadlines(page = position, pageSize =params.loadSize,
                    country = NetworkConstants.COUNTRY_KEY,
                apiKey =NetworkConstants.API_KEY )
            val articles =response.articles
            for(item in articles) {
                if (item.urlToImage == null) {
                    item.urlToImage = ""
                }
                if (item.content == null) {
                    item.content = ""
                }
                if(item.author==null){
                    item.author=""
                }
                newsArticlesDao.insertNewsItem(item)
            }
            return LoadResult.Page(
                data=articles,
                prevKey = if(position== PAGE_INITIAL)null else position-1,
                nextKey = if(articles.isEmpty())  null else position+1
            )
        }catch (e:IOException){
             LoadResult.Error(e)
        }
        catch (e:HttpException){
             LoadResult.Error(e)
        }

    }


    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}