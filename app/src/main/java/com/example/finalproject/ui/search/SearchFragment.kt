package com.example.finalproject.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentSearchBinding
import com.example.finalproject.model.HomePost
import com.example.finalproject.ui.MainActivity


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    private var homePosts: List<HomePost?> = listOf()
    private var isEmpty : Boolean = true


    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setToolbarVisibility(true)
        (requireActivity() as MainActivity).setToolbarTitle("Search")
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        homePosts = viewModel.homePosts.value ?: listOf()
        isEmpty = viewModel.isEmpty.value ?: true

        if (isEmpty) {
            viewModel.getPosts()
        }
        viewModel.homePosts.observe(viewLifecycleOwner) { posts ->
            homePosts = posts
            binding.progressBar2.visibility = View.GONE
            binding.searchRv.visibility = View.VISIBLE
            binding.searchRv.adapter = SearchRecyclerAdapter(homePosts)
            binding.searchRv.layoutManager = LinearLayoutManager(context)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar2.visibility = View.VISIBLE
                binding.searchRv.visibility = View.GONE
                viewModel.getPosts(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })



        return binding.root
    }
}