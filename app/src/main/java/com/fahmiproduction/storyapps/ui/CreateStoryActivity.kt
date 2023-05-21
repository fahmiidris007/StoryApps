package com.fahmiproduction.storyapps.ui

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fahmiproduction.storyapps.databinding.ActivityCreateStoryBinding
import com.fahmiproduction.storyapps.utils.createCustomTempFile
import com.fahmiproduction.storyapps.utils.reduceFileImage
import com.fahmiproduction.storyapps.viewmodel.CreateStoryViewModel
import com.fahmiproduction.storyapps.viewmodel.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CreateStoryActivity : AppCompatActivity() {
    private var _binding: ActivityCreateStoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var createStoryViewModel: CreateStoryViewModel
    private var token: String = ""
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreateStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToken()
        setViewModel()
        setCamera()
        setUpload()
    }

    private fun setToken() {
        val preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        token = "Bearer " + preferences.getString("token", "").toString()
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        createStoryViewModel = ViewModelProvider(this, factory)[CreateStoryViewModel::class.java]
    }

    private fun setCamera() {
        binding.btnCamera.setOnClickListener { startTakePhoto() }

    }

    private fun setUpload() {
        binding.btnSubmit.setOnClickListener {
            uploadValidate()
        }
    }

    private fun uploadValidate() {
        var isValid = true
        val validDescription = binding.etDescription
        if (validDescription.text.toString().isEmpty()) {
            validDescription.error = "Description is Empty"
            Toast.makeText(this, "Description is Empty", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (getFile == null) {
            Toast.makeText(this, "Image is Empty", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (isValid) {
            uploaded()
        }
    }

    private fun uploaded() {
        val file = reduceFileImage(getFile as File)
        val image = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            image
        )
        val description =
            binding.etDescription.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        createStoryViewModel.upload(token, imageMultipart, description).observe(this) { response ->
            if (response.error) {
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Upload Success", Toast.LENGTH_SHORT).show()
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@CreateStoryActivity,
                "com.fahmiproduction.storyapps",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile

            val photo = BitmapFactory.decodeFile(myFile.path)
            Glide.with(this)
                .load(photo)
                .into(binding.imgStory)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}