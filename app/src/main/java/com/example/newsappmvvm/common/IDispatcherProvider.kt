package com.example.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface IDispatcherProvider {

   fun mainDispatcher():CoroutineDispatcher
   fun ioDispatcher():CoroutineDispatcher
   fun defaultDispatcher():CoroutineDispatcher
}