package com.his.features.login.data.entity.mapper

import UserLoginQuery
import com.apollographql.apollo.api.Response
import com.his.features.login.view.model.UserLogin

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