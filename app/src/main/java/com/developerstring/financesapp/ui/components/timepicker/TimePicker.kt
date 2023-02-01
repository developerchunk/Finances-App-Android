package com.developerstring.financesapp.ui.components.timepicker

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.developerstring.financesapp.ui.theme.LighterUIBlue
import com.developerstring.financesapp.ui.theme.UIBlue
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.developerstring.financesapp.ui.theme.textColorBW
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TimePicker(
    visible: Boolean,
    currentMeridiem: Meridiem,
    interactionSource: MutableInteractionSource,
    currentHour: Int,
    currentMinute1: Int,
    currentMinute2: Int,
    configuration: Configuration,
    onSelected: (String,Meridiem) -> Unit,
    onCanceled: () -> Unit
) {

    var format24 by remember {
        mutableStateOf(currentMeridiem == Meridiem.HOUR24)
    }

    var launched by remember {
        mutableStateOf(false)
    }

    var hourChanged by remember {
        mutableStateOf(false)
    }

    var hour by remember {
        mutableStateOf(currentHour)
    }

    var timePicked by remember {
        mutableStateOf(TimePick.HOUR)
    }

    val width by remember {
        mutableStateOf(configuration.screenWidthDp)
    }

    var minute1 by remember {
        mutableStateOf(currentMinute1)
    }

    var minute2 by remember {
        mutableStateOf(currentMinute2)
    }

    var minuteSelect by remember {
        mutableStateOf(MinuteSelected.FIRST)
    }

    var meridiem by remember {
        mutableStateOf(currentMeridiem)
    }

    val height = ((width / 6f) * 5)

    val disableMinutes1 = (6..9).toList()

    val scope = rememberCoroutineScope()

    val meridiemList = listOf(Meridiem.HOUR24, Meridiem.AM, Meridiem.PM)

    LaunchedEffect(key1 = true) {
        launched = true
    }

    if (visible) {
        Dialog(
            onDismissRequest = {
                               onCanceled()
            },
            content = {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .background(color = backgroundColor, shape = RoundedCornerShape(20.dp))
                        .padding(vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            10.dp,
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {

                        TimePickerText(
                            hour = hour,
                            minute = Pair(minute1, minute2),
                            timePicked = TimePick.HOUR,
                            selected = timePicked == TimePick.HOUR,
                            interactionSource = interactionSource,
                            onClick = {
                                timePicked = TimePick.HOUR
                            },
                        )

                        Text(
                            text = ":",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = textColorBW
                        )

                        TimePickerText(
                            hour = hour,
                            minute = Pair(minute1, minute2),
                            timePicked = TimePick.MINUTE,
                            minuteSelected = minuteSelect,
                            selected = timePicked == TimePick.MINUTE,
                            interactionSource = interactionSource,
                            onClick = {
                                timePicked = TimePick.MINUTE
                                launched = false
                            }
                        )

                    }

                    MeridiemPick(
                        meridiem = meridiemList,
                        selected = meridiem,
                        interactionSource = interactionSource,
                        paddingValues = PaddingValues(vertical = 10.dp),
                        onSelected = {
                            convertToMeridiemTime(
                                hours = hour,
                                oldMeridiem = meridiem,
                                currentMeridiem = it,
                                time = { time ->
                                    hour = time.first
                                    meridiem = time.second
                                }
                            )
                            format24 = meridiem == Meridiem.HOUR24
                            hourChanged = true
                        }
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        when (timePicked) {
                            TimePick.HOUR -> {
                                if (hourChanged) {
                                    ClockTimePicker(
                                        hour = hour,
                                        format24 = format24,
                                        clickedHour = {
                                            hour = it
                                            if (launched) {
                                                scope.launch {
                                                    timePicked =
                                                        delayTransactionPeriod(TimePick.MINUTE)
                                                }
                                                launched = false
                                            }
                                        }
                                    )
                                    hourChanged = false
                                } else {
                                    ClockTimePicker(
                                        hour = hour,
                                        format24 = format24,
                                        clickedHour = {
                                            hour = it
                                            if (launched) {
                                                scope.launch {
                                                    timePicked =
                                                        delayTransactionPeriod(TimePick.MINUTE)
                                                }
                                                launched = false
                                            }
                                        }
                                    )
                                }

                            }

                            TimePick.MINUTE -> {
                                MinutePicker(
                                    disable = minuteSelect == MinuteSelected.FIRST,
                                    disableMinutes = disableMinutes1,
                                    screenWidth = width,
                                    minutes = {
                                        when (minuteSelect) {
                                            MinuteSelected.FIRST -> {
                                                minute1 = it
                                                minuteSelect = MinuteSelected.SECOND
                                            }
                                            MinuteSelected.SECOND -> {
                                                minute2 = it
                                                minuteSelect = MinuteSelected.FIRST
                                            }
                                            else -> {}
                                        }
                                    }
                                )
                            }

                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        onCanceled()
                                    }
                                ),
                            text = "Cancel",
                            color = UIBlue,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Box(
                            modifier = Modifier
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {

                                        onSelected(hour.timeConvert()+minute1+minute2, meridiem)

                                    }
                                )
                                .background(
                                    color = LighterUIBlue,
                                    shape = CircleShape
                                )
                                .padding(vertical = 7.dp, horizontal = 16.dp)
                        ) {

                            Text(
                                text = "OK",
                                color = UIBlue,
                                fontSize = 19.sp,
                                fontWeight = FontWeight.Bold
                            )

                        }

                    }

                }

            },
        )
    }

}

@Composable
fun TimePickerText(
    hour: Int,
    minute: Pair<Int, Int>,
    timePicked: TimePick,
    minuteSelected: MinuteSelected = MinuteSelected.NONE,
    selected: Boolean,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .width(40.dp)
            .height(50.dp)
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                onClick()
            },
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp),
        backgroundColor = if (selected) LighterUIBlue else Color.Transparent
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (timePicked == TimePick.HOUR) {
                Text(
                    text = hour.timeConvert(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selected) UIBlue else textColorBW
                )
            } else {
                Row {
                    Text(
                        text = minute.first.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (minuteSelected == MinuteSelected.FIRST && timePicked == TimePick.MINUTE && selected) UIBlue else textColorBW
                    )
                    Text(
                        text = minute.second.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (minuteSelected == MinuteSelected.SECOND && timePicked == TimePick.MINUTE && selected) UIBlue else textColorBW
                    )
                }
            }
        }
    }

}

@Composable
fun MeridiemPick(
    meridiem: List<Meridiem>,
    selected: Meridiem,
    interactionSource: MutableInteractionSource,
    onSelected: (Meridiem) -> Unit,
    paddingValues: PaddingValues
) {

    Row(
        modifier = Modifier.padding(paddingValues),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        meridiem.forEachIndexed { index, it ->
            Card(
                modifier = Modifier
                    .width(if (selected == it) 50.dp else 35.dp)
                    .height(25.dp)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource
                    ) {
                        onSelected(it)
                    },
                elevation = 0.dp,
                shape = RoundedCornerShape(5.dp),
                backgroundColor = if (selected == it) LighterUIBlue else Color.Transparent
            ) {

                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {

                    if (selected == it) {
                        Box(
                            modifier = Modifier
                                .padding(start = 7.dp)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(
                                        color = UIBlue,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        Text(
                            text = if (it == Meridiem.HOUR24) "24Hr" else it.name,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selected == it) UIBlue else textColorBW
                        )

                    }
                }
            }

            if (index % 2 != 0 || index == 0) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = if (it == Meridiem.HOUR24) "|" else "/",
                    color = textColorBW,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}

enum class TimePick {
    HOUR,
    MINUTE,
}

enum class Meridiem {
    AM,
    PM,
    HOUR24
}

enum class MinuteSelected {
    FIRST,
    SECOND,
    NONE
}

fun Int.timeConvert(): String {

    return "${if (this < 10) 0 else ""}$this"

}

fun convertToMeridiemTime(
    hours: Int,
    oldMeridiem: Meridiem,
    currentMeridiem: Meridiem,
    time: (Pair<Int, Meridiem>) -> Unit
) {

    time(
        Pair(
            first = if (currentMeridiem != Meridiem.HOUR24) {
                if (hours > 13) hours - 12 else hours
            } else {
                if (oldMeridiem == Meridiem.PM) hours + 12 else hours
            },
            second = currentMeridiem
        )
    )

}

suspend fun delayTransactionPeriod(timePicked: TimePick): TimePick {
    delay(500)
    return timePicked
}

fun String.stringToTime(
    returnTime:(Pair<String, String>)-> Unit
) {

    returnTime(Pair(
        first = (this.dropLast(2)),
        second = (this.drop(2))
    ))


}