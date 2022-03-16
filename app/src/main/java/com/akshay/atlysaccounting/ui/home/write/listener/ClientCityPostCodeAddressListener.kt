/**
 * Created by anandwana001 on
 * 05, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.listener

import com.akshay.atlysaccounting.ui.home.write.viewmodel.ClientCityPostCodeViewModel

/**
 * Listener for items in [ClientCityPostCodeViewModel].
 */
interface ClientCityPostCodeAddressListener {

  fun retrieveInput(): Pair<String, String>
}
