/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.roomaccounting.ui.binding

import android.R.layout.simple_list_item_1
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter

/**
 * General BindingAdapter for Supporting Views.
 */
@BindingAdapter("app:dropDownList")
fun setDropDownList(
  spinner: AppCompatSpinner,
  string: Array<String>
) {
  val adapter =
    ArrayAdapter(spinner.context, simple_list_item_1, string)
  spinner.adapter = adapter
}

@BindingAdapter("app:onItemSelected")
fun setDropDownListValue(
  spinner: AppCompatSpinner,
  listener: AdapterView.OnItemSelectedListener
) {
  spinner.onItemSelectedListener = listener
}
