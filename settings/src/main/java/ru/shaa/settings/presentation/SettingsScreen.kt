package ru.shaa.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.shaa.settings.R

@Composable
fun SettingsScreen(viewModel: SettingsViewModel, routeToMainScreen: () -> Unit) {

    val currentApi = viewModel.api.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                @OptIn(ExperimentalMaterial3Api::class)
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = stringResource(R.string.company_name))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = routeToMainScreen
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.choose_api),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                RadioButtons(currentApi.value, viewModel)
            }
        }

    }
}

@Composable
private fun RadioButtons(value: String, viewModel: SettingsViewModel) {
    val radioButtonDogApiName = stringResource(R.string.radio_button_dog_api)
    val radioButtonNationalizeApiName = stringResource(R.string.radio_button_nationalize_api)
    val radioButtonCustomApiName = stringResource(R.string.radio_button_custom_api)

    val radioButtons = remember {
        mutableStateListOf(
            RadioButtonType(
                isChecked = radioButtonDogApiName == value,
                text = radioButtonDogApiName
            ),
            RadioButtonType(
                isChecked = radioButtonNationalizeApiName == value,
                text = radioButtonNationalizeApiName
            ),
            RadioButtonType(
                isChecked = radioButtonCustomApiName == value,
                text = radioButtonCustomApiName
            )
        )
    }


    radioButtons.forEach { radioButtonType ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    radioButtons.replaceAll {
                        it.copy(isChecked = it.text == radioButtonType.text)
                    }
                    viewModel.changeApi(radioButtonType.text)
                }
                .fillMaxWidth()
                .padding(start = 16.dp)) {
            RadioButton(
                selected = radioButtonType.isChecked,
                onClick = {
                    radioButtons.replaceAll {
                        it.copy(isChecked = it.text == radioButtonType.text)
                    }
                })
            Text(text = radioButtonType.text)
        }
    }
}

data class RadioButtonType(val isChecked: Boolean, val text: String)
