/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil

/**
 * Diffing Logic to be used in [RoomAccRecyclerViewAdapter].
 */
class RoomAccDiffCallback<T : Any>(
  var oldList: MutableList<T>,
  var newList: List<T>
) : DiffUtil.Callback() {

  override fun getOldListSize(): Int = oldList.size

  override fun getNewListSize(): Int = newList.size

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldList[oldItemPosition]::class == newList[newItemPosition]::class
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldList[oldItemPosition].equals(newList[newItemPosition])
  }
}
