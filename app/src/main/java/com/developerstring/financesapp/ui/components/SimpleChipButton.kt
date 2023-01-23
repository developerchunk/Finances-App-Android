package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.keyToTransactionType

@Composable
fun SimpleChipButton(
    text: String,
    select: String,
    onClick: (String) -> Unit
) {

    val selected: Boolean = text == select

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Surface(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(text)
                }
            )
            .padding(vertical = 5.dp),
        color = if (selected) LightestBlue else contentColorCard,
        border = BorderStroke(
            width = if (selected) 0.dp else 1.dp,
            color = if (selected) Color.Transparent else SlightGray
        ),
        shape = RoundedCornerShape(5.dp),
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = 7.dp, horizontal = 12.dp)
                .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text.keyToTransactionType(),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = MEDIUM_TEXT_SIZE,
                color = if (selected) DarkBlue else colorLightGray,
                textAlign = TextAlign.Center
            )
        }

    }

}