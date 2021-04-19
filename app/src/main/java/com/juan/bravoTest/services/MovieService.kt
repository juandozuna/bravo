package com.juan.bravoTest.services

import android.content.Context
import com.juan.bravoTest.models.GenericResultModel
import com.juan.bravoTest.models.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class MovieService(): BaseService() {
    var API: MovieService = retrofit.create(MovieService::class.java)

    interface MovieService {

        @GET("movie/latest")
        fun getLatestMovies(): Call<MovieModel>;

        @GET("movie/popular")
        fun getPopular(): Call<GenericResultModel<List<MovieModel>>>

        @GET("movie/top_rated")
        fun getTopRated(): Call<GenericResultModel<List<MovieModel>>>

        @GET("movie/upcoming")
        fun getUpcoming(): Call<GenericResultModel<List<MovieModel>>>

        @GET("movie/{id}")
        fun getMovieDetails(@Path("id") id: Int): Call<MovieModel>
    }
}