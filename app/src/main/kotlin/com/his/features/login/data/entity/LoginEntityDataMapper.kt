package com.his.features.login.data.entity

import UserLoginQuery
import com.apollographql.apollo.api.Response
import com.his.features.login.domain.model.UserLogin

class LoginEntityDataMapper {
	fun toUserLogin(entity: Response<UserLoginQuery.Data>): UserLogin? {
		return entity.data()?.login()?.let {
			UserLogin(
				tokenType = it.tokenType(),
				accessToken = it.accessToken(),
				expiresIn = it.expiresIn(),
				refreshToken = it.refreshToken()
			)
		}
	}
}