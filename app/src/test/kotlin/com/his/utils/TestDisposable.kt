package com.his.utils

import io.reactivex.disposables.Disposable

class TestDisposable : Disposable {
	override fun isDisposed(): Boolean = true
	override fun dispose() {
	}
}