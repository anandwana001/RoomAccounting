/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.data

import com.akshay.atlysaccounting.data.model.Address
import com.akshay.atlysaccounting.data.model.Invoice
import com.akshay.atlysaccounting.data.model.MinimalInvoice
import com.akshay.atlysaccounting.data.model.SingleEntryInvoice
import com.akshay.atlysaccounting.data.model.StatusInvoice
import com.akshay.atlysaccounting.data.model.UpdateInvoiceStatus

/**
 * Mock data for Testing.
 */
object MockData {

  val pendingInvoice = getSinglePendingInvoice()
  val pendingToPaidInvoice = getSinglePendingPaidInvoice()
  val paidInvoice = getSinglePaidInvoice()
  val draftInvoice = getSingleDraftInvoice()

  val modifiedPendingInvoice = getSingleModifiedPendingInvoice()

  val invoiceList = getListOfInvoice()
  val invoiceListJson = getListOfInvoiceInJsonFormat()

  val invoiceAddress = getAddressObject()
  val invoiceAddressJson = getAddressJsonFormat()

  private fun getListOfInvoiceInJsonFormat(): String {
    return """
      [{"id":"XM9141","createdAt":"2021-08-21","paymentDue":"2021-09-20","description":"Graphic Design","paymentTerms":30,"clientName":"Alex Grim","clientEmail":"alexgrim@mail.com","status":"pending","senderAddress":{"street":"19 Union Terrace","city":"London","postCode":"E1 3EZ","country":"United Kingdom"},"clientAddress":{"street":"84 Church Way","city":"Bradford","postCode":"BD1 9PB","country":"United Kingdom"},"items":[{"name":"Banner Design","quantity":1,"price":156.0,"total":156.0},{"name":"Email Design","quantity":2,"price":200.0,"total":400.0}],"total":556.0},{"id":"RT3080","createdAt":"2021-08-18","paymentDue":"2021-08-19","description":"Re-branding","paymentTerms":1,"clientName":"Jensen Huang","clientEmail":"jensenh@mail.com","status":"paid","senderAddress":{"street":"19 Union Terrace","city":"London","postCode":"E1 3EZ","country":"United Kingdom"},"clientAddress":{"street":"106 Kendell Street","city":"Sharrington","postCode":"NR24 5WQ","country":"United Kingdom"},"items":[{"name":"Brand Guidelines","quantity":1,"price":1800.9,"total":1800.9}],"total":1800.9},{"id":"RG0314","createdAt":"2021-08-21","paymentDue":"2021-09-20","description":"Graphic Design","paymentTerms":30,"clientName":"Alex Grim","clientEmail":"alexgrim@mail.com","status":"draft","senderAddress":{"street":"19 Union Terrace","city":"London","postCode":"E1 3EZ","country":"United Kingdom"},"clientAddress":{"street":"84 Church Way","city":"Bradford","postCode":"BD1 9PB","country":"United Kingdom"},"items":[{"name":"Banner Design","quantity":1,"price":156.0,"total":156.0},{"name":"Email Design","quantity":2,"price":200.0,"total":400.0}],"total":556.0}]
      """.trimIndent()
  }

  fun getUpdateInvoiceStatusToPaid(id: String): UpdateInvoiceStatus {
    return UpdateInvoiceStatus(
      id = id,
      status = StatusInvoice.PAID
    )
  }

  private fun getListOfInvoice(): List<Invoice> {
    return listOf(
      pendingInvoice,
      paidInvoice,
      draftInvoice
    )
  }

  private fun getSinglePendingInvoice(): Invoice {
    return Invoice(
      "XM9141",
      "2021-08-21",
      "2021-09-20",
      "Graphic Design",
      30,
      "Alex Grim",
      "alexgrim@mail.com",
      StatusInvoice.PENDING,
      Address(
        "19 Union Terrace",
        "London",
        "E1 3EZ",
        "United Kingdom"
      ),
      Address(
        "84 Church Way",
        "Bradford",
        "BD1 9PB",
        "United Kingdom"
      ),
      listOf<SingleEntryInvoice>(
        SingleEntryInvoice(
          "Banner Design",
          1,
          156.00,
          156.00
        ),
        SingleEntryInvoice(
          "Email Design",
          2,
          200.00,
          400.00
        )
      ),
      556.00
    )
  }

  private fun getSinglePendingPaidInvoice(): Invoice {
    return Invoice(
      "XM9141",
      "2021-08-21",
      "2021-09-20",
      "Graphic Design",
      30,
      "Alex Grim",
      "alexgrim@mail.com",
      StatusInvoice.PAID,
      Address(
        "19 Union Terrace",
        "London",
        "E1 3EZ",
        "United Kingdom"
      ),
      Address(
        "84 Church Way",
        "Bradford",
        "BD1 9PB",
        "United Kingdom"
      ),
      listOf<SingleEntryInvoice>(
        SingleEntryInvoice(
          "Banner Design",
          1,
          156.00,
          156.00
        ),
        SingleEntryInvoice(
          "Email Design",
          2,
          200.00,
          400.00
        )
      ),
      556.00
    )
  }

  private fun getSingleModifiedPendingInvoice(): Invoice {
    return Invoice(
      "XM9141",
      "2021-08-21",
      "2021-09-20",
      "Graphic Design",
      30,
      "Akshay Nandwana",
      "alexgrim@mail.com",
      StatusInvoice.PENDING,
      Address(
        "19 Union Terrace",
        "London",
        "E1 3EZ",
        "United Kingdom"
      ),
      Address(
        "84 Church Way",
        "Bradford",
        "BD1 9PB",
        "United Kingdom"
      ),
      listOf<SingleEntryInvoice>(
        SingleEntryInvoice(
          "Android App",
          1,
          100.00,
          200.00
        )
      ),
      10.00
    )
  }

  private fun getSinglePaidInvoice(): Invoice {
    return Invoice(
      "RT3080",
      "2021-08-18",
      "2021-08-19",
      "Re-branding",
      1,
      "Jensen Huang",
      "jensenh@mail.com",
      StatusInvoice.PAID,
      Address(
        "19 Union Terrace",
        "London",
        "E1 3EZ",
        "United Kingdom"
      ),
      Address(
        "106 Kendell Street",
        "Sharrington",
        "NR24 5WQ",
        "United Kingdom"
      ),
      listOf<SingleEntryInvoice>(
        SingleEntryInvoice(
          "Brand Guidelines",
          1,
          1800.90,
          1800.90
        )
      ),
      1800.90
    )
  }

  private fun getSingleDraftInvoice(): Invoice {
    return Invoice(
      "RG0314",
      "2021-08-21",
      "2021-09-20",
      "Graphic Design",
      30,
      "Alex Grim",
      "alexgrim@mail.com",
      StatusInvoice.DRAFT,
      Address(
        "19 Union Terrace",
        "London",
        "E1 3EZ",
        "United Kingdom"
      ),
      Address(
        "84 Church Way",
        "Bradford",
        "BD1 9PB",
        "United Kingdom"
      ),
      listOf<SingleEntryInvoice>(
        SingleEntryInvoice(
          "Banner Design",
          1,
          156.00,
          156.00
        ),
        SingleEntryInvoice(
          "Email Design",
          2,
          200.00,
          400.00
        )
      ),
      556.00
    )
  }

  private fun getAddressObject(): Address {
    return Address(
      "19 Union Terrace",
      "London",
      "E1 3EZ",
      "United Kingdom"
    )
  }

  private fun getAddressJsonFormat(): String {
    return """
      {"street":"19 Union Terrace","city":"London","postCode":"E1 3EZ","country":"United Kingdom"}
    """.trimIndent()
  }
}