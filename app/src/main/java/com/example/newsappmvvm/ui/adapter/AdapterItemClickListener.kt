package com.example.newsappmvvm.ui.adapter

import com.example.newsappmvvm.model.models.Article

interface AdapterItemClickListener {
   fun onHeadlineItemClicked(article:Article)
}