package com.his.features.productlist.data.entity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageMapper @Inject constructor() {
	fun toImage(imageEntity: fragment.Image?): Image? {
		return if (imageEntity?.baseUri()?.isNotEmpty() == true && imageEntity.filename()?.isNotEmpty() != true) {
			Image(
				fileName = imageEntity.filename()!!,
				version = imageEntity.version() ?: "",
				prefix = imageEntity.prefix() ?: "",
				baseUrl = imageEntity.baseUri() ?: ""
			)
		} else {
			null
		}
	}
}