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
package com.his.features.movies.view.ui

import android.os.Bundle
import android.view.View
import com.his.R
import com.his.core.exception.NetworkConnectionException
import com.his.core.exception.ServerErrorException
import com.his.core.extension.*
import com.his.core.navigation.Navigator
import com.his.core.platform.BaseFragment
import com.his.features.movies.view.MovieDetailsAnimator
import com.his.features.movies.view.model.MovieDetailsView
import com.his.features.movies.view.model.MovieView
import com.his.features.movies.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment() {

	@Inject
	lateinit var navigator: Navigator
	@Inject
	lateinit var movieDetailsAnimator: MovieDetailsAnimator

	private lateinit var movieDetailsViewModel: MovieDetailsViewModel

	override fun layoutId() = R.layout.fragment_movie_details

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		appComponent.inject(this)
		activity?.let { movieDetailsAnimator.postponeEnterTransition(it) }

		movieDetailsViewModel = viewModel(viewModelFactory) {
			observe(movieDetails, ::renderMovieDetails)
			failure(error, ::handleFailure)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (firstTimeCreated(savedInstanceState)) {
			movieDetailsViewModel.loadMovieDetails((arguments?.get(PARAM_MOVIE) as MovieView).id)
		}
		else {
			movieDetailsAnimator.scaleUpView(moviePlay)
			movieDetailsAnimator.cancelTransition(imageViewMoviePoster)
			imageViewMoviePoster.loadFromUrl((arguments!![PARAM_MOVIE] as MovieView).poster)
		}
	}

	override fun onBackPressed() {
		movieDetailsAnimator.fadeInvisible(scrollView, movieDetails)
		if (moviePlay.isVisible())
			movieDetailsAnimator.scaleDownView(moviePlay)
		else
			movieDetailsAnimator.cancelTransition(imageViewMoviePoster)
	}

	private fun renderMovieDetails(movie: MovieDetailsView?) {
		movie?.let {
			with(movie) {
				activity?.let {
					imageViewMoviePoster.loadUrlAndPostponeEnterTransition(poster, it)
					it.toolbar.title = title
				}
				movieSummary.text = summary
				movieCast.text = cast
				movieDirector.text = director
				movieYear.text = year.toString()
				moviePlay.setOnClickListener { navigator.openVideo(activity!!, trailer) }
			}
		}
		movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
		movieDetailsAnimator.scaleUpView(moviePlay)
	}

	private fun handleFailure(failure: Throwable?) {
		when (failure) {
			is NetworkConnectionException -> {
				notify(R.string.failure_network_connection); close()
			}
			is ServerErrorException       -> {
				notify(R.string.failure_server_error); close()
			}
		}
	}

	companion object {
		private const val PARAM_MOVIE = "param_movie"

		fun forMovie(movie: MovieView): MovieDetailsFragment {
			val movieDetailsFragment = MovieDetailsFragment()
			val arguments = Bundle()
			arguments.putParcelable(PARAM_MOVIE, movie)
			movieDetailsFragment.arguments = arguments

			return movieDetailsFragment
		}
	}
}
