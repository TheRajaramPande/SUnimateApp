package com.example.finalproject.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentMatchesBinding
import com.example.finalproject.model.Profile
import com.example.finalproject.ui.MainActivity


class MatchesFragment : Fragment() {

    private lateinit var binding: FragmentMatchesBinding
    private lateinit var viewModel: MatchesViewModel
    private lateinit var matches: ArrayList<Profile>
    private lateinit var percents: List<Int>

    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setToolbarTitle("Matches")
        (activity as MainActivity).setToolbarVisibility(true)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MatchesViewModel::class.java]
        viewModel.updateMatches()
        percents = listOf(100, 90, 80, 70, 60, 50, 40, 30, 20, 10)

        viewModel.matches.observe(viewLifecycleOwner) {
            matches = it
            val adapter = MatchesRecyclerAdapter(matches, percents)
            binding.matchesRv.adapter = adapter
            binding.matchesRv.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            val swipe = adapter.SwipeGestures()
            val itemTouchHelper = ItemTouchHelper(swipe)
            itemTouchHelper.attachToRecyclerView(binding.matchesRv)

            binding.matchesRv.visibility = View.VISIBLE
            binding.imageView4.visibility = View.GONE
        }


        return binding.root
    }

}