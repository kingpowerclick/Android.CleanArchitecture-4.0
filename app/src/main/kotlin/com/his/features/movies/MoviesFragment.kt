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

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.his.R
import com.his.core.exception.NetworkConnectionException
import com.his.core.exception.ServerErrorException
import com.his.core.extension.*
import com.his.core.navigation.Navigator
import com.his.core.platform.BaseFragment
import com.his.features.movies.adapter.epoxymodel.MoviesController
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

class MoviesFragment : BaseFragment() {

	@Inject lateinit var navigator: Navigator
	private lateinit var moviesViewModel: MoviesViewModel

	private val moviesController by lazy { MoviesController() }

	override fun layoutId() = R.layout.fragment_movies

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		appComponent.inject(this)

		moviesViewModel = viewModel(viewModelFactory) {
			observe(movies, ::renderMoviesList)
			failure(failure, ::handleFailure)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initializeView()
		loadMoviesList()
	}


	private fun initializeView() {
		recyclerViewMovie.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
		recyclerViewMovie.setController(moviesController)

		moviesController.cardMovieOnClickListener = { movieModel, navigationExtras ->
			navigator.showMovieDetails(activity!!, movieModel, navigationExtras)
		}
	}

	private fun loadMoviesList() {
		emptyView.invisible()
		recyclerViewMovie.visible()
		showProgress()
		moviesViewModel.loadMovies()
	}

	private fun renderMoviesList(movies: List<MovieView>?) {
		movies?.isNotEmpty().let {
			moviesController.setMoviesList(movies!!)
		}

		moviesController.requestModelBuild()
		hideProgress()
	}

	private fun handleFailure(failure: Throwable?) {
		when (failure) {
			is NetworkConnectionException -> renderFailure(R.string.failure_network_connection)
			is ServerErrorException       -> renderFailure(R.string.failure_server_error)
		}
	}

	private fun renderFailure(@StringRes message: Int) {
		recyclerViewMovie.invisible()
		emptyView.visible()
		hideProgress()
		notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
	}
}
