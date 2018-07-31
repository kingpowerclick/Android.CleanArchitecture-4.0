package com.his.features.core.view.validator

class FormValidator {

	fun isPickerItemValid(itemPicker: String?): Boolean {
		return itemPicker?.isNotEmpty() ?: false
	}

	fun isNameInValid(firstName: String): Boolean {
		return firstName.length >= 256
	}

	fun isNameValid(name: String): Boolean {
		return name.length < 256
	}

	fun isPhoneNumberValid(phone: String): Boolean {
		return isInputNotEmpty(phone) && validatePhoneNumber(phone) && phone.length < 30 && phone.length > 8
	}

	fun isPhoneNumberInValid(phoneNumber: String): Boolean {
		return !isPhoneNumberValid(phoneNumber)
	}

	fun isEmailFormatValid(email: String): Boolean {
		return isInputNotEmpty(email) && email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+"))
	}

	fun isPasswordFormatValid(password: String): Boolean {
		return isInputNotEmpty(password) && password.length in 6..30
	}

	fun isConfirmPasswordFormatValid(confirmPassword: String, password: String): Boolean {
		return isInputNotEmpty(confirmPassword) && confirmPassword == password
	}

	fun isTaxFormatValid(tax: String): Boolean {
		return isInputNotEmpty(tax) && tax.length == 13
	}

	fun isAddressFormatValid(address: String): Boolean {
		return isInputNotEmpty(address) && address.length < 256
	}

	fun isPostalCodeFormatValid(postalCode: String): Boolean {
		return isInputNotEmpty(postalCode) && postalCode.length == 5
	}

	fun isInputNotEmpty(input: String): Boolean {
		return input.isNotEmpty()
	}

	fun isEmptyOrBlank(input: String): Boolean {
		return input.isBlank()
	}

	fun isNationalityIdFormatValid(input: String): Boolean {
		return input.length == 13
	}

	fun isPassportIdFormatValid(input: String): Boolean {
		return input.length < 256
	}

	fun isPassportIdFormatInValid(input: String): Boolean {
		return input.length >= 256
	}

	fun isOrderIdFormatValid(input: String): Boolean {
		return input.length == 11
	}

	private fun validatePhoneNumber(phoneNo: String): Boolean {
		return when {
			phoneNo.matches("\\+?\\d[-+\\d -#*]{8,12}\\d".toRegex()) -> true
			phoneNo.matches("\\d{9}".toRegex())                      -> true
			else                                                     -> false
		}
	}
}