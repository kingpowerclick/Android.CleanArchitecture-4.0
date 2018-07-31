package com.his.features.login.data.usecase

import com.his.core.interactor.UseCase
import com.his.features.login.data.entity.mapper.UserLogin
import com.his.features.login.data.repository.LoginRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoginByUserId @Inject constructor(private val loginRepository: LoginRepository) : UseCase<UserLogin, LoginByUserId.UserLoginParams>() {

	override fun buildUseCase(params: UserLoginParams): Observable<UserLogin> {
		return loginRepository.login(clientId = params.clientId,
			clientSecret = params.clientSecret,
			email = params.email,
			password = params.password)
	}

	class UserLoginParams constructor(
		var clientId: String,
		var clientSecret: String,
		var email: String,
		var password: String
	) : UseCase.Parameter.FeatureParameter()
}