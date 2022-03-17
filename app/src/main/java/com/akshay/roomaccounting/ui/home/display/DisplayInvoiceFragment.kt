/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.display

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import com.akshay.roomaccounting.databinding.FragmentDisplayInvoiceBinding
import com.akshay.roomaccounting.databinding.MinimalInvoiceItemBinding
import com.akshay.roomaccounting.ui.recyclerview.RoomAccRecyclerViewAdapter
import com.akshay.roomaccounting.ui.recyclerview.SingleViewTypeBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Fragment of the Room Accounting app.
 */
@AndroidEntryPoint
class DisplayInvoiceFragment : Fragment() {

  /**
   * Another approach to perform Fragment Transaction is to use the [FragmentResultListener].
   */
  companion object {
    const val TAG_DISPLAY_INVOICE_FRAGMENT = "DISPLAY_INVOICE_FRAGMENT"

    fun newInstance(): DisplayInvoiceFragment {
      return DisplayInvoiceFragment()
    }
  }

  private lateinit var binding: FragmentDisplayInvoiceBinding
  private val invoiceViewModel: DisplayInvoiceViewModel by viewModels()
  private lateinit var listener: DisplayInvoiceItemListener

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentDisplayInvoiceBinding.inflate(inflater, container, false)
    binding.let {
      it.lifecycleOwner = viewLifecycleOwner
      it.viewModel = invoiceViewModel
    }

    setUpListeners()

    binding.invoiceRecyclerView.apply {
      adapter = createRoomAccRecyclerViewAdapter()
    }

    return binding.root
  }

  private fun setUpListeners() {
    binding.createNewInvoiceButton.setOnClickListener {
      listener.openWriteInvoice()
    }
  }

  private fun createRoomAccRecyclerViewAdapter(): RoomAccRecyclerViewAdapter<DisplayInvoiceItemViewModel> {
    return SingleViewTypeBuilder
      .newBuilder<DisplayInvoiceItemViewModel>()
      .setLifecycleOwner(viewLifecycleOwner)
      .registerViewDataBinderWithSameModelType(
        inflateDataBinding = MinimalInvoiceItemBinding::inflate,
        setViewModel = this::bindMinimalInvoiceItem
      )
      .build()
  }

  private fun bindMinimalInvoiceItem(
    binding: MinimalInvoiceItemBinding,
    viewModel: DisplayInvoiceItemViewModel
  ) {
    binding.viewModel = viewModel
    binding.root.setOnClickListener {
      listener.openDetailInvoiceFragment(viewModel.id)
    }
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