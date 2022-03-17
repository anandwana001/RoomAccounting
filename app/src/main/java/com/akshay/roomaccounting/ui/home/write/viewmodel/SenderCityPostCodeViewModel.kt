/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.akshay.roomaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.roomaccounting.ui.home.write.listener.SenderCityPostCodeAddressListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class SenderCityPostCodeViewModel(
  senderCity: String?,
  senderPostCode: String?
): WriteInvoiceItemViewModel(), SenderCityPostCodeAddressListener {

  var city: CharSequence
  var postCode: CharSequence

  init {
    city = senderCity ?: ""
    postCode = senderPostCode ?: ""
  }

  override fun retrieveInput(): Pair<String, String> {
    return Pair<String, String>(city.toString(), postCode.toString())
  }

  fun getCityAddressTextWatcher(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        city = answer.toString().trim()
        // we can show real time errors
      }

      override fun afterTextChanged(s: Editable) {
      }
    }
  }

  fun getPostCodeAddressTextWatcher(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        postCode = answer.toString().trim()
        // we can show real time errors
      }

      override fun afterTextChanged(s: Editable) {
      }
    }
  }

}
