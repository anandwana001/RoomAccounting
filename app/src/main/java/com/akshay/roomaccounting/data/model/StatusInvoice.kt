/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.data.model

import com.squareup.moshi.Json

/**
 * This class represents the different types of status for an invoice.
 */
enum class StatusInvoice {
  @Json(name = "paid")
  PAID,

  @Json(name = "pending")
  PENDING,

  @Json(name = "draft")
  DRAFT
}
