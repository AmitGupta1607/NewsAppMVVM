package com.example.newsappmvvm.ui.newsList

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.IDispatcherProvider
import com.example.newsappmvvm.model.models.Article
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
class NewsListViewModel @Inject constructor(private val repository: INewsRepository,
                                            private val dispatcher:IDispatcherProvider):ViewModel() {

    private val _news = MutableLiveData<PagingData<Article>>()
    private val _newsFromDb = MutableLiveData<List<Article>>()
    val newsData get()=_news as LiveData<PagingData<Article>>
    val newsFromDb get()=  _newsFromDb as LiveData<List<Article>>

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch(dispatcher.mainDispatcher()) {
           repository.fetchNews().cachedIn(viewModelScope).collectLatest {
                _news.postValue(it)
            }
        }
    }

    fun fetchNewsFromDb(){
        viewModelScope.launch(dispatcher.ioDispatcher()) {
            repository.fetchNewsFromDb().collectLatest {
               _newsFromDb.postValue(it)
            }

        }
    }






}