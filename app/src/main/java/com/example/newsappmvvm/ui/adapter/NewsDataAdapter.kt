package com.example.newsappmvvm.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ItemNewsBinding
import com.example.newsappmvvm.model.models.ArticlesResponse

class NewsDataAdapter(val activity:Activity):
    PagingDataAdapter<ArticlesResponse.Article,NewsDataAdapter.NewsDataViewHolder>(diff) {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDataViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemNewsBinding>(layoutInflater,R.layout.item_news,
            parent,false)
        return NewsDataViewHolder(view)


    }


    override fun onBindViewHolder(holder: NewsDataViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.binding.textViewTitle.text= it.title
            holder.binding.textViewSubtitle.text = it.content
            Glide.with(activity).load(it.urlToImage)
                .placeholder(R.drawable.news_grey_256)
                .centerInside()
                .into(holder.binding.imgNews)
        }

    }



    class NewsDataViewHolder(itemNewsBinding: ItemNewsBinding):RecyclerView.ViewHolder(
        itemNewsBinding.root){
        val binding = itemNewsBinding
    }


     object diff : DiffUtil.ItemCallback<ArticlesResponse.Article>() {
        override fun areItemsTheSame(
            oldItem: ArticlesResponse.Article,
            newItem: ArticlesResponse.Article
        ): Boolean {
            return oldItem.urlToImage==newItem.urlToImage
        }

        override fun areContentsTheSame(
            oldItem: ArticlesResponse.Article,
            newItem: ArticlesResponse.Article
        ): Boolean {
            return oldItem == newItem
        }
    }
}