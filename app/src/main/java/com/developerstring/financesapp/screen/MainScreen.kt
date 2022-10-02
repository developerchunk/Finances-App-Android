package com.developerstring.financesapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.developerstring.financesapp.navigation.bottomnav.BottomNavGraph
import com.developerstring.financesapp.navigation.bottomnav.BottomNavRoute
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.ui.theme.backgroundColorBW
import com.developerstring.financesapp.ui.theme.textColorBW

@Composable
fun MainScreen(
    profileViewModel: ProfileViewModel,
    sharedViewModel: SharedViewModel
) {

    val nav = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = nav) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                BottomNavGraph(
                    navController = nav,
                    profileViewModel = profileViewModel,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    )

}

@Composable
fun BottomNavBar(
    navController: NavHostController,
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
                    .background(MaterialTheme.colors.backgroundColorBW)
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomNavRoute,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val contentColor =
        if (selected) UIBlue else MaterialTheme.colors.textColorBW

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
                text = screen.title,
                color = contentColor
            )
        }
    }
}


