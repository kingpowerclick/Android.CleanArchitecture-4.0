package com.his.features.login.data.entity.mapper

data class UserLogin (
	val tokenType: String?,
	val accessToken: String?,
	val refreshToken: String?,
	val expiresIn: Int?
)