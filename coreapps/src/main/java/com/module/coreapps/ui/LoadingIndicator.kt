package com.module.coreapps.ui

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.module.coreapps.R

class LoadingIndicator : LinearLayout {

    private var _bgColor: Int = ContextCompat.getColor(
        context,
        R.color.transparent
    )

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    fun start() {
        visibility = View.VISIBLE
    }

    fun stop() {
        Handler().postDelayed({
            visibility = View.GONE
        }, 300)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        inflate(context, R.layout.loading_indicator, this)

        val loadingIcon: ImageView = findViewById(R.id.ivLoadingIcon)
        val lavLoading: LottieAnimationView = findViewById(R.id.lavLoading)

        val attributes = context.obtainStyledAttributes(
            attrs, R.styleable.LoadingIndicator, defStyle, 0
        )

        val bgColor = attributes.getColor(
            R.styleable.LoadingIndicator_bgColor,
            _bgColor
        )

        val icon = attributes.getDrawable(
            R.styleable.LoadingIndicator_icon
        )

        this.setBackgroundColor(bgColor)

        icon?.let {
            loadingIcon.setImageDrawable(it)
        }

        attributes.recycle()
    }
}