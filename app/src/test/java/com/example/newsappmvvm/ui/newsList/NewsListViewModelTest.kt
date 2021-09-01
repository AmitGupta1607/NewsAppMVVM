package com.example.newsappmvvm.ui.newsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.common.DispatcherProviderImpl
import com.example.newsappmvvm.model.NetworkConstants
import com.example.newsappmvvm.model.db.NewsArticlesDao
import com.example.newsappmvvm.model.models.Article
import com.example.newsappmvvm.model.repository.INewsRepository
import com.example.newsappmvvm.model.repository.NewsDataSource
import com.example.newsappmvvm.retrofit.NewsApiService
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Flow
import androidx.paging.PagingData as PagingData
import com.google.common.truth.Truth.assertThat
import dagger.Component
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class NewsListViewModelTest{
    lateinit var viewModel:NewsListViewModel

    @ExperimentalCoroutinesApi
    val dispatcher= TestDispatcherProvider()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    var rule =InstantTaskExecutorRule()

    @Mock
    lateinit var iNewsRepository:INewsRepository


    @Mock
    lateinit var apiService:NewsApiService

    @Mock
    lateinit var newsDao:NewsArticlesDao

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel = NewsListViewModel(iNewsRepository,dispatcher)
    }

    @Test
    fun load(){
        testCoroutineRule.runBlockingTest {
            val pager = Pager(
                config = PagingConfig(
                    pageSize = NetworkConstants.PAGE_SIZE,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { NewsDataSource(apiService, newsDao) }
            ).flow
            Mockito.`when`(iNewsRepository.fetchNews()).thenReturn(pager)
            viewModel.fetchNews()

            val value = viewModel.newsData.getOrAwaitValueTest()
            assertThat(value).isNotNull()
            assertThat(value).isInstanceOf(PagingData::class.java)
        }
    }

    @Test
    fun noInternet(){
        testCoroutineRule.runBlockingTest{
        val list = ArrayList<Article>()

        val article = Article("title of article",
            "www.image.com","content")
        list.add(article)

        val flowListOfArticle = flowOf(list)
        Mockito.`when`(iNewsRepository.fetchNewsFromDb()).thenReturn(flowListOfArticle)
        viewModel.fetchNewsFromDb()
        val liveDataValue = viewModel.newsFromDb.getOrAwaitValueTest()
         assertThat(liveDataValue).isEqualTo(list)
        }

    }






}