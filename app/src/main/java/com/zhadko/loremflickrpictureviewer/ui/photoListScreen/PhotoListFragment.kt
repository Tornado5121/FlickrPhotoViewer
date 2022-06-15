package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.databinding.PhotoListFragmentBinding
import com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen.DetailedPhotoFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {

    private val photoListViewModel by viewModel<PhotoListViewModel>()
    private lateinit var binding: PhotoListFragmentBinding
    private val photoListAdapter by lazy {
        PhotoListAdapter(requireContext()) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.my_container_view,
                    DetailedPhotoFragment.getDetailedFragmentInstance(it.file)
                )
                .addToBackStack("")
                .commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhotoListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            photoList.adapter = photoListAdapter
            photoList.layoutManager = LinearLayoutManager(requireContext())
        }

        photoListViewModel.myPhotoListLiveData.observe(viewLifecycleOwner) {
            photoListAdapter.submitList(it)
        }
        photoListViewModel.getPhotoList()
    }

}