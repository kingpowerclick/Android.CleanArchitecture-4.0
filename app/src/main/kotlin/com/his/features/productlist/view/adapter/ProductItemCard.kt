package com.his.features.productlist.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.his.R
import com.his.core.extension.loadFromUrl
import com.his.features.productlist.data.entity.ProductItem
import kotlinx.android.synthetic.main.row_product_item.view.*

@EpoxyModelClass(layout = R.layout.row_product_item)
abstract class ProductItemCard : EpoxyModelWithHolder<ProductItemCard.ViewHolder>() {

	@EpoxyAttribute(EpoxyAttribute.Option.NoGetter) protected var productItem: ProductItem? = null

	override fun bind(holder: ViewHolder) {
		super.bind(holder)
		with(holder) {
			imageViewProductItem.loadFromUrl(productItem?.image?.getImageUrl() ?: "")
			textViewProductName.text = productItem?.name ?: ""
		}
	}

	inner class ViewHolder : EpoxyHolder() {
		lateinit var imageViewProductItem: ImageView
		lateinit var textViewProductName: TextView

		override fun bindView(itemView: View) {
			imageViewProductItem = itemView.imageViewProductItem
			textViewProductName  = itemView.textViewProductName
		}
	}
}