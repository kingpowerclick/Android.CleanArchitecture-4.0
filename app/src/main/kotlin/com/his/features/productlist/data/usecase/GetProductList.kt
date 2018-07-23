package com.his.features.productlist.data.usecase

import com.his.core.interactor.UseCase
import com.his.features.productlist.data.entity.LocaleType
import com.his.features.productlist.data.entity.ProductItem
import com.his.features.productlist.data.entity.ProductSortableFieldEnum
import com.his.features.productlist.data.entity.SortType
import com.his.features.productlist.data.repository.ProductListRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetProductList @Inject constructor(private val getProductListRepository: ProductListRepository)
	: UseCase<List<ProductItem>, GetProductList.Param>() {

	override fun buildUseCase(params: Param): Observable<List<ProductItem>> {
		return getProductListRepository.getProductList(
			sku = params.sku,
			category = params.category,
			brand = params.brand,
			prettyUrl = params.prettyUrl,
			sortBy = params.sortBy,
			sortType = params.sortType,
			page = params.page,
			recordPerPage = params.recordPerPage,
			lang = params.lang,
			keyword = params.keyword,
			brands = params.brands,
			categories = params.categories,
			genders = params.genders,
			priceMin = params.priceMin,
			priceMax = params.priceMax,
			isDutyFree = params.isDutyFree
		)
	}

	class Param(
		val sku: String? = null,
		val category: String? = null,
		val brand: String? = null,
		val prettyUrl: String? = null,
		val sortBy: ProductSortableFieldEnum? = null,
		val sortType: SortType? = null,
		val page: Int? = null,
		val recordPerPage: Int? = null,
		val lang: LocaleType,
		val keyword: String? = null,
		val brands: List<String>? = null,
		val categories: List<String>? = null,
		val genders: List<String>? = null,
		val priceMax: Double? = null,
		val priceMin: Double? = null,
		val isDutyFree: Boolean? = null
	) : UseCase.Parameter.FeatureParameter()
}