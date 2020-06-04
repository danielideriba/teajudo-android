package br.com.android.teajudo.ui.maps

import android.content.Context
import android.widget.LinearLayout
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import br.com.android.teajudo.data.db.entities.StoreEntity

class MapsDataViewModel(
    private val context: Context,
    private val storeEntity: StoreEntity
) : BaseObservable() {
    @Bindable
    fun getOrientation() = LinearLayout.VERTICAL

    @Bindable
    fun getName(): String? = "NOMEEEEE"
}