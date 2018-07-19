package com.his.features.login.data.repository

import com.his.features.login.view.model.UserLogin
import io.reactivex.Observable

interface LoginDataStore {
	fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin>
}