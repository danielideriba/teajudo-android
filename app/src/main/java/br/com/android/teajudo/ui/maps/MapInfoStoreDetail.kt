package br.com.android.teajudo.ui.maps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import br.com.android.teajudo.BaseActivity
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import com.module.coreapps.utils.SharedUtils
import kotlinx.android.synthetic.main.activity_map_info_store_detail.*
import kotlinx.android.synthetic.main.activity_maps.toolbar
import timber.log.Timber

class MapInfoStoreDetail: BaseActivity() {

    private lateinit var tagStore: StoreEntity
    private lateinit var tagStoreDetails: StoreDetailsEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_info_store_detail)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it?.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationIcon(R.drawable.arrow_left_white_18)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        renderLayout()

    }

    private fun renderLayout() {
        tagStore = intent.getParcelableExtra(TAG)
        tagStoreDetails = intent.getParcelableExtra(TAG_DETAILS)

        txtMarkerName.text = tagStore.name

        settingWhatAppButton(tagStore)


        Timber.d("----"+tagStoreDetails.idStoreDetails)
    }

    private fun settingWhatAppButton(tagStore: StoreEntity) {
        if(tagStore.whatapp != null && tagStore.whatapp == 1) {
            tagStore.whatapp.let {
                SharedUtils.whatsappRedirect(this, whatsappButton, it.toString())
            }
        } else {
            whatsappButton.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "TAG"
        private const val TAG_DETAILS = "TAG_DETAILS"
        fun start(context: Context, tag: StoreEntity, tagDetails: StoreDetailsEntity) {
            val intent = Intent(context, MapInfoStoreDetail::class.java)
            intent.putExtra(TAG, tag)
            intent.putExtra(TAG_DETAILS, tagDetails)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}