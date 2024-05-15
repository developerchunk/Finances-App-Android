package com.developerstring.finspare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developerstring.finspare.ui.theme.Green
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW

@Composable
fun LanguageScreenItem(
    title: String,
    selected: String,
    interactionSource: MutableInteractionSource,
    onClick: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(start = 30.dp, bottom = 40.dp, end = 30.dp)
            .background(backgroundColor)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(title)
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (title == selected) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(7.dp)
                        .background(
                            color = Green,
                            shape = CircleShape
                        )
                )
            }
            Text(
                text = title,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = MEDIUM_TEXT_SIZE,
                color = textColorBW
            )
        }


    }

}