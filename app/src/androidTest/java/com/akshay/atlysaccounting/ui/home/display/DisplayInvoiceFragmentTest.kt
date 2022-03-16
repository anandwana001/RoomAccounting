/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.display

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.akshay.atlysaccounting.HiltTestActivity
import com.akshay.atlysaccounting.R
import com.akshay.atlysaccounting.data.MockData
import com.akshay.atlysaccounting.data.helper.DatabaseHelper
import com.akshay.atlysaccounting.di.module.DatabaseModule
import com.akshay.atlysaccounting.ui.home.detail.DetailInvoiceFragment
import com.akshay.atlysaccounting.utility.DrawableMatcher.Companion.withDrawable
import com.akshay.atlysaccounting.utility.ResourceHandler.getResourceString
import com.akshay.atlysaccounting.utility.atPositionOnView
import com.akshay.atlysaccounting.utility.checkTextOnRecyclerViewItem
import com.akshay.atlysaccounting.utility.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Tests for [DisplayInvoiceFragment].
 */
@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class DisplayInvoiceFragmentTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var helper: DatabaseHelper

  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun test_invoiceList_isDisplayed() {
    populateDatabase()
    launchDisplayInvoiceFragment().use {
      onView(withId(R.id.invoice_recycler_view)).check(matches(isDisplayed()))
      checkTextOnInvoiceRecyclerView(
        itemId = R.id.invoice_id_text_view,
        text = "#XM9141",
        position = 0
      )
      checkTextOnInvoiceRecyclerView(
        itemId = R.id.client_name_text_view,
        text = "Alex Grim",
        position = 0
      )
      checkTextOnInvoiceRecyclerView(
        itemId = R.id.payment_due_text_view,
        text = "Due 20 Sep 2021",
        position = 0
      )
      checkTextOnInvoiceRecyclerView(
        itemId = R.id.total_amount_text_view,
        text = "£556.00",
        0
      )
      checkTextOnInvoiceRecyclerView(
        itemId = R.id.status_text_view,
        text = getResourceString(R.string.pending_status),
        position = 0
      )
    }
  }

  @Test
  fun test_invoiceCount_one_isCorrectlyDisplayed() {
    populateDatabase()
    launchDisplayInvoiceFragment().use {
      onView(withId(R.id.invoice_count_text_view)).check(matches(withText("1 invoice")))
    }
  }

  @Test
  fun test_invoiceCount_two_isCorrectlyDisplayed() {
    populateMultipleInvoices()
    launchDisplayInvoiceFragment().use {
      onView(withId(R.id.invoice_count_text_view)).check(matches(withText("2 invoices")))
    }
  }

  @Test
  fun test_invoiceCount_zero_isCorrectlyDisplayed() {
    launchDisplayInvoiceFragment().use {
      onView(withId(R.id.invoice_count_text_view)).check(matches(withText(R.string.invoice_count_zero)))
    }
  }

  @Test
  fun test_emptyInvoiceTitleMessage_isCorrectlyDisplayed() {
    launchDisplayInvoiceFragment().use {
      onView(withId(R.id.empty_list_text_view)).check(matches(withText(R.string.empty_list_title)))
      onView(withId(R.id.empty_list_message_text_view)).check(matches(withText(R.string.empty_list_message)))
      onView(withId(R.id.empty_image_view)).check(matches(withDrawable(R.drawable.illustration_empty)))
    }
  }


  /**
   * Helper function to call another helper function for invoice_recycler_view.
   */
  private fun checkTextOnInvoiceRecyclerView(itemId: Int, text: String, position: Int) {
    checkTextOnRecyclerViewItem(
      recyclerViewId = R.id.invoice_recycler_view,
      itemId = itemId,
      text = text,
      position = position
    )
  }

  /**
   * Launch the  [DisplayInvoiceFragment] in isolation.
   */
  private fun launchDisplayInvoiceFragment(): ActivityScenario<HiltTestActivity> {
    return launchFragmentInHiltContainer<DisplayInvoiceFragment> {
      this.view?.findViewById<RecyclerView>(R.id.invoice_recycler_view)?.itemAnimator = null
    }
  }

  /**
   * Populate Single Invoice item.
   */
  private fun populateDatabase() = runBlocking {
    helper.saveInvoice(MockData.pendingInvoice)
  }

  /**
   * Populate Two Invoice items.
   */
  private fun populateMultipleInvoices() = runBlocking {
    helper.saveInvoice(MockData.pendingInvoice)
    helper.saveInvoice(MockData.paidInvoice)
  }
}