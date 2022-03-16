/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

/**
 * ViewModel to hold the bill for a single invoice item.
 */
class SingleEntryInvoiceViewModel(
  val name: String?,
  val quantity: Int?,
  val price: Double?,
  val total: Double?
) : DetailInvoiceItemViewModel()
