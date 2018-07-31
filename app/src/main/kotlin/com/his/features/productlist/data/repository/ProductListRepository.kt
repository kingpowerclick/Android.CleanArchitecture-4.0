package com.his.features.productlist.data.repository

import com.his.features.core.data.model.LocaleType
import com.his.features.productlist.data.model.ProductItem
import com.his.features.productlist.data.model.ProductSortableFieldEnum
import com.his.features.productlist.data.model.SortType
import io.reactivex.Observable

interface ProductListRepository {
	fun getProductList(sku: String?,
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
	                   isDutyFree: Boolean?) : Observable<List<ProductItem>>
}