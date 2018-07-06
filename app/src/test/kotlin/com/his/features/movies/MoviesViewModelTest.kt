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

import com.his.AndroidTest
import com.his.features.movies.data.GetMovies
import com.his.features.movies.view.model.Movie
import com.his.features.movies.viewmodel.MoviesViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Observable
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MoviesViewModelTest : AndroidTest() {

	private lateinit var moviesViewModel: MoviesViewModel

	@Mock
	private lateinit var getMovies: GetMovies

	@Before
	fun setUp() {
		moviesViewModel = MoviesViewModel(getMovies)
	}

	@Test
	fun `loading movies should update live data`() {
		val moviesList = listOf(Movie(0, "IronMan"), Movie(1, "Batman"))
		given { getMovies.buildUseCase(any()) }.willReturn { Observable.just(moviesList) }

		moviesViewModel.movies.observeForever {
			it!!.size shouldEqualTo 2
			it[0].id shouldEqualTo 0
			it[0].poster shouldBeEqualTo "IronMan"
			it[1].id shouldEqualTo 1
			it[1].poster shouldBeEqualTo "Batman"
		}

		moviesViewModel.loadMovies()
	}
}