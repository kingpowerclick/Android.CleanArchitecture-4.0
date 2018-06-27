package com.his.core.platform

import io.reactivex.observers.DisposableObserver

abstract class DefaultDisposable<Type> : DisposableObserver<Type>() {
	override fun onComplete() {
	}

	override fun onNext(value: Type) {
	}

	override fun onError(e: Throwable) {
	}
}