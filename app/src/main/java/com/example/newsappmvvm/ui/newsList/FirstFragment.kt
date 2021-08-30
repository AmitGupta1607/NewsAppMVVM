package com.example.newsappmvvm.ui.newsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappmvvm.R
import com.example.newsappmvvm.databinding.FragmentFirstBinding
import com.example.newsappmvvm.ui.adapter.NewsDataAdapter
import com.example.newsappmvvm.ui.adapter.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FirstFragment : Fragment() {


    var binding:FragmentFirstBinding?=null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =DataBindingUtil.inflate<FragmentFirstBinding>(inflater, R.layout.fragment_first,
        container,false)
        return binding!!.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding!!.recyclerviewNews
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }
        val adapter = NewsDataAdapter(requireActivity())
        recyclerView.adapter=adapter
       val viewModel = ViewModelProvider(requireActivity()).get(NewsListViewModel::class.java)

       viewModel.newsData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

               adapter.submitData(viewLifecycleOwner.lifecycle, it)

       })

        adapter.addLoadStateListener { loadState->

            if(loadState.refresh is LoadState.Loading){
                binding?.progressBar?.visibility = View.VISIBLE
            }
            else{
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }

                binding?.progressBar?.visibility=View.GONE
            }

        }





//        viewModel.headLines.observe(viewLifecycleOwner, Observer {
//            val adapter = NewsListAdapter.
//        })

//        binding.buttonFirst?.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    fun showErrorView(){

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}