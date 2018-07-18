package com.his.features.movies.data.repository.local

import com.his.features.movies.data.entity.mapper.MovieDataMapper
import com.his.features.movies.data.repository.MoviesDataStore
import com.his.features.movies.data.repository.local.db.MovieDetailsDao
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesLocalDataStore @Inject constructor(private val dao: MovieDetailsDao,
                                               private val moviesDataMapper: MovieDataMapper): MoviesDataStore {
	override fun getMovies(): Observable<List<Movie>> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override fun getMoviesDetail(movieId: Int): Observable<MovieDetails> {
		return dao.getMovieDetailById(movieId).map { moviesDataMapper.toMovieDetails(it) }.toObservable()
	}
}