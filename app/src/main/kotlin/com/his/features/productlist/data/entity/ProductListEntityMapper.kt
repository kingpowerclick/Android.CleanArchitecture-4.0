package com.his.features.productlist.data.entity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductListEntityMapper @Inject constructor(private val imageMapper: ImageMapper) {

	fun toProductItem(productEntity: fragment.ProductItem?): ProductItem? {
		return if (productEntity?.sku() == null || productEntity.sku()?.isEmpty() == true) null
		else {
			productEntity.let {
				ProductItem(
					sku = it.sku()!!,
					name = it.name() ?: "",
					dutyFree = it.dutyFree() ?: false,
					pickup = it.pickup() ?: false,
					hotItem = it.hotItem() ?: false,
					exclusiveKP = it.exclusiveKP() ?: false,
					bestSeller = it.bestSeller() ?: false,
					lag = it.lag() ?: false,
					soldOut = it.soldOut() ?: false,
					images = it.images()?.mapNotNull { imageMapper.toImage(it?.fragments()?.image()) },
					prettyUrl = it.prettyUrl() ?: ""
				)
			}
		}
	}

	fun toProductSortableFieldEnumEntity(sortBy: ProductSortableFieldEnum?): type.ProductSortableFieldEnum?{
		return sortBy?.let {
			when(it) {
				ProductSortableFieldEnum.NAME       -> type.ProductSortableFieldEnum.NAME
				ProductSortableFieldEnum.BRAND      -> type.ProductSortableFieldEnum.BRAND
				ProductSortableFieldEnum.PRICE      -> type.ProductSortableFieldEnum.PRICE
				ProductSortableFieldEnum.LATEST     -> type.ProductSortableFieldEnum.LATEST
				ProductSortableFieldEnum.SCORE      -> type.ProductSortableFieldEnum.SCORE
			}
		}
	}

	fun toSortEnumTypeEntity(sortType: SortType?): type.SortEnumType? {
		return sortType?.let {
			when(it) {
				SortType.ASC    -> type.SortEnumType.ASC
				SortType.DESC   -> type.SortEnumType.DESC
			}
		}
	}
}