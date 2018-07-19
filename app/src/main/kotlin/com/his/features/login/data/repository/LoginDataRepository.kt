package com.his.features.login.data.repository

import com.his.features.login.data.entity.mapper.LoginEntityDataMapper
import com.his.features.login.view.model.UserLogin
import io.reactivex.Observable
import javax.inject.Inject

class LoginDataRepository @Inject constructor(private val mLoginDataStore: LoginCloudDataStore) : LoginRepository {
	override fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin> {
		return mLoginDataStore.login(clientId, clientSecret, email, password).map { LoginEntityDataMapper().toUserLogin(it) }
	}
}