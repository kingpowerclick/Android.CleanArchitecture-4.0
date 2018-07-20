package com.his.features.login.view.extensions

import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.ViewGroup

fun TextInputLayout.setText(text: String?) {
	this.editText?.setText(text)
}

fun TextInputLayout.getText(): String? {
	return this.editText?.text?.toString()?.trim()
}

fun TextInputLayout.disableError() {
	this.isErrorEnabled = false
}

fun View.allChildren(): List<View> {
	val visited = mutableListOf<View>()
	val unvisited = mutableListOf<View>()
	unvisited.add(this)
	while(unvisited.isNotEmpty()) {
		val child = unvisited.removeAt(0)
		visited.add(child)
		if ((child !is ViewGroup)) continue
		(0..child.childCount).forEach {
			unvisited.add(child.getChildAt(it))
		}
	}

	return visited
}