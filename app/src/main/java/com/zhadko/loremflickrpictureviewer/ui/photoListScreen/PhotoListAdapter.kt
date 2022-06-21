package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.databinding.FlickrPhotoListItemBinding
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

const val TRIGGER_PAGING_ITEM_NUMBER = 1

class PhotoListAdapter(
    private val context: Context,
    private val click: (FlickrPhoto) -> Unit,
    private val itemHasReached: () -> Unit
) : ListAdapter<FlickrPhoto, PhotoListAdapter.PhotoViewHolder>(FlickrPictureDiffUtil()) {

    class PhotoViewHolder(
        private val binding: FlickrPhotoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            context: Context,
            data: FlickrPhoto,
            click: (FlickrPhoto) -> Unit
        ) {
            with(binding) {
                photoAuthorName.text = data.owner
                Glide.with(context)
                    .load(data.file)
                    .placeholder(R.drawable.ic_launcher_background)
                    .apply(RequestOptions().override(800, 600))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photo)
                progressBar.isVisible = false
                root.setOnClickListener {
                    click(data)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FlickrPhotoListItemBinding.inflate(layoutInflater, parent, false)
                return PhotoViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(context, currentList[position], click)
        if (position == itemCount - TRIGGER_PAGING_ITEM_NUMBER) {
            itemHasReached()
        }
    }

    class FlickrPictureDiffUtil : DiffUtil.ItemCallback<FlickrPhoto>() {

        override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean {
            return oldItem.file == newItem.file
        }

    }

}