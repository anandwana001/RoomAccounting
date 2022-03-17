/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.detail

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.di.qualifier.IoDispatcher
import com.akshay.roomaccounting.di.qualifier.MainDispatcher
import com.akshay.roomaccounting.repository.DatabaseRepository
import com.akshay.roomaccounting.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for [DetailInvoiceFragment].
 */
@HiltViewModel
class DetailInvoiceViewModel @Inject constructor(
  private val databaseRepository: DatabaseRepository,
  @IoDispatcher val ioDispatcher: CoroutineDispatcher,
  @MainDispatcher val mainDispatcher: CoroutineDispatcher
) : BaseViewModel() {

  val isInvoiceDeleted = MutableLiveData<Boolean>(false)
  val currentStatus = ObservableField<StatusInvoice>()

  private val computedItemList = ArrayList<DetailInvoiceItemViewModel>()
  val detailItemList: LiveData<List<DetailInvoiceItemViewModel>> by lazy {
    fetchInvoiceById()
  }

  private lateinit var invoiceId: String

  /**
   * Get the complete Invoice and display it.
   */
  fun fetchInvoiceById(): LiveData<List<DetailInvoiceItemViewModel>> {
    val tempList = MutableLiveData<List<DetailInvoiceItemViewModel>>()
    viewModelScope.launch {
      databaseRepository.getInvoiceById(invoiceId).collect {
        it?.let {
          tempList.value = processData(it)
          currentStatus.set(it.status)
        }
      }
    }
    return tempList
  }

  /**
   * Delete Invoice.
   */
  fun deleteInvoice(id: String) {
    viewModelScope.launch(ioDispatcher) {
      databaseRepository.deleteInvoiceById(id)
      // thread switching
      withContext(mainDispatcher) {
        isInvoiceDeleted.value = true
      }
    }
  }

  /**
   * Update the status of the Invoice to paid if not.
   */
  fun markInvoiceAsPaid() {
    if (currentStatus.get() != StatusInvoice.PAID) {
      viewModelScope.launch(ioDispatcher) {
        databaseRepository.markInvoiceAsPaidById(invoiceId)
        // thread switching
        withContext(mainDispatcher) {
          currentStatus.set(StatusInvoice.PAID)
        }
      }
    }
  }

  private fun processData(invoice: Invoice): List<DetailInvoiceItemViewModel> {
    computedItemList.clear()
    computedItemList.add(StatusViewModel(invoice.status))
    computedItemList.add(
      DetailViewModel(
        invoice.id,
        invoice.description,
        invoice.clientName,
        invoice.clientEmail,
        invoice.clientAddress,
        invoice.senderAddress,
        invoice.createdAt,
        invoice.paymentDue,
        invoice.total,
        invoice.items
      )
    )
    return computedItemList
  }

  fun setInvoiceId(id: String) {
    invoiceId = id
  }
}
