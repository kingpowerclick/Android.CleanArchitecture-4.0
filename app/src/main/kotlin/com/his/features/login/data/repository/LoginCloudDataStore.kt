/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.his.features.login.data.repository

import UserLoginQuery
import com.his.features.login.data.entity.mapper.LoginEntityDataMapper
import com.his.core.platform.graphql.GraphQLClient
import com.his.features.login.view.model.UserLogin
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginCloudDataStore @Inject constructor(private val graphQLClient: GraphQLClient,
                                              private val loginEntityDataMapper: LoginEntityDataMapper) : LoginDataStore {
	override fun login(clientId: String, clientSecret: String, email: String, password: String): Observable<UserLogin> {
		val login = UserLoginQuery.builder()
			.clientId(clientId)
			.clientSecret(clientSecret)
			.email(email)
			.password(password)
			.build()

		return graphQLClient.queryRx(login).map { loginEntityDataMapper.toUserLogin(it) }
	}
}

