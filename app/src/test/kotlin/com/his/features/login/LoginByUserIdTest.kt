package com.his.features.login

import com.his.UnitTest
import com.his.features.login.data.repository.LoginRepository
import com.his.features.login.data.usecase.LoginByUserId
import com.his.features.login.view.model.UserLogin
import com.his.features.login.view.model.params.UserLoginParams
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class LoginByUserIdTest : UnitTest() {
	private lateinit var loginByUserId: LoginByUserId

	private val textInputEmail = "admin@kingpowerclick.com"
	private val textInputPassword = "adminkingpowerclick"

	private val params = UserLoginParams(
		clientId = KEY_CLIENT_ID,
		clientSecret = KEY_CLIENT_SECRET,
		email = textInputEmail,
		password = textInputPassword
	)

	@Mock
	private lateinit var loginRepository: LoginRepository

	@Before
	fun setUp() {
		loginByUserId = LoginByUserId(loginRepository)
		given { loginRepository.login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword) }.willReturn(Observable.just(UserLogin.empty()))
	}

	@Test
	fun `should get data from repository`() {
		loginByUserId.buildUseCase(params)
			.subscribe()

		verify(loginRepository).login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword)
		verifyNoMoreInteractions(loginRepository)
	}

	companion object {
		private const val KEY_CLIENT_ID = "2"
		private const val KEY_CLIENT_SECRET = "***REMOVED***"
	}
}