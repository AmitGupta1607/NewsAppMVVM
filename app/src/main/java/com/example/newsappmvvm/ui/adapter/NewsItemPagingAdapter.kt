package com.example.newsappmvvm.ui.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ItemNewsBinding
import com.example.newsappmvvm.model.models.ArticlesResponse
import android.view.View as View


class NewsItemPagingAdapter:PagedListAdapter<ArticlesResponse.Article,
        NewsItemPagingAdapter.ArticlesViewHolder>(diffUtilCallback) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news,parent,false)
        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val newsItem = getItem(position)
        newsItem?.let {
            holder.binding.textViewTitle.text= it.title
            holder.binding.textViewSubtitle.text = it.content
            Glide.with(holder.binding.imgNews).load(it.urlToImage)
                .into(holder.binding.imgNews)
        }


    }




    class ArticlesViewHolder(itemViewBinding: ItemNewsBinding) :RecyclerView.ViewHolder(
        itemViewBinding.root
    ) {
        val binding = itemViewBinding
    }
}


val diffUtilCallback = object:DiffUtil.ItemCallback<ArticlesResponse.Article>(){
    override fun areItemsTheSame(
        oldItem: ArticlesResponse.Article,
        newItem: ArticlesResponse.Article
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: ArticlesResponse.Article,
        newItem: ArticlesResponse.Article
    ): Boolean {
        return  oldItem==newItem
    }

}