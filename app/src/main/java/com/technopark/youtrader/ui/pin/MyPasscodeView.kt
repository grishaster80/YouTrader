package com.technopark.youtrader.ui.pin

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import online.devliving.passcodeview.PasscodeView

class MyPasscodeView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    PasscodeView(context, attrs, defStyleAttr) {
    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onTouchEvent(event: MotionEvent) = true
}
