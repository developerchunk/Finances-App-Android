package com.developerstring.finspare.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_ELEVATION
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.util.state.MessageBarState
import kotlinx.coroutines.delay

@Composable
fun MessageBar(
    profileAmountType: String = "",
    height: Dp = TOP_APP_BAR_HEIGHT,
    message: String,
    publicSharedViewModel: PublicSharedViewModel,
    action_type: String,
    action: () -> Unit,
) {

    var animated by publicSharedViewModel.messageShow

    LaunchedEffect(key1 = true) {
        animated = true
        delay(5000)
        animated = false
        delay(500)
        publicSharedViewModel.messageBarState.value = MessageBarState.CLOSED
    }

    val heightAnimated by animateDpAsState(
        targetValue = if (animated) height else 0.dp,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing,
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(heightAnimated),
        elevation = TOP_APP_BAR_ELEVATION,
        color = UIBlue
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (icon, text1) = createRefs()

            IconButton(
                modifier = Modifier.constrainAs(icon) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                onClick = {
                    if (profileAmountType.isEmpty()) {
                        action()
                    }
                }
            ) {
                if (profileAmountType.isEmpty()) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "delete",
                        tint = Color.White,
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(icon.start)
                    },
                text = "${if (action_type.isNotEmpty()) "$action_type " else "" }$message",
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = TEXT_FIELD_SIZE,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                maxLines = 1
            )
        }

    }

}