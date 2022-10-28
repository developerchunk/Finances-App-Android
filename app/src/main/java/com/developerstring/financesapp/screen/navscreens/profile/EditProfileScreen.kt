package com.developerstring.financesapp.screen.navscreens.profile

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
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
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.ui.theme.*

@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {


    val name by profileViewModel.profileName.collectAsState()
    val currency by profileViewModel.profileCurrency.collectAsState()
    val totalAmount by profileViewModel.profileTotalAmount.collectAsState()
    val spending by profileViewModel.profileSpending.collectAsState()
    val savings by profileViewModel.profileSavings.collectAsState()

    var amount by remember {
        mutableStateOf(
            if (totalAmount == 0) {
                ""
            } else {
                totalAmount.toString()
            }
        )
    }
    var newName by remember {
        mutableStateOf(name)
    }
    var newSpending by remember {
        mutableStateOf(spending.toString())
    }
    var newSavings by remember {
        mutableStateOf(savings.toString())
    }

    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    val list = stringArrayResource(id = R.array.currencies)
    var selectedCurrency by remember { mutableStateOf(currency) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val heightTextFields by remember { mutableStateOf(55.dp) }

    val interactionSource = remember { MutableInteractionSource() }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor)
            .verticalScroll(state = scrollState)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close",
                    tint = MaterialTheme.colors.textColorBW
                )
            }

            Text(
                text = stringResource(id = R.string.profile),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = MaterialTheme.colors.textColorBW
            )

            IconButton(onClick = {
                if (
                    name != newName ||
                    currency != selectedCurrency ||
                    totalAmount != amount.toInt() ||
                    spending != newSpending.toInt() ||
                    savings != newSavings.toInt()
                ) {

                    profileViewModel.saveProfileDetails1(
                        context = context,
                        name = newName,
                        amount = amount.toInt(),
                        currency = selectedCurrency
                    )

                    profileViewModel.saveProfileDetail2(
                        context = context,
                        spending = newSpending.toInt(),
                        savings = newSavings.toInt()
                    )

                    navController.popBackStack()

                } else {
                    navController.popBackStack()
                }
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "check",
                    tint = MaterialTheme.colors.textColorBW
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 30.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            // group of Name textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.create_profile_screen_name),
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
                    value = newName,
                    onValueChange = {
                        newName = it
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

            // group of Currency textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.create_profile_screen_currency),
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
                        .clickable { expanded = !expanded },
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
                            text = selectedCurrency,
                            fontSize = TEXT_FIELD_SIZE,
                            color = MaterialTheme.colors.textColorBW
                        )

                        Icon(
                            imageVector =
                            if (!expanded) Icons.Filled.KeyboardArrowDown
                            else Icons.Rounded.KeyboardArrowUp,
                            contentDescription = "currency_icon",
                            Modifier
                                .padding(end = 15.dp)
                                .size(28.dp),
                            tint = MaterialTheme.colors.textColorBLG
                        )
                    }


                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .background(MaterialTheme.colors.backgroundColorCard)
                ) {
                    list.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedCurrency = label
                            expanded = false
                        }) {
                            Text(
                                text = label,
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.textColorBW
                            )
                        }
                    }
                }

            }

            // group of Amount textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.create_profile_screen_amount),
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
            // group of Spending textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.create_profile_screen_2_spending),
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
                    value = newSpending,
                    onValueChange = {
                        newSpending = it
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
            // group of Savings textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = R.string.create_profile_screen_2_savings),
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
                    value = newSavings,
                    onValueChange = {
                        newSavings = it
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
        }

    }

}