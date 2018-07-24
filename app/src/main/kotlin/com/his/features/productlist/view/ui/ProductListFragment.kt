package com.his.features.productlist.view.ui

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.his.R
import com.his.core.extension.failure
import com.his.core.extension.observe
import com.his.core.extension.viewModel
import com.his.core.platform.BaseFragment
import com.his.features.productlist.data.entity.ProductItem
import com.his.features.productlist.view.adapter.ProductListController
import com.his.features.productlist.viewmodel.ProductListViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*
import timber.log.Timber

class ProductListFragment : BaseFragment() {
	private lateinit var mProductListViewModel: ProductListViewModel
	private lateinit var mProductListController: ProductListController

	override fun layoutId() = R.layout.fragment_product_list

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		appComponent.inject(this)

		mProductListViewModel = viewModel(viewModelFactory) {
			observe(productList, ::renderProductList)
			failure(error, ::handleFailure)
		}
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initView()
		if (firstTimeCreated(savedInstanceState)) {
			mProductListViewModel.loadProductList()
		}
	}

	private fun initView() {
		val layoutManager =  GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
		mProductListController = ProductListController()
		mProductListController.spanCount = 2
		layoutManager.spanSizeLookup = mProductListController.spanSizeLookup
		recyclerViewProductList.layoutManager = layoutManager
		recyclerViewProductList.setController(mProductListController)
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