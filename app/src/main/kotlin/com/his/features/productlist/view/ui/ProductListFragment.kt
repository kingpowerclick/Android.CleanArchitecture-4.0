package com.his.features.productlist.view.ui

import android.os.Bundle
import android.view.View
import com.his.R
import com.his.core.extension.failure
import com.his.core.extension.observe
import com.his.core.extension.viewModel
import com.his.core.platform.BaseFragment
import com.his.features.productlist.data.entity.ProductItem
import com.his.features.productlist.viewmodel.ProductListViewModel
import timber.log.Timber

class ProductListFragment : BaseFragment() {
	private lateinit var productListViewModel: ProductListViewModel

	override fun layoutId() = R.layout.fragment_product_list

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		appComponent.inject(this)

		productListViewModel = viewModel(viewModelFactory) {
			observe(productList, ::renderProductList)
			failure(error, ::handleFailure)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		if (firstTimeCreated(savedInstanceState)) {
			productListViewModel.loadProductList()
		}
	}

	private fun renderProductList(productList: List<ProductItem>?) {
		Timber.e("${productList}")
	}


	private  fun handleFailure(e: Throwable?) {
		Timber.e("${e}")
	}

	companion object {
		fun getInstance(): ProductListFragment {
			return ProductListFragment()
		}
	}
}