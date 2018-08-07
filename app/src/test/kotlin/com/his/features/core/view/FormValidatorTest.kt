package com.his.features.core.view

import com.his.UnitTest
import com.his.features.core.view.validator.*
import org.amshove.kluent.shouldEqualTo
import org.apache.commons.lang3.RandomStringUtils
import org.junit.Test

class FormValidatorTest : UnitTest() {

//	@Test
//	fun `should validate success first name`() {
//		formValidator.isNameValid(generatingRandomString(20, true, true)) shouldEqualTo true
//		formValidator.isNameValid(generatingRandomString(256, true, true)) shouldEqualTo false
//	}
//
//	@Test
//	fun `should validate fail first name`() {
//		formValidator.isNameInValid(generatingRandomString(256, true, true)) shouldEqualTo true
//		formValidator.isNameInValid(generatingRandomString(257, true, true)) shouldEqualTo true
//	}

	@Test
	fun `should validate success email`() {
		"abc@gmail.com".validateEmail().isValidate() shouldEqualTo true
		"admin@kingpowerclick.com".validateEmail().isValidate() shouldEqualTo true
	}

	@Test
	fun `should validate fail email`() {
		"".validateEmail().isInvalidate() shouldEqualTo true
		"aaa".validateEmail().isInvalidate() shouldEqualTo true
		"aaa@#$#%@$".validateEmail().isInvalidate() shouldEqualTo true
	}

	@Test
	fun `should validate success password`() {
		"abc@gmail.com".validatePassword().isValidate() shouldEqualTo true
		"admin@kingpowerclick.com".validatePassword().isValidate() shouldEqualTo true
		generatingRandomString(6, false, true).validatePassword().isValidate() shouldEqualTo true
		generatingRandomString(30, false, true).validatePassword().isValidate() shouldEqualTo true
	}

	@Test
	fun `should validate fail password`() {
		"".validatePassword().isInvalidate() shouldEqualTo true
		generatingRandomString(5, false, true).validatePassword().isInvalidate() shouldEqualTo true
		generatingRandomString(31, false, true).validatePassword().isInvalidate() shouldEqualTo true
	}

	@Test
	fun `should validate success mobile number`() {
		"0835462939".validateMobileNumber().isValidate() shouldEqualTo true
		"0835462939#1".validateMobileNumber().isValidate() shouldEqualTo true
		"+66835462939".validateMobileNumber().isValidate() shouldEqualTo true
		generatingRandomString(10, false, true).validateMobileNumber().isValidate() shouldEqualTo true
	}

	@Test
	fun `should validate fail mobile number`() {
		"".validateMobileNumber().isInvalidate() shouldEqualTo true
		"abc".validateMobileNumber().isInvalidate() shouldEqualTo true
		"+-==090".validateMobileNumber().isInvalidate() shouldEqualTo true
		generatingRandomString(8, false, true).validateMobileNumber().isInvalidate() shouldEqualTo true
		generatingRandomString(30, false, true).validateMobileNumber().isInvalidate() shouldEqualTo true
	}

	@Test
	fun `should validate success address`() {
		generatingRandomString(255, false, true).validateAddress().isValidate() shouldEqualTo true
	}

	@Test
	fun `should validate fail address`() {
		"".validateAddress().isInvalidate() shouldEqualTo true
		generatingRandomString(256, false, true).validateAddress().isInvalidate() shouldEqualTo true
	}

	@Test
	fun `should validate success postal code`() {
		generatingRandomString(5, false, true).validatePostalCode().isValidate() shouldEqualTo true
	}

	@Test
	fun `should validate fail postal code`() {
		"".validateAddress().isInvalidate() shouldEqualTo true
		generatingRandomString(6, false, true).validatePostalCode().isInvalidate() shouldEqualTo true
	}

	private fun generatingRandomString(length: Int, useLetters: Boolean, useNumbers: Boolean): String {
		return RandomStringUtils.random(length, useLetters, useNumbers)
	}
}