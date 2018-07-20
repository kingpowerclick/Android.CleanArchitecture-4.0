/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.his.features.login.view.ui

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import com.his.R
import com.his.core.exception.NetworkConnectionException
import com.his.core.exception.ServerErrorException
import com.his.core.extension.failure
import com.his.core.extension.observe
import com.his.core.extension.viewModel
import com.his.core.navigation.Navigator
import com.his.core.platform.BaseFragment
import com.his.features.login.view.extensions.getText
import com.his.features.login.view.extensions.validateInput
import com.his.features.login.view.model.UserLogin
import com.his.features.login.view.validator.FormValidator
import com.his.features.login.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {
	@Inject lateinit var navigator: Navigator
	private lateinit var loginViewModel: LoginViewModel
	private lateinit var mFormValidator: FormValidator


	override fun layoutId() = R.layout.fragment_login

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		appComponent.inject(this)

		loginViewModel = viewModel(viewModelFactory) {
			observe(userLogin, ::renderUserLogin)
			failure(error, ::handleFailure)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		onBindEvent()

	}

	private fun onBindEvent() {
		buttonSignIn.setOnClickListener {
			if (isValid()) {
				loginViewModel.signIn(
					email = editTextEmail.text.toString(),
					password = editTextPassword.text.toString())
			}
		}
	}

	private fun isValid(): Boolean {
		val validateResults = mutableListOf<Boolean>()

		if (mFormValidator.isInputNotEmpty(textInputLayoutEmail.getText() ?: "").not()) {
			validateResults.add(textInputLayoutEmail.validateInput(
				errorText = getString(R.string.form_error_required_email),
				validator = false
			))
		}
		else {
			validateResults.add(textInputLayoutEmail.validateInput(
				errorText = getString(R.string.form_error_email_invalid),
				validator = mFormValidator.isEmailFormatValid(textInputLayoutEmail.getText() ?: "")
			))
		}

		if (mFormValidator.isInputNotEmpty(textInputLayoutPassword.getText() ?: "").not()) {
			validateResults.add(textInputLayoutPassword.validateInput(
				errorText = getString(R.string.form_error_required_password),
				validator = false
			))
		}
		else {
			validateResults.add(textInputLayoutPassword.validateInput(
				errorText = getString(R.string.form_error_password_invalid),
				validator = mFormValidator.isPasswordFormatValid(textInputLayoutPassword.getText() ?: "")
			))
		}

		return validateResults.all { it }
	}

	private fun renderUserLogin(userLogin: UserLogin?) {
		userLogin?.let {
			navigator.showMain(this.requireContext())
		}
		hideProgress()
	}

	private fun handleFailure(failure: Throwable?) {
		when (failure) {
			is NetworkConnectionException -> renderFailure(R.string.failure_network_connection)
			is ServerErrorException       -> renderFailure(R.string.failure_server_error)
		}
	}

	private fun renderFailure(@StringRes message: Int) {
		hideProgress()
		notify(message)
	}
}