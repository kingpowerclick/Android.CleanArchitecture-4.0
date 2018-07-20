package com.his.features.login.view.extensions

import android.support.design.widget.TextInputLayout
import android.widget.CheckBox
import com.his.R

fun TextInputLayout.validateInput(errorText: String, validator: Boolean): Boolean {
	if (this.isErrorEnabled) {
		this.isErrorEnabled = false
		this.error = null
	}
	if (validator.not()) {
		this.error = errorText
		this.isErrorEnabled = true
		return false
	}
	return true
}

//fun PickerView.validateInput(errorText: String): Boolean {
//	this.disableError()
//
//	val text = this.getText()
//	if (text.isNullOrEmpty()) {
//		this.showError(errorText)
//		return false
//	}
//	return true
//}

fun CheckBox.validateInput(isDisplayError: Boolean): Boolean {
	if (this.isChecked.not()) {
		if (isDisplayError) this.error = this.context.getString(R.string.form_error_required_field)
		return false
	}
	return true
}