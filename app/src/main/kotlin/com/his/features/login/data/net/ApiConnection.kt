package com.kingpower.data.net

import com.kingpower.data.net.graphql.GraphQLClient

interface ApiConnection {
	fun createGraphQLClient(): GraphQLClient
}
