package com.his.features.productlist.view.ui

import android.content.Context
import android.content.Intent
import com.his.core.platform.BaseActivity
import com.his.core.platform.BaseFragment

class ProductListActivity : BaseActivity() {
	override fun fragment(): BaseFragment {
		return ProductListFragment.getInstance()
	}

	companion object {
		fun getCallingIntent(callerContext: Context): Intent {
			return Intent(callerContext, ProductListActivity::class.java)
		}
	}
}