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
		val sku: String?,
		val category: String?,
		val brand: String?,
		val prettyUrl: String?,
		val sortBy: ProductSortableFieldEnum?,
		val sortType: SortType?,
		val page: Int?,
		val recordPerPage: Int?,
		val lang: LocaleType,
		val keyword: String?,
		val brands: List<String>?,
		val categories: List<String>?,
		val genders: List<String>?,
		val priceMax: Double?,
		val priceMin: Double?,
		val isDutyFree: Boolean?
	) : UseCase.Parameter.FeatureParameter()
}