/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.viewmodel

import androidx.databinding.ObservableField
import com.akshay.atlysaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.atlysaccounting.ui.home.write.listener.InvoiceDateListener
import com.akshay.atlysaccounting.utility.DateParser.formatDateStringToPattern

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class InvoiceDateViewModel(
  createdAt: String?
) : WriteInvoiceItemViewModel(), InvoiceDateListener {

  var date = ObservableField<String>("")

  init {
    createdAt?.let {
      date.set(formatDateStringToPattern(it, "dd MMM yyyy"))
    }
  }

  override fun retrieveInput(): String {
    return date.get().toString()
  }

  fun setDate(date: String) {
    this.date.set(date)
  }
}
