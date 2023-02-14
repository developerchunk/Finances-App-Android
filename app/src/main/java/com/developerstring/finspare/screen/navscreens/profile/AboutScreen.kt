package com.developerstring.finspare.screen.navscreens.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.LanguageText

@Composable
fun AboutScreen(
    navController: NavController
) {

    val verticalScrollState = rememberScrollState()
    val languageText = LanguageText(LANGUAGE)
    val interactionSource = remember {
        MutableInteractionSource()
    }

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
                text = stringResource(id = languageText.about),
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
                .verticalScroll(verticalScrollState)
                .padding(it)
                .padding(top = 20.dp)
        ) {

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
                                navController.navigate(route = NavRoute.TermsAndConditionsScreen.route)
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = languageText.termsAndConditions),
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = TEXT_FIELD_SIZE,
                        color = textColorBW
                    )

                        Image(
                            modifier = Modifier.size(34.dp),
                            painter = painterResource(id = R.drawable.terms_and_conditions_logo),
                            contentDescription = "icon",
                            colorFilter = ColorFilter.tint(color = textColorBW)
                        )



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
    }

}