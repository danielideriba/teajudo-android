package br.com.android.teajudo.ui.maps.viewHolder

import android.view.View
import androidx.databinding.DataBindingUtil
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.databinding.FragmentMapsBinding
import br.com.android.teajudo.databinding.StoreMapsBinding
import br.com.android.teajudo.ui.maps.MapsDataViewModel
import com.module.coreapps.base.BaseViewHolder

class MapsViewholder(
    private val view: View,
    private val delegate: Delegate
) : BaseViewHolder<Any>(view){

    interface Delegate {
        fun onMapResultClick(storeResult: StoreEntity, view: View?)
    }

    private lateinit var storeResult: StoreEntity

//    private val binding by lazy { DataBindingUtil.bind<FragmentMapsBinding>(view) }

    override fun bindData(data: kotlin.Any?) {
        if (data is StoreEntity) {
            storeResult = data
//            drawUI()
        }
    }

//    private fun drawUI() {
//        binding?.let {
//            val viewModel = MapsDataViewModel(
//                view.context,
//                storeResult
//            )
//
//            it.vModel = viewModel
//
//            it.executePendingBindings()
//        }
//    }

    override fun onClick(view: View?) {
        delegate.onMapResultClick(storeResult, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }
}