package android.ahmed.khaled.domain.mappers

import android.ahmed.khaled.core.bases.BaseMapper
import android.ahmed.khaled.core.utils.Constants
import android.ahmed.khaled.entities.local.MovieDetail
import android.ahmed.khaled.entities.remote.RemoteMovies
import javax.inject.Inject

/**
 * Created by Ahmed Khaled on 29/04/2023.
 */

class MovieDetailMapper @Inject constructor() : BaseMapper<RemoteMovies?, MovieDetail> {

    override fun convert(from: RemoteMovies?): MovieDetail {
        return from?.let {
            MovieDetail(
                it.id,
                it.title ?: "",
                it.original_title ?: "",
                Constants.IMAGE_BASE_URL + it.poster_path,
                it.overview ?: "",
                it.voteAverage ?: 0.0,
                it.voteCount ?: 0,
                it.release_date ?: ""
            )
        } ?: MovieDetail()
    }

}