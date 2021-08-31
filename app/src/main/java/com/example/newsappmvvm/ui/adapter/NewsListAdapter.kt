package com.example.newsappmvvm.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.ItemNewsBinding
import com.example.newsappmvvm.model.models.Article
import com.example.newsappmvvm.model.models.ArticlesResponse

class NewsListAdapter(val activity:Activity,val list:ArrayList<Article>,
      val itemClickListener: AdapterItemClickListener)
    :RecyclerView.Adapter<NewsListAdapter.NewsDataViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDataViewHolder {


        val layoutInflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemNewsBinding>(layoutInflater, R.layout.item_news,
            parent,false)
        return NewsDataViewHolder(view)


    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: NewsDataViewHolder, position: Int) {
        val item = list[position]
        item?.let {
            holder.binding.textViewTitle.text= it.title
            holder.binding.textViewSubtitle.text = it.content
            Glide.with(activity).load(it.urlToImage)
                .placeholder(R.drawable.news_grey_256)
                .into(holder.binding.imgNews)
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onHeadlineItemClicked(item)
        }
    }



    class NewsDataViewHolder(itemNewsBinding: ItemNewsBinding):RecyclerView.ViewHolder(
        itemNewsBinding.root){
        val binding = itemNewsBinding
    }
}