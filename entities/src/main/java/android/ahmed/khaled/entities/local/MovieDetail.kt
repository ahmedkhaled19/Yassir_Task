package android.ahmed.khaled.entities.local

/**
 * Created by Ahmed Khaled on 29/04/2023.
 */

data class MovieDetail(
    var externalId: Int = 0,
    var movieTitle: String = "",
    var movieOriginalTitle: String = "",
    var posterPath: String = "",
    var overview: String = "",
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0,
    var releaseDate: String = ""
)
