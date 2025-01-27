package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.features.nightSurvey.NightSurveyEvent
import com.spapalamprou.turtlesafe.features.nightSurvey.NightSurveyViewModel
import com.spapalamprou.turtlesafe.ui.components.DateDialog
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.DropdownTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.IconTextField
import com.spapalamprou.turtlesafe.ui.components.LabelRoundButton
import com.spapalamprou.turtlesafe.ui.components.LabelSwitch
import com.spapalamprou.turtlesafe.ui.components.TextDivider
import com.spapalamprou.turtlesafe.ui.components.TimeDialog
import com.spapalamprou.turtlesafe.validators.isDouble
import com.spapalamprou.turtlesafe.validators.isInt
import com.spapalamprou.turtlesafe.validators.isName
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NightSurveyScreen(
    navigateBack: () -> Unit
) {
    val viewModel: NightSurveyViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val datePickerState: DatePickerState = rememberDatePickerState()

    val areas: List<String> = listOf("ZAK", "KYP", "RET", "LAK", "CHA", "KOR", "NED", "MES")

    val sectors: List<String> = listOf("West", "East", "A", "B", "C", "H", "I", "K", "O")

    val subsectors: List<String> = listOf("As1200", "On1976")

    val tagLocations: List<String> = listOf(
        "FL: Front Left Flipper",
        "FR: Front Right Flipper",
        "HL: Hind Left Flipper",
        "HR: Hind Right Flipper"
    )
    val newScarTypes: List<String> = listOf(
        "Hole", "Rip"
    )
    var isDamageLocatorDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val currentTime = Calendar.getInstance()

    var isLayingTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val layingTimePickerState: TimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    var isCoverTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val coverTimePickerState: TimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    var isCamouflageTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val camouflageTimePickerState: TimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    var isDepartNestTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val departNestTimePickerState: TimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    var isTurtleAtSeaTimeDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val turtleAtSeaTimePickerState: TimePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )
    val nightSurveySnackbarState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Night Survey",
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
        snackbarHost = { SnackbarHost(nightSurveySnackbarState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column {

                    Spacer(modifier = Modifier.height(24.dp))

                    IconTextField(
                        text = state.value.date,
                        label = "Date*",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        isError = state.value.invalidDateMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDateMessage,
                        onIconClick = { isDateDialogShown = true },
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Icon to select date"
                    )

                    if (isDateDialogShown) {
                        DateDialog(
                            state = datePickerState,
                            onDismissRequest = { isDateDialogShown = false },
                            onDismissButtonClick = { isDateDialogShown = false },
                            onDateSelected = { selectedDate ->
                                viewModel.sendEvent(NightSurveyEvent.DateSelected(selectedDate))
                                isDateDialogShown = false
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownTextField(
                        selectedValue = state.value.area,
                        options = areas,
                        label = "Area*",
                        isError = state.value.invalidAreaMessage.isNotEmpty(),
                        errorMessage = state.value.invalidAreaMessage,
                        onValueChangedEvent = { selection ->
                            viewModel.sendEvent(NightSurveyEvent.AreaSelected(selection))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.beach,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.BeachChanged(input))
                        },
                        isError = state.value.invalidBeachMessage.isNotEmpty(),
                        errorMessage = state.value.invalidBeachMessage,
                        label = "Beach*",
                        onIconClick = { viewModel.sendEvent(NightSurveyEvent.BeachChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        imeAction = ImeAction.Done
                    )

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
                                viewModel.sendEvent(NightSurveyEvent.SectorSelected(selection))
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
                                viewModel.sendEvent(NightSurveyEvent.SubsectorSelected(selection))
                            },
                            isError = state.value.invalidSubsectorMessage.isNotEmpty(),
                            errorMessage = state.value.invalidSubsectorMessage,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        )
                    }

                    TextDivider(
                        text = "Tagging",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                    )

                    DefaultTextField(
                        text = state.value.tagger,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.TaggerChanged(input))
                        },
                        isError = state.value.invalidTaggerMessage.isNotEmpty(),
                        errorMessage = state.value.invalidTaggerMessage,
                        label = "Tagger*",
                        onIconClick = { viewModel.sendEvent(NightSurveyEvent.TaggerChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LabelRoundButton(text = "Add Old Tag",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onClick = { viewModel.sendEvent(NightSurveyEvent.AddOldTagButtonClicked) })

                    for (index in 0 until state.value.oldTagDataList.size) {

                        Column {

                            TextDivider(
                                text = "Old Tag ${index + 1}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 24.dp,
                                        bottom = 24.dp,
                                        start = 24.dp,
                                        end = 24.dp
                                    )
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            ) {

                                DropdownTextField(
                                    selectedValue = state.value.oldTagDataList[index].selectedOldTagLocation,
                                    options = tagLocations,
                                    label = "Tag Location*",
                                    onValueChangedEvent = { selection ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.OldTagLocationSelected(
                                                index = index,
                                                tagLocation = selection
                                            )
                                        )
                                    },
                                    isError = state.value.oldTagDataList[index].invalidOldTagLocationMessage.isNotEmpty(),
                                    errorMessage = state.value.oldTagDataList[index].invalidOldTagLocationMessage,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                )

                                Spacer(modifier = Modifier.width(24.dp))

                                DefaultTextField(
                                    text = state.value.oldTagDataList[index].oldTagCode,
                                    onValueChange = { input ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.OldTagCodeChanged(
                                                index = index,
                                                tagCode = input
                                            )
                                        )
                                    },
                                    isError = state.value.oldTagDataList[index].invalidOldTagCodeMessage.isNotEmpty(),
                                    errorMessage = state.value.oldTagDataList[index].invalidOldTagCodeMessage,
                                    label = "Tag Code*",
                                    onIconClick = {
                                        viewModel.sendEvent(
                                            NightSurveyEvent.OldTagCodeChanged(
                                                index = index,
                                                tagCode = ""
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically),
                                    imeAction = ImeAction.Done
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            LabelSwitch(
                                onCheckedChange = {
                                    viewModel.sendEvent(
                                        NightSurveyEvent.TurtleSafeTagSwitchChecked(
                                            index = index,
                                            switch = !state.value.oldTagDataList[index].isTurtleSafeSwitchChecked
                                        )
                                    )
                                },
                                text = "TurtleSafe Tag",
                                subText = "Switch off in case this is not an TurtleSafe tag",
                                switchChecked = state.value.oldTagDataList[index].isTurtleSafeSwitchChecked,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            )

                            if (!state.value.oldTagDataList[index].isTurtleSafeSwitchChecked) {

                                Spacer(modifier = Modifier.height(24.dp))

                                DefaultTextField(
                                    text = state.value.oldTagDataList[index].tagNotes,
                                    onValueChange = { input ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.TagNotesChanged(
                                                index = index,
                                                tagNotes = input
                                            )
                                        )
                                    },
                                    isError = state.value.oldTagDataList[index].invalidTagNotesMessage.isNotEmpty(),
                                    errorMessage = state.value.oldTagDataList[index].invalidTagNotesMessage,
                                    label = "Tag Notes",
                                    onIconClick = {
                                        viewModel.sendEvent(
                                            NightSurveyEvent.TagNotesChanged(
                                                index = index,
                                                tagNotes = ""
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    imeAction = ImeAction.Done,
                                    singleLine = false
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = if (state.value.oldTagDataList[index].isTurtleSafeSwitchChecked) 24.dp else 12.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 12.dp
                                    )
                            ) {

                                FilledButton(
                                    buttonText = "Delete",
                                    onClick = {
                                        viewModel.sendEvent(
                                            NightSurveyEvent.DeleteOldTagButtonClicked(
                                                index = index
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        if (state.value.oldTagDataList.isNotEmpty() && index == state.value.oldTagDataList.size - 1) {

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 24.dp,
                                        bottom = 12.dp
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    LabelRoundButton(text = "Add New Tag",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onClick = { viewModel.sendEvent(NightSurveyEvent.AddNewTagButtonClicked) }
                    )

                    for (index in 0 until state.value.newTagDataList.size) {

                        Column {

                            TextDivider(
                                text = "New Tag ${index + 1}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 24.dp,
                                        bottom = 24.dp
                                    )
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                            ) {

                                DropdownTextField(
                                    selectedValue = state.value.newTagDataList[index].selectedNewTagLocation,
                                    options = tagLocations,
                                    label = "Tag Location*",
                                    onValueChangedEvent = { selection ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.NewTagLocationSelected(
                                                index = index,
                                                tagLocation = selection
                                            )
                                        )
                                    },
                                    isError = state.value.newTagDataList[index].invalidNewTagLocationMessage.isNotEmpty(),
                                    errorMessage = state.value.newTagDataList[index].invalidNewTagLocationMessage,
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically)
                                )

                                Spacer(modifier = Modifier.width(24.dp))

                                DefaultTextField(
                                    text = state.value.newTagDataList[index].newTagCode,
                                    onValueChange = { input ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.NewTagCodeChanged(
                                                index = index,
                                                tagCode = input
                                            )
                                        )
                                    },
                                    isError = state.value.newTagDataList[index].invalidNewTagCodeMessage.isNotEmpty(),
                                    errorMessage = state.value.newTagDataList[index].invalidNewTagCodeMessage,
                                    label = "Tag Code*",
                                    onIconClick = {
                                        viewModel.sendEvent(
                                            NightSurveyEvent.NewTagCodeChanged(
                                                index = index,
                                                tagCode = ""
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(alignment = Alignment.CenterVertically),
                                    imeAction = ImeAction.Done
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            LabelSwitch(
                                onCheckedChange = {
                                    viewModel.sendEvent(
                                        NightSurveyEvent.TaggingSuccessfulSwitchChecked(
                                            index = index,
                                            switch = !state.value.newTagDataList[index].isTagSwitchChecked
                                        )
                                    )
                                },
                                text = "Tagging Successful",
                                switchChecked = state.value.newTagDataList[index].isTagSwitchChecked,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 24.dp, end = 24.dp)
                            )

                            if (!state.value.newTagDataList[index].isTagSwitchChecked) {

                                Spacer(modifier = Modifier.height(24.dp))

                                DropdownTextField(
                                    selectedValue = state.value.newTagDataList[index].selectedNewScarType,
                                    options = newScarTypes,
                                    label = "New Scar Type",
                                    onValueChangedEvent = { selection ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.NewScarTypeSelected(
                                                index = index,
                                                scarType = selection
                                            )
                                        )
                                    },
                                    isError = state.value.newTagDataList[index].invalidNewScarTypeMesssage.isNotEmpty(),
                                    errorMessage = state.value.newTagDataList[index].invalidNewScarTypeMesssage,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                DefaultTextField(
                                    text = state.value.newTagDataList[index].newScarLocation,
                                    onValueChange = { input ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.NewScarLocationChanged(
                                                index = index,
                                                scarLocation = input
                                            )
                                        )
                                    },
                                    isError = state.value.newTagDataList[index].invalidNewScarLocationMessage.isNotEmpty(),
                                    errorMessage = state.value.newTagDataList[index].invalidNewScarLocationMessage,
                                    label = "New Scar Location",
                                    onIconClick = {
                                        viewModel.sendEvent(
                                            NightSurveyEvent.NewScarLocationChanged(
                                                index = index,
                                                scarLocation = ""
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 24.dp),
                                    imeAction = ImeAction.Done
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = if (state.value.newTagDataList[index].isTagSwitchChecked) 24.dp else 12.dp,
                                        start = 24.dp,
                                        end = 24.dp,
                                        bottom = 12.dp
                                    )
                            ) {

                                FilledButton(
                                    buttonText = "Delete",
                                    onClick = {
                                        viewModel.sendEvent(
                                            NightSurveyEvent.DeleteNewTagButtonClicked(
                                                index = index
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .align(alignment = Alignment.Center),
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        if (state.value.newTagDataList.isNotEmpty() && index == state.value.newTagDataList.size - 1) {

                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 24.dp,
                                        end = 24.dp,
                                        top = 24.dp,
                                        bottom = 12.dp
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        text = state.value.lostTagScars,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.LostTagScarsChanged(input))
                        },
                        isError = state.value.invalidLostTagScarsMessage.isNotEmpty(),
                        errorMessage = state.value.invalidLostTagScarsMessage,
                        label = "Lost Tag Scars",
                        onIconClick = { viewModel.sendEvent(NightSurveyEvent.LostTagScarsChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    TextDivider(
                        text = "Measurements",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
                    )

                    DefaultTextField(
                        text = state.value.straightCarapaceLength,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.StraightCarapaceLengthChanged(input))
                        },
                        isError = state.value.invalidStraightCarapaceLengthMessage.isNotEmpty(),
                        errorMessage = state.value.invalidStraightCarapaceLengthMessage,
                        label = "Straight Carapace Length*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NightSurveyEvent.StraightCarapaceLengthChanged(
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
                        text = state.value.straightCarapaceWidth,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.StraightCarapaceWidthChanged(input))
                        },
                        isError = state.value.invalidStraightCarapaceWidthMessage.isNotEmpty(),
                        errorMessage = state.value.invalidStraightCarapaceWidthMessage,
                        label = "Straight Carapace Width*",
                        onIconClick = {
                            viewModel.sendEvent(
                                NightSurveyEvent.StraightCarapaceWidthChanged(
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
                        text = state.value.curvedCarapaceLength,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceLengthChanged(input))
                        },
                        isError = state.value.invalidCurvedCarapaceLengthMessage.isNotEmpty(),
                        label = "Curved Carapace Length*",
                        onIconClick = {
                            viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceLengthChanged(""))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.curvedCarapaceWidth,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceWidthChanged(input))
                        },
                        isError = state.value.invalidCurvedCarapaceWidthMessage.isNotEmpty(),
                        label = "Curved Carapace Width*",
                        onIconClick = {
                            viewModel.sendEvent(NightSurveyEvent.CurvedCarapaceWidthChanged(""))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LabelRoundButton(
                        text = "Scute & Damage Locator",
                        subText = "Sea turtle bodypart codes",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        onClick = { isDamageLocatorDialogShown = true },
                        buttonIcon = Icons.Outlined.Info
                    )

                    if (isDamageLocatorDialogShown) {
                        Dialog(onDismissRequest = { isDamageLocatorDialogShown = false }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.turtle_body),
                                contentDescription = "Shows codes of turtle bodyparts",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(32.dp))
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        text = state.value.damageFlippersHead,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.DamageFlippersHeadChanged(input))
                        },
                        label = "Damage to Flippers and Head",
                        isError = state.value.invalidDamageFlippersHeadMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDamageFlippersHeadMessage,
                        onIconClick = {
                            viewModel.sendEvent(
                                NightSurveyEvent.DamageFlippersHeadChanged(
                                    ""
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.damageCarapace,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.DamageCarapaceChanged(input))
                        },
                        isError = state.value.invalidDamageCarapaceMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDamageCarapaceMessage,
                        label = "Damage to Carapace",
                        onIconClick = {
                            viewModel.sendEvent(NightSurveyEvent.DamageCarapaceChanged(""))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        singleLine = false,
                        imeAction = ImeAction.Done
                    )

                    TextDivider(
                        text = "Nest",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
                    )

                    LabelSwitch(
                        onCheckedChange = {
                            viewModel.sendEvent(
                                NightSurveyEvent.NestingEmergenceSwitchChecked(
                                    switch = !state.value.nestEmergenceSwitch
                                )
                            )
                        },
                        text = "Nesting Emergence",
                        switchChecked = state.value.nestEmergenceSwitch,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    if (state.value.nestEmergenceSwitch) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        ) {

                            Spacer(modifier = Modifier.height(24.dp))

                            DefaultTextField(
                                text = state.value.nestCode,
                                onValueChange = { input ->
                                    viewModel.sendEvent(NightSurveyEvent.NestCodeChanged(input))
                                },
                                isError = state.value.invalidNestCodeMessage.isNotEmpty(),
                                errorMessage = state.value.invalidNestCodeMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Nest Code*",
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.NestCodeChanged(""))
                                })

                            Spacer(modifier = Modifier.height(12.dp))

                            DefaultTextField(
                                text = state.value.depthTopEgg,
                                onValueChange = { input ->
                                    viewModel.sendEvent(NightSurveyEvent.DepthTopEggChanged(input))
                                },
                                isError = state.value.invalidDepthTopEggMessage.isNotEmpty(),
                                errorMessage = state.value.invalidDepthTopEggMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Depth to Top Egg - h*",
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.DepthTopEggChanged(""))
                                },
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DefaultTextField(
                                text = state.value.depthBottomNests,
                                onValueChange = { input ->
                                    viewModel.sendEvent(
                                        NightSurveyEvent.DepthToBottomNestsChanged(
                                            input
                                        )
                                    )
                                },
                                isError = state.value.invalidDepthBottomNestMessage.isNotEmpty(),
                                errorMessage = state.value.invalidDepthBottomNestMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Depth to Bottom of Nests - H*",
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.DepthToBottomNestsChanged(""))
                                },
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DefaultTextField(
                                text = state.value.distanceToSea,
                                onValueChange = { input ->
                                    viewModel.sendEvent(NightSurveyEvent.DistanceToSeaChanged(input))
                                },
                                isError = state.value.invalidDistanceToSeaMessage.isNotEmpty(),
                                errorMessage = state.value.invalidDistanceToSeaMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Distance to Sea (m)*",
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.DistanceToSeaChanged(""))
                                },
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DefaultTextField(
                                text = state.value.numEggsRelocated,
                                onValueChange = { input ->
                                    viewModel.sendEvent(
                                        NightSurveyEvent.NumOfEggsRelocatedChanged(
                                            input
                                        )
                                    )
                                },
                                isError = state.value.invalidNumEggsRelocatedMessage.isNotEmpty(),
                                errorMessage = state.value.invalidNumEggsRelocatedMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Number of Eggs (reloc)*",
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.NumOfEggsRelocatedChanged(""))
                                },
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DefaultTextField(
                                text = state.value.relocationComments,
                                onValueChange = { input ->
                                    viewModel.sendEvent(
                                        NightSurveyEvent.RelocationCommentsChanged(
                                            input
                                        )
                                    )
                                },
                                isError = state.value.invalidRelocationCommentsMessage.isNotEmpty(),
                                errorMessage = state.value.invalidRelocationCommentsMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Relocation Comments",
                                singleLine = false,
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.RelocationCommentsChanged(""))
                                }
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DefaultTextField(
                                text = state.value.numEggsExcavated,
                                onValueChange = { input ->
                                    viewModel.sendEvent(
                                        NightSurveyEvent.NumOfEggsExcavatedChanged(
                                            input
                                        )
                                    )
                                },
                                isError = state.value.invalidNumEggsExcavatedMessage.isNotEmpty(),
                                errorMessage = state.value.invalidNumEggsExcavatedMessage,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                label = "Number of Eggs (exc)*",
                                onIconClick = {
                                    viewModel.sendEvent(NightSurveyEvent.NumOfEggsExcavatedChanged(""))
                                },
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            IconTextField(
                                text = state.value.startLaying,
                                label = "Start Laying",
                                modifier = Modifier.fillMaxWidth(),
                                isError = state.value.invalidStartLayingMessage.isNotEmpty(),
                                errorMessage = state.value.invalidStartLayingMessage,
                                onIconClick = { isLayingTimeDialogShown = true },
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = "Icon to select time"
                            )

                            if (isLayingTimeDialogShown) {
                                TimeDialog(state = layingTimePickerState,
                                    onTimeSelected = { selectedTime ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.StartLayingSelected(
                                                selectedTime
                                            )
                                        )
                                        isLayingTimeDialogShown = false
                                    },
                                    onDismiss = { isLayingTimeDialogShown = false }
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            IconTextField(
                                text = state.value.startCover,
                                label = "Start Cover",
                                modifier = Modifier.fillMaxWidth(),
                                isError = state.value.invalidStartCoverMessage.isNotEmpty(),
                                errorMessage = state.value.invalidStartCoverMessage,
                                onIconClick = { isCoverTimeDialogShown = true },
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = "Icon to select time"
                            )

                            if (isCoverTimeDialogShown) {
                                TimeDialog(state = coverTimePickerState,
                                    onTimeSelected = { selectedTime ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.StartCoverSelected(
                                                selectedTime
                                            )
                                        )
                                        isCoverTimeDialogShown = false
                                    },
                                    onDismiss = { isCoverTimeDialogShown = false }
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            IconTextField(
                                text = state.value.startCamouflage,
                                label = "Start Camouflage",
                                modifier = Modifier.fillMaxWidth(),
                                isError = state.value.invalidStartCamouflageMessage.isNotEmpty(),
                                errorMessage = state.value.invalidStartCamouflageMessage,
                                onIconClick = { isCamouflageTimeDialogShown = true },
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = "Icon to select time"
                            )

                            if (isCamouflageTimeDialogShown) {
                                TimeDialog(state = camouflageTimePickerState,
                                    onTimeSelected = { selectedTime ->
                                        viewModel.sendEvent(
                                            NightSurveyEvent.StartCamouflageSelected(
                                                selectedTime
                                            )
                                        )
                                        isCamouflageTimeDialogShown = false
                                    },
                                    onDismiss = { isCamouflageTimeDialogShown = false }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(if (state.value.nestEmergenceSwitch) 12.dp else 24.dp))

                    IconTextField(
                        text = state.value.departNestSite,
                        label = "Depart Nest Site",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        isError = state.value.invalidDepartNestSiteMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDepartNestSiteMessage,
                        onIconClick = { isDepartNestTimeDialogShown = true },
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = "Icon to select time"
                    )

                    if (isDepartNestTimeDialogShown) {
                        TimeDialog(state = departNestTimePickerState,
                            onTimeSelected = { selectedTime ->
                                viewModel.sendEvent(
                                    NightSurveyEvent.DepartNestSiteSelected(
                                        selectedTime
                                    )
                                )
                                isDepartNestTimeDialogShown = false
                            },
                            onDismiss = { isDepartNestTimeDialogShown = false }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    IconTextField(
                        text = state.value.turtleAtSea,
                        label = "Turtle At Sea",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        isError = state.value.invalidTurtleAtSeaMessage.isNotEmpty(),
                        errorMessage = state.value.invalidTurtleAtSeaMessage,
                        onIconClick = { isTurtleAtSeaTimeDialogShown = true },
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = "Icon to select time"
                    )

                    if (isTurtleAtSeaTimeDialogShown) {
                        TimeDialog(state = turtleAtSeaTimePickerState,
                            onTimeSelected = { selectedTime ->
                                viewModel.sendEvent(
                                    NightSurveyEvent.TurtleAtSeaSelected(
                                        selectedTime
                                    )
                                )
                                isTurtleAtSeaTimeDialogShown = false
                            },
                            onDismiss = { isTurtleAtSeaTimeDialogShown = false }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.commentsRemarks,
                        onValueChange = { input ->
                            viewModel.sendEvent(NightSurveyEvent.CommentsRemarksChanged(input))
                        },
                        isError = state.value.invalidCommentsRemarksMessage.isNotEmpty(),
                        errorMessage = state.value.invalidCommentsRemarksMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Comments or Remarks",
                        onIconClick = {
                            viewModel.sendEvent(NightSurveyEvent.CommentsRemarksChanged(""))
                        },
                        singleLine = false,
                        imeAction = ImeAction.Done
                    )

                    Spacer(modifier = Modifier.height(52.dp))

                    FilledButton(
                        buttonText = "Submit",
                        onClick = { viewModel.sendEvent(NightSurveyEvent.SubmitButtonClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.date.isNotEmpty() &&
                                state.value.area.isNotEmpty() &&
                                state.value.beach.isName() &&
                                state.value.sector.isNotEmpty() &&
                                state.value.subsector.isNotEmpty() &&
                                state.value.tagger.isName() &&
                                state.value.oldTagDataList.all { tag ->
                                    tag.selectedOldTagLocation.isNotEmpty() &&
                                            tag.oldTagCode.isNotEmpty()
                                } &&
                                state.value.newTagDataList.all { tag ->
                                    tag.selectedNewTagLocation.isNotEmpty() &&
                                            tag.newTagCode.isNotEmpty()
                                } &&
                                state.value.straightCarapaceLength.isDouble() &&
                                state.value.straightCarapaceWidth.isDouble() &&
                                state.value.curvedCarapaceLength.isDouble() &&
                                state.value.curvedCarapaceWidth.isDouble() &&
                                if (state.value.nestEmergenceSwitch) {
                                    state.value.nestCode.isNotEmpty() &&
                                            state.value.depthTopEgg.isDouble() &&
                                            state.value.depthBottomNests.isDouble() &&
                                            state.value.distanceToSea.isDouble() &&
                                            state.value.numEggsRelocated.isInt() &&
                                            state.value.numEggsExcavated.isInt()
                                } else true

                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    LaunchedEffect(state.value.success) {
                        if (state.value.success) {
                            nightSurveySnackbarState.showSnackbar("Survey successfully submitted ✔\uFE0F")
                            navigateBack()
                        }
                    }

                    LaunchedEffect(state.value.exception) {
                        if (state.value.exception != null) {
                            nightSurveySnackbarState.showSnackbar("Survey could not be submitted")
                        }
                    }
                }
            }
        }
    )
}