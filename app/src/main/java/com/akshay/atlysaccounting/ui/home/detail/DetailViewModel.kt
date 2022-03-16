/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akshay.atlysaccounting.data.model.Address
import com.akshay.atlysaccounting.data.model.SingleEntryInvoice

/**
 * ViewModel to hold the detail view type of the list in [DetailInvoiceFragment].
 */
class DetailViewModel(
  val id: String,
  val description: String?,
  val clientName: String?,
  val clientEmail: String?,
  val clientAddress: Address?,
  val senderAddress: Address?,
  val createdAt: String?,
  val paymentDue: String?,
  val total: Double?,
  val items: List<SingleEntryInvoice>?
) : DetailInvoiceItemViewModel() {

  private val computedItemList = ArrayList<SingleEntryInvoiceViewModel>()

  val billItemList: LiveData<List<SingleEntryInvoiceViewModel>?> by lazy {
    processData(items)
  }

  private fun processData(items: List<SingleEntryInvoice>?): LiveData<List<SingleEntryInvoiceViewModel>?> {
    val tempList = MutableLiveData<List<SingleEntryInvoiceViewModel>>()
    items?.let { list ->
      computedItemList.clear()
      list.forEach { item ->
        val singleEntryInvoiceViewModel = SingleEntryInvoiceViewModel(
          item.name,
          item.quantity,
          item.price,
          item.total
        )
        computedItemList.add(singleEntryInvoiceViewModel)
      }
      tempList.value = computedItemList
      return tempList
    } ?: run {
      return tempList
    }
  }
}
