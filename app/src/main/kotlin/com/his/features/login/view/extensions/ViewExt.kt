package com.his.features.login.view.extensions

import android.support.design.widget.TextInputLayout

fun TextInputLayout.setText(text: String?) {
	this.editText?.setText(text)
}

fun TextInputLayout.getText(): String? {
	return this.editText?.text?.toString()?.trim()
}