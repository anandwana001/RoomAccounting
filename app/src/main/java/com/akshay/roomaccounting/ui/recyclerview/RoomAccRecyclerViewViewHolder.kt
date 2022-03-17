/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Bind data to view.
 */
abstract class RoomAccRecyclerViewViewHolder<T> internal constructor(
  view: View
) : RecyclerView.ViewHolder(view) {
  internal abstract fun bind(data: T)
}
