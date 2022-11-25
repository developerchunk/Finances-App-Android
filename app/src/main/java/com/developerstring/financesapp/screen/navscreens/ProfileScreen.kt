package com.developerstring.financesapp.screen.navscreens

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.bottomnav.BottomNavRoute
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.screen.navscreens.content.profilescreen.CustomSwitchButton
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.DARK_THEME
import com.developerstring.financesapp.util.Constants.PROFILE
import com.developerstring.financesapp.util.Constants.PROFILE_CONTENT_LIST

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {

    val context = LocalContext.current

    profileViewModel.getProfileDetails(context = context)
    profileViewModel.getThemeSetting(context = context)

    val profileName by profileViewModel.profileName.collectAsState()
    val darkThemeEnable by profileViewModel.themeSetting.collectAsState()

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
                text = stringResource(id = R.string.profile),
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
                    modifier = Modifier.padding(horizontal = 30.dp).fillMaxSize()
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
                                .width(screenWidth/3)
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

//                Row(
//                    modifier = Modifier
//                        .padding(horizontal = 30.dp)
//                        .fillMaxSize(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = CenterVertically
//                ) {
//                }

            }

        }

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
        ) {
            PROFILE_CONTENT_LIST.forEach {
                ProfileOptionsContent(
                    title = it.key,
                    icon = it.value,
                    profileViewModel = profileViewModel,
                    context = context,
                    darkThemeEnable = darkThemeEnable
                ) { title ->
                    navController.navigate(
                        when (title) {
                            PROFILE -> NavRoute.EditProfileScreen.route
                            else -> BottomNavRoute.Profile.route
                        }
                    )
                }
            }
        }

    }

}

@Composable
fun ProfileOptionsContent(
    title: String,
    icon: Int,
    profileViewModel: ProfileViewModel,
    context: Context,
    darkThemeEnable: Boolean,
    onClick: (String) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(top = 20.dp, start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .clickable {
                    onClick(title)
                },
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = TEXT_FIELD_SIZE,
                color = textColorBW
            )

            if (title == DARK_THEME) {
                CustomSwitchButton(
                    switchPadding = 3.dp,
                    buttonSizeWidth = 60.dp,
                    buttonSizeHeight = 35.dp,
                    darkThemeEnable = darkThemeEnable
                ) {
                    profileViewModel.saveThemeSetting(
                        context = context,
                        darkTheme = it
                    )
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