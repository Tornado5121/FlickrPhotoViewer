package com.zhadko.loremflickrpictureviewer.ui.photoListScreen

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhadko.loremflickrpictureviewer.R
import com.zhadko.loremflickrpictureviewer.databinding.PhotoListFragmentBinding
import com.zhadko.loremflickrpictureviewer.ui.detailedPhotoScreen.DetailedPhotoFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {

    private val groupAnimFadeIn: LayoutAnimationController by lazy {
        AnimationUtils.loadLayoutAnimation(
            requireContext(),
            R.anim.view_group_move_in
        )
    }

    private val fadeOut by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out) }
    private val fadeIn by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in) }

    private val photoListViewModel by viewModel<PhotoListViewModel>()
    private lateinit var binding: PhotoListFragmentBinding

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
            binding.progressBar.animation = fadeIn
            photoListViewModel.getNextPagePhotosList()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            if (it.isEmpty()) {
                binding.errorMessageView.text =
                    getString(R.string.error_internet_message)
                binding.progressBar.animation = fadeOut
            } else {
                binding.errorMessageView.isVisible = false
                photoListAdapter.submitList(it)
                binding.progressBar.animation = fadeOut
            }

            with(binding) {
                photoList.layoutAnimation = groupAnimFadeIn
                photoList.scheduleLayoutAnimation()
            }
        }

        binding.refreshLayout.setOnRefreshListener {
            with(binding) {
                refreshLayout.isRefreshing = false
                progressBar.animation = fadeIn
            }
            photoListViewModel.getNextPagePhotosList()
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