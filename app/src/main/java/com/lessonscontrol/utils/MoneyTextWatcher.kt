package com.lessonscontrol.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat

/**
 * Watcher used for the currency input.
 * Source: https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext
 *
 * @author hblonski
 */
class MoneyTextWatcher(editText: EditText) : TextWatcher {

    private val editTextWeakReference: WeakReference<EditText> = WeakReference(editText)

    override fun afterTextChanged(editable: Editable) {
        val editText = editTextWeakReference.get() ?: return
        val s = editable.toString()
        if (s.isEmpty()) return
        editText.removeTextChangedListener(this)
        val cleanString = s.replace("[^\\d]".toRegex(), "")
        val parsed = BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)
        val formatted = NumberFormat.getCurrencyInstance().format(parsed)
        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}