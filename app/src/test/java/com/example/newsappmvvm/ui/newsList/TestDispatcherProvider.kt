package com.example.newsappmvvm.ui.newsList

import com.example.common.IDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : IDispatcherProvider {
    override fun mainDispatcher(): CoroutineDispatcher {
        return TestCoroutineDispatcher()
    }

    override fun ioDispatcher(): CoroutineDispatcher {
       return TestCoroutineDispatcher()
    }

    override fun defaultDispatcher(): CoroutineDispatcher {
       return TestCoroutineDispatcher()
    }


}