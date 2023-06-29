package com.developerstring.finspare.screen.charts

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
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
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.ProfileListReturn
import com.developerstring.finspare.util.formatNumberingStyle
import com.developerstring.finspare.util.profileAmountChartData
import com.developerstring.finspare.util.refineProfileModel
import com.developerstring.finspare.util.state.ProfileAmountType

@Composable
fun AmountChartScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel
) {

    profileViewModel.getProfileDetails()

    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()
    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()
    val allProfiles by profileViewModel.allProfiles.collectAsState()

    var profiles = mutableListOf<ProfileModel>()

    val languageText = LanguageText(LANGUAGE)

    val context = LocalContext.current

    ProfileListReturn(
        profiles = allProfiles,
        profileList = {
            profiles = it.refineProfileModel() as MutableList<ProfileModel>
        }
    )

    val profilesDataMap: MutableMap<String, Long> = profileAmountChartData(
        totalAmount = totalAmount,
        profiles = profiles,
        context = context,
        languageText = languageText,
    ) as MutableMap<String, Long>

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
                .background(backgroundColor)
                .verticalScroll(rememberScrollState())
        ) {

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
                    text = stringResource(id = languageText.totalBalance),
                    fontSize = EXTRA_SMALL_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
            ) {
                PieChart(
                    data = profilesDataMap,
                    currency = currency,
                    subTextEdit = true,
                    subText = stringResource(id = languageText.amountTextField).filterNot { text -> text == '*' },
                    onItemClick = { value ->
                        when (value) {
                            context.getString(languageText.moneyTaken) -> {
                                profileViewModel.sortedProfiles = profiles.filter { contact ->
                                    contact.amount_type == ProfileAmountType.MONEY_TAKEN.name
                                } as MutableList<ProfileModel>
                                navController.navigate(NavRoute.ProfileAmountChartScreen.route)
                            }

                            context.getString(languageText.moneyGiven) -> {
                                profileViewModel.sortedProfiles = profiles.filter { contact ->
                                    contact.amount_type == ProfileAmountType.MONEY_GIVEN.name
                                } as MutableList<ProfileModel>
                                navController.navigate(NavRoute.ProfileAmountChartScreen.route)
                            }
                        }
                    }
                )
            }
        }
    }


}