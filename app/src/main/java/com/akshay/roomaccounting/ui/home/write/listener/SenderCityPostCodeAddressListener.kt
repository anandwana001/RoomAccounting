/**
 * Created by anandwana001 on
 * 05, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.listener

import com.akshay.roomaccounting.ui.home.write.viewmodel.SenderCityPostCodeViewModel

/**
 * Listener for items in [SenderCityPostCodeViewModel].
 */
interface SenderCityPostCodeAddressListener {

  fun retrieveInput() : Pair<String, String>
}
