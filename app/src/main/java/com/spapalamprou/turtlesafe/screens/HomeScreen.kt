package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.features.home.HomeEvent
import com.spapalamprou.turtlesafe.features.home.HomeViewModel
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme

@Composable
fun HomeScreen(
    navigateToMorningSurvey: () -> Unit,
    navigateToNightSurvey: () -> Unit
) {

    val viewModel: HomeViewModel = hiltViewModel()

    HomeScreenContent(
        navigateToMorningSurvey = navigateToMorningSurvey,
        navigateToNightSurvey = navigateToNightSurvey,
        onLogoutClick = { viewModel.sendEvent(HomeEvent.LogoutButtonClicked) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navigateToMorningSurvey: () -> Unit,
    navigateToNightSurvey: () -> Unit,
    onLogoutClick: () -> Unit
) {
    var isMenuExpanded: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    val carouselState = rememberCarouselState { 2 }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                actions = {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Icon to expand menu",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(text = {
                            Text(text = "Logout")
                        }, onClick = {
                            isMenuExpanded = false
                            onLogoutClick()
                        })
                    }
                }
            )
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxHeight()
            ) {

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = "Welcome,",
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .testTag("homeScreen"),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Tap one of the cards below to begin a new survey",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(48.dp))

                HorizontalMultiBrowseCarousel(
                    state = carouselState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 24.dp, end = 24.dp, bottom = 60.dp),
                    preferredItemWidth = 360.dp,
                    itemSpacing = 8.dp,
                    flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(state = carouselState),
                ) { page ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .maskClip(shape = RoundedCornerShape(12.dp))
                            .background(
                                color = when (page) {
                                    0 -> Color(0xFFD8D0CE)
                                    else -> Color(0xFF213141)
                                }
                            )
                    ) {

                        Image(
                            painter = painterResource(
                                id = when (page) {
                                    0 -> R.drawable.bg_morning_home
                                    else -> R.drawable.bg_night_home
                                }
                            ),
                            contentDescription = "Beach landscape",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            contentScale = ContentScale.Crop

                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .background(
                                    Brush.verticalGradient(
                                        colorStops = arrayOf(
                                            0.8f to Color(0x00000000),
                                            1.0f to when (page) {
                                                0 -> Color(0xFFD8D0CE)
                                                else -> Color(0xFF213141)
                                            }
                                        )
                                    )
                                )
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colorStops = arrayOf(
                                            0.0f to Color(0x00000000),
                                            1.0f to Color(0x26000000)
                                        )
                                    )
                                )

                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 24.dp)
                                .align(alignment = Alignment.BottomStart)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(alignment = Alignment.Start)
                                    .size(24.dp),
                                imageVector = when (page) {
                                    0 -> Icons.Outlined.LightMode
                                    else -> Icons.Outlined.DarkMode
                                },
                                contentDescription = "Icon showing day or night",
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = when (page) {
                                    0 -> "Morning Survey"
                                    else -> "Night Survey"
                                },
                                textAlign = TextAlign.Start,
                                style = MaterialTheme
                                    .typography
                                    .headlineMedium
                                    .copy(fontWeight = FontWeight.SemiBold),
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            OutlinedButton(
                                onClick = {
                                    when (page) {
                                        0 -> navigateToMorningSurvey()
                                        else -> navigateToNightSurvey()
                                    }
                                },
                                modifier = Modifier
                                    .testTag(
                                        when (page) {
                                            0 -> "morningSurvey"
                                            else -> "nightSurvey"
                                        }
                                    )
                                    .wrapContentWidth()
                                    .height(40.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.White
                                ),
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                Text(
                                    text = "Start now",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
@Preview
fun HomeScreenPreview() {
    TurtleSafeTheme {
        HomeScreenContent({}, {}, {})
    }
}