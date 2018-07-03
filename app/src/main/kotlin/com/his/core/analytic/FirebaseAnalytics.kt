package com.his.core.analytic

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.his.core.analytic.base.Analytic

class FirebaseAnalytics(context: Context) : Analytic {
	private val fireBaseAnalytics by lazy { FirebaseAnalytics.getInstance(context) }
}
