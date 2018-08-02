package com.his.features.core.view.validator

import android.annotation.SuppressLint
import android.support.annotation.IntDef
import java.util.regex.Pattern

//common validate = 1
const val VALIDATE = 0x000000000000101
const val INVALIDATE = 0x000000000000102

//common string = 2
const val INVALIDATE_EMPTY = 0x000000000000201 or INVALIDATE

//email validate = 3
const val INVALIDATE_EMAIL = 0x000000000000301 or INVALIDATE
const val INVALIDATE_EMAIL_FORMAT = 0x000000000000302 or INVALIDATE_EMAIL

//password validate = 4
const val INVALIDATE_PASSWORD = 0x000000000000401 or INVALIDATE
const val INVALIDATE_PASSWORD_LENGTH = 0x000000000000402 or INVALIDATE_PASSWORD

//mobile number validate = 5
const val INVALIDATE_MOBILE_NUMBER = 0x000000000000501 or INVALIDATE
const val INVALIDATE_MOBILE_NUMBER_FORMAT = 0x000000000000502 or INVALIDATE_MOBILE_NUMBER
const val INVALIDATE_MOBILE_NUMBER_LENGTH = 0x000000000000503 or INVALIDATE_MOBILE_NUMBER

//address validate = 6
const val INVALIDATE_ADDRESS = 0x000000000000601 or INVALIDATE
const val INVALIDATE_ADDRESS_LENGTH = 0x000000000000602 or INVALIDATE_ADDRESS

//postal code validate = 7
const val INVALIDATE_POSTAL_CODE = 0x000000000000701 or INVALIDATE
const val INVALIDATE_POSTAL_CODE_LENGTH = 0x000000000000702 or INVALIDATE_POSTAL_CODE

@SuppressLint("UniqueConstants")
@IntDef(VALIDATE,
	INVALIDATE,
	INVALIDATE_EMPTY,
	INVALIDATE_EMAIL,
	INVALIDATE_EMAIL_FORMAT,
	INVALIDATE_PASSWORD,
	INVALIDATE_PASSWORD_LENGTH,
	INVALIDATE_MOBILE_NUMBER,
	INVALIDATE_MOBILE_NUMBER_FORMAT,
	INVALIDATE_MOBILE_NUMBER_LENGTH,
	INVALIDATE_ADDRESS,
	INVALIDATE_ADDRESS_LENGTH,
	INVALIDATE_POSTAL_CODE,
	INVALIDATE_POSTAL_CODE_LENGTH)
@Retention(AnnotationRetention.SOURCE)
annotation class ValidateString


fun @receiver:ValidateString Int.isValidate(): Boolean = this and VALIDATE == VALIDATE
fun @receiver:ValidateString Int.isInvalidate(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validateCommon(): Int {
	if (this.isNullOrEmpty()) return INVALIDATE_EMPTY
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidateEmail(): Boolean = this and INVALIDATE_EMAIL == INVALIDATE_EMAIL
fun String?.validateEmail(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (!Pattern.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+", this)) return INVALIDATE_EMAIL_FORMAT
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidatePassword(): Boolean = this and INVALIDATE_PASSWORD == INVALIDATE_PASSWORD
fun String?.validatePassword(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (this!!.length !in 6..30) return INVALIDATE_PASSWORD_LENGTH
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidateMobileNumber(): Boolean = this and INVALIDATE_MOBILE_NUMBER == INVALIDATE_MOBILE_NUMBER
fun String?.validateMobileNumber(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (!Pattern.matches("\\+?\\d[-+\\d -#*]{8,12}\\d", this)) return INVALIDATE_MOBILE_NUMBER_FORMAT
	if (!Pattern.matches("\\d{9}", this)) return INVALIDATE_MOBILE_NUMBER_FORMAT
	if (this!!.length !in 7..29) return INVALIDATE_MOBILE_NUMBER_LENGTH
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidateAddress(): Boolean = this and INVALIDATE_ADDRESS == INVALIDATE_ADDRESS
fun String?.validateAddress(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (this!!.length >= 256) return INVALIDATE_ADDRESS_LENGTH
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidatePostalCode(): Boolean = this and INVALIDATE_POSTAL_CODE == INVALIDATE_POSTAL_CODE
fun String?.validatePostalCode(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (this!!.length != 5) return INVALIDATE_POSTAL_CODE_LENGTH
	return VALIDATE
}