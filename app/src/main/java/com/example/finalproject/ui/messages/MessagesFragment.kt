package com.example.finalproject.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentMessagesBinding
import com.example.finalproject.model.Chat
import com.example.finalproject.ui.MainActivity


class MessagesFragment : Fragment() {

    private lateinit var binding: FragmentMessagesBinding
    private lateinit var viewModel: MessagesViewModel
    private lateinit var chats : List<Chat>


    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setToolbarTitle("Messages")
        (activity as MainActivity).setToolbarVisibility(true)
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MessagesViewModel::class.java]
        viewModel.getChats()

        viewModel.chats.observe(viewLifecycleOwner) {
            chats = it
            binding.messagesRv.adapter = MessagesRecyclerAdapter(chats)
            binding.messagesRv.layoutManager = LinearLayoutManager(requireContext())
        }


        return binding.root
    }


}