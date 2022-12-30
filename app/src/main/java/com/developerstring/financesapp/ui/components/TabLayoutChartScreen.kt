package com.developerstring.financesapp.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developerstring.financesapp.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.financesapp.ui.theme.fontInter
import com.developerstring.financesapp.ui.theme.textColorBW

@Composable
fun TabLayoutChartScreen(
    text: Int,
    select: Int,
    onClick: (Int) -> Unit
) {

    val selected = (text==select)

    val bottomCard by animateDpAsState(
        targetValue = if (selected) 80.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .background(color = Color.Transparent)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(text)
                }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = text),
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium,
            fontSize = TEXT_FIELD_SIZE,
            color = textColorBW
        )

        Card(
            modifier = Modifier
                .padding(top = 5.dp)
                .width(bottomCard)
                .height(5.dp),
            shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp),
            backgroundColor = textColorBW
        ) {}

    }

}