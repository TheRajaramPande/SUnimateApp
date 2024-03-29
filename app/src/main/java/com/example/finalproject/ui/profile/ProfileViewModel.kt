package com.example.finalproject.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    private val _mAuth = FirebaseAuth.getInstance()
    val mAuth: FirebaseAuth
        get() = _mAuth

    private val uid = _mAuth.currentUser?.uid

    private val database = FirebaseFirestore.getInstance()

    //Get Profile
    private val _profile = MutableLiveData<Profile>()
    val profile: MutableLiveData<Profile>
        get() = _profile


    private val _isDownloaded = MutableLiveData<Boolean>()
    val isDownloaded: MutableLiveData<Boolean>
        get() = _isDownloaded

    val errorDownload = MutableLiveData<String>()
    val isEmpty = MutableLiveData<Boolean>()

    fun download() {
        database.collection("Profiles").document(uid!!).addSnapshotListener { data, error ->
            Log.d("SignOut", uid)
            if (error != null) {
                _isDownloaded.value = false
                errorDownload.value = error.message
            } else {

                if (data != null) {
                    val name = _mAuth.currentUser?.displayName
                    val image = data["image"].toString()
                    val gender = data["gender"].toString()

                    _profile.value = Profile(uid,name, gender, null,null,null, image, null)

                    isEmpty.value = name.isNullOrEmpty()

                    _isDownloaded.value = true

                }
            }

        }
    }

}