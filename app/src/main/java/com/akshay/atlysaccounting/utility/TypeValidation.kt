/**
 * Created by anandwana001 on
 * 09, March, 2022
 **/

package com.akshay.atlysaccounting.utility

/**
 * Validation functions for different data types.
 */

fun String?.isValid(): Boolean {
  return this != null && this.isNotBlank()
}

fun Int?.isValid(): Boolean {
  return this != null
}

fun Double?.isValid(): Boolean {
  return this != null
}

fun <T> List<T>?.isValid(): Boolean {
  return this != null && this.isNotEmpty()
}
