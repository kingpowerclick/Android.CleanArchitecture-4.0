package com.his.features.productlist.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.his.core.platform.BaseViewModel
import com.his.core.platform.DefaultDisposable
import com.his.features.core.data.model.LocaleType
import com.his.features.productlist.data.model.ProductItem
import com.his.features.productlist.data.usecase.GetProductList
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val getProductList: GetProductList): BaseViewModel() {
	var productList: MutableLiveData<List<ProductItem>> = MutableLiveData()

	fun loadProductList() {
		val params = GetProductList.Param(
			lang = LocaleType.EN
		)
		getProductList
			.execute(observer = GetProductListObserver(), params = params)
			.autoClear()
	}

	private inner class GetProductListObserver : DefaultDisposable<List<ProductItem>>() {
		override fun onError(e: Throwable) {
			handleFailure(e)
		}

		override fun onNext(value: List<ProductItem>) {
			productList.value = value
		}
	}
}