/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.detail

import com.akshay.roomaccounting.data.model.StatusInvoice

/**
 * ViewModel for status item in [DetailInvoiceItemViewModel].
 */
class StatusViewModel(
  val status: StatusInvoice
) : DetailInvoiceItemViewModel()
