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
import com.his.core.platform.DefaultDisposable
import com.his.utils.TestDisposable
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
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

		given { getMovies.execute(any(), any()) }.willReturn(TestDisposable())
	}

	@Test
	fun `loading movies should execute usecase`() {
		// Act
		moviesViewModel.loadMovies()

		// Assert
		verify(getMovies).execute(any(), any())
	}

	@Test
	fun `loading movies success should update movie live data`() {
		// Act
		val expectedMoviesList = listOf(Movie(0, "IronMan"), Movie(1, "Batman"))
		movieCaptor.onNext(expectedMoviesList)

		// Assert
		moviesViewModel.movies.value.let {
			it!!.size shouldEqualTo 2
			it[0].id shouldEqualTo 0
			it[0].poster shouldBeEqualTo "IronMan"
			it[1].id shouldEqualTo 1
			it[1].poster shouldBeEqualTo "Batman"
		}
	}

	@Test
	fun `loading movies fail should update failure live data`() {
		// Act
		movieCaptor.onError(Throwable())

		// Assert
		moviesViewModel.failure.value.let { it is Throwable }
	}

	private val movieCaptor: DefaultDisposable<List<Movie>>
		get() {
			moviesViewModel.loadMovies()
			return argumentCaptor<DefaultDisposable<List<Movie>>>()
				.apply { verify(getMovies).execute(this.capture(), any()) }
				.firstValue
		}
}