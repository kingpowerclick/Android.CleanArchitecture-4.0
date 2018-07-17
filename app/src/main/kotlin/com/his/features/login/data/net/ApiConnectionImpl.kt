package com.kingpower.data.net

import com.his.features.login.data.ClientCreator
import com.kingpower.data.net.graphql.GraphQLClient
import com.kingpower.data.net.graphql.GraphQLClientImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiConnectionImpl @Inject constructor(private val clientCreator: ClientCreator) : ApiConnection {
	override fun createGraphQLClient(): GraphQLClient = GraphQLClientImpl(clientCreator)
}