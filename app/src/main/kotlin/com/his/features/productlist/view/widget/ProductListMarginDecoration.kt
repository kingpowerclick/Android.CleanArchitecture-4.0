package com.his.features.productlist.view.widget

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

class ProductListMarginDecoration(context: Context, @DimenRes resId: Int, private val isIgnoreFirstItem: Boolean) : RecyclerView.ItemDecoration() {

	private val margin: Int

	init {
		val resources = context.resources
		this.margin = resources.getDimensionPixelSize(resId)
	}

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		val itemPosition = parent.getChildAdapterPosition(view)
		if (RecyclerView.NO_POSITION == itemPosition) return

		val doubleMargin = margin * DOUBLE

		if (isIgnoreFirstItem)
			when {
				itemPosition == 0     -> outRect.set(0, 0, 0, margin)
				itemPosition % 2 == 0 -> outRect.set(margin, margin, doubleMargin, margin)
				else                  -> outRect.set(doubleMargin, margin, margin, margin)
			}
		else {
			when {
				itemPosition % 2 == 0 -> outRect.set(doubleMargin, margin, margin, margin)
				else                  -> outRect.set(margin, margin, doubleMargin, margin)
			}
		}
	}

	companion object {
		private val DOUBLE = 2
	}
}