package com.juan.bravoTest.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juan.bravoTest.R
import com.juan.bravoTest.adapters.MovieListAdapter
import com.juan.bravoTest.databinding.FragmentHomeBinding
import com.juan.bravoTest.models.GenericResultModel
import com.juan.bravoTest.models.MovieModel
import com.juan.bravoTest.services.MovieService
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private val movieService: MovieService = MovieService()
    private var popularMoviesAdapter: MovieListAdapter = MovieListAdapter(emptyList(), {onMovieSelected(it)})
    private var upcommingMoviesAdapter: MovieListAdapter = MovieListAdapter(emptyList(), {onMovieSelected(it)})
    private var topRatedMoviesAdapter: MovieListAdapter = MovieListAdapter(emptyList(), {onMovieSelected(it)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val res = FragmentHomeBinding.inflate(LayoutInflater.from(context), null, false)
        return res.root
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        loadPopularMovies()
        loadTopRatedMovies()
        loadUpcommingMovies()
    }

    private fun loadPopularMovies() {
        val call = movieService.API.getPopular()
        val layourManager = LinearLayoutManager(context)
        layourManager.orientation = LinearLayoutManager.HORIZONTAL
        popular_movies_rv.layoutManager = layourManager
        popular_movies_rv.adapter = popularMoviesAdapter
        call.enqueue(object: Callback<GenericResultModel<List<MovieModel>>> {
            
            override fun onResponse(
                call: Call<GenericResultModel<List<MovieModel>>>,
                response: Response<GenericResultModel<List<MovieModel>>>
            ) {
                val body = response.body()
                val data = body!!.results
                popularMoviesAdapter.updateData(data)
            }

            override fun onFailure(call: Call<GenericResultModel<List<MovieModel>>>, t: Throwable) {
                print("Error Occurred")
            }

        })
    }

    private fun loadUpcommingMovies() {
        val call = movieService.API.getUpcoming()
        val layourManager = LinearLayoutManager(context)
        layourManager.orientation = LinearLayoutManager.HORIZONTAL
        upcomming_rv.layoutManager = layourManager
        upcomming_rv.adapter = upcommingMoviesAdapter
        call.enqueue(object: Callback<GenericResultModel<List<MovieModel>>> {

            override fun onResponse(
                call: Call<GenericResultModel<List<MovieModel>>>,
                response: Response<GenericResultModel<List<MovieModel>>>
            ) {
                val body = response.body()
                val data = body!!.results
                upcommingMoviesAdapter.updateData(data)
            }

            override fun onFailure(call: Call<GenericResultModel<List<MovieModel>>>, t: Throwable) {
                print("Error Occurred")
            }

        })
    }

    private fun loadTopRatedMovies() {
        val call = movieService.API.getTopRated()
        val layourManager = LinearLayoutManager(context)
        layourManager.orientation = LinearLayoutManager.HORIZONTAL
        top_rated_rv.layoutManager = layourManager
        top_rated_rv.adapter = topRatedMoviesAdapter
        call.enqueue(object: Callback<GenericResultModel<List<MovieModel>>> {

            override fun onResponse(
                call: Call<GenericResultModel<List<MovieModel>>>,
                response: Response<GenericResultModel<List<MovieModel>>>
            ) {
                val body = response.body()
                val data = body!!.results
                topRatedMoviesAdapter.updateData(data)
            }

            override fun onFailure(call: Call<GenericResultModel<List<MovieModel>>>, t: Throwable) {
                print("Error Occurred")
            }

        })
    }

    private fun onMovieSelected(movie: MovieModel) {
        val navController = findNavController(this)
        val bundle = bundleOf("movieId" to movie.id)
        navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
    }
    
}