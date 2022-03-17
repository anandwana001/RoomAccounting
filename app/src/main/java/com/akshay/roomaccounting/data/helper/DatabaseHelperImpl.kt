/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.data.helper

import com.akshay.roomaccounting.data.dao.InvoiceDao
import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.MinimalInvoice
import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.data.model.UpdateInvoiceStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of database helper functions.
 * This Implementation can be simply replaced with the fake one, while testing.
 */
class DatabaseHelperImpl @Inject constructor(
  private val invoiceDao: InvoiceDao
) : DatabaseHelper {

  override fun getAllMinimalInvoices(): Flow<List<MinimalInvoice>> =
    invoiceDao.getAllMinimalInvoices()

  override fun getInvoiceById(id: String): Flow<Invoice?> =
    invoiceDao.getInvoice(id)

  override suspend fun deleteInvoiceById(id: String) =
    invoiceDao.deleteInvoice(id)

  override suspend fun markInvoiceAsPaid(id: String) {
    val updatedStatusInvoice = UpdateInvoiceStatus(id = id, status = StatusInvoice.PAID)
    invoiceDao.markInvoiceAsPaid(updatedStatusInvoice)
  }

  override suspend fun saveInvoice(invoice: Invoice) =
    invoiceDao.insertInvoice(invoice)
}
