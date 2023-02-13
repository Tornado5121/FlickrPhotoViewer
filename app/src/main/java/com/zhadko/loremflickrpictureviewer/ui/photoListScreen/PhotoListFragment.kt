package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.animation.fadeGroupIn
import com.zhadko.loremflickrpictureviewer.animation.fadeIn
import com.zhadko.loremflickrpictureviewer.animation.fadeOut
import com.zhadko.loremflickrpictureviewer.base.BaseFragment
import com.zhadko.loremflickrpictureviewer.databinding.PhotoListFragmentBinding
import com.zhadko.loremflickrpictureviewer.models.domainModels.FlickrPhoto
import com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen.DetailedPhotoFragment
import com.zhadko.loremflickrpictureviewer.utils.collectOnLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : BaseFragment<PhotoListFragmentBinding>(
    vbFactory = PhotoListFragmentBinding::inflate
) {
    private val photoListViewModel by viewModel<PhotoListViewModel>()

    private val photoListAdapter by lazy {
        PhotoListAdapter(requireContext(), {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.my_container_view,
                    DetailedPhotoFragment.getDetailedFragmentInstance(it.file)
                )
                .addToBackStack("")
                .commit()
        }, {
            binding.progressBar.animation = fadeIn()
            photoListViewModel.getNextPagePhotosList()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        collectOnLifecycle(photoListViewModel.myPhotoList, collect = ::renderItems)
    }

    private fun renderItems(photoList: List<FlickrPhoto>) {
        if (photoList.isNotEmpty() && photoList[0].file != "my_custom_error_sdrwerdcs") {
            binding.errorMessageView.isVisible = false
            photoListAdapter.submitList(photoList)
            binding.progressBar.animation = fadeOut()
        } else {
            binding.errorMessageView.isVisible = false
            binding.progressBar.animation = fadeOut()
            binding.errorMessageView.text = context?.getText(R.string.error_internet_message)
        }
    }

    private fun setupView() {
        with(binding) {
            refreshLayout.setOnRefreshListener {
                refreshLayout.isRefreshing = false
                progressBar.animation = fadeIn()
                photoListViewModel.getNextPagePhotosList()
            }
            photoList.adapter = photoListAdapter
            photoList.layoutManager = LinearLayoutManager(requireContext())

            photoList.layoutAnimation = fadeGroupIn()
            photoList.scheduleLayoutAnimation()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.my_menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = menuItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.progressBar.isVisible = true
                    photoListViewModel.searchPhotosByTag(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

}