/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.akshay.roomaccounting.data.model.Address
import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.SingleEntryInvoice
import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.di.qualifier.IoDispatcher
import com.akshay.roomaccounting.di.qualifier.MainDispatcher
import com.akshay.roomaccounting.repository.DatabaseRepository
import com.akshay.roomaccounting.ui.base.BaseViewModel
import com.akshay.roomaccounting.ui.home.write.listener.ClientCityPostCodeAddressListener
import com.akshay.roomaccounting.ui.home.write.listener.ClientCountryAddressListener
import com.akshay.roomaccounting.ui.home.write.listener.ClientEmailListener
import com.akshay.roomaccounting.ui.home.write.listener.ClientNameListener
import com.akshay.roomaccounting.ui.home.write.listener.ClientStreetAddressListener
import com.akshay.roomaccounting.ui.home.write.listener.DescriptionListener
import com.akshay.roomaccounting.ui.home.write.listener.InvoiceDateListener
import com.akshay.roomaccounting.ui.home.write.listener.ItemListListener
import com.akshay.roomaccounting.ui.home.write.listener.PaymentTermsListener
import com.akshay.roomaccounting.ui.home.write.listener.SenderCityPostCodeAddressListener
import com.akshay.roomaccounting.ui.home.write.listener.SenderCountryAddressListener
import com.akshay.roomaccounting.ui.home.write.listener.SenderStreetAddressListener
import com.akshay.roomaccounting.ui.home.write.viewmodel.BillFromViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.BillToViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ClientCityPostCodeViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ClientCountryViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ClientEmailViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ClientNameViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ClientStreetAddressViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.DescriptionViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.InvoiceDateViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ItemListItemViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ItemListViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.PaymentTermsViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.SenderCityPostCodeViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.SenderCountryViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.SenderStreetAddressViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.TitleViewModel
import com.akshay.roomaccounting.utility.DateParser.getDateAfterNumberOfDays
import com.akshay.roomaccounting.utility.StringGenerator.generateUniqueId
import com.akshay.roomaccounting.utility.isValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for [WriteInvoiceFragment].
 */
@HiltViewModel
class WriteInvoiceViewModel @Inject constructor(
  private val databaseRepository: DatabaseRepository,
  @MainDispatcher val dispatcher: CoroutineDispatcher,
  @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

  var isEdit = false

  val isSavedSuccessfully = MutableLiveData<Boolean>(false)
  val isDiscardOrComplete = MutableLiveData<Boolean>(false)

  private var senderStreetAddress: String? = null
  private var senderCityAddress: String? = null
  private var senderPostCodeAddress: String? = null
  private var senderCountryAddress: String? = null

  private var clientStreetAddress: String? = null
  private var clientCityAddress: String? = null
  private var clientPostCodeAddress: String? = null
  private var clientCountryAddress: String? = null

  private var clientName: String? = null
  private var clientEmail: String? = null
  private var description: String? = null

  private var invoiceDate: String? = null
  private var paymentTerm: Int? = null
  private var status: StatusInvoice? = null

  private var itemList: MutableList<SingleEntryInvoice>? = null
  private var total: Double = 0.0

  private lateinit var invoiceId: String

  private val invoiceMutableLiveData = MutableLiveData<Invoice>(null)
  private val computedItemList = ArrayList<WriteInvoiceItemViewModel>()

  val writeInvoiceList: LiveData<List<WriteInvoiceItemViewModel>> by lazy {
    Transformations.map(fetchInvoice(), ::processData)
  }

  private fun fetchInvoice(): LiveData<Invoice> {
    if (::invoiceId.isInitialized) {
      viewModelScope.launch {
        databaseRepository.getInvoiceById(invoiceId).collectLatest {
          invoiceMutableLiveData.value = it
          status = it?.status
        }
      }
    }
    return invoiceMutableLiveData
  }

  private fun processData(invoice: Invoice?): List<WriteInvoiceItemViewModel> {
    computedItemList.clear()
    computedItemList.add(TitleViewModel(id = invoice?.id))
    computedItemList.add(BillFromViewModel())
    computedItemList.add(SenderStreetAddressViewModel(invoice?.senderAddress?.street))
    computedItemList.add(
      SenderCityPostCodeViewModel(
        invoice?.senderAddress?.city,
        invoice?.senderAddress?.postCode
      )
    )
    computedItemList.add(SenderCountryViewModel(invoice?.senderAddress?.country))
    computedItemList.add(BillToViewModel())
    computedItemList.add(ClientNameViewModel(invoice?.clientName))
    computedItemList.add(ClientEmailViewModel(invoice?.clientEmail))
    computedItemList.add(ClientStreetAddressViewModel(invoice?.clientAddress?.street))
    computedItemList.add(
      ClientCityPostCodeViewModel(
        invoice?.clientAddress?.city,
        invoice?.clientAddress?.postCode
      )
    )
    computedItemList.add(ClientCountryViewModel(invoice?.clientAddress?.country))
    computedItemList.add(DescriptionViewModel(invoice?.description))
    computedItemList.add(InvoiceDateViewModel(invoice?.createdAt))
    // TODO - load existing value
    computedItemList.add(PaymentTermsViewModel())
    computedItemList.add(ItemListViewModel(invoice?.items))
    return computedItemList
  }

  fun discard() {
    isDiscardOrComplete.value = true
  }

  fun onSaveClick() {
    retrieveAllFields()
    if (validateAllFields()) {
      saveInvoice(createInvoice(StatusInvoice.PENDING))
    } else {
      // Tell user there are some fields which are not valid.
    }
  }

  fun saveAsDraft() {
    retrieveAllFields()
    saveInvoice(createInvoice(StatusInvoice.DRAFT))
  }

  fun saveChanges() {
    retrieveAllFields()
    if (validateAllFields()) {
      val updatedStatus = if (status!! == StatusInvoice.DRAFT) {
        StatusInvoice.PENDING
      } else {
        status
      }
      saveInvoice(createInvoice(updatedStatus!!, invoiceId))
    } else {
      // Tell user there are some fields which are not valid.
    }
  }

  fun setInvoiceId(id: String) {
    invoiceId = id
  }

  /**
   * Save the given invoice to the database.
   */
  private fun saveInvoice(invoice: Invoice) {
    viewModelScope.launch(ioDispatcher) {
      databaseRepository.saveInvoice(invoice)
      withContext(dispatcher) {
        isSavedSuccessfully.value = true
      }
    }
  }

  /**
   * Get the user inputs from each field.
   */
  private fun retrieveAllFields() {
    computedItemList.forEach { writeInvoiceItemViewModel ->
      when (writeInvoiceItemViewModel) {
        is SenderStreetAddressListener -> {
          val listener = writeInvoiceItemViewModel as SenderStreetAddressListener
          senderStreetAddress = listener.retrieveInput()
        }
        is SenderCityPostCodeAddressListener -> {
          val listener = writeInvoiceItemViewModel as SenderCityPostCodeAddressListener
          val pair = listener.retrieveInput()
          senderCityAddress = pair.first
          senderPostCodeAddress = pair.second
        }
        is SenderCountryAddressListener -> {
          val listener = writeInvoiceItemViewModel as SenderCountryAddressListener
          senderCountryAddress = listener.retrieveInput()
        }
        is ClientStreetAddressListener -> {
          val listener = writeInvoiceItemViewModel as ClientStreetAddressListener
          clientStreetAddress = listener.retrieveInput()
        }
        is ClientCityPostCodeAddressListener -> {
          val listener = writeInvoiceItemViewModel as ClientCityPostCodeAddressListener
          val pair = listener.retrieveInput()
          clientCityAddress = pair.first
          clientPostCodeAddress = pair.second
        }
        is ClientCountryAddressListener -> {
          val listener = writeInvoiceItemViewModel as ClientCountryAddressListener
          clientCountryAddress = listener.retrieveInput()
        }
        is ClientNameListener -> {
          val listener = writeInvoiceItemViewModel as ClientNameListener
          clientName = listener.retrieveInput()
        }
        is ClientEmailListener -> {
          val listener = writeInvoiceItemViewModel as ClientEmailListener
          clientEmail = listener.retrieveInput()
        }
        is DescriptionListener -> {
          val listener = writeInvoiceItemViewModel as DescriptionListener
          description = listener.retrieveInput()
        }
        is InvoiceDateListener -> {
          val listener = writeInvoiceItemViewModel as InvoiceDateListener
          invoiceDate = listener.retrieveInput()
        }
        is PaymentTermsListener -> {
          val listener = writeInvoiceItemViewModel as PaymentTermsListener
          paymentTerm = listener.retrieveInput()
        }
        is ItemListListener -> {
          val listener = writeInvoiceItemViewModel as ItemListListener
          setItemList(listener.retrieveInput())
        }
      }
    }
  }

  /**
   * Check the validity of each user input.
   */
  private fun validateAllFields(): Boolean {
    return (senderStreetAddress.isValid()
        && senderCityAddress.isValid()
        && senderPostCodeAddress.isValid()
        && senderCountryAddress.isValid()
        && clientStreetAddress.isValid()
        && clientCityAddress.isValid()
        && clientPostCodeAddress.isValid()
        && clientCountryAddress.isValid()
        && clientName.isValid()
        && clientEmail.isValid()
        && description.isValid()
        && invoiceDate.isValid()
        && paymentTerm.isValid()
        && itemList.isValid())
  }

  /**
   * Create an Invoice object.
   *
   * @param status is the one of the constant from the [StatusInvoice].
   * @param id is the primary key of the invoice. If not pass, it will use the default parameter which
   * generates the new id.
   *
   * @return [Invoice] object.
   */
  private fun createInvoice(status: StatusInvoice, id: String = generateUniqueId()): Invoice {
    return Invoice(
      id = id,
      createdAt = invoiceDate,
      paymentDue = createPaymentDue(),
      description = description,
      paymentTerms = paymentTerm,
      clientName = clientName,
      clientEmail = clientEmail,
      status = status,
      senderAddress = createSenderAddress(),
      clientAddress = createClientAddress(),
      items = itemList,
      total = total
    )
  }

  /**
   * Compute payment due date.
   */
  private fun createPaymentDue(): String? {
    if (invoiceDate.isValid() && paymentTerm.isValid()) {
      return getDateAfterNumberOfDays(
        startingDate = invoiceDate!!,
        numberOfDays = paymentTerm!!
      )
    } else {
      return null
    }
  }

  /**
   * Create [Address] object sender's address.
   */
  private fun createSenderAddress(): Address {
    return Address(
      street = senderStreetAddress,
      city = senderCityAddress,
      postCode = senderPostCodeAddress,
      country = senderCountryAddress
    )
  }

  /**
   * Create [Address] object client's address.
   */
  private fun createClientAddress(): Address {
    return Address(
      street = clientStreetAddress,
      city = clientCityAddress,
      postCode = clientPostCodeAddress,
      country = clientCountryAddress
    )
  }

  private fun setItemList(list: List<ItemListItemViewModel>?) {
    if (list.isValid()) {
      itemList = mutableListOf<SingleEntryInvoice>()
      list!!.forEach {
        itemList!!.add(createSingleEntryInvoice(it))
      }
    } else {
      itemList = null
    }
  }

  /**
   * Create [SingleEntryInvoice] object.
   */
  private fun createSingleEntryInvoice(itemListItemViewModel: ItemListItemViewModel): SingleEntryInvoice {
    var quantity: Int? = null
    if (itemListItemViewModel.quantity.toString().isNotBlank()) {
      quantity = itemListItemViewModel.quantity.toString().toInt()
    }
    var price: Double? = null
    if (itemListItemViewModel.price.toString().isNotBlank()) {
      price = itemListItemViewModel.price.toString().toDouble()
    }
    var itemTotal: Double? = null
    if (itemListItemViewModel.total.get().toString().isNotBlank()) {
      itemTotal = itemListItemViewModel.total.get().toString().toDouble()
      total += itemTotal
    }
    return SingleEntryInvoice(
      name = itemListItemViewModel.name.toString(),
      quantity = quantity,
      price = price,
      total = itemTotal
    )
  }

  /**
   * Check if the list of [SingleEntryInvoice] is valid or not.
   */
  private fun MutableList<SingleEntryInvoice>?.isValid(): Boolean {
    if (this != null && this.isNotEmpty()) {
      this.forEach {
        if (!it.isValid())
          return false
      }
      return true
    } else {
      return false
    }
  }

  /**
   * Check if all the items of a [SingleEntryInvoice] is valid or not.
   */
  private fun SingleEntryInvoice.isValid(): Boolean {
    return (name.isValid()
        && quantity.isValid()
        && price.isValid()
        && total.isValid())
  }
}
