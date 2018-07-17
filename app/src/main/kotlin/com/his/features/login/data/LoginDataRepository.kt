package com.his.features.login.data

import com.his.features.login.data.entity.LoginEntityDataMapper
import com.his.features.login.data.repository.LoginDataStoreFactory
import com.his.features.login.domain.model.UserLogin
import com.his.features.login.domain.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginDataRepository @Inject constructor(private val mLoginDataStoreFactory: LoginDataStoreFactory) : LoginRepository {
	override fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin> {
		return mLoginDataStoreFactory.createCloudDataStore().login(clientId, clientSecret, email, password).map { LoginEntityDataMapper().toUserLogin(it) }
	}
}