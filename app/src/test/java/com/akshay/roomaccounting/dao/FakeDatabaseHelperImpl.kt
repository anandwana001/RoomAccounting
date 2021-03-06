/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.dao

import com.akshay.roomaccounting.data.dao.InvoiceDao
import com.akshay.roomaccounting.data.helper.DatabaseHelper
import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.MinimalInvoice
import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.data.model.UpdateInvoiceStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Fake DatabaseHelperImpl for unit tests.
 */
class FakeDatabaseHelperImpl @Inject constructor(
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
