package com.his.features.login.data.entity.mapper

import fragment.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginEntityDataMapper @Inject constructor() {
	fun toUserLogin(entity: LoginResponse?): UserLogin? {
		return entity.let {
			UserLogin(tokenType = it?.tokenType(), accessToken = it?.accessToken(), expiresIn = it?.expiresIn(), refreshToken = it?.refreshToken())
		}
	}
}