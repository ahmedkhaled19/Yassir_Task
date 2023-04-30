package android.ahmed.khaled.homescreen.ui

import android.ahmed.khaled.core.bases.BaseFragment
import android.ahmed.khaled.core.bases.BaseViewModel
import android.ahmed.khaled.core.utils.UiUtils
import android.ahmed.khaled.entities.local.Movie
import android.ahmed.khaled.entities.local.MovieDetail
import android.ahmed.khaled.homescreen.databinding.FragmentMovieDetailBinding
import android.ahmed.khaled.homescreen.view_model.MoviesViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Ahmed Khaled on 29/04/2023.
 */

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val viewModel by viewModels<MoviesViewModel>()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.apply {
            viewModel.selectedMovieId = movieId
            viewModel.getMovieDetail()
            handleParentView(movie.movieTitle)
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            showMessageWithAction.observe(viewLifecycleOwner) {
                UiUtils.showSnackBarWithAction(
                    getActivityBinding(), it.getMessage(requireContext())
                ) {
                    viewModel.getMovieDetail()
                }
            }

            showLoadingProgressBar.observe(viewLifecycleOwner) {
                binding.fragmentMoviesLoadingProgress.isVisible = it
            }

            moviesDetailLiveData.observe(viewLifecycleOwner){
                setData(it)
            }
        }
    }

    private fun handleParentView(movieTitle: String) {
        (requireActivity() as HomeActivity).setHomeTitle(movieTitle)
        (requireActivity() as HomeActivity).showBackButton(true)
    }

    private fun setData(movie: MovieDetail) {
        Glide.with(requireContext())
            .load(movie.posterPath)
            .into(binding.fragmentMovieDetailImage)

        binding.fragmentMovieDetailName.text = movie.movieTitle
        binding.fragmentMovieDetailOriginalName.text = movie.movieOriginalTitle
        binding.fragmentMovieDetailDate.text = movie.releaseDate
        binding.fragmentMovieDetailVoteAverage.text = String.format("%.1f", movie.voteAverage)
        binding.fragmentMovieDetailVoteCount.text = movie.voteCount.toString()
        binding.fragmentMovieDetailOverview.text = movie.overview
    }

    override fun getBaseViewModel(): BaseViewModel = viewModel

    override fun getActivityBinding(): View = binding.root
}