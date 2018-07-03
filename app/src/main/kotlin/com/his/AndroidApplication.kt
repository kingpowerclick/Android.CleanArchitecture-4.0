/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.his

import android.app.Application
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.core.CrashlyticsCore
import com.his.core.analytic.CrashlyticsTree
import com.his.core.di.ApplicationComponent
import com.his.core.di.ApplicationModule
import com.his.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class AndroidApplication : Application() {

	val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
		DaggerApplicationComponent
			.builder()
			.applicationModule(ApplicationModule(this))
			.build()
	}

	override fun onCreate() {
		super.onCreate()
		injectMembers()
		initializeLeakDetection()
		initializeFabric()
		initializeLogging()
	}

	private fun injectMembers() = appComponent.inject(this)

	private fun initializeLeakDetection() {
		if (BuildConfig.DEBUG) LeakCanary.install(this)
	}

	private fun initializeFabric() {
		val crashlytics = CrashlyticsCore.Builder()
			.disabled(BuildConfig.DEBUG)
			.build()
		val answer = Answers()

		Fabric.Builder(applicationContext)
			.kits(crashlytics, answer)
			.build()
			.run {
				Fabric.with(this)
			}
	}

	private fun initializeLogging() {
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
		else {
			Timber.plant(CrashlyticsTree())
		}
	}
}
