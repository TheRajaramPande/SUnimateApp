package com.example.finalproject.ui.info

import com.google.gson.annotations.SerializedName

data class FileResponse(
    @SerializedName("file_name") val fileName: String,
    @SerializedName("file_link") val fileLink: String
)
