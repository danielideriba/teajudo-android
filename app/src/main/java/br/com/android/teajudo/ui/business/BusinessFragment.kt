package br.com.android.teajudo.ui.business

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import br.com.android.teajudo.utils.ScreenUtils
import kotlinx.android.synthetic.main.fragment_business.*
import javax.inject.Inject

class BusinessFragment: BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            BusinessViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_business, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        linearLayoutBackground.layoutParams.height = ScreenUtils.getScreenHeightInDP(view.context)
    }
}