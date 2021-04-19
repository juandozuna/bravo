package com.juan.bravoTest.models

import com.google.gson.annotations.SerializedName

data class MovieModel(
    val adult: Boolean,
    val budget: Double,
    val genreIds: List<Int>?,
    val genres: List<GenreModel>?,
    val id: Int,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val releaseDate: String,
    val revenue: Double,
    val runtime: Double,
    val status: String,
    val title: String,
    val posterPath: String,
    val voteAverage: Double
)