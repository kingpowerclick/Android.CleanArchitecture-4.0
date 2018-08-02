package com.his.features.login.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.his.R
import com.his.core.platform.BaseViewModel
import com.his.core.platform.DefaultDisposable
import com.his.features.core.view.validator.*
import com.his.features.login.data.entity.mapper.UserLogin
import com.his.features.login.data.usecase.LoginByUserId
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val mLogin: LoginByUserId) : BaseViewModel() {
	var userLogin: MutableLiveData<UserLogin> = MutableLiveData()
	var errorTextEmail: MutableLiveData<String> = MutableLiveData()
	var errorTextPassword: MutableLiveData<String> = MutableLiveData()

	private fun doSignIn(email: String, password: String) {
		val params = LoginByUserId.UserLoginParams(
			clientId = KEY_CLIENT_ID,
			clientSecret = KEY_CLIENT_SECRET,
			email = email,
			password = password
		)
		mLogin.execute(LoginObserver(), params)
	}

	fun signIn(textInputUserName: String, textInputPassword: String, context: Context) {
		val validateResults = mutableListOf<Boolean>()

		Timber.e("textInputUserName : $textInputUserName")
		when (textInputUserName.validateEmail()) {
			INVALIDATE_EMPTY        -> {
				errorTextEmail.value = context.getString(R.string.form_error_required_email)
				validateResults.add(false)
			}
			INVALIDATE_EMAIL_FORMAT -> {
				errorTextEmail.value = context.getString(R.string.form_error_email_invalid)
				validateResults.add(false)
			}
			else                    -> {
				errorTextEmail.value = null
			}
		}

		Timber.e("textInputPassword : $textInputPassword")
		when (textInputPassword.validatePassword()) {
			INVALIDATE_EMPTY           -> {
				errorTextPassword.value = context.getString(R.string.form_error_required_password)
				validateResults.add(false)
			}
			INVALIDATE_PASSWORD_LENGTH -> {
				errorTextPassword.value = context.getString(R.string.form_error_password_invalid)
				validateResults.add(false)
			}
			else                       -> {
				errorTextPassword.value = null
			}
		}

		if (validateResults.all { it }) {
			doSignIn(textInputUserName, textInputPassword)
		}
	}

	private inner class LoginObserver : DefaultDisposable<UserLogin>() {
		override fun onError(e: Throwable) {
			Timber.e("Sign In Error")
			handleFailure(e)
		}

		override fun onNext(value: UserLogin) {
			if (value.accessToken.isNullOrEmpty()) {
				Timber.e("Sign In Fail")
			}
			else {
				Timber.e("Sign In Success")
				userLogin.value = value
			}
		}
	}

	companion object {
		private const val KEY_CLIENT_ID = "2"
		private const val KEY_CLIENT_SECRET = "***REMOVED***"
	}
}