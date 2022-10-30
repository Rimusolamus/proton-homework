package ch.protonmail.android.protonmailtest.ui.screens

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ch.protonmail.android.protonmailtest.R
import ch.protonmail.android.protonmailtest.ui.MainViewModel
import ch.protonmail.android.protonmailtest.ui.common.DefaultButton
import ch.protonmail.android.protonmailtest.ui.common.TaskItemDetail
import ch.protonmail.android.protonmailtest.ui.theme.DarkGrey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailScreen(
    viewModel: MainViewModel,
    backToMain: () -> Unit,
    id: String
) {

    val viewState by viewModel.state.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(vertical = 8.dp),
                navigationIcon = {
                    IconButton(onClick = backToMain) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                title = { Text(stringResource(R.string.proton_vpn)) }, elevation = 0.dp
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val filtered = tasks.filter { it.id == id }

            if (viewState.refreshing) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                if (filtered.isNotEmpty()) {
                    item {
                        if (viewState.onlyFromCache) {
                            GlideImage(
                                model = filtered[0].image,
                                contentScale = ContentScale.FillWidth,
                                contentDescription = null,
                                requestBuilderTransform = {
                                    it.onlyRetrieveFromCache(true)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(202.dp)
                                    .background(DarkGrey)
                            )
                        } else {
                            GlideImage(
                                model = filtered[0].image,
                                contentScale = ContentScale.FillWidth,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(202.dp)
                                    .background(DarkGrey)
                            )
                        }
                        TaskItemDetail(task = filtered[0])
                    }
                }
            }
            DefaultButton(text = "Load Image") {
                viewModel.setOnlyFromCache(false)
            }
        }
    }
}

@Composable
private fun ShowImage(url: String, onlyRetrieveFromCache: Boolean) {

}

