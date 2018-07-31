package com.his.features.login.data.entity.mapper

import com.his.core.extension.empty

data class UserLogin(
	val tokenType: String?,
	val accessToken: String?,
	val refreshToken: String?,
	val expiresIn: Int?) {

	companion object {
		fun empty() = UserLogin(String.empty(), String.empty(), String.empty(), 0)
	}
}