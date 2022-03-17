/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.repository

import com.akshay.roomaccounting.data.helper.DatabaseHelper
import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.MinimalInvoice
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Repository to connect the UI and the Data package for local database interaction.
 * This repository can be replaced with a module which makes it scalable.
 */
class DatabaseRepository @Inject constructor(
  private val databaseHelper: DatabaseHelper
) {

  fun getAllMinimalInvoices(): Flow<List<MinimalInvoice>> = databaseHelper.getAllMinimalInvoices()

  fun getInvoiceById(id: String): Flow<Invoice?> = databaseHelper.getInvoiceById(id)

  suspend fun deleteInvoiceById(id: String) =
    databaseHelper.deleteInvoiceById(id)

  suspend fun markInvoiceAsPaidById(id: String) =
    databaseHelper.markInvoiceAsPaid(id)

  suspend fun saveInvoice(invoice: Invoice) =
    databaseHelper.saveInvoice(invoice)
}
