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
import com.his.core.extension.empty
import com.his.core.platform.NetworkHandler
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import io.reactivex.Observable
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class MoviesRepositoryTest : UnitTest() {

	private lateinit var networkRepository: MoviesRepository.Network

	@Mock
	private lateinit var networkHandler: NetworkHandler
	@Mock
	private lateinit var service: MoviesService

	@Before
	fun setUp() {
		networkRepository = MoviesRepository.Network(networkHandler, service)
	}

	@Test
	fun `should return empty list by default`() {
		given { networkHandler.isConnected }.willReturn(true)
		given { service.movies() }.willReturn(Observable.just(emptyList()))

		val movies = networkRepository.movies().blockingFirst()

		movies shouldEqual emptyList()
		verify(service).movies()
	}

	@Test
	fun `should get movie list from service`() {
		given { networkHandler.isConnected }.willReturn(true)
		given { service.movies() }.willReturn(Observable.just(listOf(MovieEntity(1, "poster"))))

		val movies = networkRepository.movies().blockingFirst()

		movies shouldEqual listOf(Movie(1, "poster"))
		verify(service).movies()
	}

	@Test
	fun `movies service should return network failure when no connection`() {
		given { networkHandler.isConnected }.willReturn(false)

		networkRepository.movies()

		verifyZeroInteractions(service)
	}

	@Test
	fun `movies service should return network failure when undefined connection`() {
		given { networkHandler.isConnected }.willReturn(null)

		networkRepository.movies()

		verifyZeroInteractions(service)
	}

	@Test
	fun `should return empty movie details by default`() {
		given { networkHandler.isConnected }.willReturn(true)
		given { service.movieDetails(1) }.willReturn(Observable.just(MovieDetailsEntity.empty()))

		val movieDetails = networkRepository.movieDetails(1).blockingFirst()

		movieDetails shouldEqual MovieDetails.empty()
		verify(service).movieDetails(1)
	}

	@Test
	fun `should get movie details from service`() {
		given { networkHandler.isConnected }.willReturn(true)
		given { service.movieDetails(1) }.willReturn(Observable.just(
			MovieDetailsEntity(8, "title", String.empty(), String.empty(), String.empty(), String.empty(), 0, String.empty())))

		val movieDetails = networkRepository.movieDetails(1).blockingFirst()

		movieDetails shouldEqual MovieDetails(8, "title", String.empty(), String.empty(), String.empty(), String.empty(), 0, String.empty())
		verify(service).movieDetails(1)
	}

	@Test
	fun `movie details service should return network failure when no connection`() {
		given { networkHandler.isConnected }.willReturn(false)

		networkRepository.movieDetails(1)

		verifyZeroInteractions(service)
	}

	@Test
	fun `movie details service should return network failure when undefined connection`() {
		given { networkHandler.isConnected }.willReturn(null)

		networkRepository.movieDetails(1)

		verifyZeroInteractions(service)
	}
}