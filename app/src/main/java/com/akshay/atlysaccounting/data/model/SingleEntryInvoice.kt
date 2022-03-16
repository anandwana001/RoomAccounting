/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.data.model

/**
 * This class represents the an item of an invoice.
 * All fields should be nullable to create a draft invoice.
 */
data class SingleEntryInvoice(
  var name: String?,
  var quantity: Int?,
  var price: Double?,
  var total: Double?
)
