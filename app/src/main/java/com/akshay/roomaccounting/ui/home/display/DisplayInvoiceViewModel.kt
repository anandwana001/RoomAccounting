/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.display

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akshay.roomaccounting.data.model.MinimalInvoice
import com.akshay.roomaccounting.repository.DatabaseRepository
import com.akshay.roomaccounting.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel of [DisplayInvoiceFragment].
 */
@HiltViewModel
class DisplayInvoiceViewModel @Inject constructor(
  private val databaseRepository: DatabaseRepository
) : BaseViewModel() {

  private val computedItemList = ArrayList<DisplayInvoiceItemViewModel>()
  val displayItemViewModelList: LiveData<List<DisplayInvoiceItemViewModel>> by lazy {
    fetchMinimalInvoiceList()
  }

  val invoiceCount = ObservableField<Int>(0)

  private fun fetchMinimalInvoiceList(): LiveData<List<DisplayInvoiceItemViewModel>> {
    val tempList = MutableLiveData<List<DisplayInvoiceItemViewModel>>()
    viewModelScope.launch {
      databaseRepository.getAllMinimalInvoices().collectLatest {
        tempList.value = processData(it)
        invoiceCount.set(it.size)
      }
    }
    return tempList
  }

  private fun processData(list: List<MinimalInvoice>): List<DisplayInvoiceItemViewModel> {
    computedItemList.clear()
    list.forEach { minimalInvoice ->
      val minimalInvoiceItemViewModel = DisplayInvoiceItemViewModel(
        minimalInvoice.id,
        minimalInvoice.clientName,
        minimalInvoice.paymentDue,
        minimalInvoice.total,
        minimalInvoice.status
      )
      computedItemList.add(minimalInvoiceItemViewModel)
    }
    return computedItemList
  }
}
