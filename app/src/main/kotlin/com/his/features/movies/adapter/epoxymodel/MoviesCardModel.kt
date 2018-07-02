package com.his.features.movies.adapter.epoxymodel

import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.his.R
import com.his.core.extension.loadFromUrl
import com.his.core.navigation.Navigator
import com.his.features.movies.MovieView
import kotlinx.android.synthetic.main.row_movie.view.*

typealias OnMovieClickListener = (movieModel: MovieView, extra: Navigator.Extras) -> Unit

@EpoxyModelClass(layout = R.layout.row_movie)
abstract class MoviesCardModel : EpoxyModelWithHolder<MoviesCardModel.ViewHolder>() {

	@EpoxyAttribute(EpoxyAttribute.Option.NoGetter) protected var moview: MovieView? = null
	@EpoxyAttribute(EpoxyAttribute.Option.NoGetter, EpoxyAttribute.Option.DoNotHash) protected var movieClickListener: OnMovieClickListener? = null

	override fun bind(view: ViewHolder) {
		super.bind(view)
		with(view) {
			moview?.poster?.let {
				moviePoster.loadFromUrl(it)
			}
			movieCard.setOnClickListener {
				moview?.let {
					movieClickListener?.invoke(it, Navigator.Extras(moviePoster))
				}
			}
		}
	}

	inner class ViewHolder : EpoxyHolder() {
		lateinit var moviePoster: ImageView
		lateinit var movieCard: CardView

		override fun bindView(itemView: View) {
			moviePoster = itemView.moviePoster
			movieCard = itemView.movieCard
		}
	}
}