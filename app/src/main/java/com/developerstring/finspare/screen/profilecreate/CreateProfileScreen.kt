package com.developerstring.finspare.screen.profilecreate

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.setupnav.SetUpNavRoute
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.theme.MAX_TEXT_SIZE
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.backgroundColorCard
import com.developerstring.finspare.ui.theme.colorGray
import com.developerstring.finspare.ui.theme.contentBackgroundColor
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.fontOpenSans
import com.developerstring.finspare.ui.theme.textColorBLG
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.convertStringToAlphabets
import com.developerstring.finspare.util.convertStringToInt
import com.developerstring.finspare.util.formatNumberingStyle
import com.developerstring.finspare.util.formatNumberingStyleToInt

@Composable
fun CreateProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
) {

    //context
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    val list = stringArrayResource(id = R.array.currencies)
    var selectedCurrency by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val language = profileViewModel.language
    val languageText = LanguageText(language = language)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                text = stringResource(id = languageText.profile),
                fontSize = MAX_TEXT_SIZE,
                color = textColorBW,
                fontFamily = fontOpenSans,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(top = 50.dp, start = 30.dp),
                text = stringResource(id = languageText.createProfileScreenText1),
                fontSize = MEDIUM_TEXT_SIZE,
                color = colorGray,
                fontFamily = fontInter,
                fontWeight = FontWeight.Normal
            )

            // group of Name textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp)
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
                        .heightIn(min = 55.dp)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = name,
                    onValueChange = {
                        name = it.convertStringToAlphabets()
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

            // group of Total Amount textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 25.dp)
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
                        .heightIn(min = 55.dp)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = if (amount.isEmpty()) "" else amount.toInt().formatNumberingStyle("$"),
                    onValueChange = {
                        amount = it.convertStringToInt()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW
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
            // group of Currency textField
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 25.dp)
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
                        .heightIn(min = 55.dp)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        }
                        .clickable { expanded = !expanded },
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
                        .background(color = backgroundColorCard)
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
        }

        // next Button
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
                    backgroundColor = contentBackgroundColor
                ),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (
                        name.isNotEmpty() &&
                        amount.isNotEmpty() &&
                        selectedCurrency.isNotEmpty()
                    ) {
                        profileViewModel.saveProfileDetails1(
                            name_ = name,
                            amount_ = amount.formatNumberingStyleToInt(),
                            currency_ = selectedCurrency
                        )
                        navController.popBackStack()
                        navController.navigate(SetUpNavRoute.CreateProfileSetUpNavRoute2.route)
                    } else {
                        Toast.makeText(context, "Please fill all details", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            ) {
                Text(text = stringResource(id = languageText.next), color = Color.White, fontSize = 20.sp)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
            )

        }

    }

}