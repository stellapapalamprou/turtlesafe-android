package com.spapalamprou.turtlesafe.screens

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationEvent
import com.spapalamprou.turtlesafe.features.nestRelocation.NestRelocationViewModel
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.DropdownTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.validators.isDouble
import com.spapalamprou.turtlesafe.validators.isInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestRelocationScreen(
    sharedViewModel: MorningSurveyViewModel,
    navigateBack: () -> Unit
) {
    val viewModel: NestRelocationViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    val relocationOptions: List<String> = listOf("H: Hatchery", "BoB: Back of Beach")

    val sectors: List<String> = listOf("West", "East", "A", "B", "C", "H", "I", "K", "O")

    val subsectors: List<String> = listOf("As1200", "On1976")

    val relocationReasons: List<String> = listOf(
        "I: Inundation", "T: Trampling",
        "R: Root Invasion", "O: Other"
    )

    LaunchedEffect(Unit) {
        viewModel.sendEvent(NestRelocationEvent.OldNestCodeChanged(sharedViewModel.state.value.nestCode))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nest Relocation",
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
                        text = state.value.oldNestCode,
                        onValueChange = {},
                        label = "Old Nest Code",
                        readOnly = true,
                        onIconClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.newNestCode,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestRelocationEvent.NewNestCodeChanged(input))
                        },
                        isError = state.value.invalidNewNestCodeMessage.isNotEmpty(),
                        errorMessage = state.value.invalidNewNestCodeMessage,
                        label = "New Nest Code*",
                        onIconClick = { viewModel.sendEvent(NestRelocationEvent.NewNestCodeChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownTextField(
                        selectedValue = state.value.relocatedTo,
                        options = relocationOptions,
                        label = "Relocated to*",
                        onValueChangedEvent = { selection ->
                            viewModel.sendEvent(NestRelocationEvent.RelocatedToSelected(selection))
                        },
                        isError = state.value.invalidRelocatedToMessage.isNotEmpty(),
                        errorMessage = state.value.invalidRelocatedToMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    if (state.value.relocatedTo.equals("BoB: Back of Beach")) {

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        ) {

                            DropdownTextField(
                                selectedValue = state.value.sector,
                                options = sectors,
                                label = "Sector*",
                                onValueChangedEvent = { selection ->
                                    viewModel.sendEvent(NestRelocationEvent.SectorSelected(selection))
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
                                label = "Subsector*",
                                onValueChangedEvent = { selection ->
                                    viewModel.sendEvent(
                                        NestRelocationEvent.SubsectorSelected(
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
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownTextField(
                        selectedValue = state.value.reasonForRelocation,
                        options = relocationReasons,
                        label = "Reason for Relocation*",
                        onValueChangedEvent = { selection ->
                            viewModel.sendEvent(
                                NestRelocationEvent.ReasonForRelocationSelected(
                                    selection
                                )
                            )
                        },
                        isError = state.value.invalidReasonForRelocationMessage.isNotEmpty(),
                        errorMessage = state.value.invalidReasonForRelocationMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.depthTopEgg,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestRelocationEvent.DepthTopEggChanged(input))
                        },
                        isError = state.value.invalidDepthTopEggMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDepthTopEggMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Depth to Top Egg - h*",
                        onIconClick = { viewModel.sendEvent(NestRelocationEvent.DepthTopEggChanged("")) },
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.depthBottomNest,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestRelocationEvent.DepthToBottomNestChanged(input))
                        },
                        isError = state.value.invalidDepthBottomNestMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDepthBottomNestMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Depth to Bottom of Nests - H*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestRelocationEvent.DepthToBottomNestChanged(
                                    ""
                                )
                            )
                        },
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.distanceToSea,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestRelocationEvent.DistanceToSeaChanged(input))
                        },
                        isError = state.value.invalidDistanceToSeaMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDistanceToSeaMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Distance to Sea (m)*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestRelocationEvent.DistanceToSeaChanged(
                                    ""
                                )
                            )
                        },
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.numOfEggsTransplanted,
                        onValueChange = { input ->
                            viewModel.sendEvent(
                                NestRelocationEvent.NumOfEggsTransplantedChanged(
                                    input
                                )
                            )
                        },
                        isError = state.value.invalidNumOfEggsTransplanted.isNotEmpty(),
                        errorMessage = state.value.invalidNumOfEggsTransplanted,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Number of Eggs Transplanted*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestRelocationEvent.NumOfEggsTransplantedChanged(
                                    ""
                                )
                            )
                        },
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.commentsOrRemarks,
                        onValueChange = { input ->
                            viewModel.sendEvent(NestRelocationEvent.CommentsOrRemarksChanged(input))
                        },
                        isError = state.value.invalidCommentsOrRemarksMessage.isNotEmpty(),
                        errorMessage = state.value.invalidCommentsOrRemarksMessage,
                        label = "Comments or Remarks",
                        onIconClick = {
                            viewModel.sendEvent(
                                NestRelocationEvent.CommentsOrRemarksChanged(
                                    ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        imeAction = ImeAction.Done,
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Save",
                        onClick = {
                            viewModel.sendEvent(NestRelocationEvent.SaveButtonClicked)
                            sharedViewModel.sendEvent(MorningSurveyEvent.NestRelocationAdded(state.value))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.newNestCode.isNotEmpty() &&
                                state.value.relocatedTo.isNotEmpty() &&
                                state.value.reasonForRelocation.isNotEmpty() &&
                                if (state.value.reasonForRelocation.equals("BoB: Back of Beach")) {
                                    state.value.sector.isNotEmpty() && state.value.subsector.isNotEmpty()
                                } else true &&
                                        state.value.depthTopEgg.isDouble() &&
                                        state.value.depthBottomNest.isDouble() &&
                                        state.value.distanceToSea.isDouble() &&
                                        state.value.numOfEggsTransplanted.isInt()
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