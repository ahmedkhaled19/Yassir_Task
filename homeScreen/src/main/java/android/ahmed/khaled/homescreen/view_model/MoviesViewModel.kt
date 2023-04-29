package android.ahmed.khaled.homescreen.view_model

import android.ahmed.khaled.core.bases.BaseViewModel
import android.ahmed.khaled.core.common.SingleLiveEvent
import android.ahmed.khaled.core.utils.NetworkingUtils
import android.ahmed.khaled.domain.usecases.MovieDetailUseCase
import android.ahmed.khaled.domain.usecases.MoviesRemoteUseCase
import android.ahmed.khaled.entities.local.Movie
import android.ahmed.khaled.entities.local.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Ahmed Khaled on 29/04/2023.
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRemoteUseCase: MoviesRemoteUseCase,
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel() {

    val moviesListLiveData: SingleLiveEvent<MutableList<Movie>> by lazy {
        SingleLiveEvent()
    }


    val moviesDetailLiveData: SingleLiveEvent<MovieDetail> by lazy {
        SingleLiveEvent()
    }
    private var localMoviesList: MutableList<Movie> = ArrayList()
    var page: Int = 0
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var selectedMovieId: Int = 0

    fun loadData() {
        if (!NetworkingUtils.isNetworkConnected) {
            showLoadingProgressBar.value = false
            showMessageByStringId(
                android.ahmed.khaled.core.R.string.check_connection,
                withAction = true
            )
            return
        }

        page++
        moviesRemoteUseCase.sendRequest(page) {
            onComplete { moviesList ->
                if (moviesList.isEmpty()) {
                    isLastPage = true
                    return@onComplete
                }

                localMoviesList.addAll(moviesList)
                moviesListLiveData.value = moviesList
            }

            onError { errorMessage ->
                showTheErrorMessage(errorMessage)
            }

            isLoading {
                isLoading = it

                if (page == 1) {
                    showLoadingProgressBar.value = it
                } else {
                    showLoadingMoreProgressBar.value = it
                }
            }
        }
    }

    fun getMovieDetail(){
        if (!NetworkingUtils.isNetworkConnected) {
            showLoadingProgressBar.value = false
            showMessageByStringId(
                android.ahmed.khaled.core.R.string.check_connection,
                withAction = true
            )
            return
        }

        movieDetailUseCase.sendRequest(selectedMovieId){
            onComplete {
                moviesDetailLiveData.value = it
            }

            onError { errorMessage ->
                showTheErrorMessage(errorMessage)
            }

            isLoading {
                showLoadingProgressBar.value = it
            }
        }
    }
}
