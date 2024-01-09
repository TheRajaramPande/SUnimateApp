package com.example.finalproject.ui.info

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FileService {
    @GET("/get_pdf")
    fun getPDF(@Query("file_name") fileName: String): Call<FileResponse>
}