package com.his.features.movies.data.repository.net.api

import com.his.features.movies.data.entity.mapper.MovieDataMapper
import com.his.features.movies.data.repository.MoviesDataStore
import com.his.features.movies.data.repository.local.db.MovieDetailsDao
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesCloudDataStore @Inject constructor(private val moviesApi: MoviesApi,
                                               private val dao: MovieDetailsDao,
                                               private val moviesDataMapper: MovieDataMapper) : MoviesDataStore {
	override fun getMovies(): Observable<List<Movie>> {
		return moviesApi.movies().map { it.map { moviesDataMapper.toMovie(it) } }
	}

	override fun getMoviesDetail(movieId: Int): Observable<MovieDetails> {
		return moviesApi.movieDetails(movieId = movieId)
			.doOnNext { dao.insertOrUpdateMovieDetail(moviesDataMapper.toMovieDetailsRoom(it)) }
			.map { moviesDataMapper.toMovieDetails(it) }
	}
}