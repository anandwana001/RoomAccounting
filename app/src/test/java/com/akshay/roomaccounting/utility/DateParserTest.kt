/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.utility

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [DateParser].
 */
@RunWith(RobolectricTestRunner::class)
class DateParserTest {

  @Test
  fun testFormatDateStringToPattern_isParsedCorrectly() {
    val unmodifiedDate = "2022-02-02"
    val expectedDate = "02 Feb 2022"

    val modifiedDate = DateParser.formatDateStringToPattern(unmodifiedDate, "dd MMM yyyy")
    assertThat(modifiedDate).isEqualTo(expectedDate)
  }

  @Test
  fun testFormatDateStringToPattern_unFormattedDate_isNotFormatted() {
    val unmodifiedDate = "2 - 2 - 2022"

    val modifiedDate = DateParser.formatDateStringToPattern(unmodifiedDate, "dd MMM yyyy")
    assertThat(modifiedDate).isEqualTo(unmodifiedDate)
  }

  @Test
  fun testGetDateAfterNumberOfDays_isParsedCorrectly() {
    val date = "3 Mar 2022"
    val expectedDate = "10 Mar 2022"

    val modifiedDate = DateParser.getDateAfterNumberOfDays(date, 7)
    assertThat(modifiedDate).isEqualTo(expectedDate)
  }

  @Test
  fun testGetDateUsingPattern_isParsedCorrectly() {
    val expectedDate = "20 Jan 1970"

    val modifiedDate = DateParser.getDateUsingPattern(1646796958, "dd MMM yyyy")
    assertThat(modifiedDate).isEqualTo(expectedDate)
  }
}
