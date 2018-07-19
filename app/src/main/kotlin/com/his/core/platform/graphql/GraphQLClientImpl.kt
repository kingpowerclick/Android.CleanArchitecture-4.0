package com.his.core.platform.graphql

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.internal.util.Cancelable
import com.his.core.exception.ServerErrorException
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.Exceptions
import okhttp3.OkHttpClient
import timber.log.Timber

class GraphQLClientImpl(httpClient: OkHttpClient) : GraphQLClient {

	private val apolloClient = ApolloClient.builder()
		.serverUrl(BASE_API_GATEWAY_URL)
		.okHttpClient(httpClient)
		.build()

	override fun <D : Operation.Data, T, V : Operation.Variables> queryRx(query: Query<D, T, V>): Observable<Response<T>> {
		val apolloQueryCall = apolloClient.query(query)
		return toRx(apolloQueryCall)
	}

	override fun <D : Operation.Data, T, V : Operation.Variables> mutateRx(mutation: Mutation<D, T, V>): Observable<Response<T>> {
		val apolloMutateCall = apolloClient.mutate(mutation)
		return toRx(apolloMutateCall)
	}

	/**
	 * Handle graphQL [response.errors] exist.
	 * Should call [Emitter.onError()]
	 *
	 * Original source code by [com.apollographql.apollo.rx2.Rx2Apollo] (0.4.1)
	 */
	private fun <T> toRx(originalCall: ApolloCall<T>): Observable<Response<T>> {
		checkNotNull(originalCall) { "call == null" }

		return Observable.create { emitter ->
			cancelOnObservableDisposed(emitter, originalCall)
			originalCall.enqueue(object : ApolloCall.Callback<T>() {
				override fun onResponse(response: Response<T>) {
					if (!emitter.isDisposed) {
						if (response.errors().isEmpty()) {
							emitter.onNext(response)
						}
						else {
							emitter.tryOnError(ServerErrorException())
						}
					}
				}

				override fun onFailure(e: ApolloException) {
					Timber.i(e, "GraphQL error")

					Exceptions.throwIfFatal(e)
					if (!emitter.isDisposed) {
						emitter.tryOnError(e)
					}
				}

				override fun onStatusEvent(event: ApolloCall.StatusEvent) {
					if (event == ApolloCall.StatusEvent.COMPLETED && !emitter.isDisposed) {
						emitter.onComplete()
					}
				}
			})
		}
	}

	private fun <T> cancelOnObservableDisposed(emitter: ObservableEmitter<T>, cancelable: Cancelable) {
		emitter.setDisposable(getRx2Disposable(cancelable))
	}

	private fun getRx2Disposable(cancelable: Cancelable): Disposable {
		return object : Disposable {
			override fun dispose() {
				cancelable.cancel()
			}

			override fun isDisposed(): Boolean {
				return cancelable.isCanceled
			}
		}
	}

	companion object {
		private const val BASE_API_GATEWAY_URL = "https://dev-api.kpc-dev.com/"
	}
}
