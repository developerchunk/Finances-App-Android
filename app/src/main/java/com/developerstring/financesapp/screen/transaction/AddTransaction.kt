package com.developerstring.financesapp.screen.transaction

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.ADD_TRANSACTION_TYPE
import com.developerstring.financesapp.util.Constants.CATEGORIES
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.keyToTransactionType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTransaction(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel
) {

    val shape: Shape = RoundedCornerShape(10.dp)
    val context = LocalContext.current

    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor)
            .padding(start = 10.dp, end = 20.dp, top = 10.dp)
            .verticalScroll(state = scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                modifier = Modifier.size(30.dp, 30.dp),
                onClick = {
                    navController.popBackStack()
                }) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(90f),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "back_arrow",
                    tint = MaterialTheme.colors.textColorBW
                )
            }

            Column(modifier = Modifier.padding(start = 10.dp)) {

                Text(
                    text = "Regular",
                    fontSize = LARGE_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.textColorBW
                )
                Box(
                    modifier = Modifier
                        .padding(start = 3.dp, end = 3.dp)
                        .clip(shape)
                        .background(MaterialTheme.colors.textColorBW)
                        .size(65.dp, 3.dp)
                )

            }

            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Event",
                fontSize = LARGE_TEXT_SIZE,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.textColorBW
            )
        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxSize()
        ) {
            TransactionContent(modifier = Modifier, transactionModel = TransactionModel()) {
                sharedViewModel.addTransaction(transactionModel = it)
                profileViewModel.saveTotalAmount(
                    context = context,
                    amount = when (it.transaction_type) {
                        SPENT -> (totalAmount - it.amount)
                        ADD_FUND -> (totalAmount + it.amount)
                        else -> totalAmount
                    }
                )
                navController.popBackStack()
            }
        }
    }


}

@Composable
fun TransactionContent(
    modifier: Modifier,
    transactionModel: TransactionModel,
    onSaveClicked: (TransactionModel) -> Unit
) {

    var amount by remember {
        mutableStateOf(
            if (transactionModel.amount == 0) {
                ""
            } else {
                transactionModel.amount.toString()
            }
        )
    }
    var category by remember { mutableStateOf(transactionModel.category) }
    var extraInfo by remember { mutableStateOf(transactionModel.info) }
    var place by remember { mutableStateOf(transactionModel.place) }
    var transactionType by remember { mutableStateOf(transactionModel.transaction_type) }
    var date by remember { mutableStateOf(transactionModel.date) }
    var dateClicked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val heightTextFields by remember { mutableStateOf(55.dp) }

    val interactionSource = remember { MutableInteractionSource() }
    var categoriesExpanded by remember { mutableStateOf(false) }

    // Chip Selection
    val chipList = ADD_TRANSACTION_TYPE

    val categories = CATEGORIES
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // for year, month, day and time
    var year by remember { mutableStateOf(transactionModel.year) }
    var month by remember { mutableStateOf(transactionModel.month) }
    var day by remember { mutableStateOf(transactionModel.day) }

    // for year, month, day and time temporary
    var mYear by remember { mutableStateOf(1) }
    var mMonth by remember { mutableStateOf(1) }
    var mDay by remember { mutableStateOf(1) }

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    var moreClicked by remember { mutableStateOf(false) }

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    Column(
        modifier = modifier
            .padding(top = 30.dp)
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {
                    categoriesExpanded = false
                }
            ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        // group of Amount textField
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 2.dp),
                text = stringResource(id = R.string.amount_text_field),
                fontSize = TEXT_FIELD_SIZE,
                color = MaterialTheme.colors.textColorBLG,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightTextFields)
                    .border(
                        width = 1.8.dp,
                        color = MaterialTheme.colors.textColorBLG,
                        shape = RoundedCornerShape(15.dp)
                    ),
                value = amount,
                onValueChange = {
                    amount = it
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colors.textColorBW,
                ),
                textStyle = TextStyle(
                    color = MaterialTheme.colors.textColorBW,
                    fontSize = TEXT_FIELD_SIZE
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                singleLine = true
            )
        }

        // Select Transaction Type
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            chipList.forEach { it ->
                Chip(
                    title = it,
                    selected = transactionType,
                    onSelected = {
                        transactionType = it
                    }
                )
            }
        }

        // group of Category textField
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 2.dp),
                text = stringResource(id = R.string.category_text_field),
                fontSize = TEXT_FIELD_SIZE,
                color = MaterialTheme.colors.textColorBLG,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .border(
                        width = 1.8.dp,
                        color = MaterialTheme.colors.textColorBLG,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { categoriesExpanded = !categoriesExpanded },
                shape = RoundedCornerShape(15.dp),
                backgroundColor = MaterialTheme.colors.backgroundColor
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = category,
                        fontSize = TEXT_FIELD_SIZE,
                        color = MaterialTheme.colors.textColorBW
                    )

                    Icon(
                        imageVector =
                        if (!categoriesExpanded) Icons.Filled.KeyboardArrowDown
                        else Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "currency_icon",
                        Modifier
                            .padding(end = 15.dp)
                            .size(28.dp),
                        tint = MaterialTheme.colors.textColorBLG
                    )
                }


            }

            AnimatedVisibility(visible = categoriesExpanded) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .width(textFieldSize.width.dp),
                    backgroundColor = MaterialTheme.colors.backgroundColorCard,
                    elevation = 10.dp
                ) {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 150.dp),
                        userScrollEnabled = true
                    ) {
                        items(
                            categories
                                .sorted()
                        ) {
                            CategoryItems(title = it) { title ->
                                category = title
                                categoriesExpanded = false
                            }
                        }
                    }
                }

            }
        }

        // Date TextField
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 2.dp),
                text = stringResource(id = R.string.date_text_field),
                fontSize = TEXT_FIELD_SIZE,
                color = MaterialTheme.colors.textColorBLG,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .border(
                        width = 1.8.dp,
                        color = MaterialTheme.colors.textColorBLG,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    }
                    .clickable {
                        dateClicked = true
                    },
                shape = RoundedCornerShape(15.dp),
                backgroundColor = MaterialTheme.colors.backgroundColor
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text = date,
                        fontSize = TEXT_FIELD_SIZE,
                        color = MaterialTheme.colors.textColorBW
                    )
                }

                LaunchedEffect(key1 = false) {
                    if (date == "") {
                        date = SimpleDateFormat("d/MM/yyyy").format(Date())
                        day = SimpleDateFormat("d").format(Date()).toShort()
                        month = SimpleDateFormat("M").format(Date()).toShort()
                        year = SimpleDateFormat("yyyy").format(Date()).toShort()
                    }
                }

                if (dateClicked) {

                    // Declaring DatePickerDialog and setting
                    // initial values as current values (present year, month and day)
                    DatePickerDialog(
                        context,
                        { _: DatePicker, mYear_: Int, mMonth_: Int, mDayOfMonth: Int ->
                            date = "$mDayOfMonth/${mMonth_ + 1}/$mYear_"
                            year = mYear_.toShort()
                            month = (mMonth_ + 1).toShort()
                            day = mDayOfMonth.toShort()
                        },
                        if (year.toInt() != 0) year.toInt() else mYear,
                        if (month.toInt() != 0) month.toInt()-1 else mMonth,
                        if(day.toInt()!=0) day.toInt() else mDay
                    ).show()

                    dateClicked = false
                }
            }
        }

        // more
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .clickable(onClick = {
                        moreClicked = !moreClicked
                    }),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth(0.43f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            LighterGray
                        )
                )
                Text(
                    text = if (moreClicked) "Less" else "More",
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = LightGray
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(
                            LighterGray
                        )
                )
            }
        }

        AnimatedVisibility(visible = moreClicked) {

            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // group of Extra Info textField
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = stringResource(id = R.string.extra_info_text_field),
                        fontSize = TEXT_FIELD_SIZE,
                        color = MaterialTheme.colors.textColorBLG,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = MaterialTheme.colors.textColorBLG,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = extraInfo,
                        onValueChange = {
                            extraInfo = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MaterialTheme.colors.textColorBW,
                        ),
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.textColorBW,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = false
                    )
                }

                // group of Place textField
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = stringResource(id = R.string.place_info_text_field),
                        fontSize = TEXT_FIELD_SIZE,
                        color = MaterialTheme.colors.textColorBLG,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = MaterialTheme.colors.textColorBLG,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = place,
                        onValueChange = {
                            place = it
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = MaterialTheme.colors.textColorBW,
                        ),
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.textColorBW,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true
                    )
                }
            }
        }

        // Save Button
        Column(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(220.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.contentBackgroundColor
                ),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (
                        amount.isNotEmpty() &&
                        category.isNotEmpty() &&
                        transactionType.isNotEmpty() &&
                        date.isNotEmpty()
                    ) {
                        onSaveClicked(
                            TransactionModel(
                                amount = amount.toInt(),
                                transaction_type = transactionType,
                                category = category,
                                date = date,
                                day = day,
                                month = month,
                                year = year,
                                info = extraInfo,
                                place = place,
                            ),
                        )
                    } else {
                        Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            ) {
                Text(text = "Save", color = Color.White, fontSize = 20.sp)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
            )

        }

    }

}

@Composable
fun Chip(
    title: String,
    selected: String,
    onSelected: (String) -> Unit
) {

    val isSelected = selected == title

    val background = if (isSelected) UIBlue else MaterialTheme.colors.colorDarkGray
    val contentColor = if (isSelected) Color.White else MaterialTheme.colors.textColorBLG

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(
                onClick = {
                    onSelected(title)
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(visible = isSelected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "check",
                    tint = Color.White
                )
            }
            Text(text = keyToTransactionType(title), color = contentColor, fontSize = 16.sp)
        }
    }
}

@Composable
fun CategoryItems(
    title: String,
    onSelect: (String) -> Unit
) {
    Row(modifier = Modifier
        .clickable { onSelect(title) }
        .padding(horizontal = 10.dp, vertical = 10.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            fontSize = 16.sp,
            color = MaterialTheme.colors.textColorBW
        )
    }
}
