package com.example.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl :IDispatcherProvider{

    override fun mainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    override fun ioDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    override fun defaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }


}