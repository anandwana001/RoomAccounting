/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

/** A function that returns the integer-based type of view that can bind the specified object. */
private typealias ItemViewType<T> = (T) -> Int

typealias ViewHolderFactory<T> = (ViewGroup) -> RoomAccRecyclerViewViewHolder<T>

/**
 * Generic RecyclerView for RoomAccounting App.
 */
class RoomAccRecyclerViewAdapter<T : Any> internal constructor(
  private val itemViewType: ItemViewType<T>,
  private val viewHolderFactoryMap: Map<Int, ViewHolderFactory<T>>,
  private val dataClassType: KClass<T>
) : RecyclerView.Adapter<RoomAccRecyclerViewViewHolder<T>>() {

  private val dataList: MutableList<T> = ArrayList()

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RoomAccRecyclerViewViewHolder<T> {
    val viewHolderFactory = viewHolderFactoryMap[viewType]
    checkNotNull(viewHolderFactory) { "View not exist for: $viewType" }
    return viewHolderFactory(parent)
  }

  override fun onBindViewHolder(holder: RoomAccRecyclerViewViewHolder<T>, position: Int) =
    holder.bind(dataList[position])

  override fun getItemViewType(position: Int): Int = itemViewType(dataList[position])

  override fun getItemCount(): Int = dataList.size

  private fun setData(newDataList: List<T>) {
    val result = DiffUtil.calculateDiff(
      RoomAccDiffCallback(dataList, newDataList),
      /* detectMoves= */ false
    )
    dataList.clear()
    dataList += newDataList.toMutableList()
    result.dispatchUpdatesTo(this)
  }

  /**
   * Use this function from data-binding to pass the data.
   */
  fun <T2 : Any> setDataUnchecked(newDataList: List<T2>) {
    newDataList.firstOrNull()?.let {
      check(dataClassType.java.isAssignableFrom(it.javaClass)) {
        "Trying to bind incompatible data to adapter. Data class type: ${it.javaClass}, " +
            "expected adapter class type: $dataClassType."
      }
    }
    @Suppress("UNCHECKED_CAST")
    setData(newDataList as List<T>)
  }
}