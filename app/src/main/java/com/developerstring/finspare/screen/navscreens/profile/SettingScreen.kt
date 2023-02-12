package com.developerstring.finspare.screen.navscreens.profile

import android.widget.Toast
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
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.setupnav.SetUpNavRoute
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.ui.components.CustomSwitchButton
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.components.DisplayAlertDialog
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.DARK_THEME_ENABLE
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.LanguageText

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

    var deleteAllCategories by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val deleteAllTransactionToast = stringResource(id = R.string.delete_all_transactions_toast)
    val deleteAllProfilesToast = stringResource(id = R.string.delete_all_profiles_toast)
    val resetAllCategoriesToast = stringResource(id = R.string.reset_categories_toast)

    val languageText = LanguageText(LANGUAGE)

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val timeFormat = stringResource(id = languageText.timeFormat)
    val resetCategories = stringResource(id = languageText.resetCategories)
    val deleteAllTransactions = stringResource(id = languageText.deleteAllTransaction)
    val deleteProfile = stringResource(id = languageText.deleteProfile)

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
                text = stringResource(id = languageText.settings),
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


            languageText.settingsList.forEach { setting ->
                SettingsItems(
                    title = setting.key,
                    icon = setting.value,
                    enableTitle = stringResource(id = languageText.timeFormat),
                    enable = time24Hours,
                    interactionSource = interactionSource,
                    onClick = { value, enableValue ->
                        settingSelected = value
                        when (value) {
                            timeFormat -> {
                                profileViewModel.updateTime24Hours(
                                    time24Hours = enableValue
                                )
                            }
                            deleteAllTransactions -> {
                                alertDialogShow = true
                            }
                            deleteProfile -> {
                                alertDialogShow = true
                            }
                            resetCategories -> {
                                alertDialogShow = true
                            }
                        }
                    }
                )
            }


            when (settingSelected) {
                deleteAllTransactions -> {
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
                            Toast.makeText(context, deleteAllTransactionToast, Toast.LENGTH_LONG).show()
                        },
                        languageText = languageText
                    )
                }
                deleteProfile -> {
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
                            )
                            profileViewModel.deleteAllProfiles()
                            Toast.makeText(context, deleteAllProfilesToast, Toast.LENGTH_LONG).show()
                            navController.popBackStack()
                            navController.navigate(route = SetUpNavRoute.SplashScreenSetUpNavRoute.route)
                            DARK_THEME_ENABLE = true
                        },
                        languageText = languageText
                    )

                }

                resetCategories -> {
                    DisplayAlertDialog(
                        title = stringResource(id = R.string.reset_categories_title),
                        message = stringResource(id = R.string.reset_categories_message),
                        openDialog = alertDialogShow,
                        captchaVerification = true,
                        onCloseClicked = {
                            alertDialogShow = false
                            settingSelected = ""
                        },
                        onYesClicked = {
                            profileViewModel.deleteAllCategories()
                            deleteAllCategories = true
                        },
                        languageText = languageText
                    )
                }
            }


        }
    }

    if (deleteAllCategories) {
        val separator = ","
        Constants.SUB_CATEGORY.forEach {
            profileViewModel.addCategory(
                CategoryModel(
                    category = it.key,
                    subCategory = it.value.joinToString(separator)
                )
            )
        }
        Toast.makeText(context, resetAllCategoriesToast, Toast.LENGTH_LONG).show()
        deleteAllCategories = false
    }

}

@Composable
fun SettingsItems(
    title: Int,
    icon: Int,
    enableTitle: String,
    enable: Boolean = false,
    interactionSource: MutableInteractionSource,
    onClick: (value: String, enableValue: Boolean) -> Unit
) {

    val text = stringResource(id = title)

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
                        if (text != enableTitle) {
                            onClick(text, enable)
                        }
                    }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = TEXT_FIELD_SIZE,
                color = textColorBW
            )

            if (text == enableTitle) {
                CustomSwitchButton(
                    switchPadding = 3.dp,
                    buttonSizeWidth = 60.dp,
                    buttonSizeHeight = 35.dp,
                    darkThemeEnable = enable
                ) {
                    onClick(text, it)
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