package com.his.features.productlist.data.repository

import com.his.features.productlist.data.entity.LocaleType
import com.his.features.productlist.data.entity.ProductItem
import com.his.features.productlist.data.entity.ProductSortableFieldEnum
import com.his.features.productlist.data.entity.SortType
import io.reactivex.Observable
import javax.inject.Inject

class ProductListDataRepository @Inject constructor(private val productListCloudDataStore: ProductListCloudDataStore) : ProductListRepository {
	override fun getProductList(sku: String?,
	                            category: String?,
	                            brand: String?,
	                            prettyUrl: String?,
	                            sortBy: ProductSortableFieldEnum?,
	                            sortType: SortType?,
	                            page: Int?,
	                            recordPerPage: Int?,
	                            lang: LocaleType,
	                            keyword: String?,
	                            brands: List<String>?,
	                            categories: List<String>?,
	                            genders: List<String>?,
	                            priceMax: Double?,
	                            priceMin: Double?,
	                            isDutyFree: Boolean?): Observable<List<ProductItem>> {

		return productListCloudDataStore.getProductList(
			sku = sku,
			category = category,
			brand = brand,
			prettyUrl = prettyUrl,
			sortBy = sortBy,
			sortType = sortType,
			page = page,
			recordPerPage = recordPerPage,
			lang = lang,
			keyword = keyword,
			brands = brands,
			categories = categories,
			genders = genders,
			priceMax = priceMax,
			priceMin = priceMin,
			isDutyFree = isDutyFree
		)
	}
}