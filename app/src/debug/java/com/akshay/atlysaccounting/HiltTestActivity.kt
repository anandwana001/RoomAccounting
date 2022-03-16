/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting

import androidx.appcompat.app.AppCompatActivity
import com.akshay.atlysaccounting.ui.home.display.DisplayInvoiceItemListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * Base Activity required for testing Fragments in isolation using Hilt.
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity(), DisplayInvoiceItemListener {

  override fun openDetailInvoiceFragment(invoiceId: String) {
  }

  override fun openWriteInvoice(id: String?) {
  }

}
