/**
 * Created by anandwana001 on
 * 05, March, 2022
 **/

package com.akshay.atlysaccounting.ui.binding

import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter

/**
 * BindingAdapter specific to EditText and its relatable views.
 */
@BindingAdapter("app:textChangedListener")
fun bindTextWatcher(editText: EditText, textWatcher: TextWatcher?) {
  editText.addTextChangedListener(textWatcher)
}
