package com.developerstring.financesapp.screen.navscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.ui.theme.fontInter

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor)
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .width(160.dp)
                    .height(35.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    navController.navigate(NavRoute.AddTransactionScreen.route)
                }
            ) {
                Text(text = "Add Transaction", fontFamily = fontInter, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            }
            Button(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .width(160.dp)
                    .height(35.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    navController.navigate(NavRoute.AddTransactionScreen.route)
                }
            ) {
                Text(text = "View History", fontFamily = fontInter, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            }
        }
    }

}