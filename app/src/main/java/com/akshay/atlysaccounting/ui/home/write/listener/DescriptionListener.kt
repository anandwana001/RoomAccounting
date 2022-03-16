/**
 * Created by anandwana001 on
 * 05, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home.write.listener

import com.akshay.atlysaccounting.ui.home.write.viewmodel.DescriptionViewModel

/**
 * Listener for items in [DescriptionViewModel].
 */
interface DescriptionListener {

  fun retrieveInput() : String
}
