package com.his.features.movies.view.ui

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.his.R
import com.his.features.movies.data.repository.local.AppDatabase
import com.his.features.movies.data.repository.local.Test
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DefaultObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test.*
import timber.log.Timber

class TestActivity : AppCompatActivity() {
	var textInput: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)

		setUI()
	}

	private fun setUI() {
		val appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database").build()

		buttonWrite.setOnClickListener {
			textInput = editTextInput.text.toString()
			Timber.e("$textInput")

			Flowable.fromCallable { appDatabase.testDao().insertInput(Test(0, textInput)) }
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe { Timber.e("insert complete") }

		}

		buttonRead.setOnClickListener {
			appDatabase.testDao().getInputAll()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe {
					it.forEach {
						Timber.e("${it.textInput}")
					}
				}
		}

	}

	private inner class TestEntityObserver : DefaultObserver<List<Test>>() {
		override fun onComplete() {
			TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
		}

		override fun onNext(t: List<Test>) {
			TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
		}

		override fun onError(e: Throwable) {
			TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
		}


	}
}