package com.developerstring.financesapp.ui.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.developerstring.financesapp.R
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.ui.theme.fontInter
import com.developerstring.financesapp.ui.theme.textColorBW
import com.developerstring.financesapp.util.Constants.MONTHS
import com.google.accompanist.flowlayout.FlowCrossAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment

@Composable
fun MonthYearPickerCalender(
    visible: Boolean,
    month_: Int,
    year_: Int,
    confirmClicked: (month: Int, year: Int) -> Unit,
    cancelClicked: () -> Unit
) {

    var month by remember {
        mutableStateOf(MONTHS[month_ - 1])
    }

    var year by remember {
        mutableStateOf(year_)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }


    if (visible) {
        Dialog(
            onDismissRequest = {
                cancelClicked()
            },
            content = {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .background(color = backgroundColor, shape = RoundedCornerShape(20.dp)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year--
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "arrow",
                            tint = textColorBW
                        )

                        Text(
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                            text = year.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW,
                            fontFamily = fontInter
                        )

                        Icon(
                            modifier = Modifier
                                .size(35.dp)
                                .rotate(-90f)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year++
                                    }
                                ),
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "arrow",
                            tint = textColorBW
                        )
                    }

                    Card(
                        modifier = Modifier
                            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp,
                        backgroundColor = backgroundColor
                    ) {

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            mainAxisSpacing = 16.dp,
                            crossAxisSpacing = 16.dp,
                            mainAxisAlignment = MainAxisAlignment.Center,
                            crossAxisAlignment = FlowCrossAxisAlignment.Center
                        ) {

                            MONTHS.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = {
                                                month = it
                                            }
                                        )
                                        .background(
                                            color = Color.Transparent,
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {

                                    val animatedSize by animateDpAsState(
                                        targetValue = if (month == it) 60.dp else 0.dp,
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing
                                        )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(
                                                animatedSize
                                            )
                                            .background(
                                                color = UIBlue,
                                                shape = CircleShape
                                            )
                                    )

                                    Text(
                                        text = it,
                                        color = if (month == it) Color.White else textColorBW,
                                        fontFamily = fontInter,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        horizontalArrangement = Arrangement.End
                    ) {

                        OutlinedButton(
                            modifier = Modifier.padding(end = 10.dp, bottom = 30.dp),
                            onClick = {
                                cancelClicked()
                            },
                            shape = CircleShape,
                            border = BorderStroke(1.dp, color = Color.Transparent),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(id = R.string.cancel),
                                color = textColorBW,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = fontInter
                            )
                        }

                        OutlinedButton(
                            modifier = Modifier.padding(end = 20.dp, bottom = 30.dp),
                            onClick = {
                                confirmClicked(MONTHS.indexOf(month) + 1, year)
                            },
                            shape = CircleShape,
                            border = BorderStroke(1.dp, color = UIBlue),
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
                        ) {
                            Text(
                                text = stringResource(id = R.string.ok),
                                color = UIBlue,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = fontInter
                            )
                        }
                    }

                }
            }

        )
    }


}

//AlertDialog(
//backgroundColor = backgroundColor,
//shape = RoundedCornerShape(10),
//title = {
//
//},
//text = {
//    Column(
//        modifier = Modifier.verticalScroll(rememberScrollState()),
//    ){
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(
//                modifier = Modifier
//                    .size(35.dp)
//                    .rotate(90f)
//                    .clickable(
//                        indication = null,
//                        interactionSource = interactionSource,
//                        onClick = {
//                            year--
//                        }
//                    ),
//                imageVector = Icons.Rounded.KeyboardArrowDown,
//                contentDescription = "arrow",
//                tint = textColorBW
//            )
//
//            Text(
//                modifier = Modifier.padding(start = 20.dp, end = 20.dp),
//                text = year.toString(),
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                color = textColorBW
//            )
//
//            Icon(
//                modifier = Modifier
//                    .size(35.dp)
//                    .rotate(-90f)
//                    .clickable(
//                        indication = null,
//                        interactionSource = interactionSource,
//                        onClick = {
//                            year++
//                        }
//                    ),
//                imageVector = Icons.Rounded.KeyboardArrowDown,
//                contentDescription = "arrow",
//                tint = textColorBW
//            )
//        }
//
//        Card(
//            modifier = Modifier
//                .padding(top = 30.dp)
//                .fillMaxWidth(),
//            elevation = 0.dp,
//            backgroundColor = backgroundColor
//        ) {
//
//            FlowRow(
//                modifier = Modifier.fillMaxWidth(),
//                mainAxisSpacing = 16.dp,
//                crossAxisSpacing = 16.dp,
//                mainAxisAlignment = MainAxisAlignment.Center,
//                crossAxisAlignment = FlowCrossAxisAlignment.Center
//            ) {
//
//                MONTHS.forEach {
//                    Box(
//                        modifier = Modifier
//                            .size(60.dp)
//                            .clickable(
//                                indication = null,
//                                interactionSource = interactionSource,
//                                onClick = {
//                                    month = it
//                                }
//                            )
//                            .background(
//                                color = Color.Transparent,
//                            ),
//                        contentAlignment = Alignment.Center
//                    ) {
//
//                        val animatedSize by animateDpAsState(
//                            targetValue = if (month == it) 60.dp else 0.dp,
//                            animationSpec = tween(
//                                durationMillis = 500,
//                                easing = LinearOutSlowInEasing
//                            )
//                        )
//
//                        Box(
//                            modifier = Modifier
//                                .size(
//                                    animatedSize
//                                )
//                                .background(
//                                    color = if (month == it) UIBlue else Color.White,
//                                    shape = CircleShape
//                                )
//                        )
//
//                        Text(
//                            text = it,
//                            color = if (month == it) Color.White else textColorBW,
//                        )
//                    }
//                }
//
//            }
//
//        }
//
//    }
//},
//confirmButton = {
//
//    OutlinedButton(
//        modifier = Modifier.padding(end = 20.dp, bottom = 30.dp),
//        onClick = {
//            confirmClicked(MONTHS.indexOf(month) + 1, year)
//        },
//        shape = CircleShape,
//        border = BorderStroke(1.dp, color = UIBlue),
//        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
//    ) {
//        Text(
//            text = "Ok",
//            color = UIBlue,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Medium
//        )
//    }
//
//},
//dismissButton = {
//    OutlinedButton(
//        modifier = Modifier.padding(end = 10.dp, bottom = 30.dp),
//        onClick = {
//            cancelClicked()
//        },
//        shape = CircleShape,
//        border = BorderStroke(1.dp, color = Color.Transparent),
//        colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
//    ) {
//        Text(
//            text = "Cancel",
//            color = textColorBW,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Medium
//        )
//    }
//},
//onDismissRequest = {
//
//}
//)