package br.com.android.teajudo.ui.volunteers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import br.com.android.teajudo.utils.ScreenUtils
import kotlinx.android.synthetic.main.fragment_volunteers.*
import javax.inject.Inject

class VolunteersFragment: BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            VolunteersViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_volunteers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutBackground.layoutParams.height = ScreenUtils.getScreenHeightInDP(view.context)
    }
}