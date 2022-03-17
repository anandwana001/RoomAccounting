/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.utility

import com.akshay.roomaccounting.data.MockData
import com.akshay.roomaccounting.data.model.Address
import com.akshay.roomaccounting.data.model.Invoice
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Test for [MoshiAdapters].
 */
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class)
class MoshiAdaptersTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var moshiAdapters: MoshiAdapters

  @Before
  fun setUp() {
    hiltRule.inject()
  }

  @Test
  fun test_toJsonFromList_isCorrect() {
    val jsonData = moshiAdapters.toJsonFromList(MockData.invoiceList)
    assertThat(jsonData).isEqualTo(MockData.invoiceListJson)
  }

  @Test
  fun test_toListfromJson_isCorrect() {
    val objectList = moshiAdapters.toListfromJson<Invoice>(MockData.invoiceListJson)
    assertThat(objectList).isEqualTo(MockData.invoiceList)
  }

  @Test
  fun test_toJson_isCorrect() {
    val jsonData = moshiAdapters.toJson(MockData.invoiceAddress)
    assertThat(jsonData).isEqualTo(MockData.invoiceAddressJson)
  }

  @Test
  fun test_fromJson_isCorrect() {
    val objectData = moshiAdapters.fromJson<Address>(MockData.invoiceAddressJson)
    assertThat(objectData).isEqualTo(MockData.invoiceAddress)
  }
}
