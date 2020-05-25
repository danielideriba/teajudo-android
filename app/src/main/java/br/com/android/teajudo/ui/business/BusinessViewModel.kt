package br.com.android.teajudo.ui.business

import androidx.lifecycle.ViewModel
import br.com.android.teajudo.data.MapsRepository
import javax.inject.Inject

class BusinessViewModel @Inject constructor(var mapsRepository: MapsRepository) : ViewModel() {
}