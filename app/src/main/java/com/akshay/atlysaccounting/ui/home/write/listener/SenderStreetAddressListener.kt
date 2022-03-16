/**
 * Created by anandwana001 on
 * 05, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.listener

import com.akshay.atlysaccounting.ui.home.write.viewmodel.SenderStreetAddressViewModel

/**
 * Listener for items in [SenderStreetAddressViewModel].
 */
interface SenderStreetAddressListener {

  fun retrieveInput() : String
}
