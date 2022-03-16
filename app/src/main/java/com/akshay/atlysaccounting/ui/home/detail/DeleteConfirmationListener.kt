/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.detail

/**
 * Listener for Delete Invoice Button.
 */
interface DeleteConfirmationListener {

  /**
   * Delete Invoice using its id.
   */
  fun deleteInvoice(id: String)
}
