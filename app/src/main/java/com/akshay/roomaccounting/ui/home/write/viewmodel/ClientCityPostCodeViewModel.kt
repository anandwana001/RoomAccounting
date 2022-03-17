/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.akshay.roomaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.roomaccounting.ui.home.write.listener.ClientCityPostCodeAddressListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class ClientCityPostCodeViewModel(
  clientCity: String?, clientPostCode: String?
) : WriteInvoiceItemViewModel(), ClientCityPostCodeAddressListener {

  var city: CharSequence
  var postCode: CharSequence

  init {
    city = clientCity ?: ""
    postCode = clientPostCode ?: ""
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
