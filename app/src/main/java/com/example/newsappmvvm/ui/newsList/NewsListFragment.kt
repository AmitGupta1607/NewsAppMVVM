package com.example.newsappmvvm.ui.newsList

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.R
import com.example.newsappmvvm.common.Constants
import com.example.newsappmvvm.databinding.FragmentFirstBinding
import com.example.newsappmvvm.model.models.Article
import com.example.newsappmvvm.ui.adapter.AdapterItemClickListener
import com.example.newsappmvvm.ui.adapter.NewsDataAdapter
import com.example.newsappmvvm.ui.adapter.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class NewsListFragment : Fragment(), AdapterItemClickListener {


    var binding: FragmentFirstBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<FragmentFirstBinding>(
            inflater, R.layout.fragment_first,
            container, false
        )
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //recyclerview initialization
        val recyclerView = binding!!.recyclerviewNews
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
        val newsDataAdapter = NewsDataAdapter(requireActivity(), this)
        recyclerView.adapter = newsDataAdapter


        // viewmodel initialization
        val viewModel = ViewModelProvider(requireActivity()).get(NewsListViewModel::class.java)

        viewModel.newsData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            newsDataAdapter.submitData(viewLifecycleOwner.lifecycle, it)

        })

        newsDataAdapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {
                setVisibilityOfProgressView(true, newsDataAdapter)
            } else {
                val error = returnIfErrorLoadState(loadState)
                // error state
                error?.let {
                    showErrorStateAndFetchCachedDataFromDb(viewModel, newsDataAdapter, it.error.message)

                }

                //success or empty state
                if (error == null) {
                    hideInternetErrorView()
                }

                setVisibilityOfProgressView(false,newsDataAdapter)
            }


        }

        handleSwipeToRefresh(newsDataAdapter)
        Handler(Looper.getMainLooper()).postDelayed({ binding?.
        swipeToRefreshLayout?.isRefreshing = false },
            1000)

    }

    private fun returnIfErrorLoadState(loadState: CombinedLoadStates):LoadState.Error?{
        val error = when {
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }
        return error
    }


    private fun handleSwipeToRefresh(newsDataAdapter: NewsDataAdapter) {
        binding?.swipeToRefreshLayout?.setOnRefreshListener {
            binding?.recyclerviewNews?.removeAllViews()
            newsDataAdapter.refresh()
            Handler(Looper.getMainLooper()).postDelayed({ binding?.
            swipeToRefreshLayout?.isRefreshing = false },
                1000)

        }
    }

    /** Shows network error state
     *  and fetches data from cache if internet not available
     */
    private fun showErrorStateAndFetchCachedDataFromDb(
        viewModel: NewsListViewModel, newsDataAdapter: NewsDataAdapter,
        errorMessage: String?
    ) {
        viewModel.fetchNewsFromDb()
        viewModel.newsFromDb.observe(viewLifecycleOwner, Observer {
            if (it != null && it.isNotEmpty()) {
                val adapter = NewsListAdapter(
                    requireActivity(), it as ArrayList<Article>,
                    this
                )
                binding?.recyclerviewNews?.adapter = adapter
                hideInternetErrorView()

            } else {
                showErrorView(viewModel, newsDataAdapter)
            }
        })
    }


    private fun setVisibilityOfProgressView(
        makeVisible: Boolean,
        newsDataAdapter: NewsDataAdapter
    ) {

        val progressView = binding?.rootLayout?.findViewById<View>(R.id.layout_progress)
        if (makeVisible) {
            if (newsDataAdapter.itemCount > 0) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                progressView?.visibility = View.VISIBLE
            }
        } else {
            progressView?.visibility = View.GONE
            binding?.progressBar?.visibility = View.GONE

        }


    }


    /** Adapter item click handle - Open detail fragment */
    override fun onHeadlineItemClicked(article: Article) {
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_ARTICLE, article)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)

    }

    private fun hideInternetErrorView() {
        val viewOffline = binding?.rootLayout?.findViewById<View>(R.id.view_offline)
        viewOffline?.visibility = View.GONE
    }

    /** Show internet error view */
    private fun showErrorView(viewModel: NewsListViewModel, adapter: NewsDataAdapter) {
        val viewOffline = binding?.rootLayout?.findViewById<View>(R.id.view_offline)
        viewOffline?.setOnClickListener {
            viewModel.fetchNews()
        }
        var retryButton: Button? = null
        viewOffline?.let {
            it.visibility = View.VISIBLE
            retryButton = it.findViewById<Button>(R.id.button_retry)

        }
        retryButton?.setOnClickListener {
            adapter.retry()
            viewOffline?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}