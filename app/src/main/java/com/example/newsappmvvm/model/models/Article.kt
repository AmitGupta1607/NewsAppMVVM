package com.example.newsappmvvm.model.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "article")
data class Article(@PrimaryKey val title:String="",
                   var urlToImage:String?="", var content:String?=""):Parcelable