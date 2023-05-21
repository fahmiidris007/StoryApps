package com.fahmiproduction.storyapps.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fahmiproduction.storyapps.api.response.Story
import com.fahmiproduction.storyapps.databinding.ActivityDetailBinding
import com.fahmiproduction.storyapps.utils.dateFormat

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_STORY = "extra_story"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setStory()
    }

    private fun setStory() {
        val item = intent.getParcelableExtra<Story>(EXTRA_STORY) as Story
        with(binding) {
            tvUsername.text = item.name
            tvDate.dateFormat(item.createdAt)
            tvDescription.text = item.description

            Glide
                .with(applicationContext)
                .load(item.photoUrl)
                .into(imgStory)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}