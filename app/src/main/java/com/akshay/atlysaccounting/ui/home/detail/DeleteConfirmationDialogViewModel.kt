/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

import androidx.databinding.ObservableField
import com.akshay.atlysaccounting.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for managing the delete confirmation dialog UI.
 */
@HiltViewModel
class DeleteConfirmationDialogViewModel @Inject constructor() : BaseViewModel() {

  val invoiceId: ObservableField<String> = ObservableField("")

  fun setInvoiceId(invoiceId: String) {
    this.invoiceId.set(invoiceId)
  }
}
