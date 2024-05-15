package com.developerstring.finspare.screen.autopayment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.ui.components.CustomAppBar
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.colorGray
import com.developerstring.finspare.ui.theme.contentBackgroundColor
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.util.Constants

@Composable
fun MessagePaymentInfoScreen(
    navController: NavController
) {

    val scrollState = rememberScrollState()

    Scaffold(topBar = {
        CustomAppBar(
            navController = navController,
            text = "Auto Payment"
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(backgroundColor)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier.padding(30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 50.dp),
                    painter =
                    painterResource(
                        id = if (Constants.DARK_THEME_ENABLE) R.drawable.message_access_dark
                        else R.drawable.message_access
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Fit
                )

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = MEDIUM_TEXT_SIZE,
                    color = colorGray,
                    textAlign = TextAlign.Center,
                    text = "This Payment system uses your Mobile Messages to detect the Bank Messages and note the Payment Credited or Debited to your account. \n\nAll your Messages will be private to you and your local device, and this means we don't store or read your messages on our servers as we are a totally local/offline app, data is only specific to your local device. To Access, this feature will require the Message Reading Permission of your phone. \n\nNote: FinSpare app is a local/offline app, which means we don't use internet to store your data, your data is stored locally on your device storage, stating that we don't collect/store/read your data on our servers. For more info please visit our terms and conditions page. Thank You"
                )

                // next Button
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 30.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier
                            .width(230.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = contentBackgroundColor
                        ),
                        shape = RoundedCornerShape(25.dp),
                        onClick = {
                           navController.navigate(NavRoute.TermsAndConditionsScreen.route)
                        },
                    ) {
                        Text(text = "Terms and Condition", color = Color.White, fontSize = 16.sp)
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.15f)
                    )

                }

            }

        }
    }

}