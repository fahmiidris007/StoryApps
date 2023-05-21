package com.fahmiproduction.storyapps.api.remote

import com.fahmiproduction.storyapps.api.response.UploadResponse

interface UploadCallback {
    fun onUpload(uploadResponse: UploadResponse)
}