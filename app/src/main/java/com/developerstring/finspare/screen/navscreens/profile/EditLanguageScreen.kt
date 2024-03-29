package com.developerstring.finspare.screen.navscreens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.components.LanguageScreenItem
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.Constants.LANGUAGES
import com.developerstring.finspare.util.LanguageText

@Composable
fun EditLanguageScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val language by profileViewModel.profileLanguage.collectAsState()

    var selected by remember {
        mutableStateOf(language)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val languageText = LanguageText(LANGUAGE)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT)
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close",
                    tint = textColorBW
                )
            }

            Text(
                text = stringResource(id = languageText.languageText),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW
            )

            IconButton(onClick = {
                if (selected != language) {
                    profileViewModel.saveProfileLanguage(
                        language_ = selected,
                        updateLanguage = true
                    )
                    LANGUAGE = selected
                }
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "check",
                    tint = textColorBW
                )
            }

        }


        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
                .background(backgroundColor)
        ) {

            LANGUAGES.forEach {
                LanguageScreenItem(
                    title = it,
                    selected = selected,
                    interactionSource = interactionSource,
                    onClick = { value ->
                        selected = value
                    })
            }

        }
    }
}