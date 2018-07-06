package com.his.features.movies.data.entity.mapper

import com.his.features.movies.data.entity.MovieDetailsEntity
import com.his.features.movies.data.entity.MovieEntity
import com.his.features.movies.view.model.Movie
import com.his.features.movies.view.model.MovieDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDataMapper @Inject constructor() {

	fun toMovie(entity: MovieEntity): Movie {
		return Movie(
			id = entity.id,
			poster = entity.poster
		)
	}

	fun toMovieDetails(entity: MovieDetailsEntity): MovieDetails {
		return MovieDetails(
			id = entity.id,
			title = entity.title,
			poster = entity.poster,
			summary = entity.summary,
			cast = entity.cast,
			director = entity.director,
			year = entity.year,
			trailer = entity.trailer
		)
	}
}