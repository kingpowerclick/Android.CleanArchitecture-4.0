package com.his.features.movies.data.repository

import com.his.core.exception.NetworkConnectionException
import com.his.core.platform.NetworkHandler
import com.his.features.movies.data.MoviesRepository
import com.his.features.movies.data.MoviesService
import com.his.features.movies.data.entity.mapper.MovieDataMapper
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import javax.inject.Inject

class MoviesDataRepository @Inject constructor(private val networkHandler: NetworkHandler,
                                              private val service: MoviesService) : MoviesRepository {
	override fun movies(): Observable<List<Movie>> {
		return when (networkHandler.isConnected) {
			true        -> service.movies().map { it.map { MovieDataMapper().toMovie(it) } }
			false, null -> Observable.error(NetworkConnectionException())
		}
	}

	override fun movieDetails(movieId: Int): Observable<MovieDetails> {
		return when (networkHandler.isConnected) {
			true        -> service.movieDetails(movieId).map { MovieDataMapper().toMovieDetails(it) }
			false, null -> Observable.error(NetworkConnectionException())
		}
	}
}