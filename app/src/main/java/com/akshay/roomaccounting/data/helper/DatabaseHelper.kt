/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.data.helper

import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.MinimalInvoice
import kotlinx.coroutines.flow.Flow

/**
 * Helper functions for [InvoiceDao] interaction.
 */
interface DatabaseHelper {

  /**
   * @return all the existing invoices in minimal invoice format.
   */
  fun getAllMinimalInvoices(): Flow<List<MinimalInvoice>>

  /**
   * Access an invoice using.
   */
  fun getInvoiceById(id: String): Flow<Invoice?>

  /**
   * Delete an invoice.
   */
  suspend fun deleteInvoiceById(id: String)

  /**
   * Update the invoice status to paid.
   */
  suspend fun markInvoiceAsPaid(id: String)

  /**
   * Save the complete invoice.
   */
  suspend fun saveInvoice(invoice: Invoice)
}
