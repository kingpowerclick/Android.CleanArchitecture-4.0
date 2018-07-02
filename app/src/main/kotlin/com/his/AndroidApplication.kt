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
import com.crashlytics.android.Crashlytics
import com.his.core.di.ApplicationComponent
import com.his.core.di.ApplicationModule
import com.his.core.di.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric

class AndroidApplication : Application() {

	val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
		DaggerApplicationComponent
			.builder()
			.applicationModule(ApplicationModule(this))
			.build()
	}

	override fun onCreate() {
		super.onCreate()
		this.injectMembers()
		this.initializeLeakDetection()
		this.initializeFabric()
	}

	private fun injectMembers() = appComponent.inject(this)

	private fun initializeLeakDetection() {
		if (BuildConfig.DEBUG) LeakCanary.install(this)
	}

	private fun initializeFabric() {
		Fabric.with(this, Crashlytics())
	}
}
