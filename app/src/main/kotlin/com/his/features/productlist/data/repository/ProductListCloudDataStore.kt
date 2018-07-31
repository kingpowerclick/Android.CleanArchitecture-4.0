package com.his.features.productlist.data.repository

import GetProductListQuery
import com.his.core.platform.graphql.GraphQLClient
import com.his.features.core.data.model.LocaleType
import com.his.features.core.data.model.mapper.LocaleTypeMapper
import com.his.features.productlist.data.model.*
import io.reactivex.Observable
import type.ProductFilterQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductListCloudDataStore @Inject constructor(private val graphQLClient: GraphQLClient,
                                                    private val productListEntityMapper: ProductListEntityMapper,
                                                    private val localeTypeMapper: LocaleTypeMapper) : ProductListDataStore {

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

		val filter = ProductFilterQuery.builder()
			.brands(brands)
			.categories(categories)
			.genders(genders)
			.priceMax(priceMax)
			.priceMin(priceMin)
			.isDutyFree(isDutyFree)
			.build()

		val getProductListQuery = GetProductListQuery.builder()
			.sku(sku)
			.category(category)
			.brand(brand)
			.prettyUrl(prettyUrl)
			.sortBy(productListEntityMapper.toProductSortableFieldEnumEntity(sortBy))
			.sortType(productListEntityMapper.toSortEnumTypeEntity(sortType))
			.page(page)
			.recordPerPage(recordPerPage)
			.lang(localeTypeMapper.toLocaleTypeEntity(lang))
			.keyword(keyword)
			.filter(filter)
			.build()


		return graphQLClient.queryRx(getProductListQuery).map {
			it.data()?.products()?.fragments()?.productsResponse()?.data()?.mapNotNull { productListEntityMapper.toProductItem(it?.fragments()?.productItem()) }
		}
	}
}