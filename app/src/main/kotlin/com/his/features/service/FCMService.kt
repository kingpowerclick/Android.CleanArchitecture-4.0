package com.his.features.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import javax.inject.Singleton

@Singleton
class FCMService : FirebaseMessagingService() {
	override fun onMessageReceived(message: RemoteMessage?) {

	}

	companion object
}