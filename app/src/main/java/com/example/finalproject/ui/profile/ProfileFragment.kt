package com.example.finalproject.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentProfileBinding
import com.example.finalproject.ui.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    //Show bottom navigation bar
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).setToolbarVisibility(true)
        (requireActivity() as MainActivity).setToolbarTitle("Profile")
        val bottomNavigationBar = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNavigationBar?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SignOut", "1")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        Log.d("SignOut", "2")

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {}

            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK){
                Configuration.UI_MODE_NIGHT_NO -> {
                    // Light theme is active, switch to dark
                    binding.themeBtn.text = "Dark Mode"
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    // Dark theme is active, switch to light
                    binding.themeBtn.text = "Light Mode"
                }
                else -> {
                    // Follow system theme
                    binding.themeBtn.text = "System Mode"
                }
            }


            //database get
            viewModel.download()
            viewModel.isDownloaded.observe(viewLifecycleOwner){
                Log.d("SignOut", "downloaded")
                if(it){
                    binding.nameTv.text = viewModel.profile.value?.name
                    Glide.with(this).load(viewModel.profile.value?.image?.toUri()).into(binding.profileIv2)
                    binding.genderTv.text = viewModel.profile.value?.gender
                }else{
                    Toast.makeText(context, viewModel.errorDownload.value, Toast.LENGTH_SHORT).show()
                }

            }
            viewModel.isEmpty.observe(viewLifecycleOwner){
                Log.d("SignOut", "not downloaded")
                if (it){
                    findNavController().navigate(R.id.action_profileFragment_to_profilingFragment)
                }
            }

            binding.profileInfoBtn.setOnClickListener {
                val action = ProfileFragmentDirections.actionProfileFragmentToProfilingFragment(isNewAccount = false)
                findNavController().navigate(action)
            }

            binding.themeBtn.setOnClickListener{
                //change theme
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        // Light theme is active, switch to dark
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        // Dark theme is active, switch to light
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                    }
                    else -> {
                        // Follow system theme
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }

            binding.postBtn.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_myPostsFragment)
            }

            binding.newBtn.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_infoFragment)
            }

            binding.signoutBtn.setOnClickListener{
                Log.d("SignOut", "onCreateView: clicked")
                viewModel.mAuth.signOut()
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                findNavController().clearBackStack(R.id.loginFragment)
            }
        val fab2: FloatingActionButton = binding.floatingActionButton2
        fab2.setOnClickListener { view ->
            Snackbar.make(view, "You have been Successfully logged out!", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
            Log.d("SignOut", "onCreateView: clicked")
            viewModel.mAuth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            findNavController().clearBackStack(R.id.loginFragment)
        }


        return binding.root
    }
}