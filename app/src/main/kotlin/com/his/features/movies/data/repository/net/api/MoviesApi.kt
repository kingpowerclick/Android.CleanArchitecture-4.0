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
package com.his.features.movies.data.repository.net.api

import com.his.features.movies.data.entity.MovieDetailsEntity
import com.his.features.movies.data.entity.MovieEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
	@GET(MOVIES)
	fun movies(): Observable<List<MovieEntity>>

	@GET(MOVIE_DETAILS)
	fun movieDetails(@Path(PARAM_MOVIE_ID) movieId: Int): Observable<MovieDetailsEntity>

	companion object {
		private const val PARAM_MOVIE_ID = "movieId"
		private const val MOVIES = "movies.json"
		private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
	}
}
