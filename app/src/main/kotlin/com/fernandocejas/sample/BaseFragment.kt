package com.fernandocejas.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fernandocejas.sample.di.ApplicationComponent

abstract class BaseFragment : Fragment() {

    init {
        retainInstance = true
    }

    abstract fun layoutId(): Int

    val appComponent: ApplicationComponent by lazy {
        (activity.application as AndroidApplication).appComponent
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(layoutId(), container, false)
    }
}