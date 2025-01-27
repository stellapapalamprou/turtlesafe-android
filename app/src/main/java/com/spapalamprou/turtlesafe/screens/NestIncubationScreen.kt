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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationEvent
import com.spapalamprou.turtlesafe.features.nestIncubation.NestIncubationViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.DropdownTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.LabelRoundButton
import com.spapalamprou.turtlesafe.ui.components.LabelSwitch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestIncubationScreen(
    sharedViewModel: MorningSurveyViewModel,
    navigateBack: () -> Unit
) {
    val viewModel: NestIncubationViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    val nestLocations: List<String> = listOf("H: Hatchery", "BoB: Back of Beach")

    val eventTypes: List<String> = listOf(
        "A: Predation Attempt", "E: Emergence",
        "I: Inundation", "P: Predation", "V: Vandalism", "W: Washed Nest", "O: Other"
    )
    val protectionTypes: List<String> = listOf(
        "A(d): Nest shading (Alleyway)",
        "A(h): Nest fencing (Alleyway)",
        "B: Boxed nest",
        "C(b): Cage (bamboo)",
        "C(i): Cage (iron)",
        "G(d): Guard (day)",
        "G(n#): Guard (night)",
        "H: Hatchery nest",
        "R: Relocated nest",
        "T: Red/White tape",
        "O: Other"
    )

    LaunchedEffect(Unit) {
        viewModel.sendEvent(NestIncubationEvent.NestCodeChanged(sharedViewModel.state.value.nestCode))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nest Incubation",
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

                    DropdownTextField(
                        selectedValue = state.value.nestLocation,
                        options = nestLocations,
                        label = "Nest Location*",
                        onValueChangedEvent = { selection ->
                            viewModel.sendEvent(NestIncubationEvent.NestLocationSelected(selection))
                        },
                        isError = state.value.invalidNestLocationMessage.isNotEmpty(),
                        errorMessage = state.value.invalidNestLocationMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    if (state.value.nestLocation.equals("H: Hatchery")) {

                        Spacer(modifier = Modifier.height(12.dp))

                        DefaultTextField(
                            text = state.value.hatcheryCode,
                            onValueChange = { input ->
                                viewModel.sendEvent(NestIncubationEvent.HatcheryCodeChanged(input))
                            },
                            isError = state.value.invalidHatcheryCodeMessage.isNotEmpty(),
                            errorMessage = state.value.invalidHatcheryCodeMessage,
                            label = "Hatchery Code*",
                            onIconClick = {
                                viewModel.sendEvent(
                                    NestIncubationEvent.HatcheryCodeChanged(
                                        ""
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            imeAction = ImeAction.Done
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LabelRoundButton(
                        onClick = { viewModel.sendEvent(NestIncubationEvent.IncEventAddButtonClicked) },
                        text = "Add Incubation Event",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    for (index in 0 until state.value.incubationDataList.size) {

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
                                    selectedValue = state.value.incubationDataList[index].selectedIncubationEvent,
                                    options = eventTypes,
                                    label = "Event*",
                                    onValueChangedEvent = { selection ->
                                        viewModel.sendEvent(
                                            NestIncubationEvent.IncEventTypeSelected(
                                                index = index,
                                                eventType = selection
                                            )
                                        )
                                    },
                                    isError = state.value.incubationDataList[index].invalidIncEventMessage.isNotEmpty(),
                                    errorMessage = state.value.incubationDataList[index].invalidIncEventMessage,
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
                                                NestIncubationEvent.DeleteIncEventButtonClicked(
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

                    Spacer(modifier = Modifier.height(if (state.value.incubationDataList.size > 0) 12.dp else 24.dp))

                    LabelSwitch(
                        onCheckedChange = {
                            viewModel.sendEvent(
                                NestIncubationEvent.ProtectedNestSwitchChecked(
                                    !state.value.protectedNestSwitch
                                )
                            )
                        },
                        text = "Protected Nest",
                        subText = "Switch on for new protection measures",
                        switchChecked = state.value.protectedNestSwitch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (state.value.protectedNestSwitch) {

                        Spacer(modifier = Modifier.height(12.dp))

                        DropdownTextField(
                            selectedValue = state.value.protectionMeasures,
                            options = protectionTypes,
                            label = "Protection Measures*",
                            onValueChangedEvent = { selection ->
                                viewModel.sendEvent(
                                    NestIncubationEvent.ProtectionMeasuresSelected(
                                        selection
                                    )
                                )
                            },
                            isError = state.value.invalidProtectionMeasuresMessage.isNotEmpty(),
                            errorMessage = state.value.invalidProtectionMeasuresMessage,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.commentsOrRemarks,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestIncubationEvent.CommentsOrRemarksChanged(input))
                        },
                        isError = state.value.invalidCommentsOrRemarksMessage.isNotEmpty(),
                        errorMessage = state.value.invalidCommentsOrRemarksMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Comments or Remarks",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestIncubationEvent.CommentsOrRemarksChanged(
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
                            viewModel.sendEvent(NestIncubationEvent.SaveButtonClicked)
                            sharedViewModel.sendEvent(MorningSurveyEvent.NestIncubationAdded(state.value))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.nestLocation.isNotEmpty() &&
                                if (state.value.nestLocation.equals("H: Hatchery")) {
                                    state.value.hatcheryCode.isNotEmpty()
                                } else true &&
                                        state.value.incubationDataList.all { event ->
                                            event.selectedIncubationEvent.isNotEmpty()
                                        } &&
                                        if (state.value.protectedNestSwitch) {
                                            state.value.protectionMeasures.isNotEmpty()
                                        } else true
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