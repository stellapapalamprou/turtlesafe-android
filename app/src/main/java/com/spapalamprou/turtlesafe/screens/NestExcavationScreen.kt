package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationEvent
import com.spapalamprou.turtlesafe.features.nestExcavation.NestExcavationViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.validators.isInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestExcavationScreen(
    sharedViewModel: MorningSurveyViewModel,
    navigateBack: () -> Unit
) {
    val viewModel: NestExcavationViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sendEvent(
            NestExcavationEvent.NestCodeChanged(sharedViewModel.state.value.nestCode)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nest Excavation",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "arrow to navigate back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column {

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        text = state.value.nestCode,
                        onValueChange = {},
                        readOnly = true,
                        label = "Nest Code",
                        onIconClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.hatchedEggs,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.HatchedEggsChanged(input))
                        },
                        isError = state.value.invalidHatchedEggsMessage.isNotEmpty(),
                        errorMessage = state.value.invalidHatchedEggsMessage,
                        label = "Hatched Eggs*",
                        onIconClick = { viewModel.sendEvent(NestExcavationEvent.HatchedEggsChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.pippedDeadHatchlings,
                        onValueChange = { input ->
                            viewModel.sendEvent(
                                NestExcavationEvent.PippedDeadHatchlingsChanged(
                                    input
                                )
                            )
                        },
                        isError = state.value.invalidPippedDeadHatchlingsMessage.isNotEmpty(),
                        errorMessage = state.value.invalidPippedDeadHatchlingsMessage,
                        label = "Pipped Dead Hatchlings (and eggs)*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestExcavationEvent.PippedDeadHatchlingsChanged(
                                    ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.pippedLiveHatchlings,
                        onValueChange = { input ->
                            viewModel.sendEvent(
                                NestExcavationEvent.PippedLiveHatchlingsChanged(
                                    input
                                )
                            )
                        },
                        isError = state.value.invalidPippedLiveHatchlingsMessage.isNotEmpty(),
                        errorMessage = state.value.invalidPippedLiveHatchlingsMessage,
                        label = "Pipped Live Hatchlings (and eggs)*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestExcavationEvent.PippedLiveHatchlingsChanged(
                                    ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.noEmbryos,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.NoEmbryosChanged(input))
                        },
                        isError = state.value.invalidNoEmbryosMessage.isNotEmpty(),
                        errorMessage = state.value.invalidNoEmbryosMessage,
                        label = "No Embryos*",
                        onIconClick = { viewModel.sendEvent(NestExcavationEvent.NoEmbryosChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.deadEmbryos,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.DeadEmbryosChanged(input))
                        },
                        isError = state.value.invalidDeadEmbryosMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDeadEmbryosMessage,
                        label = "Dead Embryos*",
                        onIconClick = { viewModel.sendEvent(NestExcavationEvent.DeadEmbryosChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.liveEmbryos,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.LiveEmbryosChanged(input))
                        },
                        isError = state.value.invalidLiveEmbryosMessage.isNotEmpty(),
                        errorMessage = state.value.invalidLiveEmbryosMessage,
                        label = "Live Embryos*",
                        onIconClick = { viewModel.sendEvent(NestExcavationEvent.LiveEmbryosChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.deadHatchlingsNest,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.DeadHatchlingsNestChanged(input))
                        },
                        isError = state.value.invalidDeadHatchlingsNestMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDeadHatchlingsNestMessage,
                        label = "Dead Hatchlings in Nest*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestExcavationEvent.DeadHatchlingsNestChanged(
                                    ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.liveHatchlingsNest,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.LiveHatchlingsNestChanged(input))
                        },
                        isError = state.value.invalidLiveHatchlingsNestMessage.isNotEmpty(),
                        errorMessage = state.value.invalidLiveHatchlingsNestMessage,
                        label = "Live Hatchlings in Nest*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestExcavationEvent.LiveHatchlingsNestChanged(
                                    ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.commentsOrRemarks,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestExcavationEvent.CommentsOrRemarksChanged(input))
                        },
                        isError = state.value.invalidCommentsOrRemarksMessage.isNotEmpty(),
                        errorMessage = state.value.invalidCommentsOrRemarksMessage,
                        label = "Comments or Remarks",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestExcavationEvent.CommentsOrRemarksChanged(
                                    ""
                                )
                            )
                        },
                        imeAction = ImeAction.Done,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Save",
                        onClick = {
                            viewModel.sendEvent(NestExcavationEvent.SaveButtonClicked)
                            sharedViewModel.sendEvent(MorningSurveyEvent.NestExcavationAdded(state.value))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.hatchedEggs.isInt() &&
                                state.value.pippedDeadHatchlings.isInt() &&
                                state.value.pippedLiveHatchlings.isInt() &&
                                state.value.noEmbryos.isInt() &&
                                state.value.deadEmbryos.isInt() &&
                                state.value.liveEmbryos.isInt() &&
                                state.value.deadHatchlingsNest.isInt() &&
                                state.value.liveHatchlingsNest.isInt()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    LaunchedEffect(state.value.success) {
                        if (state.value.success) navigateBack()
                    }
                }
            }
        }
    )
}