package com.technopark.youtrader.utils

import android.app.AlertDialog
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.technopark.youtrader.R

fun Fragment.showOneEditTextAlertDialog(
    title: String,
    text: String = "",
    hint: String = "",
    callback: (str: String) -> Unit
) {
    val inflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.one_edit_text_alert_dialog, null)
    val editFullName = dialogLayout.findViewById<EditText>(R.id.edit_full_name)
    editFullName.setText(text)
    editFullName.hint = hint
    AlertDialog.Builder(context)
        .setTitle(title)
        .setView(dialogLayout)
        .setPositiveButton("OK") { _, _ ->
            if (editFullName.text.toString().isNotEmpty()) {
                callback(editFullName.text.toString())
            }
        }
        .create().show()
}
