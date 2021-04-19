package com.juan.bravoTest.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.juan.bravoTest.BuildConfig
import com.juan.bravoTest.R
import com.juan.bravoTest.databinding.FragmentMovieDetailBinding
import com.juan.bravoTest.models.GenreModel
import com.juan.bravoTest.models.MovieModel
import com.juan.bravoTest.services.MovieService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MovieDetailFragment : Fragment() {
    private val service = MovieService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binder = FragmentMovieDetailBinding.inflate(inflater, null, false)
        binder.apply { listener = View.OnClickListener {
            when(it.id) {
                goBackButton.id -> goBack()
            }
        } }
        return binder.root
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    fun initialize() {
        val movieId = arguments?.getInt("movieId")
        if (movieId == null) {
            goBack()
            return
        }
        loadMovieDetails(movieId)
    }

    fun loadMovieDetails(id: Int) {
        val call = service.API.getMovieDetails(id)
        call.enqueue(object: retrofit2.Callback<MovieModel> {
            override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                val data = response.body()!!
                val baseImageUrl = BuildConfig.IMAGE_URL
                val completeImageUrl = "${baseImageUrl}w300/${data.posterPath}"
                Picasso.with(context).load(completeImageUrl).into(movie_poster_image)
                movie_title.text = data.title
                overview_content_tv.text = data.overview
                genres_tv.text = buildGenreString(data.genres ?: emptyList())
            }

            override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                print("ERROR")
            }

        })
    }

    fun buildGenreString(genres: List<GenreModel>): String {
        var complete = "";
        var i = 1
        for (genre in genres) {
            val name = genre.name
            complete += name
            if (i < genres.count()) {
                complete += ","
            }
            complete += " "
            i += 1
        }
        return complete
    }

    fun goBack() {
        val navigator = findNavController(this)
        navigator.popBackStack()
    }

}