package com.developerstring.financesapp.screen.onstart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.setupnav.SetUpNavRoute
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.YES
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    val context = LocalContext.current

    SplashScreenContent()

    // calling the functions to collect the value form the data store
    profileViewModel.getOnBoardingStatus(context = context)
    profileViewModel.getProfileCreatedStatus(context = context)
    // getting and saving the data got from the data store of OnBoardingStatus using a variable in ProfileViewModel
    val onBoarding = profileViewModel.onBoardingStatus.collectAsState()
    val profileCreate = profileViewModel.profileCreatedStatus.collectAsState()

    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        // logic behind navigation after splash screen
        if (onBoarding.value == YES) {
            if (profileCreate.value == YES) {
                navController.navigate(route = SetUpNavRoute.MainSetUpNavRoute.route)
            } else {
                navController.navigate(route = SetUpNavRoute.CreateProfileSetUpNavRoute.route)
            }
        } else {
            navController.navigate(route = SetUpNavRoute.BoardingSetUpNavRoute1.route)
        }
    }
}

@Composable
fun SplashScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
            Text(
                text = stringResource(id = R.string.splash_screen_title),
                fontFamily = fontFredoka,
                fontWeight = FontWeight.SemiBold,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = MaterialTheme.colors.textColorBW
            )
            Text(
                text = stringResource(id = R.string.splash_screen_text),
                fontFamily = fontPoppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = SMALL_TEXT_SIZE,
                color = MaterialTheme.colors.textColorBLG
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = MaterialTheme.colors.contentBackgroundColor,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.11f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent()
}