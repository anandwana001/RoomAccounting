/**
 * Created by anandwana001 on
 * 07, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.listener

import com.akshay.roomaccounting.ui.home.write.viewmodel.PaymentTermsViewModel

/**
 * Listener for items in [PaymentTermsViewModel].
 */
interface PaymentTermsListener {

  fun retrieveInput(): Int
}