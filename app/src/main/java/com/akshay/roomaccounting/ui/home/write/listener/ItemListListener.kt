/**
 * Created by anandwana001 on
 * 08, March, 2022
 **/

package com.akshay.roomaccounting.ui.home.write.listener

import com.akshay.roomaccounting.ui.home.write.viewmodel.ItemListItemViewModel
import com.akshay.roomaccounting.ui.home.write.viewmodel.ItemListViewModel

/**
 * Listener for items in [ItemListViewModel].
 */
interface ItemListListener {

  fun retrieveInput(): List<ItemListItemViewModel>?
}
