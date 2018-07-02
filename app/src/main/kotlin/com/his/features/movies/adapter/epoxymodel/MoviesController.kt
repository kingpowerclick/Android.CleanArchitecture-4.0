package com.his.features.movies.adapter.epoxymodel

import com.airbnb.epoxy.EpoxyController
import com.his.features.movies.MovieView

open class MoviesController : EpoxyController() {
	var cardMovieOnClickListener: OnMovieClickListener? = null
	private var mData = listOf<MovieView>()

	fun setMoviesList(moviesList : List<MovieView>) {
		this.mData = moviesList
	}

	override fun buildModels() {
		if (mData.isNotEmpty()) {
			mData.forEach { item ->
				MoviesCardModel_()
					.id(item.id)
					.moview(item)
					.movieClickListener(cardMovieOnClickListener)
					.addTo(this)
			}
		}
	}
}