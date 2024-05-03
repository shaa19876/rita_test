package ru.shaa.main.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.SubcomposeAsyncImage
import okhttp3.internal.format
import ru.shaa.main.R
import ru.shaa.main.domain.model.CustomApiModel
import ru.shaa.main.domain.model.DogApiModel
import ru.shaa.main.domain.model.NationalizeApiModel
import ru.shaa.main.presentation.util.BaseApi
import ru.shaa.main.presentation.util.MainState
import ru.shaa.main.presentation.util.validationNames
import ru.shaa.main.presentation.util.validationUrl

@Composable
fun MainScreen(viewModel: MainViewModel, routeToSettings: () -> Unit) {

    val state = viewModel.state.collectAsState()

    val currentApi = viewModel.currentApi.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCurrentApi()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                (CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(R.string.company_name))
                    },
                    actions = {
                        IconButton(onClick = routeToSettings) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = null
                            )
                        }
                    }
                ))
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card {
                    Text(text = currentApi.value.name, modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.height(16.dp))
                DrawMainContent(currentApi.value, viewModel::fetch)
                Spacer(modifier = Modifier.height(16.dp))
                when (val currentState = state.value) {
                    is MainState.Error -> DrawErrorScreen()
                    is MainState.Loading -> DrawLoadingScreen()
                    is MainState.Success<*> -> DrawSuccessScreen(currentState.data!!)
                    else -> {}
                }
            }

        }
    }
}

@Composable
fun DrawMainContent(api: BaseApi, onClick: (String?) -> Unit) {
    when (api) {
        is BaseApi.Custom -> {
            var url by remember {
                mutableStateOf("")
            }
            var isValid by remember {
                mutableStateOf(true)
            }
            OutlinedTextField(
                value = url,
                onValueChange = {
                    url = it
                    isValid = it.validationUrl()
                },
                label = { Text(text = "Enter http for api") },
                supportingText = { Text(text = "Example: http://api.ru/") },
                isError = !isValid
            )
            Button(
                onClick = { onClick(url) },
                enabled = url.isNotEmpty() && isValid
            ) {
                Text(text = stringResource(R.string.fetch))
            }
        }

        is BaseApi.Dog -> {
            Button(onClick = { onClick(null) }) {
                Text(text = stringResource(R.string.fetch))
            }
        }

        is BaseApi.Nationalize -> {
            var names by remember {
                mutableStateOf("")
            }
            var isValid by remember {
                mutableStateOf(true)
            }
            OutlinedTextField(
                value = names,
                onValueChange = {
                    names = it
                    isValid = it.validationNames()
                },
                label = { Text(text = "Enter name/names") },
                supportingText = { Text(text = "Example: John or Mike, Nick, ...") },
                isError = !isValid
            )
            Button(
                onClick = { onClick(names) },
                enabled = names.isNotEmpty() && isValid
            ) {
                Text(text = stringResource(R.string.fetch))
            }
        }
    }

}

@Composable
fun DrawSuccessScreen(data: Any) {
    when (data) {
        is CustomApiModel -> {
            Text(text = data.json)}
        is DogApiModel -> {
            var openDialog by remember { mutableStateOf(false) }
            SubcomposeAsyncImage(
                model = data.url,
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = null
                    )
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { openDialog = true }
            )
            if (openDialog) {
                Dialog(onDismissRequest = { openDialog = false }) {
                    SubcomposeAsyncImage(
                        model = data.url,
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.no_image),
                                contentDescription = null
                            )
                        },
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { openDialog = false }
                    )
                }
            }
        }

        is NationalizeApiModel -> {
            var openDialog by remember { mutableStateOf(true) }
            if (openDialog) {
                AlertDialog(
                    onDismissRequest = { openDialog = false },
                    title = { Text(text = "Result") },
                    text = {
                        Column {
                            data.response.forEach {
                                Text(
                                    text = format(
                                        "%s is from country with id: %s with %.2f certainty",
                                        it.name.substring(0, 1).uppercase() + it.name.substring(1),
                                        it.country,
                                        it.probability * 100
                                    )
                                )
                            }
                        }
                    },
                    confirmButton = {
                        Button({ openDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun DrawLoadingScreen() {
    Box(Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun DrawErrorScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.network_error),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.error),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onError
        )
    }
}