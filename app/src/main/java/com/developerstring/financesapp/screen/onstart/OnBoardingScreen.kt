package com.developerstring.financesapp.screen.onstart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.setupnav.SetUpNavRoute
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.YES

// OnBoardingScreen1
@Composable
fun BoardingScreen1(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    OnBoardingContent(
        image = if (isSystemInDarkTheme()) R.drawable.ic_on_boarding_dark_1
        else R.drawable.ic_on_boarding_1,
        title = stringResource(id = R.string.on_boarding_title_1),
        text = stringResource(id = R.string.on_boarding_text_1),
        button = stringResource(id = R.string.on_boarding_button_1),
        page = 1,
        navController = navController,
        profileViewModel = profileViewModel

    )
}

// OnBoardingScreen2
@Composable
fun BoardingScreen2(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    OnBoardingContent(
        image = if (isSystemInDarkTheme()) R.drawable.ic_on_boarding_dark_2
        else R.drawable.ic_on_boarding_2,
        title = stringResource(id = R.string.on_boarding_title_2),
        text = stringResource(id = R.string.on_boarding_text_2),
        button = stringResource(id = R.string.on_boarding_button_2),
        page = 2,
        navController = navController,
        profileViewModel = profileViewModel
    )
}

// OnBoardingScreen3
@Composable
fun BoardingScreen3(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    OnBoardingContent(
        image = if (isSystemInDarkTheme()) R.drawable.ic_on_boarding_dark_3
        else R.drawable.ic_on_boarding_3,
        title = stringResource(id = R.string.on_boarding_title_3),
        text = stringResource(id = R.string.on_boarding_text_3),
        button = stringResource(id = R.string.on_boarding_button_3),
        page = 3,
        navController = navController,
        profileViewModel = profileViewModel
    )
}

// OnBoardingScreen4
@Composable
fun BoardingScreen4(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {
    OnBoardingContent(
        image = if (isSystemInDarkTheme()) R.drawable.ic_on_boarding_dark_4
        else R.drawable.ic_on_boarding_4,
        title = stringResource(id = R.string.on_boarding_title_4),
        text = stringResource(id = R.string.on_boarding_text_4),
        button = stringResource(id = R.string.on_boarding_button_4),
        page = 4,
        navController = navController,
        profileViewModel = profileViewModel
    )
}


// the onBoardingContent has all the layouts
@Composable
fun OnBoardingContent(
    image: Int,
    title: String,
    text: String,
    button: String,
    page: Int,
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    //context
    val context = LocalContext.current
    // to get profile is created or not
    val profileCreate = profileViewModel.profileCreatedStatus.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colors.backgroundColor
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .padding(top = 90.dp),
            painter = painterResource(id = image),
            contentDescription = "OnBoardingImage"
        )
        // title
        Text(
            modifier = Modifier
                .padding(
                    top = 50.dp,
                    start = 30.dp
                ),
            text = title,
            color = MaterialTheme.colors.textColorBW,
            fontSize = EXTRA_LARGE_TEXT_SIZE,
            fontFamily = fontInter,
            fontWeight = FontWeight.SemiBold
        )
        // description / text
        Text(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 30.dp,
                    end = 10.dp
                ),
            text = text,
            color = MaterialTheme.colors.textColorGray,
            fontSize = MEDIUM_TEXT_SIZE,
            fontFamily = fontInter,
            fontWeight = FontWeight.Light
        )

        // bottom part
        Row(
            modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxSize()
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Bottom
        ) {
            // for the dots
            Row(
                modifier = Modifier.height(80.dp),
                verticalAlignment = CenterVertically
            ) {
                val circleSize = 10.dp
                // 1st
                Card(
                    modifier = Modifier
                        .padding(start = 40.dp)
                        .height(circleSize)
                        .width(circleSize)
                        .padding(0.dp),
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor =
                    if (page == 1) {
                        MaterialTheme.colors.contentBackgroundColor
                    } else {
                        LightPurple
                    }
                ) {
                }
                // 2nd
                Card(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .height(circleSize)
                        .width(circleSize),
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor =
                    if (page == 2) {
                        MaterialTheme.colors.contentBackgroundColor
                    } else {
                        LightPurple
                    }
                ) {
                }
                // 3rd
                Card(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .height(circleSize)
                        .width(circleSize),
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor =
                    if (page == 3) {
                        MaterialTheme.colors.contentBackgroundColor
                    } else {
                        LightPurple
                    }
                ) {
                }
                // 4th
                Card(
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .height(circleSize)
                        .width(circleSize),
                    shape = RoundedCornerShape(6.dp),
                    backgroundColor =
                    if (page == 4) {
                        MaterialTheme.colors.contentBackgroundColor
                    } else {
                        LightPurple
                    }
                ) {
                }
            }

            // for the buttons
            Button(
                modifier = Modifier
                    .padding(end = 40.dp)
                    .height(80.dp)
                    .width(80.dp)
                    .padding(0.dp),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.contentBackgroundColor),
                elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
                onClick = {
                    if (page == 4) {
                        // saving the data into the data store preference
                        profileViewModel.saveOnBoardingStatus(context = context)
                    }

                    navController.popBackStack()
                    navController.navigate(
                        route =
                        when (page) {
                            1 -> SetUpNavRoute.BoardingSetUpNavRoute2.route
                            2 -> SetUpNavRoute.BoardingSetUpNavRoute3.route
                            3 -> SetUpNavRoute.BoardingSetUpNavRoute4.route
                            4 -> if (profileCreate.value == YES) {
                                SetUpNavRoute.MainSetUpNavRoute.route
                            } else {
                                SetUpNavRoute.CreateProfileSetUpNavRoute.route
                            }
                            else -> SetUpNavRoute.BoardingSetUpNavRoute4.route
                        }
                    )
                }

            ) {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = button,
                    fontFamily = fontPoppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    color = Color.White,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingContentPreview() {
    OnBoardingContent(
        image = R.drawable.ic_on_boarding_1,
        title = stringResource(id = R.string.on_boarding_title_4),
        text = stringResource(id = R.string.on_boarding_text_4),
        button = stringResource(id = R.string.on_boarding_button_4),
        page = 4,
        navController = rememberNavController(),
        profileViewModel = ProfileViewModel()
    )
}