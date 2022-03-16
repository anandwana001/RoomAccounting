/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akshay.atlysaccounting.R
import com.akshay.atlysaccounting.databinding.FragmentWriteInvoiceBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceBillFromBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceBillToBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceCityPostcodeBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceClientCityBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceClientCountryBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceClientEmailBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceClientNameBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceClientStreetBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceCountryBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceDescriptionBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceInvoiceDateBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceItemListBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceItemListItemBinding
import com.akshay.atlysaccounting.databinding.WriteInvoicePaymentTermsBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceStreetAddressBinding
import com.akshay.atlysaccounting.databinding.WriteInvoiceTitleBinding
import com.akshay.atlysaccounting.ui.home.write.viewmodel.BillFromViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.BillToViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ClientCityPostCodeViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ClientCountryViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ClientEmailViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ClientNameViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ClientStreetAddressViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.DescriptionViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.InvoiceDateViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ItemListItemViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.ItemListViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.PaymentTermsViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.SenderCityPostCodeViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.SenderCountryViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.SenderStreetAddressViewModel
import com.akshay.atlysaccounting.ui.home.write.viewmodel.TitleViewModel
import com.akshay.atlysaccounting.ui.recyclerview.AtlysRecyclerViewAdapter
import com.akshay.atlysaccounting.ui.recyclerview.MultiViewTypeBuilder
import com.akshay.atlysaccounting.ui.recyclerview.SingleViewTypeBuilder
import com.akshay.atlysaccounting.utility.DateParser.getDateUsingPattern
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment to create or update an Invoice.
 */
@AndroidEntryPoint
class WriteInvoiceFragment : Fragment() {

  companion object {
    const val TAG_CREATE_INVOICE_FRAGMENT = "CREATE_INVOICE_FRAGMENT"
    const val WRITE_INVOICE_ARGUMENT_INVOICE_ID = "WRITE_INVOICE_ARGUMENT_INVOICE_ID"

    private const val TAG_DATE_PICKER = "DATE_PICKER"

    fun newInstance(id: String?): WriteInvoiceFragment {
      val createInvoiceFragment = WriteInvoiceFragment()
      val args = Bundle()
      args.putString(WRITE_INVOICE_ARGUMENT_INVOICE_ID, id)
      createInvoiceFragment.arguments = args
      return createInvoiceFragment
    }
  }

  private lateinit var binding: FragmentWriteInvoiceBinding
  private val writeInvoiceViewModel: WriteInvoiceViewModel by viewModels()
  private var invoiceId: String? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    invoiceId = arguments?.getString(WRITE_INVOICE_ARGUMENT_INVOICE_ID)

    binding = FragmentWriteInvoiceBinding.inflate(inflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      viewModel = writeInvoiceViewModel
    }

    invoiceId?.let {
      writeInvoiceViewModel.setInvoiceId(it)
      writeInvoiceViewModel.isEdit = true
    }
    setUpListener()
    setUpObserver()

    binding.invoiceFormRecyclerview.apply {
      adapter = createAtlysRecyclerViewAdapter()
    }

    return binding.root
  }

  private fun setUpListener() {
    binding.goBackArrowImageView.setOnClickListener {
      activity?.onBackPressed()
    }

    binding.goBackTextView.setOnClickListener {
      activity?.onBackPressed()
    }
  }

  private fun setUpObserver() {
    writeInvoiceViewModel.isSavedSuccessfully.observe(viewLifecycleOwner) {
      if (it) {
        activity?.onBackPressed()
      }
    }
    writeInvoiceViewModel.isDiscardOrComplete.observe(viewLifecycleOwner) {
      if (it) {
        activity?.onBackPressed()
      }
    }
  }

  private enum class ViewType {
    VIEW_TITLE_TEXT,
    VIEW_BILL_FROM,
    VIEW_SENDER_STREET_ADDRESS,
    VIEW_SENDER_CITY_POST_CODE,
    VIEW_SENDER_COUNTRY,
    VIEW_BILL_TO,
    VIEW_CLIENT_NAME,
    VIEW_CLIENT_EMAIL,
    VIEW_CLIENT_STREET_ADDRESS,
    VIEW_CLIENT_CITY_POST_CODE,
    VIEW_CLIENT_COUNTRY,
    VIEW_PROJECT_DESCRIPTION,
    VIEW_INVOICE_DATE,
    VIEW_PAYMENT_TERMS,
    VIEW_ITEM_LIST
  }

  private fun createAtlysRecyclerViewAdapter(): AtlysRecyclerViewAdapter<WriteInvoiceItemViewModel> {
    return MultiViewTypeBuilder
      .newBuilder<WriteInvoiceItemViewModel, ViewType> { viewModel ->
        when (viewModel) {
          is TitleViewModel -> ViewType.VIEW_TITLE_TEXT
          is BillFromViewModel -> ViewType.VIEW_BILL_FROM
          is SenderStreetAddressViewModel -> ViewType.VIEW_SENDER_STREET_ADDRESS
          is SenderCityPostCodeViewModel -> ViewType.VIEW_SENDER_CITY_POST_CODE
          is SenderCountryViewModel -> ViewType.VIEW_SENDER_COUNTRY
          is BillToViewModel -> ViewType.VIEW_BILL_TO
          is ClientNameViewModel -> ViewType.VIEW_CLIENT_NAME
          is ClientEmailViewModel -> ViewType.VIEW_CLIENT_EMAIL
          is ClientStreetAddressViewModel -> ViewType.VIEW_CLIENT_STREET_ADDRESS
          is ClientCityPostCodeViewModel -> ViewType.VIEW_CLIENT_CITY_POST_CODE
          is ClientCountryViewModel -> ViewType.VIEW_CLIENT_COUNTRY
          is DescriptionViewModel -> ViewType.VIEW_PROJECT_DESCRIPTION
          is InvoiceDateViewModel -> ViewType.VIEW_INVOICE_DATE
          is PaymentTermsViewModel -> ViewType.VIEW_PAYMENT_TERMS
          is ItemListViewModel -> ViewType.VIEW_ITEM_LIST
          else -> throw IllegalArgumentException("Encountered unexpected view model: $viewModel")
        }
      }
      .registerViewDataBinder(
        viewType = ViewType.VIEW_TITLE_TEXT,
        inflateDataBinding = WriteInvoiceTitleBinding::inflate,
        setViewModel = WriteInvoiceTitleBinding::setViewModel,
        transformViewModel = { it as TitleViewModel }
      )
      .registerViewBinder(
        viewType = ViewType.VIEW_BILL_FROM,
        inflateView = { parent ->
          WriteInvoiceBillFromBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            /* attachToParent= */ false
          ).root
        },
        bindView = { _, _ -> }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_SENDER_STREET_ADDRESS,
        inflateDataBinding = WriteInvoiceStreetAddressBinding::inflate,
        setViewModel = WriteInvoiceStreetAddressBinding::setViewModel,
        transformViewModel = { it as SenderStreetAddressViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_SENDER_CITY_POST_CODE,
        inflateDataBinding = WriteInvoiceCityPostcodeBinding::inflate,
        setViewModel = WriteInvoiceCityPostcodeBinding::setViewModel,
        transformViewModel = { it as SenderCityPostCodeViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_SENDER_COUNTRY,
        inflateDataBinding = WriteInvoiceCountryBinding::inflate,
        setViewModel = WriteInvoiceCountryBinding::setViewModel,
        transformViewModel = { it as SenderCountryViewModel }
      )
      .registerViewBinder(
        viewType = ViewType.VIEW_BILL_TO,
        inflateView = { parent ->
          WriteInvoiceBillToBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            /* attachToParent= */ false
          ).root
        },
        bindView = { _, _ -> }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_CLIENT_NAME,
        inflateDataBinding = WriteInvoiceClientNameBinding::inflate,
        setViewModel = WriteInvoiceClientNameBinding::setViewModel,
        transformViewModel = { it as ClientNameViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_CLIENT_EMAIL,
        inflateDataBinding = WriteInvoiceClientEmailBinding::inflate,
        setViewModel = WriteInvoiceClientEmailBinding::setViewModel,
        transformViewModel = { it as ClientEmailViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_CLIENT_STREET_ADDRESS,
        inflateDataBinding = WriteInvoiceClientStreetBinding::inflate,
        setViewModel = WriteInvoiceClientStreetBinding::setViewModel,
        transformViewModel = { it as ClientStreetAddressViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_CLIENT_CITY_POST_CODE,
        inflateDataBinding = WriteInvoiceClientCityBinding::inflate,
        setViewModel = WriteInvoiceClientCityBinding::setViewModel,
        transformViewModel = { it as ClientCityPostCodeViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_CLIENT_COUNTRY,
        inflateDataBinding = WriteInvoiceClientCountryBinding::inflate,
        setViewModel = WriteInvoiceClientCountryBinding::setViewModel,
        transformViewModel = { it as ClientCountryViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_PROJECT_DESCRIPTION,
        inflateDataBinding = WriteInvoiceDescriptionBinding::inflate,
        setViewModel = WriteInvoiceDescriptionBinding::setViewModel,
        transformViewModel = { it as DescriptionViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_INVOICE_DATE,
        inflateDataBinding = WriteInvoiceInvoiceDateBinding::inflate,
        setViewModel = this::bindInvoiceDateViewModel,
        transformViewModel = { it as InvoiceDateViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_PAYMENT_TERMS,
        inflateDataBinding = WriteInvoicePaymentTermsBinding::inflate,
        setViewModel = WriteInvoicePaymentTermsBinding::setViewModel,
        transformViewModel = { it as PaymentTermsViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_ITEM_LIST,
        inflateDataBinding = WriteInvoiceItemListBinding::inflate,
        setViewModel = this::bindItemListViewModel,
        transformViewModel = { it as ItemListViewModel }
      )
      .build()
  }

  private fun bindInvoiceDateViewModel(
    binding: WriteInvoiceInvoiceDateBinding,
    viewModel: InvoiceDateViewModel
  ) {
    binding.viewModel = viewModel
    binding.invoiceDateTextInput.setOnClickListener {
      showMaterialDatePicker(viewModel)
    }
  }

  private fun bindItemListViewModel(
    binding: WriteInvoiceItemListBinding,
    viewModel: ItemListViewModel
  ) {
    binding.viewModel = viewModel

    binding.itemListRecyclerView.apply {
      adapter = createItemListAdapter()
    }

    binding.addItemButton.setOnClickListener {
      val adapter = binding.itemListRecyclerView.adapter as AtlysRecyclerViewAdapter<*>
      viewModel.itemList.value?.let {
        viewModel.tempList.value = it + ItemListItemViewModel(null, null, null, null)
        adapter.setDataUnchecked(viewModel.tempList.value!!)
      } ?: run {
        val list = listOf(ItemListItemViewModel(null, null, null, null))
        viewModel.tempList.value = list
        adapter.setDataUnchecked(viewModel.tempList.value!!)
      }
    }
  }

  private fun createItemListAdapter(): AtlysRecyclerViewAdapter<ItemListItemViewModel> {
    return SingleViewTypeBuilder
      .newBuilder<ItemListItemViewModel>()
      .registerViewDataBinderWithSameModelType(
        inflateDataBinding = WriteInvoiceItemListItemBinding::inflate,
        setViewModel = WriteInvoiceItemListItemBinding::setViewModel
      )
      .build()
  }

  private fun showMaterialDatePicker(viewModel: InvoiceDateViewModel) {
    val datePicker =
      MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select date")
        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        .setTheme(R.style.ThemeOverlay_App_DatePicker)
        .build()
    datePicker.show(childFragmentManager, TAG_DATE_PICKER)
    datePicker.addOnPositiveButtonClickListener {
      viewModel.setDate(getDateUsingPattern(it, "dd MMM yyyy"))
    }
  }
}