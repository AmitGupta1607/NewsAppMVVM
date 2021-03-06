package com.example.newsappmvvm.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ItemNewsBinding
import com.example.newsappmvvm.model.models.Article

class NewsDataAdapter(val activity: Activity, val itemClickListener: AdapterItemClickListener) :
    PagingDataAdapter<Article, NewsDataAdapter.NewsDataViewHolder>(DiffUtilItems) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDataViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemNewsBinding>(
            layoutInflater, R.layout.item_news,
            parent, false
        )
        return NewsDataViewHolder(view)

    }


    override fun onBindViewHolder(holder: NewsDataViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.binding.textViewTitle.text = it.title
            holder.binding.textViewSubtitle.text = it.content
            Glide.with(activity)
                .load(it.urlToImage)
                .placeholder(R.drawable.offline_light)
                .centerCrop()
                .into(holder.binding.imgNews)
            holder.binding.textViewAuthor.text = it.author
        }
        holder.itemView.setOnClickListener {
            itemClickListener.onHeadlineItemClicked(item as Article)
        }

    }


    class NewsDataViewHolder(itemNewsBinding: ItemNewsBinding) : RecyclerView.ViewHolder(
        itemNewsBinding.root
    ) {
        val binding = itemNewsBinding
    }


    object DiffUtilItems : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.urlToImage == newItem.urlToImage
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem == newItem
        }
    }
}