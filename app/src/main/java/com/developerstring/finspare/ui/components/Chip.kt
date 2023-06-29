package com.developerstring.finspare.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.colorDarkGray
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.keyToTransactionType

@Composable
fun CustomChip(
    title: String,
    selected: String,
    onSelected: (String) -> Unit,
    image: ImageVector,
    key: Boolean,
    selectedColor: Color,
    color: Color,
    textColor: Color,
    selectedTextColor: Color,
    iconColor: Color
) {

    val isSelected = selected == title

    val background = if (isSelected) selectedColor else color
    val contentColor = if (isSelected) selectedTextColor else textColor

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    onSelected(title)
                },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = if (key) title.keyToTransactionType() else title,
                color = contentColor,
                fontSize = 16.sp,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = image,
                    contentDescription = "check",
                    tint = iconColor
                )
            }
        }
    }
}

@Composable
fun SearchChip(
    title: String,
    selected: String,
    onSelected: (String) -> Unit
) {

    val isSelected = selected == title

    val background = if (isSelected) UIBlue else colorDarkGray
    val contentColor = if (isSelected) Color.White else textColorBW

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    onSelected(title)
                },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, color = contentColor, fontSize = 16.sp)
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "check",
                    tint = Color.White
                )
            }
        }
    }
}