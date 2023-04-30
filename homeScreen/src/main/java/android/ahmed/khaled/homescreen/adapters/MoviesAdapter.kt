package android.ahmed.khaled.homescreen.adapters

import android.ahmed.khaled.core.bases.BaseRecyclerViewAdapter
import android.ahmed.khaled.entities.local.Movie
import android.ahmed.khaled.homescreen.R
import android.ahmed.khaled.homescreen.databinding.ItemMovieListBinding
import android.view.View
import com.bumptech.glide.Glide

/**
 * Created by Ahmed Khaled on 29/04/2023.
 */

class MoviesAdapter(val onMovieSelect: ((selectedMovie: Movie, selectedMovieId: Int) -> Unit)) :
    BaseRecyclerViewAdapter<Movie>({ oldItem, newItem -> oldItem.externalId == newItem.externalId }) {

    private lateinit var binding: ItemMovieListBinding

    override val itemLayoutRes: Int
        get() = R.layout.item_movie_list

    override fun bind(view: View, item: Movie, position: Int) {
        binding = ItemMovieListBinding.bind(view)

        Glide.with(view.context)
            .load(item.posterPath)
            .into(binding.itemMoviePoster)

        view.setOnClickListener {
            onMovieSelect(item, item.externalId)
        }
    }
}