/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import com.akshay.atlysaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.atlysaccounting.ui.home.write.listener.ClientNameListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class ClientNameViewModel(
  clientName: String?
) : WriteInvoiceItemViewModel(), ClientNameListener {

  var name: CharSequence

  init {
    name = clientName ?: ""
  }

  override fun retrieveInput(): String {
    return name.toString()
  }

  fun getNameTextWatcher(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        name = answer.toString().trim()
        // we can show real time errors
      }

      override fun afterTextChanged(s: Editable) {
      }
    }
  }

}