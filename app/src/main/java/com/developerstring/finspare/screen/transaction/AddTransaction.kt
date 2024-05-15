package com.developerstring.finspare.screen.transaction

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.components.CustomChip
import com.developerstring.finspare.ui.components.timepicker.Meridiem
import com.developerstring.finspare.ui.components.timepicker.TimePicker
import com.developerstring.finspare.ui.components.timepicker.convertToMeridiemTime
import com.developerstring.finspare.ui.components.timepicker.stringToTime
import com.developerstring.finspare.ui.components.timepicker.timeConvert
import com.developerstring.finspare.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LightUIBlue
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.backgroundColorCard
import com.developerstring.finspare.ui.theme.colorDarkGray
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textBoxBackColor
import com.developerstring.finspare.ui.theme.textColorBLG
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.CategoryListReturn
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.ADD_FUND
import com.developerstring.finspare.util.Constants.ADD_TRANSACTION_TYPE
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.Constants.LEND
import com.developerstring.finspare.util.Constants.OTHER
import com.developerstring.finspare.util.Constants.SEPARATOR_LIST
import com.developerstring.finspare.util.Constants.SPENT
import com.developerstring.finspare.util.Constants.TRANSACTION
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.ProfileListReturn
import com.developerstring.finspare.util.addZeroToStart
import com.developerstring.finspare.util.convertStringToAlphabets
import com.developerstring.finspare.util.convertStringToInt
import com.developerstring.finspare.util.formatNumberingStyle
import com.developerstring.finspare.util.formatNumberingStyleToInt
import com.developerstring.finspare.util.mapListToList
import com.developerstring.finspare.util.refineProfileModel
import com.developerstring.finspare.util.state.AddTransactionMenu
import com.developerstring.finspare.util.state.MessageBarState
import com.developerstring.finspare.util.state.ProfileAmountType
import com.developerstring.finspare.util.state.RequestState
import com.developerstring.finspare.util.stringToProfileAmountType
import com.developerstring.finspare.util.stringToSet
import com.google.accompanist.flowlayout.FlowRow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddTransaction(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    publicSharedViewModel: PublicSharedViewModel,
) {

    val context = LocalContext.current

    val transactionModel by sharedViewModel.addTransactionModel

    val getAllMessageID by profileViewModel.messageScanIDs.collectAsState()
    val allMessagesIDSet: MutableSet<String> = getAllMessageID.stringToSet().toMutableSet()
    val messageID by sharedViewModel.messageID

    var categoriesExpanded by rememberSaveable { mutableStateOf(false) }
    var subCategoriesExpanded by rememberSaveable { mutableStateOf(false) }
    var transactionModeExpanded by rememberSaveable { mutableStateOf(false) }

    profileViewModel.getTime24Hours()

    /**
     * Collects the profile total amount as a state.
     */
    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()
    val categoryModel by profileViewModel.allCategories.collectAsState()
    val time24Hours by profileViewModel.profileTime24Hours.collectAsState()

    val profileModel by profileViewModel.allProfiles.collectAsState()

    val languageText = LanguageText(LANGUAGE)

    val menuList = AddTransactionMenu.entries
    var menuSelected by rememberSaveable {
        mutableStateOf(menuList.first())
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val scrollState = rememberScrollState()

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            ) {
                menuList.forEach { text ->
                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource,
                                onClick = { menuSelected = text }),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val bottomCard by animateDpAsState(
                            targetValue = if (menuSelected == text) 65.dp else 0.dp,
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            ), label = ""
                        )

                        Text(
                            modifier = Modifier.widthIn(min = 65.dp),
                            text = if (text == AddTransactionMenu.Lend) stringResource(id = languageText.lend) else text.name,
                            fontSize = LARGE_TEXT_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = textColorBW,
                            textAlign = TextAlign.Center
                        )

                        if (menuSelected == text) {
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
                }
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
                profileModel = profileModel,
                transactionModel = transactionModel,
                publicSharedViewModel = publicSharedViewModel,
                navController = navController,
                time24Hours = time24Hours,
                profileViewModel = profileViewModel,
                languageText = languageText,
                transactionModeExpanded = transactionModeExpanded,
                categoriesExpanded = categoriesExpanded,
                subCategoriesExpanded = subCategoriesExpanded,
                onTransactionModeChange = { transactionModeExpanded = it },
                onCategoryChange = { categoriesExpanded = it },
                onSubCategoryChange = { subCategoriesExpanded = it },
                onSaveClicked = {
                    profileViewModel.getProfileAmount()
                    sharedViewModel.addTransaction(transactionModel = it)
                    profileViewModel.saveTotalAmount(
                        amount = when (it.transaction_type) {
                            SPENT -> (totalAmount - it.amount)
                            ADD_FUND -> (totalAmount + it.amount)
                            else -> totalAmount
                        }
                    )

                    if (it.amount_type.isNotEmpty()) {
                        profileViewModel.updateContactAmount(
                            profileId = it.profile_id.toInt(),
                            amount = it.amount,
                            amountType = it.amount_type.stringToProfileAmountType(),
                            context = context
                        )
                    }

                    if (messageID.isNotEmpty() && transactionModel != TransactionModel()) {
                        allMessagesIDSet.add(messageID)
                        profileViewModel.saveMessageScanID(context, id = allMessagesIDSet.toString())
                        sharedViewModel.messageID.value = ""
                    }
                    navController.popBackStack()
                },
                transactionMenu = menuSelected
            )

        }
    }


}

@Composable
fun TransactionContent(
    modifier: Modifier,
    categoryModel: RequestState<List<CategoryModel>>,
    profileModel: RequestState<List<ProfileModel>>,
    publicSharedViewModel: PublicSharedViewModel,
    transactionModel: TransactionModel,
    navController: NavController,
    time24Hours: Boolean,
    profileViewModel: ProfileViewModel,
    languageText: LanguageText,
    transactionModeExpanded: Boolean,
    categoriesExpanded: Boolean,
    subCategoriesExpanded: Boolean,
    onTransactionModeChange: (Boolean) -> Unit,
    onCategoryChange: (Boolean) -> Unit,
    onSubCategoryChange: (Boolean) -> Unit,
    onSaveClicked: (TransactionModel) -> Unit,
    launched_: Boolean = false,
    transactionMenu: AddTransactionMenu
) {

    val otherSubCategories = mutableListOf(OTHER)
    var transactionModelList = mutableListOf<String>()
    val categories = mutableMapOf<Int, String>()
    val subCategories = mutableMapOf<String, List<String>>()

    var launched by remember {
        mutableStateOf(launched_)
    }

    var profiles = mutableListOf<ProfileModel>()

    if (launched) {
        CategoryListReturn(
            categories = categoryModel,
            categoryList = {
                it.forEach { value ->
                    categories[value.id] = value.category
                    subCategories[value.category] =
                        value.subCategory.split(SEPARATOR_LIST).toList()
                }
                otherSubCategories.addAll(subCategories.mapListToList().plus(OTHER))
                transactionModelList = subCategories.getOrElse(
                    TRANSACTION,
                    defaultValue = { listOf(OTHER) }).plus(OTHER).distinct().toMutableList()
            }
        )

        ProfileListReturn(
            profiles = profileModel,
            profileList = {
                profiles = it.refineProfileModel() as MutableList<ProfileModel>
            }
        )
    }

    LaunchedEffect(key1 = true) {
        launched = true
    }

    AddTransactionContent(
        modifier = modifier,
        categories,
        profiles,
        subCategories,
        otherSubCategories,
        transactionModelList,
        publicSharedViewModel,
        transactionModel,
        navController,
        time24Hours,
        profileViewModel,
        languageText,
        transactionModeExpanded,
        categoriesExpanded,
        subCategoriesExpanded,
        onTransactionModeChange,
        onCategoryChange,
        onSubCategoryChange,
        onSaveClicked,
        transactionMenu
    )

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

@Composable
fun AddTransactionContent(
    modifier: Modifier,
    categories: MutableMap<Int, String>,
    profiles: MutableList<ProfileModel>,
    subCategories: MutableMap<String, List<String>>,
    otherSubCategories: MutableList<String>,
    transactionModelList: MutableList<String>,
    publicSharedViewModel: PublicSharedViewModel,
    transactionModel: TransactionModel,
    navController: NavController,
    time24Hours: Boolean,
    profileViewModel: ProfileViewModel,
    languageText: LanguageText,
    transactionModeExpanded: Boolean,
    categoriesExpanded: Boolean,
    subCategoriesExpanded: Boolean,
    onTransactionModeChange: (Boolean) -> Unit,
    onCategoryChange: (Boolean) -> Unit,
    onSubCategoryChange: (Boolean) -> Unit,
    onSaveClicked: (TransactionModel) -> Unit,
    transactionMenu: AddTransactionMenu = AddTransactionMenu.Regular
) {

    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()

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

    var contactExpanded by rememberSaveable { mutableStateOf(false) }

    var contactID by rememberSaveable { mutableStateOf(transactionModel.profile_id) }

    var contactName by rememberSaveable { mutableStateOf(transactionModel.profile_name) }

    var transactionMode by rememberSaveable { mutableStateOf(transactionModel.transactionMode) }

    var otherTransactionMode by rememberSaveable { mutableStateOf(transactionModel.transactionModeOther) }

    var category by rememberSaveable { mutableStateOf(transactionModel.category) }

    var otherCategory by rememberSaveable { mutableStateOf(transactionModel.categoryOther) }

    var subCategory by rememberSaveable { mutableStateOf(transactionModel.subCategory) }

    var otherSubCategory by rememberSaveable { mutableStateOf(transactionModel.subCategoryOther) }

//    val otherSubCategories = mutableListOf(OTHER)
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

    var amountType by rememberSaveable {
        mutableStateOf(transactionModel.amount_type)
    }

    if (contactID.isNotEmpty()) {
        try {
            profileViewModel.getContactDetails(id = contactID.toInt())
        } catch (_: Exception) {

        }
    }

    val selectedContact by profileViewModel.selectedContact.collectAsState()

//    var transactionModelList = mutableListOf<String>()

    var date by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }
    var dateClicked by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val heightTextFields by remember { mutableStateOf(60.dp) }

    val interactionSource = remember { MutableInteractionSource() }


    // Chip Selection
    val chipList = ADD_TRANSACTION_TYPE

    var transactionModelID by remember {
        mutableIntStateOf(1)
    }

    transactionModelID = categories.keys.find { categories.getValue(it) == TRANSACTION } ?: 1

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // for year, month, day and time
    var year by rememberSaveable { mutableStateOf(transactionModel.year) }
    var month by rememberSaveable { mutableStateOf(transactionModel.month) }
    var day by rememberSaveable { mutableStateOf(transactionModel.day) }

    // for year, month, day and time temporary
    var mYear by rememberSaveable { mutableIntStateOf(1) }
    var mMonth by rememberSaveable { mutableIntStateOf(1) }
    var mDay by rememberSaveable { mutableIntStateOf(1) }

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // time data
    var hour by rememberSaveable { mutableIntStateOf(0) }
    var minute by rememberSaveable {
        mutableStateOf("00")
    }
    var meridiem by remember {
        mutableStateOf(Meridiem.HOUR24)
    }
    var timePickerVisible by remember {
        mutableStateOf(false)
    }

    val saveButtonBackground =
        listOf(Color.Transparent, backgroundColor, backgroundColor, backgroundColor)

    var bottomHeight by remember {
        mutableStateOf(Size.Zero)
    }

    // Fetching current year, month and day
    if (date == "") {
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    }

    LaunchedEffect(key1 = true) {

//        if (otherTransactionMode.isNotEmpty()) {
//            otherTransactionMode = ""
//        }
//
//        if (otherCategory.isNotEmpty()) {
//            otherCategory = ""
//        }
//
//        if (otherSubCategory.isNotEmpty()) {
//            otherSubCategory = ""
//        }

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
                        onCategoryChange(false)
                        onSubCategoryChange(false)
                        onTransactionModeChange(false)
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
                    text = stringResource(id = languageText.amountTextField),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor),
                    value = if (amount.isEmpty()) "" else amount.toInt()
                        .formatNumberingStyle(currency),
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

                if (transactionMenu == AddTransactionMenu.Lend) {
                    Constants.PROFILE_AMOUNT_TYPE.forEach { amountTypes ->
                        CustomChip(
                            title = amountTypes.name,
                            selected = amountType,
                            onSelected = {
                                amountType = it
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
                } else {
                    chipList.forEach { it ->
                        CustomChip(
                            title = it,
                            selected = transactionType,
                            onSelected = {
                                transactionType = it
                                when (transactionType) {
                                    ADD_FUND -> category = TRANSACTION
                                    Constants.SAVINGS -> category = Constants.INVESTMENT
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
            }

            // transaction Mode
            RegularTextFieldInput(
                mainFieldLabel = stringResource(id = languageText.transactionModeTextField),
                otherFieldLabel = stringResource(id = languageText.transactionModeOptionalTextField),
                mainFieldText = transactionMode,
                otherFieldText = otherTransactionMode,
                transactionModeExpanded = transactionModeExpanded,
                heightTextFields = heightTextFields,
                textFieldSize = textFieldSize,
                interactionSource = interactionSource,
                mainTextFieldList = transactionModelList,
                otherSubCategories = otherSubCategories,
                subCategories = subCategories,
                subCategory = false,
                onMainTextFieldChanged = { onTransactionModeChange(it) },
                onMainTextFieldTextChanged = { transactionMode = it },
                onOtherMainTextFieldTextChanged = { otherTransactionMode = it },
                trailingIconEnable = true,
                trailingIconClicked = {
                    profileViewModel.categoryId.value = transactionModelID
                    profileViewModel.getSelectedCategories(
                        id = transactionModelID
                    )
                    navController.navigate(NavRoute.EditCategoryDetailScreen.route)
                }
            )

            when (transactionMenu) {

                AddTransactionMenu.Regular -> {

                    // category
                    RegularTextFieldInput(
                        mainFieldLabel = stringResource(id = languageText.categoryTextField),
                        otherFieldLabel = stringResource(id = languageText.otherCategoryTextField),
                        mainFieldText = category,
                        otherFieldText = otherCategory,
                        transactionModeExpanded = categoriesExpanded,
                        heightTextFields = heightTextFields,
                        textFieldSize = textFieldSize,
                        interactionSource = interactionSource,
                        mainTextFieldList = categories.values.toList(),
                        otherSubCategories = otherSubCategories,
                        subCategories = subCategories,
                        subCategory = false,
                        onMainTextFieldChanged = { onCategoryChange(it) },
                        onMainTextFieldTextChanged = { category = it },
                        onOtherMainTextFieldTextChanged = { otherCategory = it },
                        trailingIconEnable = true,
                        trailingIconClicked = {
                            navController.navigate(NavRoute.EditCategoryScreen.route)
                        }
                    )

                    RegularTextFieldInput(
                        mainFieldLabel = stringResource(id = languageText.subCategoryTextField),
                        otherFieldLabel = stringResource(id = languageText.otherSubCategoryTextField),
                        mainFieldText = subCategory,
                        otherFieldText = otherSubCategory,
                        transactionModeExpanded = subCategoriesExpanded,
                        heightTextFields = heightTextFields,
                        textFieldSize = textFieldSize,
                        interactionSource = interactionSource,
                        mainTextFieldList = categories.values.toList(),
                        otherSubCategories = otherSubCategories,
                        subCategories = subCategories,
                        subCategory = true,
                        category = category,
                        onMainTextFieldChanged = { onSubCategoryChange(it) },
                        onMainTextFieldTextChanged = { subCategory = it },
                        onOtherMainTextFieldTextChanged = { otherSubCategory = it },
                        trailingIconClicked = {

                        }
                    )
                }

                AddTransactionMenu.Lend -> {
                    // transaction Mode
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(start = 3.dp, bottom = 2.dp),
                            text = stringResource(id = languageText.contactText),
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBLG,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .onGloballyPositioned { coordinates ->
                                    textFieldSize = coordinates.size.toSize()
                                }
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        if (transactionModel.amount_type.isEmpty()) {
                                            contactExpanded = !contactExpanded
                                        } else {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Contact can't be changed",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    }
                                ),
                            shape = RoundedCornerShape(15.dp),
                            backgroundColor = textBoxBackColor,
                            elevation = 0.dp
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier.padding(start = 20.dp),
                                    text = profiles.find { it.id == contactID.toInt() }?.name ?: "",
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = textColorBW
                                )

                                Icon(
                                    imageVector = Icons.Rounded.Edit,
                                    contentDescription = "contact edit icon",
                                    Modifier
                                        .padding(end = 15.dp)
                                        .size(28.dp)
                                        .clickable {
                                            navController.navigate(NavRoute.EditContactsScreen.route)
                                        },
                                    tint = UIBlue
                                )
                            }


                        }
                        // contact drop down
                        AnimatedVisibility(contactExpanded) {

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
                                        if (profiles.isNotEmpty()) profiles.sortedBy { profileModel -> profileModel.name } else profiles
                                    ) { contact ->

                                        Row(modifier = Modifier
                                            .clickable {
                                                contactName = contact.name
                                                contactID = contact.id.toString()
                                                contactExpanded = false
                                                profileViewModel.getContactDetails(contact.id)
                                            }
                                            .padding(horizontal = 10.dp, vertical = 10.dp)) {
                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = profiles.find { it.id == contact.id }?.name
                                                    ?: "",
                                                fontSize = 16.sp,
                                                color = textColorBW
                                            )
                                        }

                                    }
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
                    text = stringResource(id = languageText.dateTextField),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable {
                            dateClicked = true
                        },
                    shape = RoundedCornerShape(15.dp),
                    backgroundColor = textBoxBackColor,
                    elevation = 0.dp
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

            // group of Time textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = languageText.timeTextField),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = heightTextFields)
                        .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor)
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
                    text = stringResource(id = languageText.extraInfoTextField),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = heightTextFields)
                        .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor),
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
                    text = stringResource(id = languageText.placeInfoTextField),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightTextFields)
                        .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor),
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

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bottomHeight.height.dp / 2f + 10.dp)
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
                    .height(120.dp)
                    .background(brush = Brush.verticalGradient(colors = saveButtonBackground))
                    .onGloballyPositioned {
                        bottomHeight = it.size.toSize()
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                Surface(
                    modifier = Modifier
                        .padding(top = 20.dp)
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

                                    val transactionMenuLend =
                                        transactionMenu == AddTransactionMenu.Lend
                                    val transactionMenuRegular =
                                        transactionMenu == AddTransactionMenu.Regular

                                    if (
                                        amount.isNotEmpty() &&
                                        transactionType.isNotEmpty() &&
                                        date.isNotEmpty() &&
                                        ((transactionMenuLend && amountType.isNotEmpty()) || transactionMenuRegular) &&
                                        ((transactionMenuLend && contactName.isNotEmpty()) || transactionMenuRegular)
                                    ) {

                                        onSaveClicked(
                                            TransactionModel(
                                                amount = amount.formatNumberingStyleToInt(),
                                                transaction_type = if (transactionMenuLend) {
                                                    when (amountType.stringToProfileAmountType()) {
                                                        ProfileAmountType.MONEY_GIVEN -> SPENT
                                                        ProfileAmountType.MONEY_TAKEN -> ADD_FUND
                                                    }
                                                } else {
                                                    transactionType
                                                },
                                                category = if (transactionMenuRegular) category else "",
                                                subCategory = if (transactionMenuRegular) subCategory else "",
                                                date = "$year${month.addZeroToStart()}${day.addZeroToStart()}$time",
                                                day = day,
                                                month = month,
                                                time = timeTransactionModel,
                                                year = year,
                                                info = extraInfo,
                                                place = place,
                                                categoryOther = if (category == OTHER && transactionMenuRegular) otherCategory else "",
                                                subCategoryOther = if (subCategory == OTHER && transactionMenuRegular) otherSubCategory else "",
                                                transactionMode = transactionMode,
                                                transactionModeOther = if (transactionMode == OTHER) otherTransactionMode else "",
                                                profile_id = if (transactionMenuLend) contactID else "",
                                                amount_type = if (transactionMenuLend) amountType else "",
                                                profile_name = ((if (transactionMenuLend) contactName.ifEmpty { selectedContact } else "") as String),
                                                lend = if (transactionMenuLend) LEND else ""
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
                                                "Please enter the Amount",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = languageText.save),
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
fun RegularTextFieldInput(
    mainFieldLabel: String,
    otherFieldLabel: String,
    mainFieldText: String,
    otherFieldText: String,
    transactionModeExpanded: Boolean,
    heightTextFields: Dp,
    textFieldSize: Size,
    interactionSource: MutableInteractionSource,
    mainTextFieldList: List<String>,
    subCategory: Boolean,
    category: String = "",
    subCategories: MutableMap<String, List<String>> = mutableMapOf(),
    otherSubCategories: MutableList<String> = mutableListOf(),
    onMainTextFieldChanged: (Boolean) -> Unit,
    onMainTextFieldTextChanged: (String) -> Unit,
    onOtherMainTextFieldTextChanged: (String) -> Unit,
    trailingIconEnable: Boolean = false,
    trailingIconClicked: () -> Unit
) {

    // group of textField
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 3.dp, bottom = 2.dp),
            text = mainFieldLabel,
            fontSize = TEXT_FIELD_SIZE,
            color = textColorBLG,
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(heightTextFields)
                .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                    onClick = {
                        onMainTextFieldChanged(!transactionModeExpanded)
                    }),
            value = mainFieldText,
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
                if (trailingIconEnable) {
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
                                    trailingIconClicked()
                                },
                            tint = UIBlue
                        )

                    }
                }
            }
        )
        AnimatedVisibility(transactionModeExpanded) {

            Card(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .width(textFieldSize.width.dp),
                backgroundColor = backgroundColorCard,
                elevation = 10.dp
            ) {
                if (subCategory) {
                    LazyColumn(
                        modifier = Modifier.height(200.dp),
                        userScrollEnabled = true
                    ) {
                        items(
                            if (category != OTHER) {

                                subCategories.getOrElse(
                                    category,
                                    defaultValue = { otherSubCategories }).plus(OTHER)

                            } else otherSubCategories.distinct()
                        ) {
                            SubCategoryItems(title = it) { title ->
                                onMainTextFieldTextChanged(title)
                                onMainTextFieldChanged(false)
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.height(200.dp),
                        userScrollEnabled = true
                    ) {
                        items(
                            mainTextFieldList
                        ) {
                            SubCategoryItems(title = it) { title ->
                                onMainTextFieldTextChanged(title)
                                onMainTextFieldChanged(false)
                            }
                        }
                    }
                }

            }

        }
    }

    // group of Other Transaction Mode
    AnimatedVisibility(visible = mainFieldText == OTHER) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 3.dp, bottom = 2.dp),
                text = otherFieldLabel,
                fontSize = TEXT_FIELD_SIZE,
                color = textColorBLG,
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = heightTextFields)
                    .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor),
                value = otherFieldText,
                onValueChange = {
                    onOtherMainTextFieldTextChanged(it.convertStringToAlphabets())
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

}