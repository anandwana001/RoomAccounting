/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.viewmodel

import android.view.View
import android.widget.AdapterView
import androidx.databinding.ObservableField
import com.akshay.atlysaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.atlysaccounting.ui.home.write.listener.PaymentTermsListener

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class PaymentTermsViewModel : WriteInvoiceItemViewModel(), PaymentTermsListener {

  var term = ObservableField<Int>(1)

  fun getItemSelectedListener(): AdapterView.OnItemSelectedListener {
    return object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
          0 -> term.set(1)
          1 -> term.set(7)
          2 -> term.set(14)
          3 -> term.set(30)
        }
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
      }
    }
  }

  override fun retrieveInput(): Int {
    return term.get()!!
  }
}
