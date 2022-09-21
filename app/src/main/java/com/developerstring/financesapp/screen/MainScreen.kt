package com.developerstring.financesapp.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.ui.theme.textColorBW

@Composable
fun MainScreen(
    sharedViewModel: SharedViewModel,
) {

    val nav = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController = nav) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
            ) {
                BottomNavGraph(navController = nav, sharedViewModel = sharedViewModel)
            }
        }
    )

}

@Composable
fun BottomNavBar(
    navController: NavHostController
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
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.backgroundColor)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
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

@Composable
fun RowScope.AddItem(
    screen: BottomNavRoute,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background =
        if (selected) UIBlue else Color.Transparent

    val contentColor =
        if (selected) Color.White else MaterialTheme.colors.textColorBW

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}
