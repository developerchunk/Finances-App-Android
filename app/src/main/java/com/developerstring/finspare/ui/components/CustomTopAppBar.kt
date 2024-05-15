package com.developerstring.finspare.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_ELEVATION
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.backgroundColorBW
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW

@Composable
fun CustomAppBar(
    navController: NavController,
    text: String
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = TOP_APP_BAR_ELEVATION,
        color = backgroundColorBW
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_APP_BAR_HEIGHT)
                    .padding(start = 10.dp),
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
                    text = text,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = LARGE_TEXT_SIZE,
                    color = textColorBW,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }


        }

    }
}