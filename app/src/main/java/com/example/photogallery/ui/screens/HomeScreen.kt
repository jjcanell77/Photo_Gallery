package com.example.photogallery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.photogallery.R
import com.example.photogallery.model.Photo
import com.example.photogallery.ui.theme.PhotoGalleryTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    pagingDataFlow: Flow<PagingData<Photo>>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val lazyPagingItems = pagingDataFlow.collectAsLazyPagingItems()

    when (lazyPagingItems.loadState.refresh) {
        is LoadState.Loading -> {
            LoadingScreen(modifier = modifier.fillMaxSize())
        }
        is LoadState.Error -> {
            val e = lazyPagingItems.loadState.refresh as LoadState.Error
            ErrorScreen(retryAction = { lazyPagingItems.retry() }, modifier = modifier.fillMaxSize())
        }
        else -> {
            PhotosGridScreen(
                lazyPagingItems = lazyPagingItems,
                contentPadding = contentPadding,
                modifier = modifier
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
       LoadingScreen()
    }
}

@Composable
fun ErrorItem(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        ErrorScreen(retryAction = retryAction)
    }
}

@Composable
fun PhotosGridScreen(
    lazyPagingItems: LazyPagingItems<Photo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        items(lazyPagingItems.itemCount) { index ->
            val photo = lazyPagingItems[index]
            if (photo != null) {
                PhotoCard(
                    photo,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
            }
        }
        lazyPagingItems.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
                loadState.append is LoadState.Error -> {
                    item {
                        ErrorItem(retryAction = { retry() })
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoCard(photo: Photo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.src.original)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = photo.alt,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PhotoGalleryTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    PhotoGalleryTheme {
        ErrorScreen({})
    }
}