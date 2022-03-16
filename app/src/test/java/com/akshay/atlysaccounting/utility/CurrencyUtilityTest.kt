/**
 * Created by anandwana001 on
 * 09, March, 2022
 **/

package com.akshay.atlysaccounting.utility

import com.akshay.atlysaccounting.utility.CurrencyUtility.getCurrencyOf
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Locale

/**
 * Tests for [CurrencyUtility].
 */
@RunWith(RobolectricTestRunner::class)
class CurrencyUtilityTest {

  @Test
  fun testGetCurrencyOf_UK_isParsedCorrectly() {
    val expectedCurrency = "£4,530.50"

    val modifiedCurrency = getCurrencyOf(Locale.UK, 4530.50)
    assertThat(modifiedCurrency).isEqualTo(expectedCurrency)
  }

  @Test
  fun testGetCurrencyOf_US_isParsedCorrectly() {
    val expectedCurrency = "$4,530.50"

    val modifiedCurrency = getCurrencyOf(Locale.US, 4530.50)
    assertThat(modifiedCurrency).isEqualTo(expectedCurrency)
  }
}
