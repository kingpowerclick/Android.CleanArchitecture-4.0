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
package com.his.features.movies.data.usecase

import com.his.core.interactor.UseCase
import com.his.features.movies.data.repository.MoviesRepository
import com.his.features.movies.view.model.MovieDetails
import io.reactivex.Observable
import javax.inject.Inject

class GetMovieDetails
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<MovieDetails, GetMovieDetails.Params>() {

	override fun buildUseCase(params: Params): Observable<MovieDetails> {
		return moviesRepository.movieDetails(params.id)
	}

	data class Params(val id: Int) : Parameter.FeatureParameter()
}
