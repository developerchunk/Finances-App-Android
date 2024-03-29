package com.developerstring.finspare.screen.profilecreate

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.setupnav.SetUpNavRoute
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.components.LanguageScreenItem
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.ENGLISH
import com.developerstring.finspare.util.Constants.LANGUAGES

@Composable
fun LanguageScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val scrollState = rememberScrollState()
    var selected by remember {
        mutableStateOf(ENGLISH)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val buttonHeight = 45.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight - (105.dp))
                .verticalScroll(scrollState)
        ) {

            Text(
                modifier = Modifier.padding(start = 30.dp, top = 50.dp),
                text = stringResource(id = R.string.select_language),
                fontSize = LARGE_TEXT_SIZE,
                fontFamily = fontOpenSans,
                fontWeight = FontWeight.Medium,
                color = textColorBW
            )
            Text(
                modifier = Modifier.padding(start = 30.dp),
                text = stringResource(id = R.string.select_language_desc),
                fontSize = SMALL_TEXT_SIZE,
                fontFamily = fontOpenSans,
                fontWeight = FontWeight.Medium,
                color = textColorBW
            )

            Column(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .fillMaxWidth()
            ) {

                LANGUAGES.forEach {
                    LanguageScreenItem(
                        title = it,
                        selected = selected,
                        interactionSource = interactionSource,
                        onClick = { value ->
                            selected = value
                        }
                    )
                }

            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                    profileViewModel.saveProfileLanguage(
                        language_ = selected,
                        updateLanguage = false
                    )
                    navController.navigate(route = SetUpNavRoute.CreateProfileSetUpNavRoute.route)
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(buttonHeight),
                colors = ButtonDefaults.buttonColors(contentBackgroundColor),
                shape = RoundedCornerShape(25.dp),
            ) {
                Text(text = "Continue", color = Color.White, fontSize = 20.sp)
            }
        }
    }


}