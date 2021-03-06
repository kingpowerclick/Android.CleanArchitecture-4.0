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
package com.his.features.movies.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.his.core.interactor.UseCase.Parameter
import com.his.core.platform.BaseViewModel
import com.his.core.platform.DefaultDisposable
import com.his.features.movies.data.usecase.GetMovies
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieView
import javax.inject.Inject

class MoviesViewModel
@Inject constructor(private val getMovies: GetMovies) : BaseViewModel() {

	var movies: MutableLiveData<List<MovieView>> = MutableLiveData()

	fun loadMovies() {
		getMovies
			.execute(GetMoviesObserver(), Parameter.None())
			.autoClear()
	}

	private fun handleMovieList(movies: List<Movie>) {
		this.movies.value = movies.map { MovieView(it.id, it.poster) }
	}

	private inner class GetMoviesObserver : DefaultDisposable<List<Movie>>() {
		override fun onError(e: Throwable) {
			handleFailure(e)
		}

		override fun onNext(value: List<Movie>) {
			handleMovieList(value)
		}
	}
}