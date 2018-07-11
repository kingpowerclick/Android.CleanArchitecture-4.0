package com.his.features.movies.data.repository.local.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface MovieDetailsDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertOrUpdateMovieDetail(moviesDetailRoom: MoviesDetailRoom)

	@Query("SELECT * FROM movie_details")
	fun getAllMovieDetail(): Single<List<MoviesDetailRoom>>

	@Query("SELECT * FROM movie_details WHERE id = :id")
	fun getMovieDetailById(id: Int): Single<MoviesDetailRoom>

	@Query("DELETE FROM movie_details")
	fun deleteTable()
}

