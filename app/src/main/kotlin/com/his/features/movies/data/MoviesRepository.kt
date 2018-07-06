/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.his.features.movies.data

import com.his.core.exception.NetworkConnectionException
import com.his.core.platform.NetworkHandler
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import javax.inject.Inject

interface MoviesRepository {
	fun movies(): Observable<List<Movie>>
	fun movieDetails(movieId: Int): Observable<MovieDetails>

	class Network
	@Inject constructor(private val networkHandler: NetworkHandler,
	                    private val service: MoviesService) : MoviesRepository {

		override fun movies(): Observable<List<Movie>> {
			return when (networkHandler.isConnected) {
				true        -> service.movies().map { it.map { it.toMovie() } }
				false, null -> Observable.error(NetworkConnectionException())
			}
		}

		override fun movieDetails(movieId: Int): Observable<MovieDetails> {
			return when (networkHandler.isConnected) {
				true        -> service.movieDetails(movieId).map { it.toMovieDetails() }
				false, null -> Observable.error(NetworkConnectionException())
			}
		}
	}
}
