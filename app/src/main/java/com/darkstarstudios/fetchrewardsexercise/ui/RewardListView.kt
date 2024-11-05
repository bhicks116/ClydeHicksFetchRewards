package com.darkstarstudios.fetchrewardsexercise.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.darkstarstudios.fetchrewardsexercise.model.Reward

// NOTE: This is to allow the use of 'stickyHeader' in the LazyColumn.  If can't use experimental
// then replace stickHeader with item.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RewardListView(viewModel: RewardListViewModel = viewModel(), modifier: Modifier) {
    var isLoading by remember { mutableStateOf(false) }
    var rewardGroups by remember { mutableStateOf<Map<Int, List<Reward>>?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchRewards("https://fetch-hiring.s3.amazonaws.com/hiring.json") { result ->
            rewardGroups = result
            isLoading = false
        }
    }

    if (isLoading || rewardGroups == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            rewardGroups!!.forEach { group ->
// NOTE: This uses experimental 'stickyHeader'
// If can't use experimental, then replace 'stickHeader' with 'item.
                stickyHeader {
                    Text(
                        text = "ListID = ${group.key}",
                        style = typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(group.value.count()) { index ->
                    RewardRow(group.value[index])
                }
            }
        }
    }
}
