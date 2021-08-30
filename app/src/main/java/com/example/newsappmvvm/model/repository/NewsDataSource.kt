package com.example.newsappmvvm.model.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappmvvm.model.NetworkConstants
import com.example.newsappmvvm.model.models.ArticlesResponse
import com.example.newsappmvvm.retrofit.NewsApiService
import retrofit2.HttpException
import java.io.IOException


const val PAGE_INITIAL=1

class NewsDataSource(
    private val apiService: NewsApiService): PagingSource<Int, ArticlesResponse.Article>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesResponse.Article> {
        val position = params.key?:PAGE_INITIAL
        return try {
            val response =
                apiService.getNewsTopHeadlines(page = position, pageSize =params.loadSize,
                    country = NetworkConstants.COUNTRY_KEY,
                apiKey =NetworkConstants.API_KEY )
            val articles =response.articles
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
    override fun getRefreshKey(state: PagingState<Int, ArticlesResponse.Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}