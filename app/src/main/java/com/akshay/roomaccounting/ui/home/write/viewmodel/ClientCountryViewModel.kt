/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.akshay.roomaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.roomaccounting.ui.home.write.listener.ClientCountryAddressListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class ClientCountryViewModel(
  clientCountry: String?
) : WriteInvoiceItemViewModel(), ClientCountryAddressListener {

  var country: CharSequence

  init {
    country = clientCountry ?: ""
  }

  override fun retrieveInput(): String {
    return country.toString()
  }

  fun getCountryAddressTextWatcher(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        country = answer.toString().trim()
        // we can show real time errors
      }

      override fun afterTextChanged(s: Editable) {
      }
    }
  }

}