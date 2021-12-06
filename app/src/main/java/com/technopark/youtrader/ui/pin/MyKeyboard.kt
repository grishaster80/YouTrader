package com.technopark.youtrader.ui.pin

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.technopark.youtrader.R
import online.devliving.passcodeview.PasscodeView

class MyKeyboard(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    // constructors
    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    private var mPassView: PasscodeView? = null
    private var keyValues = SparseArray<String>()

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_delete -> {
                mPassView?.clearText()
            }
            else -> {
                val value = keyValues[v.id]
                mPassView?.setText(mPassView?.text.toString() + value)
            }
        }
    }

    fun setPasscodeView(pv: PasscodeView) {
        mPassView = pv
    }

    init {
        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.keyboard, this, true)
        findViewById<View>(R.id.button_1)?.setOnClickListener(this)
        findViewById<View>(R.id.button_2)?.setOnClickListener(this)
        findViewById<View>(R.id.button_3)?.setOnClickListener(this)
        findViewById<View>(R.id.button_4)?.setOnClickListener(this)
        findViewById<View>(R.id.button_5)?.setOnClickListener(this)
        findViewById<View>(R.id.button_6)?.setOnClickListener(this)
        findViewById<View>(R.id.button_7)?.setOnClickListener(this)
        findViewById<View>(R.id.button_8)?.setOnClickListener(this)
        findViewById<View>(R.id.button_9)?.setOnClickListener(this)
        findViewById<View>(R.id.button_0)?.setOnClickListener(this)
        findViewById<View>(R.id.button_delete)?.setOnClickListener(this)

        // map buttons IDs to input strings
        keyValues.put(R.id.button_1, "1")
        keyValues.put(R.id.button_2, "2")
        keyValues.put(R.id.button_3, "3")
        keyValues.put(R.id.button_4, "4")
        keyValues.put(R.id.button_5, "5")
        keyValues.put(R.id.button_6, "6")
        keyValues.put(R.id.button_7, "7")
        keyValues.put(R.id.button_8, "8")
        keyValues.put(R.id.button_9, "9")
        keyValues.put(R.id.button_0, "0")
    }
}
