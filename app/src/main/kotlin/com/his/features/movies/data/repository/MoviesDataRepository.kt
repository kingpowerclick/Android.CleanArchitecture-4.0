package com.his.features.movies.data.repository

import com.his.core.exception.NetworkConnectionException
import com.his.core.platform.NetworkHandler
import com.his.features.movies.data.MoviesRepository
import com.his.features.movies.data.MoviesService
import com.his.features.movies.data.entity.mapper.MovieDataMapper
import com.his.features.movies.data.repository.local.MovieDetailsDao
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import io.reactivex.functions.Function
import timber.log.Timber
import javax.inject.Inject


class MoviesDataRepository @Inject constructor(private val networkHandler: NetworkHandler,
                                               private val service: MoviesService,
                                               private val dao: MovieDetailsDao) : MoviesRepository {
	override fun movies(): Observable<List<Movie>> {
		return when (networkHandler.isConnected) {
			true        -> service.movies().map { it.map { MovieDataMapper().toMovie(it) } }
			false, null -> Observable.error(NetworkConnectionException())
		}
	}

	override fun movieDetails(movieId: Int): Observable<MovieDetails> {
		return when (networkHandler.isConnected) {
			true        -> {

				val local = dao.getMovieDetailById(movieId)
					.toObservable()
					.doOnNext { Timber.e("Local") }
					.map { MovieDataMapper().toMovieDetails(it) }

				val api = service.movieDetails(movieId)
					.doOnNext {
						Timber.e("API")
						dao.insertOrUpdateMovieDetail(MovieDataMapper().toMovieDetailsRoom(it))
					}
					.map { MovieDataMapper().toMovieDetails(it) }

				return Observable.concat(local, api)
					.onErrorResumeNext(Function {
						service.movieDetails(movieId)
							.doOnNext {
								Timber.e("onErrorResumeNext")
								dao.insertOrUpdateMovieDetail(MovieDataMapper().toMovieDetailsRoom(it))
							}
							.map { MovieDataMapper().toMovieDetails(it) }
					})
					.distinct()
					.doOnNext { Timber.e("NEXT") }
			}

			false, null -> Observable.error(NetworkConnectionException())
		}
	}

}