package com.albertson.acronymsearch.framework.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.albertson.acronymsearch.R
import com.albertson.acronymsearch.databinding.ViewAcronymSingleItemBinding
import com.albertson.acronymsearch.domain.model.LongForm


class AcronymListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var acronymResponseList = ArrayList<LongForm>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymListViewHolder {
    val binding =
      ViewAcronymSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return AcronymListViewHolder(binding)
  }

  internal fun setData(acronyms: List<LongForm>) {
    acronymResponseList.apply {
      clear()
      addAll(acronyms)
    }
    notifyDataSetChanged()
  }

  override fun getItemCount() = acronymResponseList.size

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is AcronymListViewHolder -> holder.bind(acronymResponseList[position])
    }
  }
}

class AcronymListViewHolder(
  private val binding: ViewAcronymSingleItemBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(item: LongForm) {
    binding.apply {
      binding.apply {
        itemAcronymName.text = item.lf
        itemAcronymFrequency.text =
          root.resources.getString(R.string.text_label_frequency, item.freq)
        itemAcronymSince.text =
          root.resources.getString(R.string.text_label_since, item.since)
      }
    }
  }
}