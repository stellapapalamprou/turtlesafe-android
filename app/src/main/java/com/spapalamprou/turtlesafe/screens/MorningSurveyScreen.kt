package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.ui.components.DateDialog
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.DropdownTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.IconTextField
import com.spapalamprou.turtlesafe.ui.components.LabelRoundButton
import com.spapalamprou.turtlesafe.ui.components.LabelSwitch
import com.spapalamprou.turtlesafe.ui.components.TimeDialog
import com.spapalamprou.turtlesafe.validators.isInt
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MorningSurveyScreen(
    viewModel: MorningSurveyViewModel,
    navigateBack: () -> Unit,
    navigateToNewNest: () -> Unit,
    navigateToNestRelocation: () -> Unit,
    navigateToNestIncubation: () -> Unit,
    navigateToNestExcavation: () -> Unit,
    navigateToNestHatching: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    val datePickerState: DatePickerState = rememberDatePickerState()

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    val map: Map<String, List<String>> = hashMapOf(
        "ZAK" to listOf(),
        "KYP" to listOf(),
        "RET" to listOf(),
        "LAK" to listOf("Mavrovouni", "Selinitsa", "Valtaki", "Trinisa"),
        "CHA" to listOf(),
        "KOR" to listOf(),
        "NED" to listOf(),
        "MES" to listOf()
    )

    val sectors: List<String> = listOf("West", "East", "A", "B", "C", "H", "I", "K", "O")

    val subsectors: List<String> = listOf("As1200", "On1976")

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var isTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var isExistingNestBottomSheetShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val morningSurveySnackbarState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Morning Survey",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.testTag("morningSurveyScreen")
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
        snackbarHost = { SnackbarHost(morningSurveySnackbarState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column() {

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        IconTextField(
                            text = state.value.date,
                            label = "Date*",
                            isError = state.value.invalidDateMessage.isNotEmpty(),
                            errorMessage = state.value.invalidDateMessage,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically),
                            onIconClick = { isDateDialogShown = true },
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Icon to select date",
                            trailingIconTestTag = "date"
                        )

                        if (isDateDialogShown) {

                            DateDialog(
                                state = datePickerState,
                                onDismissRequest = { isDateDialogShown = false },
                                onDismissButtonClick = { isDateDialogShown = false },
                                onDateSelected = { selectedDate ->
                                    viewModel.sendEvent(MorningSurveyEvent.DateSelected(selectedDate))
                                    isDateDialogShown = false
                                }
                            )
                        }

                        Spacer(modifier = Modifier.width(24.dp))

                        IconTextField(
                            text = state.value.time,
                            label = "Time*",
                            isError = state.value.invalidTimeMessage.isNotEmpty(),
                            errorMessage = state.value.invalidTimeMessage,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically),
                            onIconClick = { isTimeDialogShown = true },
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = "Icon to select time",
                            trailingIconTestTag = "time"
                        )
                    }

                    if (isTimeDialogShown) {

                        TimeDialog(state = timePickerState,
                            onTimeSelected = { selectedTime ->
                                viewModel.sendEvent(MorningSurveyEvent.TimeSelected(selectedTime))
                                isTimeDialogShown = false
                            },
                            onDismiss = { isTimeDialogShown = false }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownTextField(
                        selectedValue = state.value.area,
                        options = map.keys.toList(),
                        label = "Area*",
                        onValueChangedEvent = { selection ->
                            viewModel.sendEvent(MorningSurveyEvent.AreaSelected(selection))
                        },
                        isError = state.value.invalidAreaMessage.isNotEmpty(),
                        errorMessage = state.value.invalidAreaMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .testTag("area"),
                        enabledDropdown = state.value.newNestList.isEmpty() &&
                                state.value.nestIncubationList.isEmpty() &&
                                state.value.nestRelocationList.isEmpty() &&
                                state.value.nestHatchingList.isEmpty() &&
                                state.value.nestExcavationList.isEmpty()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state.value.date.isNotEmpty() &&
                        state.value.time.isNotEmpty() &&
                        state.value.area.isNotEmpty()
                    ) {

                        DropdownTextField(
                            selectedValue = state.value.beach,
                            options = map.get(state.value.area) ?: listOf(),
                            label = "Beach*",
                            onValueChangedEvent = { selection ->
                                viewModel.sendEvent(MorningSurveyEvent.BeachChanged(selection))
                            },
                            isError = state.value.invalidBeachMessage.isNotEmpty(),
                            errorMessage = state.value.invalidBeachMessage,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .testTag("beach"),
                            enabledDropdown = state.value.newNestList.isEmpty() &&
                                    state.value.nestIncubationList.isEmpty() &&
                                    state.value.nestRelocationList.isEmpty() &&
                                    state.value.nestHatchingList.isEmpty() &&
                                    state.value.nestExcavationList.isEmpty()
                        )
                    }

                    if (state.value.beach.isNotEmpty()) {

                        Spacer(modifier = Modifier.height(12.dp))

                        LabelRoundButton(text = "New Nest",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            onClick = { navigateToNewNest() }
                        )

                        for (index in 0 until state.value.newNestList.size) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 24.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = state.value.newNestList[index].nestCode,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "Created",
                                    color = Color.LightGray
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        LabelRoundButton(text = "Existing Nest",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            buttonIcon = Icons.Outlined.Edit,
                            onClick = { isExistingNestBottomSheetShown = true }
                        )


                        if (isExistingNestBottomSheetShown) {
                            ModalBottomSheet(onDismissRequest = {
                                isExistingNestBottomSheetShown = false
                            }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .align(alignment = Alignment.CenterHorizontally)
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 24.dp,
                                                end = 24.dp,
                                                top = 24.dp,
                                                bottom = 12.dp
                                            )
                                    ) {

                                        DropdownTextField(
                                            selectedValue = state.value.sector,
                                            options = sectors,
                                            label = "Sector",
                                            onValueChangedEvent = { selection ->
                                                viewModel.sendEvent(
                                                    MorningSurveyEvent.SectorSelected(
                                                        selection
                                                    )
                                                )
                                            },
                                            isError = state.value.invalidSectorMessage.isNotEmpty(),
                                            errorMessage = state.value.invalidSectorMessage,
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(alignment = Alignment.CenterVertically)
                                        )

                                        Spacer(modifier = Modifier.width(24.dp))

                                        DropdownTextField(
                                            selectedValue = state.value.subsector,
                                            options = subsectors,
                                            label = "Subsector",
                                            onValueChangedEvent = { selection ->
                                                viewModel.sendEvent(
                                                    MorningSurveyEvent.SubsectorSelected(
                                                        selection
                                                    )
                                                )
                                            },
                                            isError = state.value.invalidSubsectorMessage.isNotEmpty(),
                                            errorMessage = state.value.invalidSubsectorMessage,
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(alignment = Alignment.CenterVertically)
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 24.dp, end = 24.dp, bottom = 12.dp)
                                    ) {

                                        DropdownTextField(
                                            selectedValue = state.value.nestCode,
                                            options = state.value.nestCodeOptions,
                                            label = "NestCode*",
                                            onValueChangedEvent = { selection ->
                                                viewModel.sendEvent(
                                                    MorningSurveyEvent.NestCodeSelected(
                                                        selection
                                                    )
                                                )
                                            },
                                            isError = state.value.invalidNestCodeMessage.isNotEmpty(),
                                            errorMessage = state.value.invalidNestCodeMessage,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(24.dp))

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 24.dp,
                                                end = 24.dp,
                                                bottom = 24.dp,
                                                top = 12.dp
                                            )
                                    ) {

                                        FilledButton(
                                            buttonText = "Incubation",
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(alignment = Alignment.CenterVertically),
                                            onClick = {
                                                isExistingNestBottomSheetShown = false
                                                navigateToNestIncubation()
                                            },
                                            enabled = state.value.nestCode.isNotEmpty()
                                        )

                                        Spacer(modifier = Modifier.width(24.dp))

                                        FilledButton(
                                            buttonText = "Relocation",
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(alignment = Alignment.CenterVertically),
                                            onClick = {
                                                isExistingNestBottomSheetShown = false
                                                navigateToNestRelocation()
                                            },
                                            enabled = state.value.nestCode.isNotEmpty()
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 24.dp, end = 24.dp, top = 12.dp)
                                    ) {

                                        FilledButton(
                                            buttonText = "Hatching",
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(alignment = Alignment.CenterVertically),
                                            onClick = {
                                                isExistingNestBottomSheetShown = false
                                                navigateToNestHatching()
                                            },
                                            enabled = state.value.nestCode.isNotEmpty()
                                        )

                                        Spacer(modifier = Modifier.width(24.dp))

                                        FilledButton(
                                            buttonText = "Excavation",
                                            modifier = Modifier
                                                .weight(1f)
                                                .align(alignment = Alignment.CenterVertically),
                                            onClick = {
                                                isExistingNestBottomSheetShown = false
                                                navigateToNestExcavation()
                                            },
                                            enabled = state.value.nestCode.isNotEmpty()
                                        )
                                    }
                                }
                            }
                        }

                        for (index in 0 until state.value.nestIncubationList.size) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 24.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = state.value.nestIncubationList[index].nestCode,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "Incubation\nUpdated",
                                    textAlign = TextAlign.End,
                                    color = Color.LightGray
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    )
                            )
                        }

                        for (index in 0 until state.value.nestRelocationList.size) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 24.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = state.value.nestRelocationList[index].oldNestCode,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "Relocation\nUpdated",
                                    textAlign = TextAlign.End,
                                    color = Color.LightGray
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    )
                            )
                        }

                        for (index in 0 until state.value.nestHatchingList.size) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 24.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = state.value.nestHatchingList[index].nestCode,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "Hatching\nUpdated",
                                    textAlign = TextAlign.End,
                                    color = Color.LightGray
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    )
                            )
                        }

                        for (index in 0 until state.value.nestExcavationList.size) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 24.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = state.value.nestExcavationList[index].nestCode,
                                    modifier = Modifier
                                )

                                Text(
                                    text = "Excavation\nUpdated",
                                    textAlign = TextAlign.End,
                                    color = Color.LightGray
                                )
                            }

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 0.dp,
                                        bottom = 0.dp
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        LabelSwitch(
                            onCheckedChange = {
                                viewModel.sendEvent(
                                    MorningSurveyEvent.NestingAttemptSwitchChecked(
                                        !state.value.nestingAttemptSwitch
                                    )
                                )
                            },
                            text = "Nesting Attempt",
                            subText = "Successful nests don't count as attempts",
                            switchChecked = state.value.nestingAttemptSwitch,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        if (state.value.nestingAttemptSwitch) {
                            DefaultTextField(
                                text = state.value.numberOfAttempts,
                                onValueChange = { input ->
                                    viewModel.sendEvent(
                                        MorningSurveyEvent.NumberOfAttemptsChanged(
                                            input
                                        )
                                    )
                                },
                                label = "Number of Attempts*",
                                isError = state.value.invalidNumberOfAttemptsMessage.isNotEmpty(),
                                errorMessage = state.value.invalidNumberOfAttemptsMessage,
                                onIconClick = {
                                    viewModel.sendEvent(
                                        MorningSurveyEvent.NumberOfAttemptsChanged(
                                            ""
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                        }

                        DefaultTextField(
                            text = state.value.commentsOrRemarks,
                            onValueChange = { input ->
                                viewModel.sendEvent(
                                    MorningSurveyEvent.CommentsOrRemarksChanged(
                                        input
                                    )
                                )
                            },
                            label = "Comments or Remarks",
                            onIconClick = {
                                viewModel.sendEvent(
                                    MorningSurveyEvent.CommentsOrRemarksChanged(
                                        ""
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            imeAction = ImeAction.Done
                        )

                        Spacer(modifier = Modifier.height(52.dp))

                        FilledButton(
                            buttonText = "Submit",
                            onClick = { viewModel.sendEvent(MorningSurveyEvent.SubmitButtonClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .testTag("submitButton"),
                            enabled = state.value.date.isNotEmpty() &&
                                    state.value.time.isNotEmpty() &&
                                    state.value.area.isNotEmpty() &&
                                    state.value.beach.isNotEmpty() &&
                                    if (state.value.nestingAttemptSwitch) {
                                        state.value.numberOfAttempts.isInt()
                                    } else true
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        LaunchedEffect(state.value.success) {
                            if (state.value.success) {
                                morningSurveySnackbarState.showSnackbar("Survey successfully submitted ✔\uFE0F")
                                navigateBack()
                            }
                        }

                        LaunchedEffect(state.value.exception) {
                            if (state.value.exception != null) {
                                morningSurveySnackbarState.showSnackbar("Survey could not be submitted")
                            }
                        }

                        LaunchedEffect(state.value.nestCodeException) {
                            if (state.value.nestCodeException != null) {
                                morningSurveySnackbarState.showSnackbar("Unable to retrieve nest codes")
                            }
                        }
                    }
                }
            }
        }
    )
}