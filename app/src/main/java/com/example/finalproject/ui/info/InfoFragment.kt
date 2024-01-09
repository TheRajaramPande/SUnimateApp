package com.example.finalproject.ui.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.R
import com.example.finalproject.ui.MainActivity
import com.google.api.LogDescriptor
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InfoFragment : Fragment() {

    companion object {
        fun newInstance() = InfoFragment()
    }

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button1: Button = view.findViewById(R.id.file1)
        val button2: Button = view.findViewById(R.id.file2)
        val button3: Button = view.findViewById(R.id.file3)

        button1.setOnClickListener { onFileButtonClick("file1.pdf") }
        button2.setOnClickListener { onFileButtonClick("file2.pdf") }
        button3.setOnClickListener { onFileButtonClick("file3.pdf") }

        val youTubePlayerView: YouTubePlayerView = view.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)
        val youTubePlayerView2: YouTubePlayerView = view.findViewById(R.id.youtube_player_view2)
        lifecycle.addObserver(youTubePlayerView2)
        val youTubePlayerView3: YouTubePlayerView = view.findViewById(R.id.youtube_player_view3)
        lifecycle.addObserver(youTubePlayerView3)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("5JhP6Y2J_y0", 0f)
            }
        })
        youTubePlayerView2.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("nYKZrrQh1qw", 0f)
            }
        })
        youTubePlayerView3.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo("SJ1P9CaxOJc", 0f)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setToolbarVisibility(false)
        viewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun onFileButtonClick(fileName: String) {
        Log.d("lolo", "onFileButtonClick: ")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://androidserver-ca5f27a9a59b.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(FileService::class.java)

        val call = service.getPDF(fileName)

        call.enqueue(object : Callback<FileResponse> {
            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
                if (response.isSuccessful) {
                    val fileLink = response.body()?.fileLink
                    fileLink?.let { openPDFInViewer(it) }
                } else {
                    Log.d("lolo", "fail ")
                    // Handle error
                    // You can show a Toast or log the error
                }
            }

            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                // Handle failure
                // You can show a Toast or log the failure
                Log.e("FileRequest", "Error: ${t.message}", t)

                // Show a Toast with an error message
                Toast.makeText(requireContext(), "Failed to fetch PDF file", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun openPDFInViewer(fileLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fileLink))
        startActivity(intent)
    }
}
