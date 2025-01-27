package com.spapalamprou.turtlesafe.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spapalamprou.turtlesafe.R
import com.spapalamprou.turtlesafe.ui.theme.TurtleSafeTheme

@Composable
fun LandingScreen(
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background) {

        Image(
            painter = painterResource(id = R.drawable.turtle_beach),
            contentDescription = "background image of a turtle",
            modifier = Modifier
                .fillMaxSize()
                .testTag("landingScreen"),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.35f)
                .background(Color.Black)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo_turtlesafe_transparent_white),
                    contentDescription = "image of TurtleSafe brand name",
                    modifier = Modifier
                        .width(251.dp)
                        .height(202.dp)
                        .align(alignment = Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Sustainable Planet",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Actions for the protection and conservation of sea turtles and their habitats",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navigateToLogin() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .testTag("loginButton"),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Login",
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { navigateToSignUp() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.White)
                ) {
                    Text(
                        text = "Sign Up",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun LandingScreenPreview() {
    TurtleSafeTheme {
        LandingScreen({}, {})
    }
}