package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhadko.loremflickrpictureviewer.databinding.FlickrPhotoListItemBinding
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto

class PhotoListAdapter(
    private val context: Context,
    private val click: (FlickrPhoto) -> Unit
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
                Glide.with(context).load(data.file).into(photo)
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