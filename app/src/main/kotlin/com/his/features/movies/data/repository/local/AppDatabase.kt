package com.his.features.movies.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.his.features.movies.data.repository.local.db.MovieDetailsDao
import com.his.features.movies.data.repository.local.db.MoviesDetailRoom

@Database(entities = [MoviesDetailRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun movieDetailsDao(): MovieDetailsDao
}

