package com.developerstring.finspare.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.developerstring.finspare.navigation.bottomnav.BottomNavGraph
import com.developerstring.finspare.navigation.bottomnav.BottomNavRoute
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.backgroundColorBW
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.bottomNavText

@Composable
fun MainScreen(
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    profileViewModel.getProfileDetails()
    profileViewModel.getTime24Hours()

    val nav = rememberNavController()
    val language by profileViewModel.profileLanguage.collectAsState()
    LANGUAGE = language

    Scaffold(
        bottomBar = { BottomNavBar(navController = nav, language = language) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                BottomNavGraph(
                    navController = nav,
                    profileViewModel = profileViewModel,
                    sharedViewModel = sharedViewModel,
                    publicSharedViewModel = publicSharedViewModel
                )
            }
        }
    )

}

@Composable
fun BottomNavBar(
    navController: NavHostController,
    language: String
) {

    val screens = listOf(
        BottomNavRoute.Home,
        BottomNavRoute.Activity,
        BottomNavRoute.Profile
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        Surface(modifier = Modifier.fillMaxWidth(), elevation = 10.dp) {
            Row(
                modifier = Modifier
                    .background(backgroundColorBW)
                    .fillMaxWidth()
                    .padding(bottom = 5.dp, top = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController,
                        language = language
                    )
                }
            }
        }
    }

}

@Composable
fun AddItem(
    screen: BottomNavRoute,
    currentDestination: NavDestination?,
    navController: NavHostController,
    language: String
) {

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val contentColor =
        if (selected) UIBlue else textColorBW

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )
            Text(
                text = stringResource(id = screen.route.bottomNavText(language = language)),
                color = contentColor,
                fontWeight = FontWeight.Medium,
                fontFamily = fontInter
            )
        }
    }
}


