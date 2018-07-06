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

import com.his.UnitTest
import com.his.core.interactor.UseCase
import com.his.features.movies.data.GetMovies
import com.his.features.movies.data.MoviesRepository
import com.his.features.movies.view.model.Movie
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetMoviesTest : UnitTest() {

	private lateinit var getMovies: GetMovies

	@Mock
	private lateinit var moviesRepository: MoviesRepository

	@Before
	fun setUp() {
		getMovies = GetMovies(moviesRepository)
		given { moviesRepository.movies() }.willReturn(Observable.just(listOf(Movie.empty())))
	}

	@Test
	fun `should get data from repository`() {
		getMovies.buildUseCase(UseCase.Parameter.None())
			.subscribe()

		verify(moviesRepository).movies()
		verifyNoMoreInteractions(moviesRepository)
	}
}
