package com.his.features.login

import com.his.UnitTest
import com.his.features.login.data.repository.LoginCloudDataStore
import com.his.features.login.data.repository.LoginDataRepository
import com.his.features.login.data.repository.LoginRepository
import com.his.features.login.view.model.UserLogin
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class LoginRepositoryTest : UnitTest() {
	private lateinit var loginRepository: LoginRepository
	private val textInputEmail = "admin@kingpowerclick.com"
	private val textInputPassword = "adminkingpowerclick"

	@Mock
	private lateinit var loginCloudDataStore: LoginCloudDataStore

	@Before
	fun setUp() {
		loginRepository = LoginDataRepository(loginCloudDataStore)
	}

	@Test
	fun `should return empty by default`() {
		given { loginCloudDataStore.login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword) }.willReturn(Observable.just(UserLogin.empty()))

		val userLogin = loginRepository.login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword).blockingFirst()
		userLogin shouldEqual UserLogin.empty()
		verify(loginCloudDataStore).login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword)
	}

	@Test
	fun `should get user login from service`() {
		given { loginCloudDataStore.login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword) }.willReturn(Observable.just(UserLogin("tokenType", "accessToken",
			"refreshToken", 1)))

		val userLogin = loginRepository.login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword).blockingFirst()
		userLogin shouldEqual UserLogin("tokenType", "accessToken", "refreshToken", 1)
		verify(loginCloudDataStore).login(KEY_CLIENT_ID, KEY_CLIENT_SECRET, textInputEmail, textInputPassword)
	}

	companion object {
		private const val KEY_CLIENT_ID = "2"
		private const val KEY_CLIENT_SECRET = "***REMOVED***"
	}
}