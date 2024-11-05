package com.darkstarstudios.fetchrewardsexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.darkstarstudios.fetchrewardsexercise.ui.RewardListView
import com.darkstarstudios.fetchrewardsexercise.ui.theme.FetchRewardsExerciseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchRewardsExerciseTheme {
                Scaffold(modifier = Modifier.fillMaxSize().padding()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        RewardListView(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
