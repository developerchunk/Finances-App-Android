package com.developerstring.finspare.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.developerstring.finspare.ui.theme.LightGray
import com.developerstring.finspare.ui.theme.UIBlue

@Composable
fun CustomSwitchButton(
    switchPadding: Dp,
    buttonSizeWidth: Dp,
    buttonSizeHeight: Dp,
    darkThemeEnable: Boolean,
    darkTheme: (Boolean) -> Unit
) {
    val switchSize by remember {
        mutableStateOf(buttonSizeHeight-switchPadding*2)
    }
    val interactionSource = remember { MutableInteractionSource() }

    var switchClicked by remember { mutableStateOf(darkThemeEnable) }

    var internalPadding by remember {
        mutableStateOf(0.dp)
    }

    internalPadding = if (switchClicked) buttonSizeWidth-switchSize-switchPadding*2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (switchClicked) internalPadding else 0.dp ,
         tween(
            durationMillis = 700,
            delayMillis = 0,
             easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .width(buttonSizeWidth)
            .height(buttonSizeHeight)
            .clip(CircleShape)
            .background(if (switchClicked) UIBlue else LightGray)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                switchClicked = !switchClicked
                darkTheme(switchClicked)
            },
    ) {

        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(animateSize)
                .background(Color.Transparent)
            )
            Box(
                modifier = Modifier
                    .padding(switchPadding)
                    .size(switchSize)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }

    }

}