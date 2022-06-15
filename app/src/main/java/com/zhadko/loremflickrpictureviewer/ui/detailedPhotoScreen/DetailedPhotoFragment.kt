package com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.zhadko.loremflickrpictureviewer.databinding.DetailedPhotoFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailedPhotoFragment : Fragment() {

    private val detailedPhotoViewModel by viewModel<DetailedPhotoViewModel> {
        parametersOf(requireArguments().getString(MY_PHOTO_ID))
    }
    private lateinit var binding: DetailedPhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailedPhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailedPhotoViewModel.flickrPhotoLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                authorName.text = it.owner
                downloadLink.text = it.file
                Glide.with(requireContext()).load(it.file).into(photo)
            }
        }

        detailedPhotoViewModel.getPhotoByFileName()
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