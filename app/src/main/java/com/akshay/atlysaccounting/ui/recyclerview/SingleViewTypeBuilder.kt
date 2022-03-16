/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.akshay.atlysaccounting.ui.base.BaseViewTypeBuilder
import kotlin.reflect.KClass

private const val DEFAULT_VIEW_TYPE = 0

/**
 * Helper class to build the recyclerview which consists of single view type.
 */
class SingleViewTypeBuilder<T : Any>(
  private val dataClassType: KClass<T>
) : BaseViewTypeBuilder<SingleViewTypeBuilder<T>>() {

  private lateinit var viewHolderFactory: ViewHolderFactory<T>

  companion object {
    inline fun <reified T : Any> newBuilder(): SingleViewTypeBuilder<T> {
      return SingleViewTypeBuilder(T::class)
    }
  }

  fun build(): AtlysRecyclerViewAdapter<T> {
    check(::viewHolderFactory.isInitialized) { "A view binder must be initialized" }
    return AtlysRecyclerViewAdapter(
      { DEFAULT_VIEW_TYPE },
      mapOf(DEFAULT_VIEW_TYPE to viewHolderFactory),
      dataClassType
    )
  }

  fun <DB : ViewDataBinding> registerViewDataBinderWithSameModelType(
    inflateDataBinding: (LayoutInflater, ViewGroup, Boolean) -> DB,
    setViewModel: (DB, T) -> Unit
  ): SingleViewTypeBuilder<T> {
    check(!::viewHolderFactory.isInitialized) { "A view binder is already initialized" }
    viewHolderFactory = { viewGroup ->
      val binding = inflateDataBinding(
        LayoutInflater.from(viewGroup.context),
        viewGroup,
        /* attachToRoot= */ false
      )
      binding.lifecycleOwner = getLifecycleOwner()
      object : AtlysRecyclerViewViewHolder<T>(binding.root) {
        override fun bind(data: T) {
          setViewModel(binding, data)
        }
      }
    }
    return this
  }
}