/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.listener

import com.akshay.atlysaccounting.ui.home.write.viewmodel.PaymentTermsViewModel

/**
 * Listener for items in [PaymentTermsViewModel].
 */
interface PaymentTermsListener {

  fun retrieveInput(): Int
}