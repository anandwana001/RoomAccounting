/**
 * Created by anandwana001 on
 * 06, March, 2022
 **/

package com.akshay.atlysaccounting.utility

import java.text.NumberFormat
import java.util.*

/**
 * Utility related to currency.
 */
object CurrencyUtility {

  /**
   * Generate the given amount into currency.
   *
   * @param locale is the geographical place which currency type is required.
   * @param amount is the amount to modify.
   */
  fun getCurrencyOf(locale: Locale, amount: Double): String {
    return NumberFormat.getCurrencyInstance(locale).format(amount)
  }
}
