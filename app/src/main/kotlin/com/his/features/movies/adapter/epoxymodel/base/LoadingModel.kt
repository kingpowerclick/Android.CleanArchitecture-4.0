package com.shopspot.adapter.epoxymodel.base

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.his.R

@EpoxyModelClass(layout = R.layout.row_epoxy_loading)
abstract class LoadingModel : EpoxyModelWithHolder<LoadingModel.ViewHolder>() {

	override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int) = totalSpanCount

	inner class ViewHolder : EpoxyHolder() {
		override fun bindView(itemView: View?) {}
	}
}
