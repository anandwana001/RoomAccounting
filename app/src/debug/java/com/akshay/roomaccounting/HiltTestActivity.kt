/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting

import androidx.appcompat.app.AppCompatActivity
import com.akshay.roomaccounting.ui.home.display.DisplayInvoiceItemListener
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
