package com.albertson.acronymsearch.framework.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.albertson.acronymsearch.R
import com.albertson.acronymsearch.databinding.FragmentDashboardBinding
import com.albertson.acronymsearch.utils.gone
import com.albertson.acronymsearch.utils.show
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class DashboardFragment : Fragment() {

  private var _binding: FragmentDashboardBinding? = null

  private val acronymsViewModel: AcronymViewModel by viewModels()

  private val binding get() = _binding!!

  private val acronymsAdapter by lazy { AcronymListAdapter() }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View {
    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.acronymDetailsList.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = acronymsAdapter
    }

    acronymsViewModel.currentState.observe(viewLifecycleOwner) { viewStateData ->
      if (viewStateData.isLoading) {
        showProgress()
      } else if (viewStateData.isDataAvailable) {
        hideProgress()
        if (viewStateData.data?.isNotEmpty() == true) {
          acronymsAdapter.setData(viewStateData.data[0].longFormList!!)
        } else
          showError()
      } else if (viewStateData.error != null) {
        hideProgress()
        showError()
      }
    }
    showDefaultEmptyStatus()
  }

  private fun showDefaultEmptyStatus() {
    showError(R.string.search_hint)
  }

  private fun showError(resID: Int = R.string.no_data_available) {
    binding.apply {
      progressContainer.root.gone()
      acronymDetailsList.gone()
      emptyStatus.apply {
        text = getString(resID)
        show()
      }
    }
  }

  private fun showProgress() {
    binding.apply {
      progressContainer.root.show()
      acronymDetailsList.gone()
      emptyStatus.gone()
    }
  }

  private fun hideProgress() {
    binding.apply {
      progressContainer.root.gone()
      acronymDetailsList.show()
      emptyStatus.gone()
    }
  }

  fun refreshSearchByQuery(query: String) {
    acronymsViewModel.fetchData(query)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    private val TAG = DashboardFragment::class.java.simpleName
  }
}