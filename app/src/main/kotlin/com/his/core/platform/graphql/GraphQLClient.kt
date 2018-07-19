package com.his.core.platform.graphql

import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import io.reactivex.Observable

interface GraphQLClient {
	fun <D : Operation.Data, T, V : Operation.Variables> queryRx(query: Query<D, T, V>): Observable<Response<T>>

	fun <D : Operation.Data, T, V : Operation.Variables> mutateRx(mutation: Mutation<D, T, V>): Observable<Response<T>>
}
