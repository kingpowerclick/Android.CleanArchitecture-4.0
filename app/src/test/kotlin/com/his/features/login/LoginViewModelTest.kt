package com.his.features.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.test.mock.MockContext
import com.his.UnitTest
import com.his.core.platform.DefaultDisposable
import com.his.features.login.data.entity.mapper.UserLogin
import com.his.features.login.data.usecase.LoginByUserId
import com.his.features.login.viewmodel.LoginViewModel
import com.his.utils.TestDisposable
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.given
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.verify


class LoginViewModelTest : UnitTest() {
	private lateinit var loginViewModel: LoginViewModel
	private lateinit var context: Context
	private val textInputEmail = "admin@kingpowerclick.com"
	private val textInputPassword = "adminkingpowerclick"

	@get:Rule
	val rule: TestRule = InstantTaskExecutorRule()

	@Mock
	private lateinit var loginByUserId: LoginByUserId

	@Before
	fun setup() {
		context = MockContext()

		loginViewModel = LoginViewModel(loginByUserId)
		given { loginByUserId.execute(any(), any()) }.willReturn(TestDisposable())
	}

	@Test
	fun `loading user login should execute usecase`() {
		loginViewModel.signIn(textInputEmail, textInputPassword, context)
		verify(loginByUserId).execute(any(), any())
	}

	@Test
	fun `loading user login success should update user login live data`() {
		val expectedUserLogin = UserLogin("tokenType", "accessToken", "refreshToken", 1)
		userLogin.onNext(expectedUserLogin)

		loginViewModel.userLogin.value?.let {
			with(it) {
				tokenType shouldEqual "tokenType"
				accessToken shouldEqual "accessToken"
				refreshToken shouldEqual "refreshToken"
				expiresIn shouldEqual 1
			}
		}
	}

	@Test
	fun `loading user login fail should update user login live data`() {
		userLogin.onError(Throwable())
		loginViewModel.error.value.let { it is Throwable }
	}

	private val userLogin: DefaultDisposable<UserLogin>
		get() {
			loginViewModel.signIn(textInputEmail, textInputPassword, context)
			return argumentCaptor<DefaultDisposable<UserLogin>>()
				.apply { verify(loginByUserId).execute(this.capture(), any()) }
				.firstValue
		}
}