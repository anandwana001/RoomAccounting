/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.akshay.atlysaccounting.databinding.InvoiceDeletePromptBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * DialogFragment for confirming Invoice deletion.
 */
@AndroidEntryPoint
class DeleteConfirmationDialogFragment : DialogFragment() {

  companion object {
    const val TAG_DELETE_CONFIRMATION_FRAGMENT = "DELETE_CONFIRMATION_FRAGMENT"
    private const val DELETE_CONFIRMATION_ARGUMENT_INVOICE_ID =
      "DELETE_CONFIRMATION_ARGUMENT_INVOICE_ID"

    fun newInstance(id: String): DeleteConfirmationDialogFragment {
      val args = Bundle()
      args.putString(DELETE_CONFIRMATION_ARGUMENT_INVOICE_ID, id)
      val deleteConfirmationDialogFragment = DeleteConfirmationDialogFragment()
      deleteConfirmationDialogFragment.arguments = args
      return deleteConfirmationDialogFragment
    }
  }

  private lateinit var parentContext: Context
  private lateinit var deleteConfirmationListener: DeleteConfirmationListener
  private lateinit var binding: InvoiceDeletePromptBinding
  private lateinit var invoiceId: String
  private val deleteConfirmationDialogViewModel: DeleteConfirmationDialogViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val args = checkNotNull(arguments) {
      "Expected arguments to be pass to DeleteConfirmationDialogFragment"
    }

    invoiceId = checkNotNull(args.getString(DELETE_CONFIRMATION_ARGUMENT_INVOICE_ID)) {
      "Invoice is not passed to detail screen"
    }

    binding = InvoiceDeletePromptBinding.inflate(layoutInflater, container, false)
    binding.apply {
      lifecycleOwner = viewLifecycleOwner
      viewModel = deleteConfirmationDialogViewModel
    }

    deleteConfirmationDialogViewModel.setInvoiceId(invoiceId)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpListeners()
  }

  private fun setUpListeners() {
    binding.deleteConfirmButton.setOnClickListener {
      deleteConfirmationListener.deleteInvoice(invoiceId)
    }

    binding.cancelButton.setOnClickListener {
      dialog?.dismiss()
    }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    try {
      parentContext = context
      deleteConfirmationListener = parentFragment as DeleteConfirmationListener
    } catch (e: ClassCastException) {
      throw ClassCastException(
        (context.toString() +
            " must implement ")
      )
    }
  }

  override fun onStart() {
    super.onStart()
    dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialog?.window?.setLayout(
      WindowManager.LayoutParams.MATCH_PARENT,
      WindowManager.LayoutParams.WRAP_CONTENT
    )
  }
}