/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akshay.roomaccounting.data.utility.Constants.table_name

/**
 * This class represents an entity for the [AppDatabase].
 * All fields should be nullable except [id] and [status] to create a draft invoice.
 */
@Entity(tableName = table_name)
data class Invoice(
  @PrimaryKey val id: String,
  val createdAt: String?,
  val paymentDue: String?,
  val description: String?,
  val paymentTerms: Int?,
  val clientName: String?,
  val clientEmail: String?,
  val status: StatusInvoice,
  val senderAddress: Address?,
  val clientAddress: Address?,
  val items: List<SingleEntryInvoice>?,
  val total: Double?
)