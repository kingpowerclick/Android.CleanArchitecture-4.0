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

import com.his.AndroidTest
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.amshove.kluent.shouldEqual
import org.junit.Test


class UseCaseTest : AndroidTest() {

	private val TYPE_TEST = "Test"
	private val TYPE_PARAM = "ParamTest"

	private val useCase = MyUseCase()

	private val testObserver = TestObserver.create<MyType>()

	@Test
	fun `should return correct data when executing use case`() {
		val params = MyParams(TYPE_PARAM)
		useCase.buildUseCase(params)
			.subscribe(testObserver)

		testObserver.values().first() shouldEqual MyType(TYPE_TEST)
	}

	data class MyType(val name: String)
	data class MyParams(val name: String) : UseCase.Parameter.FeatureParameter()

	private inner class MyUseCase : UseCase<MyType, MyParams>() {
		override fun buildUseCase(params: MyParams): Observable<MyType> {
			return Observable.just(MyType(TYPE_TEST))
		}
	}
}
