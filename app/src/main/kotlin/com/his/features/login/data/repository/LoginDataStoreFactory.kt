package com.his.features.login.data.repository

import com.kingpower.data.net.ApiConnection
import javax.inject.Inject

class LoginDataStoreFactory @Inject constructor(private val apiConnection: ApiConnection) {
	fun createCloudDataStore(): LoginDataStore {
		return LoginCloudDataStore(apiConnection.createGraphQLClient())
	}
}