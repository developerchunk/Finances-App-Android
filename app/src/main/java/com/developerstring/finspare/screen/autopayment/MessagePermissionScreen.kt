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
import com.developerstring.finspare.ui.components.CustomAppBar
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.colorGray
import com.developerstring.finspare.ui.theme.contentBackgroundColor
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.util.Constants.DARK_THEME_ENABLE

@Composable
fun MessagePermissionScreen(
    navController: NavController,
    permissionRequested: (Boolean) -> Unit
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
                        .size(200.dp)
                        .padding(bottom = 50.dp),
                    painter =
                    painterResource(
                        id = if (DARK_THEME_ENABLE) R.drawable.message_access_dark
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
                    text = "This Payment system uses your Mobile Messages to detect the Bank Messages and note the Payment Credited or Debited to your account. All your Messages will be private to you and your local device, and we don't store or read your messages on our servers as we are a totally local/offline app, data is only specific to your local device. To Access, this feature will require the Message Reading Permission of your phone."
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
                            .width(220.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = contentBackgroundColor
                        ),
                        shape = RoundedCornerShape(25.dp),
                        onClick = {
                                  permissionRequested(true)
                        },
                    ) {
                        Text(text = "Next", color = Color.White, fontSize = 20.sp)
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