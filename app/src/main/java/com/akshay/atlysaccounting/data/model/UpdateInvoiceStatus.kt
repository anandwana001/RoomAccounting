/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.data.model

/**
 * Helper data class to update status of [Invoice] entity.
 */
data class UpdateInvoiceStatus(
  val id: String,
  val status: StatusInvoice
)
