/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.akshay.roomaccounting.data.model.Invoice
import com.akshay.roomaccounting.data.model.MinimalInvoice
import com.akshay.roomaccounting.data.model.UpdateInvoiceStatus
import com.akshay.roomaccounting.data.utility.Constants.table_name
import kotlinx.coroutines.flow.Flow

/**
 * This interface represents the different types of query to the [Invoice] entity.
 */
@Dao
interface InvoiceDao {

  /**
   * This query adds a new invoice to the entity.
   * In case of any conflict, the REPLACE strategy will always create a new row.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertInvoice(invoice: Invoice)

  /**
   * This query adds the list of Invoice.
   */
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertListOfInvoice(invoiceList: List<Invoice>)

  /**
   * Delete single invoice.
   */
  @Query("DELETE FROM $table_name WHERE id = :invoiceId")
  suspend fun deleteInvoice(invoiceId: String)

  /**
   * @return all the existing invoices in minimal format.
   */
  @Query("SELECT id, clientName, status, paymentDue, total FROM $table_name")
  fun getAllMinimalInvoices(): Flow<List<MinimalInvoice>>

  /**
   * @return single invoice
   */
  @Query("SELECT * FROM $table_name WHERE id = :invoiceId")
  fun getInvoice(invoiceId: String): Flow<Invoice?>

  /**
   * Updates the status of the given invoice to paid.
   */
  @Update(entity = Invoice::class)
  suspend fun markInvoiceAsPaid(invoiceStatus: UpdateInvoiceStatus)

  /**
   * Updates the whole invoice item.
   */
  @Update(entity = Invoice::class)
  suspend fun saveUpdatedInvoice(invoice: Invoice)

  /**
   * Updates the whole invoice item.
   */
  @Query("SELECT EXISTS (SELECT id FROM $table_name WHERE id =:id)")
  suspend fun checkIfIdExist(id: String): Boolean
}
