package com.fahmiproduction.storyapps.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahmiproduction.storyapps.R
import com.fahmiproduction.storyapps.api.response.Story
import com.fahmiproduction.storyapps.databinding.ListStoryItemBinding
import com.fahmiproduction.storyapps.ui.DetailActivity
import com.fahmiproduction.storyapps.ui.DetailActivity.Companion.EXTRA_STORY
import com.fahmiproduction.storyapps.utils.dateFormat

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    inner class StoryViewHolder(private val binding: ListStoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            binding.apply {
                tvUsername.text = item.name
                tvDate.dateFormat(item.createdAt)
                tvDescription.text = item.description
                Glide
                    .with(itemView.context)
                    .load(item.photoUrl)
                    .placeholder(R.drawable.img_loading)
                    .error(R.drawable.img_error)
                    .into(imgStory)


                itemView.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvUsername, "username"),
                            Pair(tvDate, "date"),
                            Pair(imgStory, "image"),
                            Pair(tvDescription, "description"),
                        )
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_STORY, item)
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemBinding = ListStoryItemBinding.inflate(LayoutInflater.from(parent.context))
        return StoryViewHolder(itemBinding)
    }
}

