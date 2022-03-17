/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.ui.base

import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

/**
 * This class is meant to be used with [RoomAccRecyclerViewAdapter].
 */
abstract class BaseViewTypeBuilder<BuilderType : Any> {
  private var lifecycleOwnerRef: WeakReference<LifecycleOwner>? = null

  /**
   * This helps in keeping the child of recyclerview to maintain the lifecycle in sync with the
   * parent form where it is created.
   */
  fun setLifecycleOwner(lifecycleOwner: LifecycleOwner): BuilderType {
    check(lifecycleOwnerRef == null) {
      "A lifecycle owner has already been bound to this adapter."
    }
    lifecycleOwnerRef = WeakReference(lifecycleOwner)
    @Suppress("UNCHECKED_CAST") return this as BuilderType
  }

  protected fun getLifecycleOwner(): LifecycleOwner? {
    return lifecycleOwnerRef?.let { ref ->
      checkNotNull(ref.get()) {
        "Attempted to bind data with expired lifecycle owner"
      }
    }
  }
}