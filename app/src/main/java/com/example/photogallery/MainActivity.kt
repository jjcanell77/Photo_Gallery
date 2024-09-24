@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.photogallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.photogallery.ui.screens.HomeScreen
import com.example.photogallery.ui.screens.HomeViewModel
import com.example.photogallery.ui.theme.PhotoGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoGalleryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    PhotoGalleryApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoGalleryApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val pagingDataFlow = homeViewModel.pagingDataFlow
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { GalleryTopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreen(
                pagingDataFlow = pagingDataFlow,
                contentPadding = it
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryTopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.title),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}
