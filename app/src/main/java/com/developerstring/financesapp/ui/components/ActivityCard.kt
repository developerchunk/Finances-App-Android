package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developerstring.financesapp.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.financesapp.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.financesapp.util.dataclass.ActivityCardData

@Composable
fun ActivityCardItems(
    activityCardData: ActivityCardData,
    size: Size,
    imageCard: Dp,
    cardCorner: Dp,
    imageScale: ContentScale,
    iconCardTopPadding: Dp,
    onClick: (key: String) -> Unit,
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Card(
        modifier = Modifier
            .width(size.width.dp)
            .height(size.height.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(activityCardData.key)
                }
            ),
        backgroundColor = activityCardData.cardColor,
        elevation = 10.dp,
        shape = RoundedCornerShape(cardCorner),
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = com.developerstring.financesapp.R.drawable.transaction_activity),
                contentDescription = "",
                colorFilter = ColorFilter.tint(activityCardData.bgColor),
                contentScale = imageScale
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = iconCardTopPadding)
                    .background(color = Color.Transparent),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    modifier = Modifier.size(imageCard),
                    shape = CircleShape,
                    backgroundColor = Color.White,
                    elevation = 10.dp,

                    ) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .width(32.dp)
                                .height(28.dp),
                            painter = painterResource(id = activityCardData.icon),
                            contentDescription = ""
                        )
                    }

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent)
                        .padding(start = 15.dp, bottom = 15.dp),
                    contentAlignment = Alignment.CenterStart
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(0.8f),
                        text = activityCardData.text,
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = TEXT_FIELD_SIZE,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }
        }

    }

}