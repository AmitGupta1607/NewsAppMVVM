package com.example.newsappmvvm.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappmvvm.model.models.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsItem(article: Article)

    @Query("SELECT * FROM article")
     fun fetchAllArticles():Flow<List<Article>>

    @Query("DELETE FROM article")
    suspend fun deleteAllNewsItems()

    @Query("SELECT COUNT(*) FROM article")
    suspend fun getCount():Int


}