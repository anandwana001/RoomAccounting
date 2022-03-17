/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.display

import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.ui.base.BaseViewModel

/**
 * ViewModel for showing an item in the list in [DisplayInvoiceFragment].
 */
class DisplayInvoiceItemViewModel(
  val id: String,
  val name: String?,
  val dueDate: String?,
  val total: Double?,
  val status: StatusInvoice
) : BaseViewModel()
