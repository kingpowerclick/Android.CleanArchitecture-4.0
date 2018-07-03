package com.his.core.analytic

import android.util.Log
import com.crashlytics.android.core.CrashlyticsCore
import timber.log.Timber

class CrashlyticsTree : Timber.Tree() {
	override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
		if (priority == Log.VERBOSE || priority == Log.DEBUG) {
			return
		}

		CrashlyticsCore.getInstance().run {
			setInt(CRASHLYTICS_KEY_PRIORITY, priority)
			setString(CRASHLYTICS_KEY_TAG, tag)
			setString(CRASHLYTICS_KEY_MESSAGE, message)

			if (t == null) {
				logException(Exception(message))
			}
			else {
				logException(t)
			}
		}
	}

	companion object {
		private const val CRASHLYTICS_KEY_PRIORITY = "priority"
		private const val CRASHLYTICS_KEY_TAG = "tag"
		private const val CRASHLYTICS_KEY_MESSAGE = "message"
	}
}