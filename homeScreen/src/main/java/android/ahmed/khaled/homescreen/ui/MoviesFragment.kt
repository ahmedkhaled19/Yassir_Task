package android.ahmed.khaled.homescreen.ui

import android.ahmed.khaled.core.R
import android.ahmed.khaled.core.bases.BaseFragment
import android.ahmed.khaled.core.bases.BaseViewModel
import android.ahmed.khaled.core.bases.PaginationScrollListener
import android.ahmed.khaled.core.utils.Constants.GRID_SPAN_COUNT
import android.ahmed.khaled.core.utils.UiUtils
import android.ahmed.khaled.homescreen.adapters.MoviesAdapter
import android.ahmed.khaled.homescreen.databinding.FragmentMoviesBinding
import android.ahmed.khaled.homescreen.view_model.MoviesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ahmed Khaled on 28/04/2023.
 */

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        setupObservers()
        (requireActivity() as HomeActivity).setHomeTitle(getString(R.string.title))
        (requireActivity() as HomeActivity).showBackButton(false)
        return binding.root
    }

    private fun setupObservers() {
        with(viewModel) {
            showMessageWithAction.observe(viewLifecycleOwner) {
                UiUtils.showSnackBarWithAction(
                    getActivityBinding(), it.getMessage(requireContext())
                ) {
                    viewModel.loadData()
                }
            }

            showLoadingProgressBar.observe(viewLifecycleOwner) {
                binding.fragmentMoviesLoadingProgress.isVisible = it
            }

            showLoadingMoreProgressBar.observe(viewLifecycleOwner) {
                binding.fragmentMoviesLoadMoreProgress.isVisible = it
            }

            moviesListLiveData.observe(viewLifecycleOwner) {
                binding.fragmentMoviesRecyclerView.isVisible = true
                moviesAdapter.addToList(it)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMoviesAdapter()
        setupRecyclerView()

        if (viewModel.page == 0) {
            binding.fragmentMoviesRecyclerView.isVisible = false
            binding.fragmentMoviesLoadingProgress.isVisible = true
            viewModel.loadData()
        }
    }

    private fun initMoviesAdapter() {
        if (this::moviesAdapter.isInitialized) return

        moviesAdapter = MoviesAdapter { movie, movieId ->
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(
                    movie, movieId
                )
            )
        }
    }

    private fun setupRecyclerView() {
        binding.fragmentMoviesRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
            adapter = moviesAdapter
            addOnScrollListener(object :
                PaginationScrollListener(layoutManager as GridLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.isLoading = true
                    viewModel.loadData()
                }

                override fun isLastPage(): Boolean = viewModel.isLastPage

                override fun isLoading(): Boolean = viewModel.isLoading

            })
        }
    }

    override fun getBaseViewModel(): BaseViewModel = viewModel

    override fun getActivityBinding(): View = binding.root

}