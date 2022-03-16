/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.akshay.atlysaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.atlysaccounting.ui.home.write.listener.SenderStreetAddressListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class SenderStreetAddressViewModel(
  street: String?
) : WriteInvoiceItemViewModel(), SenderStreetAddressListener {

  var answerText: CharSequence

  init {
    answerText = street ?: ""
  }

  override fun retrieveInput(): String {
    return answerText.toString()
  }

  fun getStreetAddressTextWatcher(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        answerText = answer.toString().trim()
        // we can show real time errors
      }

      override fun afterTextChanged(s: Editable) {
      }
    }
  }
}
