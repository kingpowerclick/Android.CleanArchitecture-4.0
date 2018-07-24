package com.his.features.productlist.data.entity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocaleTypeMapper @Inject constructor() {
	fun toLocaleTypeEntity(localeType: LocaleType?): type.LocaleType {
		return when(localeType) {
			LocaleType.TH           -> type.LocaleType.TH
			LocaleType.EN           -> type.LocaleType.EN
			else                    -> type.LocaleType.EN
		}
	}
}