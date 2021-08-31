package com.example.newsappmvvm.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsappmvvm.model.models.Article

@Database(entities = [Article::class],version = 1)
abstract class NewsDatabase :RoomDatabase(){

    abstract fun newsDao() : NewsArticlesDao

}