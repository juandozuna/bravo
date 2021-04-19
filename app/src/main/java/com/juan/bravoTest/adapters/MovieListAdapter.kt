package com.juan.bravoTest.adapters

import android.graphics.Movie
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juan.bravoTest.BuildConfig
import com.juan.bravoTest.databinding.MovieItemAdapterBinding
import com.juan.bravoTest.models.MovieModel
import com.squareup.picasso.Picasso

class MovieListAdapter(var movies: List<MovieModel>, private val onItemSelected: (_: MovieModel) -> Unit): RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemAdapterBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        holder.btnPressed = {
            onItemSelected.invoke(movies[position])
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
       return movies.count()
    }

    fun updateData(newMovies: List<MovieModel>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    class ViewHolder(var binding: MovieItemAdapterBinding): RecyclerView.ViewHolder(binding.root) {
        var btnPressed: (() -> Unit)? = null
        init {
            binding.apply {
                listener = View.OnClickListener { onClickListener(it) }
            }
        }

        private fun onClickListener(view: View) {
            when (view.id) {
                binding.moviePosterIv.id -> pressButton()
            }
        }

        private fun pressButton() {
            btnPressed?.invoke()
        }

        fun bind(item: MovieModel) {
            val baseImageUrl = BuildConfig.IMAGE_URL
            val completeImageUrl = "${baseImageUrl}w300/${item.posterPath}"
            Picasso.with(binding.root.context).load(completeImageUrl).into(binding.moviePosterIv)
            binding.movieReleaseDate.text = item.releaseDate
            binding.movieTitle.text = item.title
            binding.movieUserScore.text = "${item.popularity}"
        }
    }

}