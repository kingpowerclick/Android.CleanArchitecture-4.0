package com.his.features.login.data.usecase

import com.his.core.interactor.UseCase
import com.his.features.login.data.repository.LoginRepository
import com.his.features.login.view.model.UserLogin
import com.his.features.login.view.model.params.UserLoginParams
import io.reactivex.Observable
import javax.inject.Inject

class LoginByUserId @Inject constructor(private val loginRepository: LoginRepository) : UseCase<UserLogin, UserLoginParams>() {

	override fun buildUseCase(params: UserLoginParams): Observable<UserLogin> {
		return loginRepository.login(clientId = params.clientId,
			clientSecret = params.clientSecret,
			email = params.email,
			password = params.password)
	}
}