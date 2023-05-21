package com.fahmiproduction.storyapps.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fahmiproduction.storyapps.R
import com.fahmiproduction.storyapps.adapter.LoadingStateAdapter
import com.fahmiproduction.storyapps.adapter.StoryAdapter
import com.fahmiproduction.storyapps.databinding.ActivityMainBinding
import com.fahmiproduction.storyapps.viewmodel.MainViewModel
import com.fahmiproduction.storyapps.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setStory()
        setCreateStory()
    }

    private fun setStory() {
        val factory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        val storyAdapter = StoryAdapter()
        val preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val token = "Bearer " + preferences.getString("token", "").toString()

        binding.listStory.layoutManager = LinearLayoutManager(this)
        mainViewModel.getStory(token).observe(this) {
            binding.listStory.adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
            mainViewModel.getStory(token).observe(this) {
                storyAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setCreateStory() {
        binding.fabCreate.setOnClickListener {
            intent = Intent(this@MainActivity, CreateStoryActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_map -> {
                Toast.makeText(this, "Opening Map", Toast.LENGTH_SHORT).show()
                intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_logout -> {
                Toast.makeText(this, "Success Logout", Toast.LENGTH_SHORT).show()
                mainViewModel.deleteUser()
                intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> true
        }
    }


}