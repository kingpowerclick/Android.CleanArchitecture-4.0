package com.his.features.movies.data.repository

import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable

interface MoviesDataStore {
	fun getMovies(): Observable<List<Movie>>

	fun getMoviesDetail(movieId: Int): Observable<MovieDetails>
}