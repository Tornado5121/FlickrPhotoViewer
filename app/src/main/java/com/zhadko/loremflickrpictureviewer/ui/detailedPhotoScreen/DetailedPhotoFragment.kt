package com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.base.BaseFragment
import com.zhadko.loremflickrpictureviewer.databinding.DetailedPhotoFragmentBinding
import com.zhadko.loremflickrpictureviewer.utils.isPermissionsGranted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailedPhotoFragment : BaseFragment<DetailedPhotoFragmentBinding>(
    vbFactory = DetailedPhotoFragmentBinding::inflate
) {

    private val detailedPhotoViewModel by viewModel<DetailedPhotoViewModel> {
        parametersOf(requireArguments().getString(MY_PHOTO_ID))
    }

    private lateinit var photoUrl: String

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            detailedPhotoViewModel.flickrPhoto.collect {

                photoUrl = it.file

                with(binding) {
                    authorName.text = getString(R.string.creator_name_placeholder) + it.owner
                    downloadLink.text = getString(R.string.download_link_placeholder) + it.file +
                            getString(R.string.download_button_explanation_placeholder)
                    Glide.with(requireContext()).load(photoUrl).into(photo)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            detailedPhotoViewModel.errorMessage.collect { errorMessage ->
                if (errorMessage.isNotEmpty()) {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        detailedPhotoViewModel.getPhotoByFileName()

        binding.saveGalleyButton.setOnClickListener {
            if (isPermissionsGranted(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                detailedPhotoViewModel.savePhotoToGallery(photoUrl)
            } else {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    10
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        detailedPhotoViewModel.savePhotoToGallery(photoUrl)
    }

    companion object {

        private const val MY_PHOTO_ID = "my_photo_id"
        fun getDetailedFragmentInstance(id: String): DetailedPhotoFragment {
            return DetailedPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(MY_PHOTO_ID, id)
                }
            }
        }

    }

}