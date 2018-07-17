package com.his.features.login.domain.model

data class UserLogin (
	val tokenType: String?,
	val accessToken: String?,
	val refreshToken: String?,
	val expiresIn: Int?
)