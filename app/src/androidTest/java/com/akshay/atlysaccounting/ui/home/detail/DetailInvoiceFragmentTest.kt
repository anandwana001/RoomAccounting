/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.akshay.atlysaccounting.HiltTestActivity
import com.akshay.atlysaccounting.R
import com.akshay.atlysaccounting.data.MockData
import com.akshay.atlysaccounting.data.helper.DatabaseHelper
import com.akshay.atlysaccounting.di.module.DatabaseModule
import com.akshay.atlysaccounting.utility.RecyclerViewViewActionScrollBottom
import com.akshay.atlysaccounting.utility.ResourceHandler.getResourceString
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
 * Tests for [DetailInvoiceFragment].
 */
@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class DetailInvoiceFragmentTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var helper: DatabaseHelper

  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun test_detailInvoiceList_isDisplayed() {
    populateDatabase()

    launchDetailInvoiceFragment().use {
      onView(withId(R.id.detail_invoice_recycler_view))
        .check(matches(isDisplayed()))
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.status_title,
        text = getResourceString(R.string.status),
        position = 0
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.status_text_view,
        text = getResourceString(R.string.pending_status),
        position = 0
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.id_text_view,
        "#XM9141",
        position = 1
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.description_text_view,
        text = "Graphic Design",
        position = 1
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.senders_address_text_view,
        text = "19 Union Terrace\nLondon\nE1 3EZ\nUnited Kingdom",
        position = 1
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.payment_date_text_view,
        text = "21 Aug 2021",
        position = 1
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.client_name_text_view,
        text = "Alex Grim",
        position = 1
      )
      onView(withId(R.id.detail_invoice_recycler_view)).perform(RecyclerViewViewActionScrollBottom())
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.client_address_text_view,
        text = "84 Church Way\nBradford\nBD1 9PB\nUnited Kingdom",
        position = 1
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.payment_due_date_text_view,
        text = "20 Sep 2021",
        position = 1
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.client_email_text_view,
        text = "alexgrim@mail.com",
        position = 1
      )
      checkTextOnItemRecyclerView(
        itemId = R.id.item_name_title_text_view,
        text = "Banner Design",
        position = 0
      )
      checkTextOnItemRecyclerView(
        itemId = R.id.item_quantity_price_title_text_view,
        text = "1 * £ 156.00",
        position = 0
      )
      checkTextOnItemRecyclerView(
        itemId = R.id.item_total_title_text_view,
        text = "£156.00",
        position = 0
      )
      checkTextOnDetailInvoiceRecyclerView(
        itemId = R.id.total_bill_amount_text_view,
        text = "£556.00",
        position = 1
      )
    }
  }

  @Test
  fun test_buttonsVisible_isVisible() {
    launchDetailInvoiceFragment().use {
      onView(withId(R.id.edit_button)).check(matches(isDisplayed()))
      onView(withId(R.id.delete_button)).check(matches(isDisplayed()))
      onView(withId(R.id.mark_as_paid_button)).check(matches(isDisplayed()))
    }
  }

  @Test
  fun testDeleteButton_clickDelete_dialogIsDisplayed() {
    launchDetailInvoiceFragment().use {
      onView(withId(R.id.delete_button)).perform(click())
      onView(withText(R.string.confirm_delete_title))
        .inRoot(isDialog())
        .check(matches(isDisplayed()))
      onView(withId(R.id.confirmation_message_text_view))
        .inRoot(isDialog())
        .check(matches(withText(String.format(getResourceString(R.string.confirm_delete_message), "XM9141"))))
    }
  }

  @Test
  fun testDeleteButton_clickCancel_dialogIsDismissed() {
    launchDetailInvoiceFragment().use {
      onView(withId(R.id.delete_button)).perform(click())
      onView(withId(R.id.cancel_button))
        .inRoot(isDialog())
        .perform(click())
      onView(withId(R.id.delete_confirmation_dialog_container)).check(doesNotExist())
    }
  }

  /**
   * Launch the  [DetailInvoiceFragment] in isolation.
   */
  private fun launchDetailInvoiceFragment(): ActivityScenario<HiltTestActivity> {
    val args = Bundle()
    args.putString(DetailInvoiceFragment.DETAIL_INVOICE_ARGUMENT_INVOICE_ID, "XM9141")
    return launchFragmentInHiltContainer<DetailInvoiceFragment>(fragmentArgs = args) {
      this.view?.findViewById<RecyclerView>(R.id.detail_invoice_recycler_view)?.itemAnimator = null
    }
  }

  /**
   * Helper function to test if the text matches on detail_invoice_recycler_view item or not.
   */
  private fun checkTextOnDetailInvoiceRecyclerView(itemId: Int, text: String, position: Int) {
    checkTextOnRecyclerViewItem(
      R.id.detail_invoice_recycler_view,
      itemId,
      text,
      position
    )
  }

  /**
   * Helper function to test if the text matches on item_recycler_view item or not.
   */
  private fun checkTextOnItemRecyclerView(itemId: Int, text: String, position: Int) {
    checkTextOnRecyclerViewItem(
      R.id.item_recycler_view,
      itemId,
      text,
      position
    )
  }

  /**
   * Populate Single Invoice item.
   */
  private fun populateDatabase() = runBlocking {
    helper.saveInvoice(MockData.pendingInvoice)
  }
}
