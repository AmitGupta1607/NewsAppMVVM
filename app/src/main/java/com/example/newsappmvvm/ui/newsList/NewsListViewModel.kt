package com.example.newsappmvvm.ui.newsList

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappmvvm.model.models.ArticlesResponse
import com.example.newsappmvvm.model.repository.INewsRepository
import com.example.newsappmvvm.model.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(val repository: INewsRepository):ViewModel() {

    private val _news = MutableLiveData<PagingData<ArticlesResponse.Article>>()
    val newsData get()=_news as LiveData<PagingData<ArticlesResponse.Article>>

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            repository.fetchNews().cachedIn(viewModelScope).collectLatest {
                _news.postValue(it)
            }
        }
    }






}