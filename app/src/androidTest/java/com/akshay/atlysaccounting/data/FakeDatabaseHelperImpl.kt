/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.data

import com.akshay.atlysaccounting.data.helper.DatabaseHelper
import com.akshay.atlysaccounting.data.model.Invoice
import com.akshay.atlysaccounting.data.model.MinimalInvoice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Fake DatabaseHelperImpl for Instrumentation tests.
 */
class FakeDatabaseHelperImpl @Inject constructor() : DatabaseHelper {

  var fakeInvoiceData: LinkedHashMap<String, Invoice> = LinkedHashMap()

  override fun getAllMinimalInvoices(): Flow<List<MinimalInvoice>> {
    val list = mutableListOf<MinimalInvoice>()
    fakeInvoiceData.forEach { key, value ->
      list.add(
        MinimalInvoice(
          key,
          value.clientName,
          value.status,
          value.paymentDue,
          value.total
        )
      )
    }
    return flow {
      emit(list)
      list.clear()
    }
  }

  override fun getInvoiceById(id: String): Flow<Invoice?> {
    return flow {
      emit(fakeInvoiceData[id])
    }
  }

  override suspend fun deleteInvoiceById(id: String) {
    fakeInvoiceData.remove(id)
  }

  override suspend fun markInvoiceAsPaid(id: String) {
    fakeInvoiceData[id] = MockData.pendingToPaidInvoice
  }

  override suspend fun saveInvoice(invoice: Invoice) {
    fakeInvoiceData[invoice.id] = invoice
  }
}
