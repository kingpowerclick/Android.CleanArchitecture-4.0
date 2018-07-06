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
import com.his.features.movies.data.GetMovieDetails
import com.his.features.movies.view.model.MovieDetails
import com.his.features.movies.viewmodel.MovieDetailsViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import io.reactivex.Observable
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MovieDetailsViewModelTest : AndroidTest() {

	private lateinit var movieDetailsViewModel: MovieDetailsViewModel

	@Mock
	private lateinit var getMovieDetails: GetMovieDetails

	@Before
	fun setUp() {
		movieDetailsViewModel = MovieDetailsViewModel(getMovieDetails)
	}

	@Test
	fun `loading movie details should update live data`() {
		val movieDetails = MovieDetails(0, "IronMan", "poster", "summary", "cast", "director", 2018, "trailer")
		given { getMovieDetails.buildUseCase(any()) }.willReturn { Observable.just(movieDetails) }

		movieDetailsViewModel.movieDetails.observeForever {
			with(it!!) {
				id shouldEqualTo 0
				title shouldBeEqualTo "IronMan"
				poster shouldBeEqualTo "poster"
				summary shouldBeEqualTo "summary"
				cast shouldBeEqualTo "cast"
				director shouldBeEqualTo "director"
				year shouldEqualTo 2018
				trailer shouldBeEqualTo "trailer"
			}
		}

		movieDetailsViewModel.loadMovieDetails(0)
	}
}