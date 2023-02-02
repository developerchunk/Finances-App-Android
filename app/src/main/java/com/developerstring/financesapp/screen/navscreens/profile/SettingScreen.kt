package com.developerstring.financesapp.screen.navscreens.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.setupnav.SetUpNavRoute
import com.developerstring.financesapp.screen.navscreens.content.profilescreen.CustomSwitchButton
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.DisplayAlertDialog
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.DARK_THEME_ENABLE
import com.developerstring.financesapp.util.Constants.DELETE_ALL_TRANSACTIONS
import com.developerstring.financesapp.util.Constants.DELETE_PROFILE
import com.developerstring.financesapp.util.Constants.NO
import com.developerstring.financesapp.util.Constants.SETTINGS
import com.developerstring.financesapp.util.Constants.TIME_FORMAT

@Composable
fun SettingScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val time24Hours by profileViewModel.profileTime24Hours.collectAsState()

    val scrollState = rememberScrollState()

    var settingSelected by remember {
        mutableStateOf("")
    }

    var alertDialogShow by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Scaffold(topBar = {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .height(TOP_APP_BAR_HEIGHT)
                .padding(end = 5.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "close",
                    tint = textColorBW
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.setting),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW,
                textAlign = TextAlign.Center
            )

        }

    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(scrollState)
                .padding(it)
                .padding(top = 20.dp)
        ) {


            SETTINGS.forEach { setting ->
                SettingsItems(
                    title = setting.key,
                    icon = setting.value,
                    enableTitle = TIME_FORMAT,
                    enable = time24Hours,
                    onClick = { value, enableValue ->
                        settingSelected = value
                        when (value) {
                            TIME_FORMAT -> {
                                profileViewModel.updateTime24Hours(
                                    time24Hours = enableValue
                                )
                            }
                            DELETE_ALL_TRANSACTIONS -> {
                                alertDialogShow = true
                            }
                            DELETE_PROFILE -> {
                                alertDialogShow = true
                            }
                        }
                    }
                )
            }


            when (settingSelected) {
                DELETE_ALL_TRANSACTIONS -> {
                    DisplayAlertDialog(
                        title = stringResource(id = R.string.delete_all_transaction_title),
                        message = stringResource(id = R.string.delete_all_transaction_message),
                        openDialog = alertDialogShow,
                        captchaVerification = true,
                        onCloseClicked = {
                            alertDialogShow = false
                            settingSelected = ""
                        },
                        onYesClicked = {
                            sharedViewModel.deleteAllTransactions()
                        })
                }
                DELETE_PROFILE -> {
                    DisplayAlertDialog(
                        title = stringResource(id = R.string.delete_all_profiles_title),
                        message = stringResource(id = R.string.delete_all_profiles_message),
                        openDialog = alertDialogShow,
                        captchaVerification = true,
                        onCloseClicked = {
                            alertDialogShow = false
                            settingSelected = ""
                        },
                        onYesClicked = {
                            profileViewModel.profileCreatedStatus(
                                context = context,
                                value = NO
                            )
                            profileViewModel.deleteAllProfiles()
                            navController.popBackStack()
                            navController.navigate(route = SetUpNavRoute.SplashScreenSetUpNavRoute.route)
                            DARK_THEME_ENABLE = true
                        }
                    )

                }
            }


        }
    }

}

@Composable
fun SettingsItems(
    title: String,
    icon: Int,
    enableTitle: String,
    enable: Boolean = false,
    onClick: (value: String, enableValue: Boolean) -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp, start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = {
                        if (title != enableTitle) {
                            onClick(title, enable)
                        }
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = TEXT_FIELD_SIZE,
                color = textColorBW
            )

            if (title == enableTitle) {
                CustomSwitchButton(
                    switchPadding = 3.dp,
                    buttonSizeWidth = 60.dp,
                    buttonSizeHeight = 35.dp,
                    darkThemeEnable = enable
                ) {
                    onClick(title, it)
                }
            } else {
                Image(
                    modifier = Modifier.size(34.dp),
                    painter = painterResource(id = icon),
                    contentDescription = "icon",
                    colorFilter = ColorFilter.tint(color = textColorBW)
                )
            }


        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .fillMaxWidth()
                .height(1.dp)
                .clip(CircleShape)
                .background(textColorBW)
        )
    }

}