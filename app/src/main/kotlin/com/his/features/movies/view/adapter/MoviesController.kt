package com.his.features.movies.view.adapter

import com.airbnb.epoxy.EpoxyController
import com.his.features.movies.view.adapter.epoxymodel.MoviesCardModel_
import com.his.features.movies.view.adapter.epoxymodel.OnMovieClickListener
import com.his.features.movies.view.model.MovieView

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