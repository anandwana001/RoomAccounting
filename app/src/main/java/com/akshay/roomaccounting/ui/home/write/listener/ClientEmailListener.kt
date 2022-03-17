/**
 * Created by anandwana001 on
 * 05, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.listener

import com.akshay.roomaccounting.ui.home.write.viewmodel.ClientEmailViewModel

/**
 * Listener for items in [ClientEmailViewModel].
 */
interface ClientEmailListener {

  fun retrieveInput() : String
}
