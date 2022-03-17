/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.akshay.roomaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.roomaccounting.ui.home.write.listener.DescriptionListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class DescriptionViewModel(
  des: String?
) : WriteInvoiceItemViewModel(), DescriptionListener {

  var description: CharSequence

  init {
    description = des ?: ""
  }

  override fun retrieveInput(): String {
    return description.toString()
  }

  fun getDescriptionAddressTextWatcher(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        description = answer.toString().trim()
        // we can show real time errors
      }

      override fun afterTextChanged(s: Editable) {
      }
    }
  }
}