package com.his.features.login.data.repository

import com.his.features.login.data.entity.mapper.UserLogin
import io.reactivex.Observable
import javax.inject.Inject

class LoginDataRepository @Inject constructor(private val loginDataStore: LoginCloudDataStore) : LoginRepository {
	override fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin> {
		return loginDataStore.login(clientId, clientSecret, email, password)
	}
}