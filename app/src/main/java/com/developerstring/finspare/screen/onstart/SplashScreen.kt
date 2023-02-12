package com.developerstring.finspare.screen.onstart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.setupnav.SetUpNavRoute
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.YES
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel
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
        delay(2500)
        navController.popBackStack()
        // logic behind navigation after splash screen
        if (onBoarding.value == YES) {
            if (profileCreate.value == YES) {
                sharedViewModel.setCategorySumArray()
                navController.navigate(route = SetUpNavRoute.MainSetUpNavRoute.route)
            } else {
                navController.navigate(route = SetUpNavRoute.LanguageScreenSetUpNavRoute.route)
            }
        } else {
            navController.navigate(route = SetUpNavRoute.BoardingSetUpNavRoute1.route)
        }
    }
}

@Composable
fun SplashScreenContent(
) {

    var animated by remember {
        mutableStateOf(false)
    }
    var animatedLater by remember {
        mutableStateOf(false)
    }

    val animatedSize by animateDpAsState(
        targetValue = if (animated) 160.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animated = true
        delay(500)
        animatedLater = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 20.dp)
//        ) {
//            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
//            Text(
//                text = stringResource(id = R.string.splash_screen_title),
//                fontFamily = fontFredoka,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = MAX_TEXT_SIZE,
//                color = textColorBW
//            )
//            Text(
//                text = stringResource(id = R.string.splash_screen_text),
//                fontFamily = fontPoppins,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = SMALL_TEXT_SIZE,
//                color = textColorBLG
//            )
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    modifier = Modifier.size(animatedSize),
                    painter = painterResource(id = R.drawable.finspare_logo),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }

            AnimatedVisibility(visible = animatedLater) {
                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Medium,
                    fontFamily = fontInter,
                    fontSize = EXTRA_LARGE_TEXT_SIZE,
                    color = textColorBW
                )
            }

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = contentBackgroundColor,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.11f))
        }
    }
}