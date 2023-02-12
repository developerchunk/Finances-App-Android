package com.developerstring.finspare.screen.navscreens

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.ui.components.CustomSwitchButton
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.DARK_THEME
import com.developerstring.finspare.util.Constants.DARK_THEME_ENABLE
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.Constants.LIGHT_THEME
import com.developerstring.finspare.util.LanguageText

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    profileViewModel.getProfileDetails()
    profileViewModel.getAllCategories()
    profileViewModel.getTime24Hours()

    val languageText = LanguageText(LANGUAGE)

    val profileName by profileViewModel.profileName.collectAsState()
    val darkThemeEnable by profileViewModel.profileTheme.collectAsState()

    val scrollState = rememberScrollState()

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .verticalScroll(state = scrollState)
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {

            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(contentColorLBSD)
            )

            Text(
                modifier = Modifier
                    .padding(start = 30.dp, top = 20.dp)
                    .fillMaxWidth(),
                text = stringResource(id = languageText.profile),
                fontFamily = fontInter,
                fontWeight = FontWeight.Bold,
                color = textColorBW,
                fontSize = EXTRA_LARGE_TEXT_SIZE
            )

            Card(
                modifier = Modifier
                    .padding(top = 90.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(150.dp),
                elevation = 10.dp,
                shape = RoundedCornerShape(15.dp),
                backgroundColor = contentColorCard
            ) {

                Row(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxSize()
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        val (text, card) = createRefs()

                        Card(
                            modifier = Modifier
                                .size(110.dp)
                                .constrainAs(card) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(parent.end)
                                },
                            backgroundColor = LighterBlue,
                            shape = RoundedCornerShape(60.dp),
                        ) {

                            Box(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = profileName.first().toString().uppercase(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 50.sp,
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Bold,
                                    color = Dark,
                                )
                            }

                        }

                        Text(
                            modifier = Modifier
                                .width(screenWidth / 3)
                                .constrainAs(text) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                },
                            text = profileName,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = LARGE_TEXT_SIZE,
                            color = textColorBW,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                    }
                }

            }

        }

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        ) {
            languageText.profileContentList.forEach {
                ProfileOptionsContent(
                    title = it.key,
                    icon = it.value,
                    profileViewModel = profileViewModel,
                    darkThemeEnable = when(darkThemeEnable) {
                        DARK_THEME -> true
                        LIGHT_THEME -> false
                        else -> true
                    },
                    darkThemeSection = it.key==languageText.darkTheme
                ) { title ->
                    when (title) {
                        languageText.profile -> {
                            navController.navigate(NavRoute.EditProfileScreen.route)
                        }
                        languageText.languageText -> {
                            navController.navigate(NavRoute.EditLanguageScreen.route)
                        }
                        languageText.settings -> {
                            navController.navigate(NavRoute.SettingScreen.route)
                        }
                        languageText.about -> {
                            navController.navigate(NavRoute.AboutScreen.route)
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun ProfileOptionsContent(
    title: Int,
    icon: Int,
    profileViewModel: ProfileViewModel,
    darkThemeEnable: Boolean,
    darkThemeSection: Boolean,
    onClick: (Int) -> Unit,
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
                        onClick(title)
                    }
                ),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = title),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = TEXT_FIELD_SIZE,
                color = textColorBW
            )

            if (darkThemeSection) {
                CustomSwitchButton(
                    switchPadding = 3.dp,
                    buttonSizeWidth = 60.dp,
                    buttonSizeHeight = 35.dp,
                    darkThemeEnable = darkThemeEnable
                ) {
                    profileViewModel.saveThemeSetting(
                        theme = when(it) {
                            true -> DARK_THEME
                            false -> LIGHT_THEME
                        }
                    )
                    DARK_THEME_ENABLE = it
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