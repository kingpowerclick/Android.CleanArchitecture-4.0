package com.his.features.login.data.repository

import UserLoginQuery
import com.apollographql.apollo.api.Response
import io.reactivex.Observable

interface LoginDataStore {
	fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<Response<UserLoginQuery.Data>>
}