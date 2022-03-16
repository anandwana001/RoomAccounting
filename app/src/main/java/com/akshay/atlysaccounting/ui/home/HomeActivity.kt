/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akshay.atlysaccounting.R
import com.akshay.atlysaccounting.ui.home.detail.DetailInvoiceFragment
import com.akshay.atlysaccounting.ui.home.detail.DetailInvoiceFragment.Companion.TAG_DETAIL_INVOICE_FRAGMENT
import com.akshay.atlysaccounting.ui.home.display.DisplayInvoiceFragment
import com.akshay.atlysaccounting.ui.home.display.DisplayInvoiceFragment.Companion.TAG_DISPLAY_INVOICE_FRAGMENT
import com.akshay.atlysaccounting.ui.home.display.DisplayInvoiceItemListener
import com.akshay.atlysaccounting.ui.home.write.WriteInvoiceFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity of Atlys Accounting App.
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), DisplayInvoiceItemListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    // Sometime Android failed to update the decor view while recreating the activity.
    window.decorView.layoutDirection = baseContext.resources.configuration.layoutDirection

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    if (getDisplayInvoiceFragment() == null) {
      supportFragmentManager.beginTransaction().add(
        R.id.fragment_container_view,
        DisplayInvoiceFragment.newInstance(),
        TAG_DISPLAY_INVOICE_FRAGMENT
      ).commitNow()
    }
  }

  private fun getDisplayInvoiceFragment(): DisplayInvoiceFragment? {
    return supportFragmentManager.findFragmentById(
      R.id.fragment_container_view
    ) as DisplayInvoiceFragment?
  }

  override fun openDetailInvoiceFragment(invoiceId: String) {
    supportFragmentManager.beginTransaction()
      .replace(
        R.id.fragment_container_view,
        DetailInvoiceFragment.newInstance(invoiceId),
        TAG_DETAIL_INVOICE_FRAGMENT
      )
      .addToBackStack(null)
      .commit()
  }

  override fun openWriteInvoice(id: String?) {
    supportFragmentManager.beginTransaction()
      .replace(
        R.id.fragment_container_view,
        WriteInvoiceFragment.newInstance(id),
        WriteInvoiceFragment.TAG_CREATE_INVOICE_FRAGMENT
      )
      .addToBackStack(null)
      .commit()
  }
}
