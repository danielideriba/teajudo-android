package com.module.coreapps.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<TItem>(private val view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener, View.OnLongClickListener {

    init {
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
    }

    @Throws(Exception::class)
    abstract fun bindData(data: TItem?)

    protected fun view(): View {
        return view
    }

    protected fun context(): Context {
        return view.context
    }
}