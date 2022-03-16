/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.data.model

/**
 * Brief representation of an [Invoice].
 */
data class MinimalInvoice(
  val id: String,
  val clientName: String?,
  val status: StatusInvoice,
  val paymentDue: String?,
  val total: Double?
)
