package com.his.features.core.data.model

data class Image(val fileName: String,
                 val version: String,
                 val prefix: String,
                 val baseUrl: String) {
	fun getImageUrl() = "$baseUrl/w_320/$fileName"
}