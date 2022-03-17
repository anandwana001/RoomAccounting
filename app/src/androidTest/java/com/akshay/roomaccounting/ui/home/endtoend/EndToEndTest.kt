/**
 * Created by anandwana001 on
 * 09, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.endtoend

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSpinnerText
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.akshay.roomaccounting.R
import com.akshay.roomaccounting.data.MockData
import com.akshay.roomaccounting.data.helper.DatabaseHelper
import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.ui.home.HomeActivity
import com.akshay.roomaccounting.utility.RecyclerViewViewActionScrollBottom
import com.akshay.roomaccounting.utility.atPositionOnView
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EndToEndTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var helper: DatabaseHelper

  @Before
  fun setUp() {
    hiltRule.inject()
    populateDatabase()
  }

  @Test
  fun test_clickOnInvoiceItem_detailScreenIsOpen_clickEdit_editScreenOpen() {
    launchActivity()

    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_recycler_view,
        position = 0,
        targetViewId = R.id.minimal_invoice_container_card
      )
    ).perform(click())
    onView(withId(R.id.detail_invoice_container)).check(matches(isDisplayed()))
    onView(withId(R.id.edit_button)).perform(click())
    onView(withId(R.id.invoice_form_recyclerview)).check(matches(isDisplayed()))
  }

  @Test
  fun test_clickOnInvoiceItem_detailScreenIsOpen_clickGoBackImage_backToDisplayScreen() {
    launchActivity()

    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_recycler_view,
        position = 0,
        targetViewId = R.id.minimal_invoice_container_card
      )
    ).perform(click())
    onView(withId(R.id.detail_invoice_container)).check(matches(isDisplayed()))
    onView(withId(R.id.go_back_arrow_image_view)).perform(click())
    onView(withId(R.id.invoice_recycler_view)).check(matches(isDisplayed()))
  }

  @Test
  fun test_clickOnInvoiceItem_detailScreenIsOpen_clickGoBackText_backToDisplayScreen() {
    launchActivity()

    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_recycler_view,
        position = 0,
        targetViewId = R.id.minimal_invoice_container_card
      )
    ).perform(click())
    onView(withId(R.id.detail_invoice_container)).check(matches(isDisplayed()))
    onView(withId(R.id.go_back_text_view)).perform(click())
    onView(withId(R.id.invoice_recycler_view)).check(matches(isDisplayed()))
  }

  @Test
  fun test_clickNew_createScreenIsOpen_clickGoBackImage_backToDisplayScreen() {
    launchActivity()

    onView(withId(R.id.create_new_invoice_button)).perform(click())
    onView(withId(R.id.invoice_form_recyclerview)).check(matches(isDisplayed()))
    onView(withId(R.id.go_back_arrow_image_view)).perform(click())
    onView(withId(R.id.invoice_recycler_view)).check(matches(isDisplayed()))
  }

  @Test
  fun test_clickNew_createScreenIsOpen_clickGoBackText_backToDisplayScreen() {
    launchActivity()

    onView(withId(R.id.create_new_invoice_button)).perform(click())
    onView(withId(R.id.invoice_form_recyclerview)).check(matches(isDisplayed()))
    onView(withId(R.id.go_back_text_view)).perform(click())
    onView(withId(R.id.invoice_recycler_view)).check(matches(isDisplayed()))
  }

  @Test
  fun testDeleteButton_click_invoiceDeletedSuccessfully() {
    launchActivity()

    onView(withId(R.id.invoice_recycler_view)).perform()
    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_recycler_view,
        position = 0,
        targetViewId = R.id.minimal_invoice_container_card
      )
    ).perform(click())
    onView(withId(R.id.detail_invoice_container)).check(matches(isDisplayed()))

    runBlocking {
      val invoice = helper.getInvoiceById("XM9141").first()
      assertThat(invoice).isEqualTo(MockData.pendingInvoice)
    }

    onView(withId(R.id.delete_button)).perform(click())
    onView(withId(R.id.delete_confirm_button))
      .inRoot(RootMatchers.isDialog())
      .perform(click())

    runBlocking {
      val invoice = helper.getInvoiceById("XM9141").first()
      assertThat(invoice).isNull()
    }
  }

  @Test
  fun testMarkAsPaid_click_invoiceStatusUpdatedSuccessfully() {
    launchActivity()

    onView(withId(R.id.invoice_recycler_view)).perform()
    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_recycler_view,
        position = 0,
        targetViewId = R.id.minimal_invoice_container_card
      )
    ).perform(click())
    onView(withId(R.id.detail_invoice_container)).check(matches(isDisplayed()))

    runBlocking {
      val invoice = helper.getInvoiceById("XM9141").first()
      assertThat(invoice!!.status).isEqualTo(StatusInvoice.PENDING)
    }

    onView(withId(R.id.mark_as_paid_button)).perform(click())

    runBlocking {
      val invoice = helper.getInvoiceById("XM9141").first()
      assertThat(invoice!!.status).isEqualTo(StatusInvoice.PAID)
    }
  }

  @Test
  fun test_clickNew_createScreenIsOpen_clickDiscard_backToDisplayScreen() {
    launchActivity()

    onView(withId(R.id.create_new_invoice_button)).perform(click())
    onView(withId(R.id.invoice_form_recyclerview)).check(matches(isDisplayed()))
    onView(withId(R.id.discard_button)).perform(click())
    onView(withId(R.id.invoice_recycler_view)).check(matches(isDisplayed()))
  }

  @Test
  fun test_clickNew_createScreenIsOpen_clickSaveDraft_emptyInvoiceSavedAsDraft() {
    launchActivity()

    runBlocking {
      val minimalInvoiceList = helper.getAllMinimalInvoices().first()
      assertThat(minimalInvoiceList.size).isEqualTo(1)
    }

    onView(withId(R.id.create_new_invoice_button)).perform(click())
    onView(withId(R.id.invoice_form_recyclerview)).check(matches(isDisplayed()))
    onView(withId(R.id.draft_button)).perform(click())

    runBlocking {
      val minimalInvoiceList = helper.getAllMinimalInvoices().first()
      assertThat(minimalInvoiceList.size).isEqualTo(2)
      assertThat(minimalInvoiceList[1].status).isEqualTo(StatusInvoice.DRAFT)
    }
  }

  @Test
  fun test_editName_nameEditedSuccessfully() {
    launchActivity()

    goToEditScreen()

    runBlocking {
      val minimalInvoiceList = helper.getAllMinimalInvoices().first()
      assertThat(minimalInvoiceList[0].clientName).isEqualTo("Alex Grim")
    }

    scrollToPosition(9)
    onView(withId(R.id.client_name_text_input)).perform(clearText(), typeText("Akshay"))
    onView(withId(R.id.save_changes_button)).perform(click())

    runBlocking {
      val minimalInvoiceList = helper.getAllMinimalInvoices().first()
      assertThat(minimalInvoiceList[0].clientName).isEqualTo("Akshay")
    }
  }

  @Test
  fun test_editScreen_cancel_backToDetailScreen() {
    launchActivity()
    goToEditScreen()
    onView(withId(R.id.cancel_button)).perform(click())
    onView(withId(R.id.detail_invoice_container)).check(matches(isDisplayed()))
  }

  @Test
  fun test_createNewInvoice_invoiceCreatedSuccessfully() {
    launchActivity()

    runBlocking {
      val minimalInvoiceList = helper.getAllMinimalInvoices().first()
      assertThat(minimalInvoiceList.size).isEqualTo(1)
    }

    goToCreateNewInvoiceScreen()

    onView(withId(R.id.street_address_text_input)).perform(typeText("sender street"))

    scrollToPosition(4)
    onView(withId(R.id.country_text_input)).perform(typeText("country"))

    scrollToPosition(7)
    onView(withId(R.id.client_name_text_input)).perform(typeText("name"))
    onView(withId(R.id.client_email_text_input)).perform(typeText("email@email.com"))

    scrollToPosition(9)
    onView(withId(R.id.client_street_address_text_input)).perform(typeText("client street"))
    onView(withId(R.id.client_city_text_input)).perform(typeText("client city"))

    scrollToPosition(11)
    onView(withId(R.id.client_country_text_input)).perform(typeText("client country"))
    onView(withId(R.id.description_text_input)).perform(typeText("description"))

    scrollToPosition(0)
    onView(withId(R.id.post_code_text_input)).perform(typeText("sender post code"))
    onView(withId(R.id.city_text_input)).perform(typeText("sender city"))

    scrollToPosition(10)
    onView(withId(R.id.client_post_code_text_input)).perform(typeText("client post code"))

    scrollToPosition(11)
    closeSoftKeyboard()
    onView(withId(R.id.invoice_date_text_input)).perform(click())
    onView(withText("OK"))
      .inRoot(RootMatchers.isDialog())
      .perform(click())

    scrollToPosition(12)
    onView(withId(R.id.payment_terms_input)).perform(click())

    onData(allOf(`is`(instanceOf(String::class.java)), `is`("Net 7 day"))).perform(click())
    onView(withId(R.id.payment_terms_input)).check(matches(withSpinnerText(containsString("Net 7 day"))))

    onView(withId(R.id.invoice_form_recyclerview)).perform(RecyclerViewViewActionScrollBottom())
    onView(withId(R.id.add_item_button)).perform(click())
    onView(withId(R.id.item_name_text_input_edit_text)).perform(typeText("item name"))

    onView(withId(R.id.invoice_form_recyclerview)).perform(RecyclerViewViewActionScrollBottom())
    onView(withId(R.id.price_text_input)).perform(typeText("55.80"))
    onView(withId(R.id.qty_text_input)).perform(typeText("2"))
    onView(withId(R.id.total_text_view)).check(matches(withText("111.6")))

    onView(withId(R.id.save_button)).perform(click())

    runBlocking {
      val minimalInvoiceList = helper.getAllMinimalInvoices().first()
      assertThat(minimalInvoiceList.size).isEqualTo(2)
    }
  }

  private fun launchActivity(): ActivityScenario<HomeActivity> {
    val activityScenario = launch(HomeActivity::class.java)
    activityScenario.onActivity { activity ->
      (activity.findViewById(R.id.invoice_recycler_view) as RecyclerView).itemAnimator = null
    }
    return activityScenario
  }

  private fun goToEditScreen() {
    onView(
      atPositionOnView(
        recyclerViewId = R.id.invoice_recycler_view,
        position = 0,
        targetViewId = R.id.minimal_invoice_container_card
      )
    ).perform(click())
    onView(withId(R.id.edit_button)).perform(click())
  }

  private fun goToCreateNewInvoiceScreen() {
    onView(withId(R.id.create_new_invoice_button)).perform(click())
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
