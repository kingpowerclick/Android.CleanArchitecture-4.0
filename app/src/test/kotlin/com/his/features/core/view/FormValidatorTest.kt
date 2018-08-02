package com.his.features.core.view

import com.his.UnitTest
import com.his.features.core.view.validator.FormValidator
import org.amshove.kluent.shouldEqualTo
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test

class FormValidatorTest : UnitTest() {
	private val formValidator = FormValidator()

	@Test
	fun `should validate success first name`() {
		formValidator.isNameValid(generatingRandomString(20, true, true)) shouldEqualTo true
		formValidator.isNameValid(generatingRandomString(256, true, true)) shouldEqualTo false
	}

	@Test
	fun `should validate fail first name`() {
		formValidator.isNameInValid(generatingRandomString(256, true, true)) shouldEqualTo true
		formValidator.isNameInValid(generatingRandomString(257, true, true)) shouldEqualTo true
	}

	@Test
	fun `should validate success phone number`() {
		formValidator.isPhoneNumberValid("0835462939") shouldEqualTo true
		formValidator.isPhoneNumberValid("0835462939#1") shouldEqualTo true
		formValidator.isPhoneNumberValid("+66835462939") shouldEqualTo true
	}

	@Test
	fun `should validate fail phone number`() {
		formValidator.isPhoneNumberValid("") shouldEqualTo false
		formValidator.isPhoneNumberValid("abc") shouldEqualTo false
		formValidator.isPhoneNumberValid("+-==090") shouldEqualTo false
		formValidator.isPhoneNumberValid(generatingRandomString(31, false, true)) shouldEqualTo false
	}



	private fun generatingRandomString(length: Int, useLetters: Boolean, useNumbers: Boolean): String {
		return RandomStringUtils.random(length, useLetters, useNumbers)
	}
}