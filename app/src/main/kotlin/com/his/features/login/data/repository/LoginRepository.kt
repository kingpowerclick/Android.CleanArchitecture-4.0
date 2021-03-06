package com.his.features.login.data.repository

import com.his.features.login.data.entity.mapper.UserLogin
import io.reactivex.Observable

interface LoginRepository {
	fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin>
}