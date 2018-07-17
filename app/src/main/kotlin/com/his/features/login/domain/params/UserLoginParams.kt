package com.his.features.login.domain.params

import com.his.core.interactor.UseCase

class UserLoginParams constructor(
	var clientId: String,
	var clientSecret: String,
	var email: String,
	var password: String
) : UseCase.Parameter.FeatureParameter()