/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.akshay.atlysaccounting.databinding.DetailInvoiceDetailItemBillItemBinding
import com.akshay.atlysaccounting.databinding.DetailInvoiceDetailItemBinding
import com.akshay.atlysaccounting.databinding.DetailInvoiceStatusItemBinding
import com.akshay.atlysaccounting.databinding.FragmentDetailInvoiceBinding
import com.akshay.atlysaccounting.ui.home.detail.DeleteConfirmationDialogFragment.Companion.TAG_DELETE_CONFIRMATION_FRAGMENT
import com.akshay.atlysaccounting.ui.home.display.DisplayInvoiceItemListener
import com.akshay.atlysaccounting.ui.recyclerview.AtlysRecyclerViewAdapter
import com.akshay.atlysaccounting.ui.recyclerview.MultiViewTypeBuilder
import com.akshay.atlysaccounting.ui.recyclerview.SingleViewTypeBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for showing the complete Invoice.
 */
@AndroidEntryPoint
class DetailInvoiceFragment : Fragment(), DeleteConfirmationListener {

  companion object {
    const val TAG_DETAIL_INVOICE_FRAGMENT = "DETAIL_INVOICE_FRAGMENT"
    const val DETAIL_INVOICE_ARGUMENT_INVOICE_ID = "DETAIL_INVOICE_ARGUMENT_INVOICE_ID"

    fun newInstance(id: String): DetailInvoiceFragment {
      val args = Bundle()
      args.putString(DETAIL_INVOICE_ARGUMENT_INVOICE_ID, id)
      val detailInvoiceFragment = DetailInvoiceFragment()
      detailInvoiceFragment.arguments = args
      return detailInvoiceFragment
    }
  }

  private lateinit var binding: FragmentDetailInvoiceBinding
  private val detailInvoiceViewModel: DetailInvoiceViewModel by viewModels()
  private lateinit var invoiceId: String
  private lateinit var listener: DisplayInvoiceItemListener

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val args =
      checkNotNull(arguments) { "Expected arguments to be pass to DetailInvoiceFragment" }
    invoiceId = checkNotNull(args.getString(DETAIL_INVOICE_ARGUMENT_INVOICE_ID)) {
      "Invoice is not passed to detail screen"
    }

    binding = FragmentDetailInvoiceBinding.inflate(inflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      viewModel = detailInvoiceViewModel
    }

    setUpListeners()
    setUpObservers()

    detailInvoiceViewModel.setInvoiceId(invoiceId)

    binding.detailInvoiceRecyclerView.apply {
      adapter = createAtlysRecyclerViewAdapter()
    }

    return binding.root
  }

  private enum class ViewType {
    VIEW_STATUS,
    VIEW_DETAIL
  }

  private fun createAtlysRecyclerViewAdapter(): AtlysRecyclerViewAdapter<DetailInvoiceItemViewModel> {
    return MultiViewTypeBuilder
      .newBuilder<DetailInvoiceItemViewModel, ViewType> { viewModel ->
        when (viewModel) {
          is StatusViewModel -> ViewType.VIEW_STATUS
          is DetailViewModel -> ViewType.VIEW_DETAIL
          else -> throw IllegalArgumentException("Encountered unexpected view model: $viewModel")
        }
      }
      .registerViewDataBinder(
        viewType = ViewType.VIEW_STATUS,
        inflateDataBinding = DetailInvoiceStatusItemBinding::inflate,
        setViewModel = DetailInvoiceStatusItemBinding::setViewModel,
        transformViewModel = { it as StatusViewModel }
      )
      .registerViewDataBinder(
        viewType = ViewType.VIEW_DETAIL,
        inflateDataBinding = DetailInvoiceDetailItemBinding::inflate,
        setViewModel = this::bindDetailViewModel,
        transformViewModel = { it as DetailViewModel }
      )
      .build()
  }

  private fun bindDetailViewModel(
    binding: DetailInvoiceDetailItemBinding,
    viewModel: DetailViewModel
  ) {
    binding.viewModel = viewModel

    binding.itemRecyclerView.apply {
      adapter = createAtlysItemRecyclerViewAdapter()
    }
  }

  private fun createAtlysItemRecyclerViewAdapter(): AtlysRecyclerViewAdapter<SingleEntryInvoiceViewModel> {
    return SingleViewTypeBuilder
      .newBuilder<SingleEntryInvoiceViewModel>()
      .registerViewDataBinderWithSameModelType(
        inflateDataBinding = DetailInvoiceDetailItemBillItemBinding::inflate,
        setViewModel = DetailInvoiceDetailItemBillItemBinding::setViewModel
      )
      .build()
  }

  private fun setUpListeners() {
    binding.goBackArrowImageView.setOnClickListener {
      activity?.onBackPressed()
    }

    binding.goBackTextView.setOnClickListener {
      activity?.onBackPressed()
    }

    binding.deleteButton.setOnClickListener {
      val dialogFragment = DeleteConfirmationDialogFragment
        .newInstance(id = invoiceId)
      dialogFragment.showNow(childFragmentManager, TAG_DELETE_CONFIRMATION_FRAGMENT)
    }

    binding.editButton.setOnClickListener {
      listener.openWriteInvoice(id = invoiceId)
    }
  }

  private fun setUpObservers() {
    detailInvoiceViewModel.isInvoiceDeleted.observe(viewLifecycleOwner) {
      if (it) {
        activity?.onBackPressed()
      }
    }
  }

  override fun deleteInvoice(id: String) {
    detailInvoiceViewModel.deleteInvoice(id)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is DisplayInvoiceItemListener) {
      listener = context
    } else {
      throw ClassCastException("$context must implement DisplayInvoiceItemListener")
    }
  }
}