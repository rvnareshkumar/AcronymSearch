package com.albertson.acronymsearch.framework.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.albertson.acronymsearch.R
import dagger.hilt.android.AndroidEntryPoint
import com.albertson.acronymsearch.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var appBarConfiguration: AppBarConfiguration


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)

    val navController = findNavController(R.id.nav_host_fragment_content_main)
    appBarConfiguration = AppBarConfiguration(navController.graph)

  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    val menuItem = menu.findItem(R.id.action_search)
    val searchView = menuItem.actionView as SearchView
    searchView.apply {
      setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
          query?.let { applyFilter(query) }
          return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
          newText?.let { applyFilter(newText) }
          return false
        }

      })
    }
    return true
  }

  private fun applyFilter(query: String) {
    Log.d("Text", "Searched query is $query")
    val visibleFragment = getCurrentVisibleFragment()
    if (visibleFragment is DashboardFragment && query.isNotEmpty()) {
      visibleFragment.refreshSearchByQuery(query)
    }
  }

  private fun getCurrentVisibleFragment(): Fragment? {
    val navHostFragment = supportFragmentManager.primaryNavigationFragment as NavHostFragment?
    return navHostFragment?.childFragmentManager?.primaryNavigationFragment
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_search -> true
      else -> super.onOptionsItemSelected(item)
    }
  }
}