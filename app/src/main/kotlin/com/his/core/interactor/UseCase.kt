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
package com.his.core.interactor

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<Type, in Params> where Type : Any, Params : UseCase.Parameter {

	/**
	 * Builds an [Observable] which will be used when executing the current [UseCase].
	 */
	abstract fun buildUseCase(params: Params): Observable<Type>

	/**
	 * Executes the current use case.
	 *
	 * @param observer [DisposableObserver] which will be listening to the observable build
	 * by [.buildUseCaseObservable] ()} method.
	 * @param params Parameters (Optional) used to build/execute this use case.
	 */
	fun execute(observer: DisposableObserver<Type>, params: Params): Disposable {
		checkNotNull(observer)

		return buildUseCase(params)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribeWith(observer)
	}

	sealed class Parameter {
		class None : Parameter()

		/** * Extend this class for feature specific parameters.*/
		abstract class FeatureParameter : Parameter()
	}
}
