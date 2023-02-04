package com.developerstring.financesapp.screen.transaction

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
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
import com.developerstring.financesapp.navigation.navgraph.NavRoute
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.PublicSharedViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.components.CustomChip
import com.developerstring.financesapp.ui.components.timepicker.*
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.ADD_FUND
import com.developerstring.financesapp.util.Constants.ADD_TRANSACTION_TYPE
import com.developerstring.financesapp.util.Constants.CASH
import com.developerstring.financesapp.util.Constants.INVESTMENT
import com.developerstring.financesapp.util.Constants.OTHER
import com.developerstring.financesapp.util.Constants.SAVINGS
import com.developerstring.financesapp.util.Constants.SEPARATOR_LIST
import com.developerstring.financesapp.util.Constants.SPENT
import com.developerstring.financesapp.util.Constants.TRANSACTION
import com.developerstring.financesapp.util.addZeroToStart
import com.developerstring.financesapp.util.convertStringToAlphabets
import com.developerstring.financesapp.util.convertStringToInt
import com.developerstring.financesapp.util.mapListToList
import com.developerstring.financesapp.util.state.MessageBarState
import com.developerstring.financesapp.util.state.RequestState
import com.google.accompanist.flowlayout.FlowRow
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTransaction(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    publicSharedViewModel: PublicSharedViewModel
) {

    val shape: Shape = RoundedCornerShape(10.dp)

    profileViewModel.getAllCategories()
    profileViewModel.getTime24Hours()

    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()
    val categoryModel by profileViewModel.allCategories.collectAsState()
    val time24Hours by profileViewModel.profileTime24Hours.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(start = 10.dp, end = 20.dp, top = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                modifier = Modifier.size(30.dp),
                onClick = {
                    navController.popBackStack()
                }) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(90f),
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "back_arrow",
                    tint = textColorBW
                )
            }

            Column(modifier = Modifier.padding(start = 10.dp)) {

                Text(
                    text = "Regular",
                    fontSize = LARGE_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    color = textColorBW
                )
                Box(
                    modifier = Modifier
                        .padding(start = 3.dp, end = 3.dp)
                        .clip(shape)
                        .background(textColorBW)
                        .size(65.dp, 3.dp)
                )

            }

        }

        Column(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxSize()
        ) {

            TransactionContent(
                modifier = Modifier,
                categoryModel = categoryModel,
                transactionModel = TransactionModel(),
                publicSharedViewModel = publicSharedViewModel,
                navController = navController,
                time24Hours = time24Hours,
                profileViewModel = profileViewModel
            ) {
                profileViewModel.getProfileAmount()
                sharedViewModel.addTransaction(transactionModel = it)
                profileViewModel.saveTotalAmount(
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
    categoryModel: RequestState<List<CategoryModel>>,
    publicSharedViewModel: PublicSharedViewModel,
    transactionModel: TransactionModel,
    navController: NavController,
    time24Hours: Boolean,
    profileViewModel: ProfileViewModel,
    onSaveClicked: (TransactionModel) -> Unit,
) {

    val scrollState = rememberScrollState()

    val configuration = LocalConfiguration.current

    var amount by rememberSaveable {
        mutableStateOf(
            if (transactionModel.amount == 0) {
                ""
            } else {
                transactionModel.amount.toString()
            }
        )
    }
    var category by rememberSaveable { mutableStateOf(transactionModel.category) }
    var otherCategory by rememberSaveable { mutableStateOf(transactionModel.categoryOther) }
    var subCategory by rememberSaveable { mutableStateOf(transactionModel.subCategory) }
    var otherSubCategory by rememberSaveable { mutableStateOf(transactionModel.subCategoryOther) }
    val otherSubCategories = mutableListOf(OTHER)
    var timeTransactionModel by rememberSaveable { mutableStateOf(transactionModel.time) }
    var extraInfo by rememberSaveable { mutableStateOf(transactionModel.info) }
    var place by rememberSaveable { mutableStateOf(transactionModel.place) }
    var transactionType by rememberSaveable {
        mutableStateOf(
            if (transactionModel.transaction_type == "") {
                SPENT
            } else {
                transactionModel.transaction_type
            }
        )
    }
    var transactionMode by rememberSaveable {
        mutableStateOf(
            if (transactionModel.transactionMode == "") {
                CASH
            } else {
                transactionModel.transactionMode
            }
        )
    }

    var otherTransactionModel by rememberSaveable {
        mutableStateOf(transactionModel.transactionModeOther)
    }

    var transactionModelList = mutableListOf<String>()

    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var dateClicked by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val heightTextFields by remember { mutableStateOf(55.dp) }

    val interactionSource = remember { MutableInteractionSource() }
    var categoriesExpanded by rememberSaveable { mutableStateOf(false) }
    var subCategoriesExpanded by rememberSaveable { mutableStateOf(false) }
    var transactionModeExpanded by rememberSaveable { mutableStateOf(false) }

    // Chip Selection
    val chipList = ADD_TRANSACTION_TYPE

    val categories = mutableMapOf<Int, String>()
    val subCategories = mutableMapOf<String, List<String>>()
    if (categoryModel is RequestState.Success) {
        categoryModel.data.forEach { value ->
            categories[value.id] = value.category
            subCategories[value.category] =
                value.subCategory.split(SEPARATOR_LIST).toList().plus(OTHER)
        }
        otherSubCategories.addAll(subCategories.mapListToList())
        transactionModelList = subCategories.getOrElse(
            TRANSACTION,
            defaultValue = { listOf(OTHER) }).plus(OTHER).distinct().toMutableList()
    }

    var transactionModelID by remember {
        mutableStateOf(1)
    }

    transactionModelID = categories.keys.find { categories.getValue(it) == TRANSACTION } ?: 1

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // for year, month, day and time
    var year by rememberSaveable { mutableStateOf(transactionModel.year) }
    var month by rememberSaveable { mutableStateOf(transactionModel.month) }
    var day by rememberSaveable { mutableStateOf(transactionModel.day) }

    // for year, month, day and time temporary
    var mYear by rememberSaveable { mutableStateOf(1) }
    var mMonth by rememberSaveable { mutableStateOf(1) }
    var mDay by rememberSaveable { mutableStateOf(1) }

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

//    var moreClicked by rememberSaveable { mutableStateOf(false) }

    // time data
    var hour by rememberSaveable { mutableStateOf(0) }
    var minute by rememberSaveable {
        mutableStateOf("00")
    }
    var meridiem by remember {
        mutableStateOf(Meridiem.HOUR24)
    }
    var timePickerVisible by remember {
        mutableStateOf(false)
    }

    val saveButtonBackground = listOf(Color.Transparent, backgroundColor, backgroundColor)

    // Fetching current year, month and day
    if (date == "") {
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    }

    LaunchedEffect(key1 = true) {
        if (timeTransactionModel == "") {
            hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            minute = Calendar.getInstance().get(Calendar.MINUTE).timeConvert()
            if (time24Hours) {
                meridiem = Meridiem.HOUR24
            } else {
                if (hour > 13) {
                    hour -= 12
                    meridiem = Meridiem.PM
                } else meridiem = Meridiem.AM
            }

            convertToMeridiemTime(
                hours = hour,
                oldMeridiem = meridiem,
                currentMeridiem = Meridiem.HOUR24,
                time = {
                    timeTransactionModel = it.first.timeConvert() + minute
                }
            )

        } else {
            timeTransactionModel.stringToTime(
                returnTime = {
                    hour = it.first.toInt()
                    minute = it.second
                }
            )

            if (time24Hours) {
                meridiem = Meridiem.HOUR24
            } else {
                if (hour > 13) {
                    hour -= 12
                    meridiem = Meridiem.PM
                } else meridiem = Meridiem.AM
            }

            convertToMeridiemTime(
                hours = hour,
                oldMeridiem = meridiem,
                currentMeridiem = Meridiem.HOUR24,
                time = {
                    timeTransactionModel = it.first.timeConvert() + minute
                }
            )
        }
    }

    mCalendar.time = Date()

    val buttonColor = Brush.horizontalGradient(colors = listOf(UIBlue, LightUIBlue))

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = {
                        categoriesExpanded = false
                        subCategoriesExpanded = false
                    }
                )
                .verticalScroll(scrollState)
                .padding(top = 10.dp),
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
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = amount,
                    onValueChange = {
                        amount = it.convertStringToInt()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = TEXT_FIELD_SIZE
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }

            // Select Transaction Type
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                mainAxisSpacing = 10.dp,
                crossAxisSpacing = 10.dp
            ) {
                chipList.forEach { it ->
                    CustomChip(
                        title = it,
                        selected = transactionType,
                        onSelected = {
                            transactionType = it
                            when (transactionType) {
                                ADD_FUND -> category = TRANSACTION
                                SAVINGS -> category = INVESTMENT
                            }
                        },
                        image = Icons.Filled.Check,
                        key = true,
                        selectedColor = UIBlue,
                        color = colorDarkGray,
                        selectedTextColor = Color.White,
                        textColor = textColorBW,
                        iconColor = Color.White
                    )
                }
            }

            // group of Transaction Mode textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.transaction_mode_text_field),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = {
                                transactionModeExpanded = !transactionModeExpanded
                            }),
                    value = transactionMode,
                    onValueChange = {
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = TEXT_FIELD_SIZE
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    enabled = false,
                    singleLine = true,
                    trailingIcon = {

                        Row {

                            Icon(
                                imageVector =
                                if (!transactionModeExpanded) Icons.Filled.KeyboardArrowDown
                                else Icons.Rounded.KeyboardArrowUp,
                                contentDescription = "currency_icon",
                                Modifier
                                    .padding(end = 15.dp)
                                    .size(28.dp),
                                tint = textColorBLG
                            )

                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "currency_icon",
                                Modifier
                                    .padding(end = 15.dp)
                                    .size(28.dp)
                                    .clickable {
                                        profileViewModel.categoryId.value = transactionModelID
                                        profileViewModel.getSelectedCategories(
                                            id = transactionModelID
                                        )
                                        navController.navigate(NavRoute.EditCategoryDetailScreen.route)
                                    },
                                tint = UIBlue
                            )

                        }
                    }
                )
                AnimatedVisibility(visible = transactionModeExpanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
                        backgroundColor = backgroundColorCard,
                        elevation = 10.dp
                    ) {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 200.dp),
                            userScrollEnabled = true
                        ) {
                            items(
                                transactionModelList
                            ) {
                                SubCategoryItems(title = it) { title ->
                                    transactionMode = title
                                    transactionModeExpanded = false
                                }
                            }
                        }
                    }

                }
            }

            // group of Other Transaction Mode
            AnimatedVisibility(visible = transactionMode == OTHER) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = stringResource(id = R.string.other_transaction_mode_text_field),
                        fontSize = TEXT_FIELD_SIZE,
                        color = textColorBLG,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = textColorBLG,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = otherTransactionModel,
                        onValueChange = {
                            otherTransactionModel = it.convertStringToAlphabets()
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = textColorBW,
                        ),
                        textStyle = TextStyle(
                            color = textColorBW,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = false
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
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = {
                                categoriesExpanded = !categoriesExpanded
                            }),
                    value = category,
                    onValueChange = {
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = TEXT_FIELD_SIZE
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    enabled = false,
                    singleLine = true,
                    trailingIcon = {

                        Row {
                            Icon(
                                imageVector =
                                if (!categoriesExpanded) Icons.Filled.KeyboardArrowDown
                                else Icons.Rounded.KeyboardArrowUp,
                                contentDescription = "currency_icon",
                                Modifier
                                    .padding(end = 15.dp)
                                    .size(28.dp),
                                tint = textColorBLG
                            )
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "currency_icon",
                                Modifier
                                    .padding(end = 15.dp)
                                    .size(28.dp)
                                    .clickable {
                                        navController.navigate(NavRoute.EditCategoryScreen.route)
                                    },
                                tint = UIBlue
                            )
                        }

                    }
                )
                AnimatedVisibility(visible = categoriesExpanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
                        backgroundColor = backgroundColorCard,
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .heightIn(max = 200.dp)
                                .verticalScroll(rememberScrollState()),
                        ) {
                            categories.values.forEach {
                                SubCategoryItems(it) { title ->
                                    category = title
                                    categoriesExpanded = false
                                }
                            }
                        }
                    }

                }
            }

            // group of Other Category
            AnimatedVisibility(visible = category == OTHER) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = stringResource(id = R.string.other_category_text_field),
                        fontSize = TEXT_FIELD_SIZE,
                        color = textColorBLG,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = textColorBLG,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = otherCategory,
                        onValueChange = {
                            otherCategory = it.convertStringToAlphabets()
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = textColorBW,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            color = textColorBW,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = false
                    )
                }
            }

            // group of SubCategory textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.sub_category_text_field),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = {
                                subCategoriesExpanded = !subCategoriesExpanded
                            }),
                    value = subCategory,
                    onValueChange = {
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = TEXT_FIELD_SIZE
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    enabled = false,
                    singleLine = true,
                    trailingIcon = {

                        Icon(
                            imageVector =
                            if (!subCategoriesExpanded) Icons.Filled.KeyboardArrowDown
                            else Icons.Rounded.KeyboardArrowUp,
                            contentDescription = "currency_icon",
                            Modifier
                                .padding(end = 15.dp)
                                .size(28.dp),
                            tint = textColorBLG
                        )

                    }
                )
                AnimatedVisibility(visible = subCategoriesExpanded) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                            .width(textFieldSize.width.dp),
                        backgroundColor = backgroundColorCard,
                        elevation = 10.dp
                    ) {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 200.dp),
                            userScrollEnabled = true
                        ) {
                            items(
                                if (category != OTHER) {

                                    subCategories.getOrElse(
                                        category,
                                        defaultValue = { otherSubCategories })

                                } else otherSubCategories.distinct()
                            ) {
                                SubCategoryItems(title = it) { title ->
                                    subCategory = title
                                    subCategoriesExpanded = false
                                }
                            }
                        }
                    }

                }
            }

            // group of Other Sub Category
            AnimatedVisibility(visible = subCategory == OTHER) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 3.dp, bottom = 2.dp),
                        text = stringResource(id = R.string.other_sub_category_text_field),
                        fontSize = TEXT_FIELD_SIZE,
                        color = textColorBLG,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = heightTextFields)
                            .border(
                                width = 1.8.dp,
                                color = textColorBLG,
                                shape = RoundedCornerShape(15.dp)
                            ),
                        value = otherSubCategory,
                        onValueChange = {
                            otherSubCategory = it.convertStringToAlphabets()
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = textColorBW,
                        ),
                        textStyle = TextStyle(
                            color = textColorBW,
                            fontSize = TEXT_FIELD_SIZE
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        singleLine = false
                    )
                }
            }

            // Date TextField
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.date_text_field),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable {
                            dateClicked = true
                        },
                    shape = RoundedCornerShape(15.dp),
                    backgroundColor = backgroundColor
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
                            color = textColorBW
                        )
                    }

                    LaunchedEffect(key1 = false) {
                        if (transactionModel.date == "" && date == "") {
                            date = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(Date())
                            time = SimpleDateFormat("HHmmss", Locale.ENGLISH).format(Date())
                            day = SimpleDateFormat("dd", Locale.ENGLISH).format(Date()).toShort()
                            month = SimpleDateFormat("MM", Locale.ENGLISH).format(Date()).toShort()
                            year = SimpleDateFormat("yyyy", Locale.ENGLISH).format(Date()).toShort()
                        } else {
                            date = "$day/$month/$year"
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
                            if (month.toInt() != 0) month.toInt() - 1 else mMonth,
                            if (day.toInt() != 0) day.toInt() else mDay
                        ).show()

                        dateClicked = false
                    }
                }
            }

//            // more
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//            ) {
//                Row(
//                    modifier = Modifier
//                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
//                        .fillMaxWidth()
//                        .clickable(onClick = {
//                            moreClicked = !moreClicked
//                        }),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(5.dp)
//                ) {
//                    Box(
//                        modifier = modifier
//                            .fillMaxWidth(0.43f)
//                            .height(4.dp)
//                            .clip(RoundedCornerShape(5.dp))
//                            .background(
//                                LighterGray
//                            )
//                    )
//                    Text(
//                        text = stringResource(id = if (moreClicked) R.string.less else R.string.more),
//                        fontFamily = fontInter,
//                        fontWeight = FontWeight.Normal,
//                        fontSize = 16.sp,
//                        color = LightGray
//                    )
//                    Box(
//                        modifier = modifier
//                            .fillMaxWidth()
//                            .height(4.dp)
//                            .clip(RoundedCornerShape(5.dp))
//                            .background(
//                                LighterGray
//                            )
//                    )
//                }
//            }
//
//            AnimatedVisibility(visible = moreClicked) {

//                Column(
//                    modifier = modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
            // group of Time textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.time_text_field),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = {
                                timePickerVisible = true
                            }
                        ),
                    value = "$hour:$minute ${if (meridiem == Meridiem.HOUR24) "" else meridiem.name}",
                    onValueChange = {
                    },
//                        "${hour.timeConvert()}:$minute ${if (meridiem == Meridiem.HOUR24) "" else meridiem.name}"
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = TEXT_FIELD_SIZE
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    enabled = false,
                    singleLine = false
                )
            }

            if (timePickerVisible) {
                TimePicker(
                    visible = true,
                    currentMeridiem = meridiem,
                    interactionSource = interactionSource,
                    configuration = configuration,
                    currentHour = hour,
                    currentMinute1 = minute.first().digitToInt(),
                    currentMinute2 = minute.last().digitToInt(),
                    onSelected = { it, m ->
                        if (m == Meridiem.HOUR24) {
                            profileViewModel.updateTime24Hours(
                                time24Hours = true
                            )
                        } else {
                            profileViewModel.updateTime24Hours(
                                time24Hours = false
                            )
                        }
                        it.stringToTime(
                            returnTime = { time ->
                                hour = time.first.toInt()
                                minute = time.second
                                meridiem = m
                                convertToMeridiemTime(
                                    hours = hour,
                                    oldMeridiem = meridiem,
                                    currentMeridiem = Meridiem.HOUR24,
                                    time = {
                                        timeTransactionModel =
                                            it.first.timeConvert() + minute
                                    }
                                )
                            }
                        )
                        timePickerVisible = false
                    },
                    onCanceled = {
                        timePickerVisible = false
                    }
                )
            }

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
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = extraInfo,
                    onValueChange = {
                        extraInfo = it.convertStringToAlphabets()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
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
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = place,
                    onValueChange = {
                        place = it.convertStringToAlphabets()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = TEXT_FIELD_SIZE
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
            }
//                }
//            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .background(Color.Transparent)

            )
        }

        // Save Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .background(brush = Brush.verticalGradient(colors = saveButtonBackground))
                    .padding(top = 40.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Surface(
                    modifier = Modifier

                        .width(220.dp)
                        .height(45.dp),
                    shape = CircleShape,
                    elevation = 4.dp, color = Color.Transparent,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = buttonColor)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = {
                                    if (
                                        amount.isNotEmpty() &&
                                        transactionMode.isNotEmpty() &&
                                        transactionType.isNotEmpty() &&
                                        date.isNotEmpty()
                                    ) {
                                        onSaveClicked(
                                            TransactionModel(
                                                amount = amount.toInt(),
                                                transaction_type = transactionType,
                                                category = category,
                                                subCategory = subCategory,
                                                date = "$year${month.addZeroToStart()}${day.addZeroToStart()}$time",
                                                day = day,
                                                month = month,
                                                time = timeTransactionModel,
                                                year = year,
                                                info = extraInfo,
                                                place = place,
                                                categoryOther = if (category == OTHER) otherCategory else "",
                                                subCategoryOther = if (subCategory == OTHER) otherSubCategory else "",
                                                transactionMode = transactionMode,
                                                transactionModeOther = if (transactionMode == OTHER) otherTransactionModel else ""
                                            )
                                        )

                                        if (transactionModel == TransactionModel()) {
                                            publicSharedViewModel.messageBarState.value =
                                                MessageBarState.OPENED
                                        }

                                    } else {
                                        Toast
                                            .makeText(
                                                context,
                                                "Please fill all details",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Save",
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = LARGE_TEXT_SIZE,
                            color = Color.White
                        )
                    }
                }
            }


        }
    }

}

@Composable
fun SubCategoryItems(
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
            color = textColorBW
        )
    }
}
