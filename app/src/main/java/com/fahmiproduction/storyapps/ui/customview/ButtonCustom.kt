package com.fahmiproduction.storyapps.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.fahmiproduction.storyapps.R

class ButtonCustom : AppCompatButton {
    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackgroud: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackgroud
        textSize = 12f
        gravity = Gravity.CENTER
    }

    private fun init() {
        enabledBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_button_enable) as Drawable
        disabledBackgroud =
            ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}