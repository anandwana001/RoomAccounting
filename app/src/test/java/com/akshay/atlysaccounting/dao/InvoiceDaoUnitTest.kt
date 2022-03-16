/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.dao

import com.akshay.atlysaccounting.data.MockData
import com.akshay.atlysaccounting.data.dao.InvoiceDao
import com.akshay.atlysaccounting.data.model.StatusInvoice
import com.akshay.atlysaccounting.data.persistence.AppDatabase
import com.akshay.atlysaccounting.di.module.DatabaseModule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Unit Test for [InvoiceDao].
 */
@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@UninstallModules(DatabaseModule::class)
@Config(application = HiltTestApplication::class)
class InvoiceDaoUnitTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @Inject
  lateinit var appDatabase: AppDatabase

  @Inject
  lateinit var invoiceDao: InvoiceDao

  @Before
  fun setUp() {
    hiltRule.inject()
  }

  @After
  fun closeDb() {
    appDatabase.close()
  }

  @Test
  fun testGetAllInvoices_insertInvoice_fetch_isSuccessful() = runBlocking {
    val invoice = MockData.pendingInvoice
    invoiceDao.insertInvoice(invoice)

    val minimalInvoiceList = invoiceDao.getAllMinimalInvoices().first()

    assertThat(minimalInvoiceList[0].id).isEqualTo(invoice.id)
    assertThat(minimalInvoiceList[0].clientName).isEqualTo(invoice.clientName)
    assertThat(minimalInvoiceList[0].status).isEqualTo(invoice.status)
    assertThat(minimalInvoiceList[0].paymentDue).isEqualTo(invoice.paymentDue)
    assertThat(minimalInvoiceList[0].total).isEqualTo(invoice.total)
  }

  @Test
  fun testGetAllInvoices_insertNone_fetch_emptyListReceived() = runBlocking {
    val minimalInvoiceList = invoiceDao.getAllMinimalInvoices().first()
    assertThat(minimalInvoiceList).isEmpty()
  }

  @Test
  fun testGetInvoice_insertInvoice_fetch_isSuccessful() = runBlocking {
    val exceptedInvoice = MockData.pendingInvoice
    invoiceDao.insertInvoice(exceptedInvoice)

    val invoice = invoiceDao.getInvoice(exceptedInvoice.id).first()

    assertThat(invoice?.id).isEqualTo(exceptedInvoice.id)
    assertThat(invoice?.clientName).isEqualTo(exceptedInvoice.clientName)
    assertThat(invoice?.clientEmail).isEqualTo(exceptedInvoice.clientEmail)
    assertThat(invoice?.items).isEqualTo(exceptedInvoice.items)
    assertThat(invoice?.clientAddress).isEqualTo(exceptedInvoice.clientAddress)
    assertThat(invoice?.senderAddress).isEqualTo(exceptedInvoice.senderAddress)
    assertThat(invoice?.status).isEqualTo(exceptedInvoice.status)
    assertThat(invoice?.createdAt).isEqualTo(exceptedInvoice.createdAt)
    assertThat(invoice?.description).isEqualTo(exceptedInvoice.description)
    assertThat(invoice?.paymentTerms).isEqualTo(exceptedInvoice.paymentTerms)
    assertThat(invoice?.paymentDue).isEqualTo(exceptedInvoice.paymentDue)
    assertThat(invoice?.total).isEqualTo(exceptedInvoice.total)
  }

  @Test
  fun testGetInvoice_insertNone_fetch_nullInvoiceReceived() = runBlocking {
    val invoice = invoiceDao.getInvoice("#13ABCD").first()
    assertThat(invoice).isNull()
  }

  @Test
  fun testInsertListOfInvoice_insertInvoiceList_fetch_isSuccessful() = runBlocking {
    val exceptedInvoiceList = MockData.invoiceList
    invoiceDao.insertListOfInvoice(exceptedInvoiceList)

    val item1 = invoiceDao.getInvoice(exceptedInvoiceList[0].id).first()
    assertThat(item1?.id).isEqualTo(exceptedInvoiceList[0].id)

    val item2 = invoiceDao.getInvoice(exceptedInvoiceList[1].id).first()
    assertThat(item2?.id).isEqualTo(exceptedInvoiceList[1].id)

    val item3 = invoiceDao.getInvoice(exceptedInvoiceList[2].id).first()
    assertThat(item3?.id).isEqualTo(exceptedInvoiceList[2].id)
  }

  @Test
  fun testDeleteInvoice_insertInvoice_delete_deletedSuccessfully() = runBlocking {
    val invoice = MockData.pendingInvoice
    invoiceDao.insertInvoice(invoice)

    val savedInvoice = invoiceDao.getInvoice(invoice.id).first()
    assertThat(savedInvoice).isEqualTo(invoice)

    invoiceDao.deleteInvoice(invoice.id)

    val fetchDeletedInvoice = invoiceDao.getInvoice(invoice.id).first()
    assertThat(fetchDeletedInvoice).isNull()
  }

  @Test
  fun testMarkInvoiceAsPaid_insertInvoice_markPaid_statusUpdatedSuccessfully() = runBlocking {
    val invoice = MockData.pendingInvoice
    invoiceDao.insertInvoice(invoice)

    val updatedInvoiceStatus = MockData.getUpdateInvoiceStatusToPaid(invoice.id)
    invoiceDao.markInvoiceAsPaid(updatedInvoiceStatus)

    val updatedInvoice = invoiceDao.getInvoice(invoice.id).first()
    assertThat(updatedInvoice?.status).isEqualTo(StatusInvoice.PAID)
  }

  @Test
  fun testSaveUpdatedInvoice_insertInvoice_updateFields_InvoiceUpdatedSuccessfully() = runBlocking {
    // ID for both the invoice should be same in order to update the same invoice.
    val invoice = MockData.pendingInvoice
    invoiceDao.insertInvoice(invoice)

    val oldInvoice = invoiceDao.getInvoice(invoice.id).first()
    assertThat(oldInvoice?.id).isEqualTo(invoice.id)
    assertThat(oldInvoice?.clientName).isEqualTo(invoice.clientName)

    val newInvoice = MockData.modifiedPendingInvoice
    invoiceDao.saveUpdatedInvoice(newInvoice)

    val modifiedInvoice = invoiceDao.getInvoice(invoice.id).first()
    assertThat(modifiedInvoice?.id).isEqualTo(invoice.id)
    assertThat(modifiedInvoice?.clientName).isEqualTo(newInvoice.clientName)
  }

  @Test
  fun testCheckIfIdExist_insertInvoice_check_delete_check_existenceCheckedSuccessfully() =
    runBlocking {
      val invoice = MockData.pendingInvoice
      invoiceDao.insertInvoice(invoice)

      val isPresent = invoiceDao.checkIfIdExist(invoice.id)
      assertThat(isPresent).isTrue()

      invoiceDao.deleteInvoice(invoice.id)

      val isPresentAfterDelete = invoiceDao.checkIfIdExist(invoice.id)
      assertThat(isPresentAfterDelete).isFalse()
    }

}
