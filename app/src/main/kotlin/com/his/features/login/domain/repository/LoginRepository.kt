package com.his.features.login.domain.repository

import com.his.features.login.domain.model.UserLogin
import io.reactivex.Observable

interface LoginRepository {
	fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin>
}