/**
 * Created by anandwana001 on
 * 03, March, 2022
 **/

package com.akshay.roomaccounting.ui.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.akshay.roomaccounting.ui.base.BaseViewTypeBuilder
import kotlin.reflect.KClass

typealias MultiItemViewType<T, E> = (T) -> E

/**
 * Helper class to build the recyclerview which consists of multiple view type.
 */
class MultiViewTypeBuilder<T : Any, E : Enum<E>>(
  private val dataClassType: KClass<T>,
  private val multiItemViewType: MultiItemViewType<T, E>
) : BaseViewTypeBuilder<MultiViewTypeBuilder<T, E>>() {

  private var viewHolderFactoryMap: MutableMap<E, ViewHolderFactory<T>> = HashMap()

  companion object {
    inline fun <reified T : Any, reified E : Enum<E>> newBuilder(
      noinline multiItemViewType: MultiItemViewType<T, E>
    ): MultiViewTypeBuilder<T, E> {
      return MultiViewTypeBuilder(T::class, multiItemViewType)
    }
  }

  fun build(): RoomAccRecyclerViewAdapter<T> {
    check(viewHolderFactoryMap.isNotEmpty()) { "At least one view binder must be registered" }
    return RoomAccRecyclerViewAdapter(
      { value -> multiItemViewType(value).ordinal },
      viewHolderFactoryMap.mapKeys { entry -> entry.key.ordinal },
      dataClassType
    )
  }

  fun <DB : ViewDataBinding, T2 : T> registerViewDataBinder(
    viewType: E,
    inflateDataBinding: (LayoutInflater, ViewGroup, Boolean) -> DB,
    setViewModel: (DB, T2) -> Unit,
    transformViewModel: (T) -> T2
  ): MultiViewTypeBuilder<T, E> {
    check(!viewHolderFactoryMap.containsKey(viewType)) {
      "Cannot register a second view binder for view type: $viewType (current binder: " +
          "${viewHolderFactoryMap[viewType]}."
    }
    val viewHolderFactory: ViewHolderFactory<T> = { viewGroup ->
      val binding = inflateDataBinding(
        LayoutInflater.from(viewGroup.context),
        viewGroup,
        /* attachToRoot= */ false
      )
      binding.lifecycleOwner = getLifecycleOwner()
      object : RoomAccRecyclerViewViewHolder<T>(binding.root) {
        override fun bind(data: T) {
          setViewModel(binding, transformViewModel(data))
        }
      }
    }
    viewHolderFactoryMap[viewType] = viewHolderFactory
    return this
  }

  /**
   * Bind a view where ViewModel is not required.
   */
  fun <V : View> registerViewBinder(
    viewType: E,
    inflateView: (ViewGroup) -> V,
    bindView: (V, T) -> Unit
  ): MultiViewTypeBuilder<T, E> {
    check(!viewHolderFactoryMap.containsKey(viewType)) {
      "Cannot register a second view binder for view type: $viewType (current binder: " +
          "${viewHolderFactoryMap[viewType]}."
    }
    val viewHolderFactory: ViewHolderFactory<T> = { viewGroup ->
      val inflatedView = inflateView(viewGroup)
      object : RoomAccRecyclerViewViewHolder<T>(inflatedView) {
        override fun bind(data: T) {
          bindView(inflatedView, data)
        }
      }
    }
    viewHolderFactoryMap[viewType] = viewHolderFactory
    return this
  }

}