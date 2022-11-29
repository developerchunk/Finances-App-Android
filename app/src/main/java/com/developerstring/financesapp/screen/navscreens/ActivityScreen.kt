package com.developerstring.financesapp.screen.navscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.ActivityCardItems
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.util.Constants.ActivityCardContent
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun ActivityScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
) {

    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(backgroundColor)
            .padding(top = 30.dp),
        mainAxisSpacing = 18.dp,
        crossAxisSpacing = 18.dp,
        crossAxisAlignment = FlowCrossAxisAlignment.Center,
        mainAxisAlignment = MainAxisAlignment.Center
    ) {

        ActivityCardContent.forEach {
            ActivityCardItems(
                it,
                size = Size(width = 155f, height = 210f),
                imageCard = 70.dp,
                cardCorner = 20.dp,
                imageScale = ContentScale.FillBounds,
                iconCardTopPadding = 20.dp
            ) { key ->
                navController.navigate(route = key)
            }
        }

    }

}