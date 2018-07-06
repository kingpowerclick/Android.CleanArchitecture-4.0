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
package com.his.core.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.ImageView
import com.his.core.extension.empty
import com.his.features.login.Authenticator
import com.his.features.login.LoginActivity
import com.his.features.movies.view.ui.MovieDetailsActivity
import com.his.features.movies.view.model.MovieView
import com.his.features.movies.view.ui.MoviesActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

	fun showMain(context: Context) {
		when (authenticator.userLoggedIn()) {
			true  -> showMovies(context)
			false -> showLogin(context)
		}
	}

	fun showMovieDetails(activity: FragmentActivity, movie: MovieView, navigationExtras: Extras) {
		val intent = MovieDetailsActivity.callingIntent(activity, movie)
		val sharedView = navigationExtras.transitionSharedElement as ImageView
		val activityOptions = ActivityOptionsCompat
			.makeSceneTransitionAnimation(activity, sharedView, ViewCompat.getTransitionName(sharedView))
		activity.startActivity(intent, activityOptions.toBundle())
	}

	fun openVideo(context: Context, videoUrl: String) {
		try {
			context.startActivity(createYoutubeIntent(videoUrl))
		}
		catch (ex: ActivityNotFoundException) {
			context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
		}
	}

	private fun showLogin(context: Context) = context.startActivity(LoginActivity.callingIntent(context))

	private fun showMovies(context: Context) = context.startActivity(MoviesActivity.callingIntent(context))

	private fun createYoutubeIntent(videoUrl: String): Intent {
		val videoId = when {
			videoUrl.startsWith(VIDEO_URL_HTTP)  -> videoUrl.replace(VIDEO_URL_HTTP, String.empty())
			videoUrl.startsWith(VIDEO_URL_HTTPS) -> videoUrl.replace(VIDEO_URL_HTTPS, String.empty())
			else                                 -> videoUrl
		}

		return Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId")).apply {
			putExtra("force_fullscreen", true)
		}
	}

	class Extras(val transitionSharedElement: View)

	companion object {
		private const val VIDEO_URL_HTTP = "http://www.youtube.com/watch?v="
		private const val VIDEO_URL_HTTPS = "https://www.youtube.com/watch?v="
	}
}


