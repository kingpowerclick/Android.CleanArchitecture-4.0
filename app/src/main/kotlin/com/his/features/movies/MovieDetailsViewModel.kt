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
package com.his.features.movies

import android.arch.lifecycle.MutableLiveData
import com.his.core.platform.BaseViewModel
import com.his.core.platform.DefaultDisposable
import com.his.features.movies.GetMovieDetails.Params
import javax.inject.Inject

class MovieDetailsViewModel
@Inject constructor(private val getMovieDetails: GetMovieDetails) : BaseViewModel() {

	var movieDetails: MutableLiveData<MovieDetailsView> = MutableLiveData()

	fun loadMovieDetails(movieId: Int) {
		getMovieDetails.execute(GetMovieDetailsObserver(), Params(movieId))
	}

	override fun onCleared() {
		getMovieDetails.dispose()
	}

	private fun handleMovieDetails(movie: MovieDetails) {
		this.movieDetails.value = MovieDetailsView(movie.id, movie.title, movie.poster,
			movie.summary, movie.cast, movie.director, movie.year, movie.trailer)
	}

	inner class GetMovieDetailsObserver : DefaultDisposable<MovieDetails>() {
		override fun onError(e: Throwable) {
			handleFailure(e)
		}

		override fun onNext(value: MovieDetails) {
			handleMovieDetails(value)
		}
	}
}