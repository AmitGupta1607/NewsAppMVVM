package com.example.newsappmvvm.ui.newsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.newsappmvvm.R
import com.example.newsappmvvm.common.Constants
import com.example.newsappmvvm.databinding.FragmentDetailBinding
import com.example.newsappmvvm.model.models.Article

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewsItemDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail,
        container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val article = it.getParcelable<Article>(Constants.BUNDLE_ARTICLE)
           showArticleDetailView(article)

        }
    }

    private fun showArticleDetailView(article: Article?){
        binding.textViewTitle.text=article?.title
        binding.textViewSubtitle.text=article?.content
        Glide.with(requireContext()).load(article?.urlToImage)
            .error(R.drawable.news_grey_256)
            .into(binding.imgNews)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}