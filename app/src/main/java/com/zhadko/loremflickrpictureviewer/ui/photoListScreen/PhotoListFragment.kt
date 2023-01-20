package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.animation.fadeGroupIn
import com.zhadko.loremflickrpictureviewer.animation.fadeIn
import com.zhadko.loremflickrpictureviewer.animation.fadeOut
import com.zhadko.loremflickrpictureviewer.base.BaseFragment
import com.zhadko.loremflickrpictureviewer.databinding.PhotoListFragmentBinding
import com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen.DetailedPhotoFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
        viewLifecycleOwner.lifecycleScope.launch {
            photoListViewModel.myPhotoListLiveData.collect {
                if (it.isEmpty()) {
                    binding.errorMessageView.text =
                        getString(R.string.error_internet_message)
                    binding.progressBar.animation = fadeOut()
                } else {
                    binding.errorMessageView.isVisible = false
                    photoListAdapter.submitList(it)
                    binding.progressBar.animation = fadeOut()
                }
            }
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