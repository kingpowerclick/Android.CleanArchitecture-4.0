package com.his.features.login.view.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.setText(text: String?) {
	this.editText?.setText(text)
}

fun TextInputLayout.getText(): String? {
	return this.editText?.text?.toString()?.trim()
}