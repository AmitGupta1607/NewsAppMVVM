package com.example.newsappmvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newsappmvvm.common.Constants
import com.example.newsappmvvm.databinding.FragmentSecondBinding
import com.example.newsappmvvm.model.models.Article

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_second,
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
            .placeholder(R.drawable.news_grey_256)
            .into(binding.imgNews)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}