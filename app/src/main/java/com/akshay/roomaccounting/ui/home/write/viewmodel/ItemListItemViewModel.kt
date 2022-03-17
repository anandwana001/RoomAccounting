/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableField
import com.akshay.roomaccounting.ui.home.write.WriteInvoiceItemViewModel

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class ItemListItemViewModel(
  itemName: String?, itemQuantity: Int?, itemPrice: Double?, itemTotal: Double?
) : WriteInvoiceItemViewModel() {

  var name: CharSequence
  var quantity: CharSequence
  var price: CharSequence
  var total = ObservableField<String>("")

  init {
    name = itemName ?: ""
    quantity = itemQuantity?.toString() ?: ""
    price = itemPrice?.toString() ?: ""
    itemTotal?.let {
      total.set(it.toString())
    }

  }

  fun getItemNameListener(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        name = s.toString()
      }

      override fun afterTextChanged(s: Editable?) {}
    }
  }

  fun getItemQuantityListener(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        quantity = s.toString()
        computeTotalAmount(price.toString(), quantity.toString())
      }

      override fun afterTextChanged(s: Editable?) {}
    }
  }

  fun getItemPriceListener(): TextWatcher {
    return object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        price = s.toString()
        computeTotalAmount(price.toString(), quantity.toString())
      }

      override fun afterTextChanged(s: Editable?) {}
    }
  }

  private fun computeTotalAmount(computedPrice: String, computeQuantity: String) {
    if (computeQuantity.isNotBlank() && computedPrice.isNotBlank()) {
      if (computedPrice.endsWith(".")) {
        computedPrice.replace(".", "")
      }
      val computedTotal = computedPrice.toDouble() * computeQuantity.toInt()
      total.set(computedTotal.toString())
    } else {
      total.set("")
    }
  }
}