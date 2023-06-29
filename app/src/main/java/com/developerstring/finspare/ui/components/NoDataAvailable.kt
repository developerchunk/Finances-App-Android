package com.developerstring.finspare.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developerstring.finspare.R
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants

@Composable
fun NoDataAvailable(
    title: String = "No Data Available!"
) {
    var launched by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        launched = true
    }
    AnimatedVisibility(visible = launched, enter = fadeIn()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = if (Constants.DARK_THEME_ENABLE) R.drawable.no_data_dark_theme else R.drawable.no_data_light_theme),
                contentDescription = title
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = title,
                fontSize = MEDIUM_TEXT_SIZE,
                fontFamily = fontInter,
                color = textColorBW,
                textAlign = TextAlign.Center
            )
        }
    }

}