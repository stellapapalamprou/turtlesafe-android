package com.spapalamprou.turtlesafe.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.GpsFixed
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyEvent
import com.spapalamprou.turtlesafe.features.morningSurvey.MorningSurveyViewModel
import com.spapalamprou.turtlesafe.features.newNest.NewNestEvent
import com.spapalamprou.turtlesafe.features.newNest.NewNestViewModel
import com.spapalamprou.turtlesafe.ui.components.DateDialog
import com.spapalamprou.turtlesafe.ui.components.DefaultTextField
import com.spapalamprou.turtlesafe.ui.components.DropdownTextField
import com.spapalamprou.turtlesafe.ui.components.FilledButton
import com.spapalamprou.turtlesafe.ui.components.IconTextField
import com.spapalamprou.turtlesafe.ui.components.LabelSwitch
import com.spapalamprou.turtlesafe.ui.components.PermissionDialog
import com.spapalamprou.turtlesafe.ui.components.RoundButton
import com.spapalamprou.turtlesafe.validators.isDouble
import com.spapalamprou.turtlesafe.validators.isName
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.io.File


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NewNestScreen(
    sharedViewModel: MorningSurveyViewModel,
    navigateBack: () -> Unit
) {
    val viewModel: NewNestViewModel = hiltViewModel()

    val state = viewModel.state.collectAsState()

    var isDateDialogShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val datePickerState: DatePickerState = rememberDatePickerState()

    val sectors: List<String> = listOf("West", "East", "A", "B", "C", "H", "I", "K", "O")

    val subsectors: List<String> = listOf("As1200", "On1976")

    val trackTypes: List<String> = listOf("A", "B", "C", "D", "E")

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

    val context = LocalContext.current

    val locationPermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
    )

    var isLocationRequested: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var shouldShowLocationFields: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val locationSnackbarState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    var isPhotoBottomSheetShown: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    val gallery =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null)
                    viewModel.sendEvent(
                        NewNestEvent.PhotoChanged(
                            uri
                        )
                    )
            }
        )

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA,
    )

    var isCameraPermissionRequested: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    var newPhotoUri: Uri? by remember {
        mutableStateOf(null)
    }

    val camera =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture(),
            onResult = { isPhotoSaved ->
                val uri = newPhotoUri

                if (isPhotoSaved && uri != null)
                    viewModel.sendEvent(
                        NewNestEvent.PhotoChanged(
                            photoUri = uri
                        )
                    )
            }
        )

    val emergenceOrEvents: List<String> = listOf(
        "A: Predation Attempt", "E: Emergence",
        "I: Inundation", "P: Predation", "V: Vandalism", "W: Washed Nest", "O: Other"
    )

    LaunchedEffect(Unit) {
        viewModel.sendEvent(
            NewNestEvent.LayingDateSelected(sharedViewModel.state.value.date)
        )
        viewModel.sendEvent(
            NewNestEvent.AreaSelected(sharedViewModel.state.value.area)
        )
        viewModel.sendEvent(
            NewNestEvent.BeachChanged(sharedViewModel.state.value.beach)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "New Nest",
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
        snackbarHost = { SnackbarHost(locationSnackbarState) },
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
                        text = state.value.layingDate,
                        label = "Laying Date",
                        isError = state.value.invalidLayingDateMessage.isNotEmpty(),
                        errorMessage = state.value.invalidLayingDateMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),

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
                                viewModel.sendEvent(NewNestEvent.LayingDateSelected(selectedDate))
                                isDateDialogShown = false
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.area,
                        label = "Area",
                        readOnly = true,
                        onValueChange = { selection ->
                            viewModel.sendEvent(NewNestEvent.AreaSelected(selection))
                        },
                        isError = state.value.invalidAreaMessage.isNotEmpty(),
                        errorMessage = state.value.invalidAreaMessage,
                        onIconClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.beach,
                        onValueChange = { input ->
                            viewModel.sendEvent(NewNestEvent.BeachChanged(input))
                        },
                        isError = state.value.invalidBeachMessage.isNotEmpty(),
                        errorMessage = state.value.invalidBeachMessage,
                        label = "Beach",
                        readOnly = true,
                        onIconClick = {},
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
                                viewModel.sendEvent(NewNestEvent.SectorSelected(selection))
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
                                viewModel.sendEvent(NewNestEvent.SubsectorSelected(selection))
                            },
                            isError = state.value.invalidSubsectorMessage.isNotEmpty(),
                            errorMessage = state.value.invalidSubsectorMessage,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {

                        DefaultTextField(
                            text = state.value.nestCode,
                            onValueChange = { input ->
                                viewModel.sendEvent(NewNestEvent.NestCodeChanged(input))
                            },
                            isError = state.value.invalidNestCodeMessage.isNotEmpty(),
                            errorMessage = state.value.invalidNestCodeMessage,
                            label = "Nest Code*",
                            onIconClick = { viewModel.sendEvent(NewNestEvent.NestCodeChanged("")) },
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        )

                        Spacer(modifier = Modifier.width(24.dp))

                        DropdownTextField(
                            selectedValue = state.value.trackType,
                            options = trackTypes,
                            label = "Track Type",
                            onValueChangedEvent = { selection ->
                                viewModel.sendEvent(NewNestEvent.TrackTypeSelected(selection))
                            },
                            isError = state.value.invalidTrackTypeMessage.isNotEmpty(),
                            errorMessage = state.value.invalidTrackTypeMessage,
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "Nest Location*",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme
                                    .typography
                                    .labelLarge
                                    .copy(fontSize = 18.sp)
                            )

                            Text(
                                text = "GPS Coordinates",
                                color = Color.Gray,
                                style = MaterialTheme
                                    .typography
                                    .labelLarge
                                    .copy(fontSize = 14.sp)
                            )
                        }

                        RoundButton(
                            onClick = {

                                if (!locationPermissionState.status.isGranted) {
                                    isLocationRequested = true
                                    locationPermissionState.launchPermissionRequest()
                                } else {
                                    getLocation(
                                        onGetLocationSuccess = { pair ->
                                            viewModel.sendEvent(
                                                NewNestEvent.NestLocationReceived(
                                                    gpsLatitude = pair.first.toString(),
                                                    gpsLongtitude = pair.second.toString()
                                                )
                                            )
                                            shouldShowLocationFields = true
                                        },
                                        onGetLocationFailed = {
                                            shouldShowLocationFields = true
                                        },
                                        onGetLocationIsNull = {
                                            shouldShowLocationFields = true
                                        },
                                        context = context
                                    )
                                }
                            },
                            imageVector = Icons.Outlined.GpsFixed,
                            contentDescription = "Icon showing GPS location pointer",
                        )

                        if (isLocationRequested) {
                            PermissionDialog(
                                onPermissionGranted = {
                                    getLocation(
                                        onGetLocationSuccess = { pair ->
                                            viewModel.sendEvent(
                                                NewNestEvent.NestLocationReceived(
                                                    gpsLatitude = pair.first.toString(),
                                                    gpsLongtitude = pair.second.toString()
                                                )
                                            )
                                            shouldShowLocationFields = true
                                        },
                                        onGetLocationFailed = {
                                            shouldShowLocationFields = true
                                        },
                                        onGetLocationIsNull = {
                                            shouldShowLocationFields = true
                                        },
                                        context = context
                                    )
                                },
                                onPermissionDenied = {
                                    shouldShowLocationFields = true
                                },
                                onShowRationale = {
                                    coroutineScope.launch {
                                        locationSnackbarState.showSnackbar("Permission to precise location is needed to record GPS coordinates automatically")
                                    }
                                },
                                permissionState = locationPermissionState
                            )
                        }
                    }

                    if (shouldShowLocationFields) {

                        Spacer(modifier = Modifier.height(24.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                        ) {

                            DefaultTextField(
                                text = state.value.gpsLatitude,
                                onValueChange = { input ->
                                    viewModel.sendEvent(NewNestEvent.GpsLatitudeChanged(input))
                                },
                                isError = state.value.invalidGpsLatitudeMessage.isNotEmpty(),
                                errorMessage = state.value.invalidGpsLatitudeMessage,
                                label = "Latitude*",
                                onIconClick = {
                                    viewModel.sendEvent(
                                        NewNestEvent.GpsLatitudeChanged(
                                            ""
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .align(alignment = Alignment.CenterVertically),
                                keyboardType = KeyboardType.Number
                            )

                            Spacer(modifier = Modifier.width(24.dp))

                            DefaultTextField(
                                text = state.value.gpsLongtitude,
                                onValueChange = { input ->
                                    viewModel.sendEvent(
                                        NewNestEvent.GpsLongtitudeChanged(
                                            input
                                        )
                                    )
                                },
                                isError = state.value.invalidGpsLongtitudeMessage.isNotEmpty(),
                                errorMessage = state.value.invalidGpsLongtitudeMessage,
                                label = "Longtitude*",
                                onIconClick = {
                                    viewModel.sendEvent(
                                        NewNestEvent.GpsLongtitudeChanged(
                                            ""
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .align(alignment = Alignment.CenterVertically),
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(if (shouldShowLocationFields) 12.dp else 24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            Text(
                                text = "Nest Photo",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme
                                    .typography
                                    .labelLarge
                                    .copy(fontSize = 18.sp)
                            )

                            Text(
                                text = if (state.value.photoUri != null) {
                                    "Photo selected"
                                } else {
                                    "Select photo"
                                },
                                color = Color.Gray,
                                style = MaterialTheme
                                    .typography
                                    .labelLarge
                                    .copy(fontSize = 14.sp)
                            )
                        }

                        RoundButton(
                            onClick = { isPhotoBottomSheetShown = true },
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = "Icon showing a camera",
                        )
                    }

                    if (isPhotoBottomSheetShown) {
                        ModalBottomSheet(onDismissRequest = { isPhotoBottomSheetShown = false }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 12.dp)
                            ) {

                                TextButton(
                                    onClick = {
                                        if (!cameraPermissionState.status.isGranted) {
                                            isCameraPermissionRequested = true
                                            cameraPermissionState.launchPermissionRequest()
                                        } else {
                                            val photoUri = createPhotoUri(context)
                                            newPhotoUri = photoUri
                                            camera.launch(photoUri)
                                        }
                                        isPhotoBottomSheetShown = false
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(text = "Take New Photo")
                                }

                                Spacer(modifier = Modifier.width(24.dp))

                                TextButton(
                                    onClick = {
                                        gallery.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                        isPhotoBottomSheetShown = false
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(text = "Open Gallery")
                                }
                            }
                        }
                    }

                    if (isCameraPermissionRequested) {

                        PermissionDialog(
                            onPermissionGranted = {
                                val photoUri = createPhotoUri(context)
                                newPhotoUri = photoUri
                                camera.launch(photoUri)
                            },
                            onPermissionDenied = {
                                isPhotoBottomSheetShown = false
                            },
                            onShowRationale = {
                                coroutineScope.launch {
                                    locationSnackbarState.showSnackbar("Permission to access camera is needed to take instant photo")
                                }
                            },
                            permissionState = cameraPermissionState
                        )
                    }

                    if (state.value.photoUri != null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                model = state.value.photoUri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )

                            FilledButton(
                                buttonText = "Delete",
                                onClick = {
                                    viewModel.sendEvent(
                                        NewNestEvent.PhotoChanged(
                                            photoUri = null
                                        )
                                    )
                                },
                                modifier = Modifier,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    DefaultTextField(
                        text = state.value.depthTopEgg,
                        onValueChange = { input ->
                            viewModel.sendEvent(NewNestEvent.DepthTopEggChanged(input))
                        },
                        isError = state.value.invalidDepthTopEggMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDepthTopEggMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Depth to Top Egg - h*",
                        onIconClick = { viewModel.sendEvent(NewNestEvent.DepthTopEggChanged("")) },
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.distanceToSea,
                        onValueChange = { input ->
                            viewModel.sendEvent(NewNestEvent.DistanceToSeaChanged(input))
                        },
                        isError = state.value.invalidDistanceToSeaMessage.isNotEmpty(),
                        errorMessage = state.value.invalidDistanceToSeaMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        label = "Distance to Sea (m)*",
                        onIconClick = { viewModel.sendEvent(NewNestEvent.DistanceToSeaChanged("")) },
                        keyboardType = KeyboardType.Number
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DefaultTextField(
                        text = state.value.turtleTags,
                        onValueChange = { input ->
                            viewModel.sendEvent(NewNestEvent.TurtleTagsChanged(input))
                        },
                        isError = state.value.invalidTurtleTagsMessage.isNotEmpty(),
                        errorMessage = state.value.invalidTurtleTagsMessage,
                        label = "Turtle Tags",
                        onIconClick = { viewModel.sendEvent(NewNestEvent.TurtleTagsChanged("")) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        singleLine = false
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DropdownTextField(
                        selectedValue = state.value.emergenceOrEvent,
                        options = emergenceOrEvents,
                        label = "Emergence or Events",
                        onValueChangedEvent = { selection ->
                            viewModel.sendEvent(
                                NewNestEvent.EmergenceOrEventSelected(
                                    selection
                                )
                            )
                        },
                        isError = state.value.invalidEmergenceOrEventMessage.isNotEmpty(),
                        errorMessage = state.value.invalidEmergenceOrEventMessage,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LabelSwitch(
                        onCheckedChange = {
                            viewModel.sendEvent(
                                NewNestEvent.ProtectedNestSwitchChecked(
                                    !state.value.protectedNestSwitch
                                )
                            )
                        },
                        text = "Protected Nest",
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
                                    NewNestEvent.ProtectionMeasuresSelected(
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
                            viewModel.sendEvent(NewNestEvent.CommentsOrRemarksChanged(input))
                        },
                        isError = state.value.invalidCommentsOrRemarksMessage.isNotEmpty(),
                        errorMessage = state.value.invalidCommentsOrRemarksMessage,
                        label = "Comments or Remarks",
                        onIconClick = {
                            viewModel.sendEvent(
                                NewNestEvent.CommentsOrRemarksChanged(
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
                            viewModel.sendEvent(NewNestEvent.SaveButtonClicked)
                            sharedViewModel.sendEvent(MorningSurveyEvent.NewNestAdded(state.value))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        enabled = state.value.layingDate.isNotEmpty() &&
                                state.value.area.isNotEmpty() &&
                                state.value.beach.isName() &&
                                state.value.sector.isNotEmpty() &&
                                state.value.subsector.isNotEmpty() &&
                                state.value.nestCode.isNotEmpty() &&
                                state.value.gpsLatitude.isDouble() &&
                                state.value.gpsLongtitude.isDouble() &&
                                state.value.depthTopEgg.isDouble() &&
                                state.value.distanceToSea.isDouble() &&
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

@SuppressLint("MissingPermission")
private fun getLocation(
    onGetLocationSuccess: (Pair<Double, Double>) -> Unit,
    onGetLocationFailed: () -> Unit,
    onGetLocationIsNull: () -> Unit,
    context: Context
) {
    val gps = LocationServices.getFusedLocationProviderClient(context)

    if (areLocationPermissionsGranted(context)) {

        gps.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onGetLocationSuccess(Pair(location.latitude, location.longitude))
                } else {
                    onGetLocationIsNull()
                }
            }
            .addOnFailureListener {
                onGetLocationFailed()
            }
    }
}

private fun areLocationPermissionsGranted(context: Context): Boolean {
    val isFineLocationGranted: Boolean = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val isCoarseLocationGranted: Boolean = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    return (isFineLocationGranted && isCoarseLocationGranted)
}

private fun createPhotoUri(context: Context): Uri {
    val directory = File(context.cacheDir, "images")
    directory.mkdirs()

    val file = File.createTempFile("image", ".jpg", directory)
    val uri = FileProvider.getUriForFile(
        context,
        "com.bbk.turtlesafe.fileProvider",
        file
    )
    return uri
}