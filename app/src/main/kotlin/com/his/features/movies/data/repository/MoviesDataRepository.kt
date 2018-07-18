package com.his.features.movies.data.repository

import com.his.core.exception.NetworkConnectionException
import com.his.core.platform.NetworkHandler
import com.his.features.movies.data.repository.local.MoviesLocalDataStore
import com.his.features.movies.data.repository.net.api.MoviesCloudDataStore
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject


class MoviesDataRepository @Inject constructor(private val networkHandler: NetworkHandler,
                                               private val moviesCloudDataStore: MoviesCloudDataStore,
                                               private val moviesLocalDataStore: MoviesLocalDataStore) : MoviesRepository {
	override fun movies(): Observable<List<Movie>> {
		return when (networkHandler.isConnected) {
			true        -> moviesCloudDataStore.getMovies()
			false, null -> Observable.error(NetworkConnectionException())
		}
	}

	override fun movieDetails(movieId: Int): Observable<MovieDetails> {
		return when (networkHandler.isConnected) {
			true        -> {
				val local = moviesLocalDataStore.getMoviesDetail(movieId)

				val api = moviesCloudDataStore.getMoviesDetail(movieId)

				return Observable.concat(local, api)
					.onErrorResumeNext(Function {
						moviesCloudDataStore.getMoviesDetail(movieId)
					})
					.distinct()
			}

			false, null -> Observable.error(NetworkConnectionException())
		}
	}

}