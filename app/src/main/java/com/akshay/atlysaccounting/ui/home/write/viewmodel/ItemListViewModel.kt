/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akshay.atlysaccounting.data.model.SingleEntryInvoice
import com.akshay.atlysaccounting.ui.home.write.WriteInvoiceItemViewModel
import com.akshay.atlysaccounting.ui.home.write.listener.ItemListListener
import com.akshay.atlysaccounting.utility.isValid

/**
 * ViewModel for item in [WriteInvoiceItemViewModel].
 */
class ItemListViewModel(
  items: List<SingleEntryInvoice>?
) : WriteInvoiceItemViewModel(), ItemListListener {

  val tempList = MutableLiveData<List<ItemListItemViewModel>>()

  init {
    tempList.value = parseList(items)
  }

  val itemList: LiveData<List<ItemListItemViewModel>> by lazy {
    tempList
  }

  override fun retrieveInput(): List<ItemListItemViewModel>? {
    return tempList.value
  }

  private fun parseList(items: List<SingleEntryInvoice>?): List<ItemListItemViewModel>? {
    if (items.isValid()) {
      val list = mutableListOf<ItemListItemViewModel>()
      items!!.forEach {
        val itemViewModel = ItemListItemViewModel(
          it.name,
          it.quantity,
          it.price,
          it.total
        )
        list.add(itemViewModel)
      }
      return list
    } else {
      return null
    }
  }
}
