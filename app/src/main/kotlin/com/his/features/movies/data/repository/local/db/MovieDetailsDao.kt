package com.his.features.movies.data.repository.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

