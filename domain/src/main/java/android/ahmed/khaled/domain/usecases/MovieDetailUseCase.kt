package android.ahmed.khaled.domain.usecases

import android.ahmed.khaled.core.bases.BaseNetworkError
import android.ahmed.khaled.data.repository.MoviesRepo
import android.ahmed.khaled.domain.bases.BaseRemoteUseCase
import android.ahmed.khaled.domain.mappers.MovieDetailMapper
import android.ahmed.khaled.entities.local.MovieDetail
import android.ahmed.khaled.entities.remote.RemoteMovies
import javax.inject.Inject

/**
 * Created by Ahmed Khaled on 29/04/2023.
 */

class MovieDetailUseCase  @Inject constructor(
    private val moviesRepo: MoviesRepo,
    private val movieDetailMapper: MovieDetailMapper,
    errorHandler: BaseNetworkError
) : BaseRemoteUseCase<Int, RemoteMovies, MovieDetail>(errorHandler) {


    override suspend fun executeRequest(parameters: Int): RemoteMovies {
        return moviesRepo.getMovieDetail(movieId = parameters)
    }

    override suspend fun convert(remoteResponse: RemoteMovies): MovieDetail {
        return movieDetailMapper.convert(remoteResponse)
    }
}