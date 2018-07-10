package com.his.features.movies.data.repository.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "test")
data class Test(
	@PrimaryKey(autoGenerate = true) var id: Int = 0,
	@ColumnInfo(name = "input") var textInput: String? = null)