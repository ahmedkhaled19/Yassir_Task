package android.ahmed.khaled.data.restful

import android.ahmed.khaled.entities.remote.RemoteMovies
import android.ahmed.khaled.entities.responses.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesEndPoints {

    @GET("discover/movie")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): RemoteMovies
}