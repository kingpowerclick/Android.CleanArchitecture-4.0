package com.his.features.movies.data.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [MoviesDetailRoom::class, Test::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun movieDetailsDao(): MovieDetailsDao
	abstract fun testDao(): TestDao
}

