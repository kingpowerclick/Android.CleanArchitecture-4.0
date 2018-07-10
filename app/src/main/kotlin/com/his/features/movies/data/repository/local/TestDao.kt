package com.his.features.movies.data.repository.local

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import io.reactivex.Flowable

@Dao
interface TestDao {
	@Insert(onConflict = REPLACE)
	fun insertInput(test: Test)

	@Update
	fun updateInput(test: Test)

	@Delete
	fun deleteInput(test: Test)

	@Query("SELECT * FROM test")
	fun getInputAll(): Flowable<List<Test>>

	@Query("DELETE FROM test")
	fun deleteTable()
}