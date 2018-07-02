package com.his.features.movies.adapter

import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import com.his.features.movies.adapter.epoxymodel.EmptyModel_
import com.shopspot.adapter.epoxymodel.base.ErrorModel_
import com.shopspot.adapter.epoxymodel.base.LoadingModel_

abstract class BaseEpoxyAdapter : EpoxyAdapter() {
	open protected val errorModel: EpoxyModel<*> = ErrorModel_()
	open protected val emptyModel: EpoxyModel<*> = EmptyModel_()
	open protected val loadingModel: EpoxyModel<*> = LoadingModel_()

	protected abstract fun clearItemList()

	open fun loading() {
		clearItemList()
		addModel(loadingModel)
	}

	open fun error() {
		clearItemList()
		addModel(errorModel)
	}

	open fun empty() {
		clearItemList()
		addModel(emptyModel)
	}
}