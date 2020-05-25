package br.com.android.teajudo.ui.volunteers

import androidx.lifecycle.ViewModel
import br.com.android.teajudo.data.MapsRepository
import javax.inject.Inject

class VolunteersViewModel @Inject constructor(var mapsRepository: MapsRepository) : ViewModel() {
}