package com.developerstring.finspare.screen.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.screen.navscreens.profile.ContactItem
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.components.NoDataAvailable
import com.developerstring.finspare.ui.components.PieChart
import com.developerstring.finspare.ui.theme.EXTRA_LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.EXTRA_SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_ELEVATION
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.backgroundColorBW
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.formatNumberingStyle
import com.developerstring.finspare.util.profilesToPieChartData
import com.developerstring.finspare.util.state.ContactActionState
import com.developerstring.finspare.util.state.ProfileAmountType

@Composable
fun ProfileAmountChartScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    val profiles = profileViewModel.sortedProfiles

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val scrollState = rememberScrollState()
    val languageText = LanguageText(Constants.LANGUAGE)

    val profileEmpty by remember {
        mutableStateOf(profiles.isEmpty())
    }

    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()

    val profilesMapData: MutableMap<String, Long> = profilesToPieChartData(profiles)

    val totalAmount by remember {
        mutableStateOf(profiles.sumOf { it.total_amount })
    }

    val amountType by remember {
        mutableStateOf(profiles.first().amount_type)
    }

    Scaffold(topBar = {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = TOP_APP_BAR_ELEVATION,
            color = backgroundColorBW
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_APP_BAR_HEIGHT)
                    .padding(start = 10.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        modifier = Modifier
                            .width(28.dp)
                            .height(24.dp),
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "back_arrow",
                        tint = textColorBW
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(id = languageText.totalBalance),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = LARGE_TEXT_SIZE,
                    color = textColorBW,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(backgroundColor),
            verticalArrangement = if (profileEmpty) Arrangement.Center else Arrangement.Top,
            horizontalAlignment = if (profileEmpty) Alignment.CenterHorizontally else Alignment.Start,
        ) {

            if (profiles.isEmpty()) {

                NoDataAvailable(title = "No Contacts Available!")

            } else {

                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 40.dp, end = 20.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        text = "$currency ${totalAmount.formatNumberingStyle(currency)}",
                        fontSize = EXTRA_LARGE_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        color = textColorBW
                    )
                    Text(
                        text = stringResource(
                            id = if (amountType == ProfileAmountType.MONEY_TAKEN.name) languageText.moneyTaken
                            else languageText.moneyGiven
                        ),
                        fontSize = EXTRA_SMALL_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        color = textColorBW
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, bottom = 20.dp)
                ) {
                    PieChart(
                        data = profilesMapData,
                        currency = Constants.INDIAN_CURRENCY,
                        onItemClick = {},
                        pieChartDetails = false,
                        radiusOuter = 70.dp,
                        chartBarWidth = 17.dp
                    )
                }

                profiles.forEach { contact ->
                        ContactItem(
                            profileModel = contact,
                            interactionSource = interactionSource,
                            languageText = languageText,
                            onClick = {
                                profileViewModel.contactActionState.value = ContactActionState.UPDATE
                                profileViewModel.contact.value = contact
                                profileViewModel.contactID.value = 1
                                navController.navigate(NavRoute.EditContactsDetailScreen.route)
                            }
                        )
                    }
                }
            }

        }
    }


