/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.display

/**
 * Listener for list items in [DisplayInvoiceFragment].
 */
interface DisplayInvoiceItemListener {

  /**
   * Open [DetailInvoiceFragment].
   */
  fun openDetailInvoiceFragment(invoiceId : String)

  /**
   * Open [WriteInvoiceFragment].
   */
  fun openWriteInvoice(id: String? = null)
}
