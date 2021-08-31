package com.example.newsappmvvm.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsappmvvm.model.db.DbConstants
import com.example.newsappmvvm.model.db.NewsArticlesDao
import com.example.newsappmvvm.model.db.NewsDatabase
import com.example.newsappmvvm.model.models.Article
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class LocalDbModule {

    @Provides
    fun providesRoomDb(@ApplicationContext context:Context)
             =  Room.databaseBuilder(context,NewsDatabase::class.java,
            DbConstants.DB_NAME)
            .build();



    @Provides
    fun providesDao(newsDatabase: NewsDatabase) =
        newsDatabase.newsDao();
}