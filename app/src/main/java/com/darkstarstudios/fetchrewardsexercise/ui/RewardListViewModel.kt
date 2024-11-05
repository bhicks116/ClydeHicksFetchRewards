package com.darkstarstudios.fetchrewardsexercise.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkstarstudios.fetchrewardsexercise.SoftFilter
import com.darkstarstudios.fetchrewardsexercise.datasource.DataSource
import com.darkstarstudios.fetchrewardsexercise.model.Reward
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RewardListViewModel : ViewModel()
{
    fun fetchRewards(url: String, onResult: (Map<Int, List<Reward>>?) -> Unit) {
        val sortCallback: (List<Reward>?) -> Unit = { rewards ->
            if (rewards != null) {
                onResult( SoftFilter.sort(rewards) )
            } else {
                onResult(null)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dataSource = DataSource()
                dataSource.fetchRewards(url, sortCallback)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}