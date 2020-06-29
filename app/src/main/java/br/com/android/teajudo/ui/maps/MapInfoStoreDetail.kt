package br.com.android.teajudo.ui.maps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import br.com.android.teajudo.BaseActivity
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.AvailableEntity
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.utils.GeneralUtils
import com.module.coreapps.utils.SharedUtils
import kotlinx.android.synthetic.main.activity_map_info_store_detail.*
import kotlinx.android.synthetic.main.activity_maps.toolbar

class MapInfoStoreDetail: BaseActivity() {

    private lateinit var tagStore: StoreEntity
    private lateinit var tagStoreDetails: StoreDetailsEntity
    private lateinit var tagAvailable: AvailableEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_info_store_detail)

        setSupportActionBar(toolbar)
        supportActionBar.let {
            it?.setDisplayHomeAsUpEnabled(true)
        }
        toolbar.setNavigationIcon(R.drawable.arrow_left_white_18)

        toolbar.setNavigationOnClickListener {
//            onBackPressed()
            MapsActivity.start(this)
            finish()
        }

        renderLayout()

    }

    private fun renderLayout() {
        tagStore = intent.getParcelableExtra(TAG)
        tagStoreDetails = intent.getParcelableExtra(TAG_DETAILS)
        tagAvailable = intent.getParcelableExtra(TAG_AVAILABLE)

        txtMarkerName.text = tagStore.name

        settingWhatAppButton(tagStore)
        settingEmailButton(tagStore)
        settingPhoneButton(tagStore)

        settingShowAndHideOptionsFromAvailable(tagAvailable)
        settingShowAndHideOptions(tagStoreDetails)
        removeLabelOptions(tagStoreDetails, tagAvailable)
    }

    private fun settingShowAndHideOptionsFromAvailable(tagAvailable: AvailableEntity) {
        if(tagAvailable.idAvailable != 0) {
            delivery_item.visibility = GeneralUtils.showAndHideButton(tagAvailable.delivery)
        }
    }

    private fun settingShowAndHideOptions(tagStoreDetails: StoreDetailsEntity) {
        tagStoreDetails.let {
            if (it != null) {
                shopping_item.visibility = GeneralUtils.showAndHideButton(it.talk)
                food_item.visibility = GeneralUtils.showAndHideButton(it.food)
                farmacy_item.visibility = GeneralUtils.showAndHideButton(it.health)
                conversation_item.visibility = GeneralUtils.showAndHideButton(it.market)
                dog_item.visibility = GeneralUtils.showAndHideButton(it.dog)
            }
        }
    }

    private fun removeLabelOptions(tagStoreDetails: StoreDetailsEntity, tagAvailable: AvailableEntity){
        val anyElementNull = listOf(
            tagStoreDetails.talk,
            tagStoreDetails.food,
            tagStoreDetails.health,
            tagStoreDetails.market,
            tagAvailable.delivery
        ).any { it != null }

        if(!anyElementNull && tagAvailable.delivery == false) {
            textView3.visibility = View.GONE
        } else {
            textView3.visibility = View.VISIBLE
        }
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

    private fun settingEmailButton(tagStore: StoreEntity) {
        if(tagStore.email != "") {
            tagStore.email.let {
                var emailStuff = arrayOf(
                    resources.getString(R.string.email_subject),
                    resources.getString(R.string.email_body),
                    resources.getString(R.string.email_chooser_title),
                    "")
                SharedUtils.emailRedirect(this, emailButton, it, emailStuff)
            }
        } else {
            emailButton.visibility = View.GONE
        }
    }

    private fun settingPhoneButton(tagStore: StoreEntity) {
        if(tagStore.phone != null) {
            tagStore.phone.let {
                SharedUtils.phoneRedirect(this, phoneButton, tagStore.phone)
            }
        } else {
            phoneButton.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "TAG"
        private const val TAG_DETAILS = "TAG_DETAILS"
        private const val TAG_AVAILABLE = "TAG_AVAILABLE"
        fun start(context: Context, tag: StoreEntity, tagDetails: StoreDetailsEntity, available: AvailableEntity) {
            val intent = Intent(context, MapInfoStoreDetail::class.java)
            intent.putExtra(TAG, tag)
            intent.putExtra(TAG_DETAILS, tagDetails)
            intent.putExtra(TAG_AVAILABLE, available)
            ContextCompat.startActivity(context, intent, null)
        }
    }
}