/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.his.core.platform

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Exception
 */
abstract class BaseViewModel : ViewModel() {

	private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

	var failure: MutableLiveData<Throwable> = MutableLiveData()

	protected fun handleFailure(failure: Throwable) {
		this.failure.value = failure
	}

	protected fun Disposable.autoClear() {
		disposables.add(this)
	}

	public override fun onCleared() {
		disposables.dispose()
	}
}