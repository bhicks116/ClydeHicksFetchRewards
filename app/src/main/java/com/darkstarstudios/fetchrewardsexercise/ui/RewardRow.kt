package com.darkstarstudios.fetchrewardsexercise.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkstarstudios.fetchrewardsexercise.model.Reward

@Composable
fun RewardRow(reward: Reward) {
    Text(
        text = reward.name!!,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
    )
}