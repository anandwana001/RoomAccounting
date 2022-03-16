/**
 * Created by anandwana001 on
 * 09, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.akshay.atlysaccounting.HiltTestActivity
import com.akshay.atlysaccounting.R
import com.akshay.atlysaccounting.data.MockData
import com.akshay.atlysaccounting.data.helper.DatabaseHelper
import com.akshay.atlysaccounting.di.module.DatabaseModule
import com.akshay.atlysaccounting.utility.ResourceHandler.getResourceString
import com.akshay.atlysaccounting.utility.atPositionOnView
import com.akshay.atlysaccounting.utility.checkTextOnRecyclerViewItem
import com.akshay.atlysaccounting.utility.hasItemCount
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
 * Tests for [WriteInvoiceFragment].
 */
@HiltAndroidTest
@UninstallModules(DatabaseModule::class)
class WriteInvoiceFragmentTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var helper: DatabaseHelper

  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun testButtonVisibility_discard_saveDraft_saveSend_isVisible() {
    launchWriteInvoiceFragment().use {
      onView(withId(R.id.discard_button)).check(matches(isDisplayed()))
      onView(withId(R.id.save_button)).check(matches(isDisplayed()))
      onView(withId(R.id.draft_button)).check(matches(isDisplayed()))
    }
  }

  @Test
  fun testButtonVisibility_cancel_saveChanges_isVisible() {
    populateDatabase()
    launchWriteInvoiceFragmentWithArg("XM9141").use {
      onView(withId(R.id.cancel_button)).check(matches(isDisplayed()))
      onView(withId(R.id.save_changes_button)).check(matches(isDisplayed()))
    }
  }

  @Test
  fun testEditTitle_withId_isDisplayedCorrectly() {
    populateDatabase()
    launchWriteInvoiceFragmentWithArg("XM9141").use {
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.title_text_view,
        string = "Edit #XM9141",
        position = 0
      )
    }
  }

  @Test
  fun testEditTitle_withoutId_isDisplayedCorrectly() {
    launchWriteInvoiceFragment().use {
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.title_text_view,
        stringId = R.string.create_new_invoice,
        position = 0
      )
    }
  }

  @Test
  fun testFieldsVisibility_createForm_allFieldTitles_areVisible() {
    launchWriteInvoiceFragment().use {

      checkItemVisibilityOnRecyclerView(R.id.title_text_view, 0)
      checkTextOnInvoiceFormRecyclerViewItem(R.id.title_text_view, R.string.create_new_invoice, 0)

      checkItemVisibilityOnRecyclerView(R.id.bill_title_text_view, 1)
      checkTextOnInvoiceFormRecyclerViewItem(R.id.bill_title_text_view, R.string.bill_from_title, 1)

      checkItemVisibilityOnRecyclerView(R.id.sender_street_address_container, 2)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.street_address_title_text_view,
        R.string.street_address_title,
        2
      )

      checkItemVisibilityOnRecyclerView(R.id.sender_city_address_container, 3)
      checkTextOnInvoiceFormRecyclerViewItem(R.id.city_title_text_view, R.string.city_title, 3)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.post_code_title_text_view,
        R.string.post_code_title,
        3
      )

      checkItemVisibilityOnRecyclerView(R.id.sender_country_address_container, 4)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.country_title_text_view,
        R.string.country_title,
        4
      )

      scrollToPosition(6)
      checkItemVisibilityOnRecyclerView(R.id.bill_title_to_text_view, 5)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.bill_title_to_text_view,
        R.string.bill_to_title,
        5
      )

      checkItemVisibilityOnRecyclerView(R.id.client_name_container, 6)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.client_name_title_text_view,
        R.string.clients_name_title,
        6
      )

      checkItemVisibilityOnRecyclerView(R.id.client_email_container, 7)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.client_email_title_text_view,
        R.string.clients_email_title,
        7
      )

      scrollToPosition(8)

      checkItemVisibilityOnRecyclerView(R.id.client_street_address_container, 8)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.street_address_title_text_view,
        R.string.street_address_title,
        8
      )

      checkItemVisibilityOnRecyclerView(R.id.client_city_address_container, 9)
      checkTextOnInvoiceFormRecyclerViewItem(R.id.city_title_text_view, R.string.city_title, 9)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.post_code_title_text_view,
        R.string.post_code_title,
        9
      )

      scrollToPosition(10)

      checkItemVisibilityOnRecyclerView(R.id.client_country_address_cotainer, 10)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.country_title_text_view,
        R.string.country_title,
        10
      )

      checkItemVisibilityOnRecyclerView(R.id.description_container, 11)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.description_title_text_view,
        R.string.project_description_title,
        11
      )

      scrollToPosition(12)

      checkItemVisibilityOnRecyclerView(R.id.payment_date_container, 12)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.invoice_date_title_text_view,
        R.string.invoice_date_title,
        12
      )

      checkItemVisibilityOnRecyclerView(R.id.payment_terms_container, 13)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.payment_terms_title_text_view,
        R.string.payment_terms_title,
        13
      )

      scrollToPosition(14)

      checkItemVisibilityOnRecyclerView(R.id.item_list_container, 14)
      checkTextOnInvoiceFormRecyclerViewItem(
        R.id.item_list_title_text_view,
        getResourceString(R.string.item_list),
        14
      )
      checkItemVisibilityOnRecyclerView(R.id.add_item_button, 14)
      clickOnRecyclerViewItem(R.id.add_item_button, 14)
      onView(withId(R.id.item_list_recycler_view)).check(hasItemCount(1))
      checkItemVisibilityOnRecyclerView(R.id.item_list_recycler_view, 14)
    }
  }

  @Test
  fun testEditScreen_invoiceLoadedCorrectly() {
    populateDatabase()
    launchWriteInvoiceFragmentWithArg("XM9141").use {
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.street_address_text_input,
        string = "19 Union Terrace",
        position = 2
      )
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.city_text_input,
        string = "London",
        position = 3
      )
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.post_code_text_input,
        string = "E1 3EZ",
        position = 3
      )
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.country_text_input,
        string = "United Kingdom",
        position = 4
      )
      scrollToPosition(6)
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.client_name_text_input,
        string = "Alex Grim",
        position = 6
      )
      scrollToPosition(7)
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.client_email_text_input,
        string = "alexgrim@mail.com",
        position = 7
      )
      scrollToPosition(8)
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.client_street_address_text_input,
        string = "84 Church Way",
        position = 8
      )
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.client_city_text_input,
        string = "Bradford",
        position = 9
      )
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.client_post_code_text_input,
        string = "BD1 9PB",
        position = 9
      )
      scrollToPosition(10)
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.client_country_text_input,
        string = "United Kingdom",
        position = 10
      )
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.description_text_input,
        string = "Graphic Design",
        position = 11
      )
      scrollToPosition(12)
      checkTextOnInvoiceFormRecyclerViewItem(
        itemId = R.id.invoice_date_text_input,
        string = "21 Aug 2021",
        position = 12
      )
      scrollToPosition(13)
      onView(withId(R.id.item_list_recycler_view)).check(hasItemCount(2))
      checkTextOnItemListRecyclerViewItem(
        itemId = R.id.item_name_text_input_edit_text,
        string = "Banner Design",
        position = 0
      )
      checkTextOnItemListRecyclerViewItem(
        itemId = R.id.qty_text_input,
        string = "1",
        position = 0
      )
      checkTextOnItemListRecyclerViewItem(
        itemId = R.id.price_text_input,
        string = "156.0",
        position = 0
      )
      checkTextOnItemListRecyclerViewItem(
        itemId = R.id.total_text_view,
        string = "156.0",
        position = 0
      )
    }
  }


  private fun launchWriteInvoiceFragment(): ActivityScenario<HiltTestActivity> {
    return launchFragmentInHiltContainer<WriteInvoiceFragment>()
  }

  private fun launchWriteInvoiceFragmentWithArg(id: String): ActivityScenario<HiltTestActivity> {
    val args = Bundle()
    args.putString(WriteInvoiceFragment.WRITE_INVOICE_ARGUMENT_INVOICE_ID, id)
    return launchFragmentInHiltContainer<WriteInvoiceFragment>(args)
  }

  /**
   * Helper function to test if a view is visible on recyclerview or not.
   */
  private fun checkItemVisibilityOnRecyclerView(itemId: Int, position: Int) {
    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_form_recyclerview,
        position = position,
        targetViewId = itemId
      )
    ).check(matches(isDisplayed()))
  }

  /**
   * Helper function to check text on invoice_form_recyclerview using hardcode string.
   */
  private fun checkTextOnInvoiceFormRecyclerViewItem(itemId: Int, string: String, position: Int) {
    checkTextOnRecyclerViewItem(
      R.id.invoice_form_recyclerview,
      itemId,
      string,
      position
    )
  }

  /**
   * Helper function to check text on invoice_form_recyclerview using string resources.
   */
  private fun checkTextOnInvoiceFormRecyclerViewItem(
    itemId: Int,
    @StringRes stringId: Int,
    position: Int
  ) {
    checkTextOnRecyclerViewItem(
      R.id.invoice_form_recyclerview,
      itemId,
      getResourceString(stringId),
      position
    )
  }

  /**
   * Helper function to check text on item_list_recycler_view using hardcode string.
   */
  private fun checkTextOnItemListRecyclerViewItem(itemId: Int, string: String, position: Int) {
    checkTextOnRecyclerViewItem(
      R.id.item_list_recycler_view,
      itemId,
      string,
      position
    )
  }

  /**
   * Helper function to click on recyclerview item.
   */
  private fun clickOnRecyclerViewItem(viewId: Int, position: Int) {
    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_form_recyclerview,
        position = position,
        targetViewId = viewId
      )
    ).perform(click())
  }

  /**
   * Helper function to scroll the invoice_form_recyclerview at given position.
   */
  private fun scrollToPosition(position: Int) {
    onView(withId(R.id.invoice_form_recyclerview)).perform(
      RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
        position
      )
    )
  }

  /**
   * Populate Single Invoice item.
   */
  private fun populateDatabase() = runBlocking {
    helper.saveInvoice(MockData.pendingInvoice)
  }
}
