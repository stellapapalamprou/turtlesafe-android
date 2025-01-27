package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingEvent
import com.spapalamprou.turtlesafe.features.nestHatching.NestHatchingViewModel
import com.spapalamprou.turtlesafe.ui.components.DateDialog
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.DropdownTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.IconTextField
import com.spapalamprou.turtlesafe.ui.components.LabelRoundButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestHatchingScreen(
    sharedViewModel: MorningSurveyViewModel,
    navigateBack: () -> Unit
) {
    val viewModel: NestHatchingViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    var isFirstDayHatchingDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val fistDayHatchingState: DatePickerState = rememberDatePickerState()

    var isLastDayHatchingDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val lastDayHatchingState: DatePickerState = rememberDatePickerState()

    val eventTypes: List<String> = listOf(
        "A: Predation Attempt", "E: Emergence",
        "I: Inundation", "P: Predation", "V: Vandalism", "W: Washed Nest", "O: Other"
    )

    LaunchedEffect(Unit) {
        viewModel.sendEvent(
            NestHatchingEvent.NestCodeChanged(sharedViewModel.state.value.nestCode)
        )
        viewModel.sendEvent(
            NestHatchingEvent.FirstDayOfHatchingSelected(sharedViewModel.state.value.date)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nest Hatching",
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
                        label = "Nest Code",
                        readOnly = true,
                        onIconClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    IconTextField(
                        text = state.value.firstDayHatching,
                        label = "First Day of Hatching",
                        isError = state.value.invalidFirstDayHatchingMessage.isNotEmpty(),
                        errorMessage = state.value.invalidFirstDayHatchingMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { isFirstDayHatchingDialogShown = true },
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Icon to select date"
                    )

                    if (isFirstDayHatchingDialogShown) {
                        DateDialog(
                            state = fistDayHatchingState,
                            onDismissRequest = { isFirstDayHatchingDialogShown = false },
                            onDismissButtonClick = { isFirstDayHatchingDialogShown = false },
                            onDateSelected = { selectedDate ->
                                viewModel.sendEvent(
                                    NestHatchingEvent.FirstDayOfHatchingSelected(
                                        selectedDate
                                    )
                                )
                                isFirstDayHatchingDialogShown = false
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    IconTextField(
                        text = state.value.lastDayHatching,
                        label = "Last Day of Hatching",
                        isError = state.value.invalidLastDayHatchingMessage.isNotEmpty(),
                        errorMessage = state.value.invalidLastDayHatchingMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onIconClick = { isLastDayHatchingDialogShown = true },
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Icon to select date"
                    )

                    if (isLastDayHatchingDialogShown) {
                        DateDialog(
                            state = lastDayHatchingState,
                            onDismissRequest = { isLastDayHatchingDialogShown = false },
                            onDismissButtonClick = { isLastDayHatchingDialogShown = false },
                            onDateSelected = { selectedDate ->
                                viewModel.sendEvent(
                                    NestHatchingEvent.LastDayOfHatchingSelected(
                                        selectedDate
                                    )
                                )
                                isLastDayHatchingDialogShown = false
                            })
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LabelRoundButton(
                        onClick = { viewModel.sendEvent(NestHatchingEvent.HatchEventAddButtonClicked) },
                        text = "Add Hatching Event",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    for (index in 0 until state.value.hatchingDataList.size) {

                        Column {

                            Spacer(
                                modifier = Modifier
                                    .height(if (index == 0) 24.dp else 12.dp)
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                            ) {

                                DropdownTextField(
                                    selectedValue = state.value.hatchingDataList[index].selectedHatchingEvent,
                                    options = eventTypes,
                                    label = "Event*",
                                    onValueChangedEvent = { selection ->
                                        viewModel.sendEvent(
                                            NestHatchingEvent.HatchEventTypeSelected(
                                                index = index,
                                                eventType = selection
                                            )
                                        )
                                    },
                                    isError = state.value.hatchingDataList[index].invalidHatchEventMessage.isNotEmpty(),
                                    errorMessage = state.value.hatchingDataList[index].invalidHatchEventMessage,
                                    modifier = Modifier
                                        .weight(2f)
                                        .align(alignment = Alignment.CenterVertically)
                                )

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                ) {

                                    FilledButton(
                                        buttonText = "Delete",
                                        onClick = {
                                            viewModel.sendEvent(
                                                NestHatchingEvent.DeleteHatchEventButtonClicked(
                                                    index = index
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .align(alignment = Alignment.CenterEnd),
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(if (state.value.hatchingDataList.size > 0) 12.dp else 24.dp))

                    DefaultTextField(
                        text = state.value.commentsOrRemarks,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestHatchingEvent.CommentsOrRemarksChanged(input))
                        },
                        isError = state.value.invalidCommentsOrRemarksMessage.isNotEmpty(),
                        errorMessage = state.value.invalidCommentsOrRemarksMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Comments or Remarks",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestHatchingEvent.CommentsOrRemarksChanged(
                                    ""
                                )
                            )
                        },
                        singleLine = false,
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Save",
                        onClick = {
                            viewModel.sendEvent(NestHatchingEvent.SaveButtonClicked)
                            sharedViewModel.sendEvent(MorningSurveyEvent.NestHatchingAdded(state.value))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.hatchingDataList.all { event ->
                            event.selectedHatchingEvent.isNotEmpty()
                        } && (state.value.firstDayHatching.isNotEmpty() ||
                                state.value.lastDayHatching.isNotEmpty() ||
                                state.value.hatchingDataList.isNotEmpty() ||
                                state.value.commentsOrRemarks.isNotEmpty())

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