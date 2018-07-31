package com.his.features.core.data.model.mapper

import com.his.features.core.data.model.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageMapper @Inject constructor() {
	fun toImage(imageEntity: fragment.Image?): Image? {
		return if (imageEntity?.baseUri()?.isNotBlank() == true && imageEntity.filename()?.isNotBlank() == true) {
			Image(fileName = imageEntity.filename()!!, version = imageEntity.version() ?: "", prefix = imageEntity.prefix()
				?: "", baseUrl = imageEntity.baseUri() ?: "")
		} else {
			null
		}
	}
}