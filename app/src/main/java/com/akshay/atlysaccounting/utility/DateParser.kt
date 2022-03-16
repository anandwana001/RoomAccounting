/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.utility

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility for parsing or formatting the Date.
 */
object DateParser {

  /**
   * Modify the [date] pattern to another pattern.
   *
   * @param date string in the format `yyyy-MM-dd` | `2021-02-25`
   * @param pattern to which [date] converts to.
   *
   * @return converted date format.
   */
  fun formatDateStringToPattern(date: String, pattern: String): String {
    val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val format2 = SimpleDateFormat(pattern, Locale.getDefault())

    val parsedDate: Date?
    parsedDate = try {
      format1.parse(date)
    } catch (exception: Exception) {
      null
    }

    parsedDate?.let {
      return format2.format(parsedDate)
    } ?: run {
      return date
    }
  }

  /**
   * Returns the date after given number of days.
   *
   * @param startingDate is the date from which the number of days starting.
   * @param numberOfDays is the count of days.
   */
  fun getDateAfterNumberOfDays(startingDate: String, numberOfDays: Int): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val calendar = Calendar.getInstance()
    calendar.time = dateFormat.parse(startingDate)
    calendar.add(Calendar.DAY_OF_YEAR, numberOfDays)
    return dateFormat.format(Date(calendar.timeInMillis))
  }

  /**
   * Returns the date in the string format, parsed into the given pattern.
   *
   * @param date single date in the Long format.
   * @param pattern to which we need to modify the given date.
   */
  fun getDateUsingPattern(date: Long, pattern: String): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
  }
}