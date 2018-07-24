package com.his.features.productlist.view.adapter

import com.airbnb.epoxy.EpoxyController
import com.his.features.productlist.data.entity.ProductItem

open class ProductListController : EpoxyController() {
	private var productItemList: List<ProductItem> = listOf()

	fun setProductItemList(productItemList: List<ProductItem>) {
		this.productItemList = productItemList
		requestModelBuild()
	}

	override fun buildModels() {
		if (productItemList.isNotEmpty()) {
			productItemList.forEach {
				ProductItemCard_()
					.productItem(it)
					.id(it.sku)
					.addTo(this)
			}
		}
	}
}
