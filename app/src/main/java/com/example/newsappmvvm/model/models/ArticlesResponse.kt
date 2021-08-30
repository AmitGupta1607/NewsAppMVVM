package com.example.newsappmvvm.model.models

data class ArticlesResponse(val articles:ArrayList<Article>){

    data class Article(val title:String, val description:String,
                      val urlToImage:String,val content:String)

}