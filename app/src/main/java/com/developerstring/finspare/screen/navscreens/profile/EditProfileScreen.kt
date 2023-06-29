package com.developerstring.finspare.screen.navscreens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.theme.EXTRA_LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.backgroundColorCard
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textBoxBackColor
import com.developerstring.finspare.ui.theme.textColorBLG
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.PROFILE_ID
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.convertStringToAlphabets
import com.developerstring.finspare.util.convertStringToInt
import com.developerstring.finspare.util.formatNumberingStyle
import com.developerstring.finspare.util.formatNumberingStyleToInt

@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val profileModel by profileViewModel.selectedProfile.collectAsState()

    val languageText = LanguageText(Constants.LANGUAGE)

    var amount by rememberSaveable {
        mutableStateOf(
            if (profileModel.total_amount == 0) {
                ""
            } else {
                profileModel.total_amount.toString()
            }
        )
    }
    var newName by rememberSaveable {
        mutableStateOf(profileModel.name)
    }
    var newSpending by rememberSaveable {
        mutableStateOf(profileModel.month_spent.toString())
    }
    var newSavings by rememberSaveable {
        mutableStateOf(profileModel.month_saving.toString())
    }

    var expanded by remember { mutableStateOf(false) }
    val list = stringArrayResource(id = R.array.currencies)
    var selectedCurrency by rememberSaveable { mutableStateOf(profileModel.currency) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val heightTextFields by remember { mutableStateOf(60.dp) }

    val scrollState = rememberScrollState()

    val currency = profileModel.currency.last().toString()

    profileViewModel.getAllProfiles()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(state = scrollState)
            .padding(bottom = 50.dp)
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
                    tint = textColorBW
                )
            }

            Text(
                text = stringResource(id = languageText.profile),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW
            )

            IconButton(onClick = {
                if (
                    profileModel.name != newName ||
                    profileModel.currency != selectedCurrency ||
                    profileModel.total_amount != amount.toInt() ||
                    profileModel.month_spent != newSpending.toInt() ||
                    profileModel.month_saving != newSavings.toInt()
                ) {

                    profileViewModel.updateProfile(
                        profileModel = ProfileModel(
                            id = PROFILE_ID,
                            name = newName,
                            currency = selectedCurrency,
                            total_amount = amount.formatNumberingStyleToInt(),
                            month_spent = newSpending.toInt(),
                            month_saving =
                                if (newSavings.isEmpty()) {
                                    (newSpending.toInt() / 3)
                                } else {
                                    newSavings.toInt()
                                }
                        )
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
                    tint = textColorBW
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
                    text = stringResource(id = languageText.createProfileScreenName),
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
                    value = newName,
                    onValueChange = {
                        newName = it.convertStringToAlphabets()
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
                    text = stringResource(id = languageText.createProfileScreenCurrency),
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
                        .clickable { expanded = !expanded },
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
                            text = selectedCurrency,
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBW
                        )

                        Icon(
                            imageVector =
                            if (!expanded) Icons.Filled.KeyboardArrowDown
                            else Icons.Rounded.KeyboardArrowUp,
                            contentDescription = "currency_icon",
                            Modifier
                                .padding(end = 15.dp)
                                .size(28.dp),
                            tint = textColorBLG
                        )
                    }


                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .background(backgroundColorCard)
                ) {
                    list.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedCurrency = label
                            expanded = false
                        }) {
                            Text(
                                text = label,
                                fontSize = 18.sp,
                                color = textColorBW
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
                    text = stringResource(id = languageText.createProfileScreenAmount),
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
                    value = if (amount.isEmpty()) "" else amount.toInt().formatNumberingStyle(currency),
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
                    text = stringResource(id = languageText.createProfileScreen2Spending),
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
                    value = newSpending,
                    onValueChange = {
                        newSpending = it.convertStringToInt()
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
                    text = stringResource(id = languageText.createProfileScreen2Saving),
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
                    value = newSavings,
                    onValueChange = {
                        newSavings = it.convertStringToInt()
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
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

            // group of Category textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = languageText.categoryTextField),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 55.dp)
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable {
                            navController.navigate(route = NavRoute.EditCategoryScreen.route)
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
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(start = 20.dp),
                            text = "Edit Category List",
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBW,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "currency_icon",
                            Modifier
                                .padding(end = 15.dp)
                                .size(24.dp),
                            tint = textColorBW
                        )
                    }


                }

            }

            // group of Contacts textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 3.dp, bottom = 2.dp),
                    text = stringResource(id = languageText.contacts),
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 55.dp)
                        .clickable {
                            navController.navigate(route = NavRoute.EditContactsScreen.route)
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
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(start = 20.dp),
                            text = stringResource(id = languageText.editContacts),
                            fontSize = TEXT_FIELD_SIZE,
                            color = textColorBW,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "currency_icon",
                            Modifier
                                .padding(end = 15.dp)
                                .size(24.dp),
                            tint = textColorBW
                        )
                    }


                }

            }
        }

    }

}