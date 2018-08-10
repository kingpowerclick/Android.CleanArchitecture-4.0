package com.his.features.core.view.validator

import android.annotation.SuppressLint
import androidx.annotation.IntDef
import java.util.regex.Pattern

//common validate = 1
const val VALIDATE = 0x0000001
const val INVALIDATE = 0x0000002

//common string = 2
const val INVALIDATE_EMPTY = 0x0000010 or INVALIDATE
const val INVALIDATE_LENGTH = 0x0000020 or INVALIDATE
const val INVALIDATE_FORMAT = 0x0000030 or INVALIDATE

@SuppressLint("UniqueConstants")
@IntDef(VALIDATE,
	INVALIDATE,
	INVALIDATE_EMPTY,
	INVALIDATE_LENGTH,
	INVALIDATE_FORMAT)
@Retention(AnnotationRetention.SOURCE)
annotation class ValidateString


fun @receiver:ValidateString Int.isValidate(): Boolean = this and VALIDATE == VALIDATE
fun @receiver:ValidateString Int.isInvalidate(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validateCommon(): Int {
	if (this.isNullOrEmpty()) return INVALIDATE_EMPTY
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidateEmail(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validateEmail(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (!Pattern.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+", this)) return INVALIDATE_FORMAT
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidatePassword(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validatePassword(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (this!!.length !in 6..30) return INVALIDATE_LENGTH
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidateMobileNumber(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validateMobileNumber(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (!Pattern.matches("\\+?\\d[-+\\d -#*]{8,12}\\d", this)) return INVALIDATE_FORMAT
	if (this!!.length !in 7..29) return INVALIDATE_LENGTH
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidateAddress(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validateAddress(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (this!!.length >= 256) return INVALIDATE_LENGTH
	return VALIDATE
}

fun @receiver:ValidateString Int.isInvalidatePostalCode(): Boolean = this and INVALIDATE == INVALIDATE
fun String?.validatePostalCode(): Int {
	if (this.validateCommon().isInvalidate()) return this.validateCommon()
	if (this!!.length != 5) return INVALIDATE_LENGTH
	return VALIDATE
}