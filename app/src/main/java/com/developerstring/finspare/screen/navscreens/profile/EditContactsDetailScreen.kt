package com.developerstring.finspare.screen.navscreens.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
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
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.components.CustomChip
import com.developerstring.finspare.ui.theme.Dark
import com.developerstring.finspare.ui.theme.EXTRA_LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LightUIBlue
import com.developerstring.finspare.ui.theme.LighterBlue
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.UIBlue
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.colorDarkGray
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textBoxBackColor
import com.developerstring.finspare.ui.theme.textColorBLG
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.convertStringToAlphabets
import com.developerstring.finspare.util.convertStringToInt
import com.developerstring.finspare.util.formatNumberingStyle
import com.developerstring.finspare.util.formatNumberingStyleToInt
import com.developerstring.finspare.util.state.ContactActionState
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun EditContactsDetailScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val contact by profileViewModel.contact
    val id by profileViewModel.contactID
    val contactActionState by profileViewModel.contactActionState

    val context = LocalContext.current

    val languageText = LanguageText(LANGUAGE)

    var bottomHeight by remember {
        mutableStateOf(Size.Zero)
    }

    val saveButtonBackground =
        listOf(Color.Transparent, backgroundColor, backgroundColor, backgroundColor)

    val buttonColor = Brush.horizontalGradient(colors = listOf(UIBlue, LightUIBlue))

    var name by rememberSaveable {
        mutableStateOf(contact.name)
    }

    var amount by rememberSaveable {
        mutableStateOf(contact.total_amount.toString())
    }

    var amountType by rememberSaveable {
        mutableStateOf(contact.amount_type)
    }

    var phNo by rememberSaveable {
        mutableStateOf(contact.ph_no)
    }

    var email by rememberSaveable {
        mutableStateOf(contact.email)
    }

    var place by rememberSaveable {
        mutableStateOf(contact.place)
    }

    var extraInfo by rememberSaveable {
        mutableStateOf(contact.extra_info)
    }

    val scrollState = rememberScrollState()

    val fieldEnable = id != 1

    val currency = profileViewModel.profileCurrency.collectAsState().value.last().toString()

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT)
                .background(backgroundColor),
            horizontalArrangement = if (!fieldEnable) Arrangement.Start else Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }) {
                Icon(
                    modifier = Modifier
                        .size(28.dp),
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "close",
                    tint = textColorBW
                )
            }

            if (contactActionState == ContactActionState.UPDATE) {
                Text(
                    modifier = Modifier.padding(start = if (!fieldEnable) 0.dp else 10.dp),
                    text = name,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = EXTRA_LARGE_TEXT_SIZE,
                    color = textColorBW,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (fieldEnable && contactActionState == ContactActionState.UPDATE) {
                IconButton(onClick = {
                    profileViewModel.contactActionState.value = ContactActionState.DELETE
                    profileViewModel.contactID.value = id
                    navController.popBackStack()
                }) {

                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "add contact button",
                        tint = textColorBW
                    )

                }
            }

        }
    }) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it)
                    .background(backgroundColor)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .size(120.dp)
                        .clip(RoundedCornerShape(100))
                        .background(LighterBlue),
                    contentAlignment = Alignment.Center
                ) {

                    if (name.isNotEmpty()) {
                        Text(
                            text = name.first().toString(),
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 56.sp,
                            color = Dark
                        )
                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    // Name
                    BasicTextBox(
                        fieldText = stringResource(id = languageText.createProfileScreenName),
                        field = name,
                        onChange = { newText -> name = newText },
                        fieldEnable = fieldEnable
                    )

                    // Amount
                    BasicTextBox(
                        fieldText = stringResource(id = languageText.amountTextField),
                        field = amount,
                        fieldIsInt = true,
                        onChange = { newText ->
                            amount =
                                if (newText.isEmpty()) "" else newText.convertStringToInt().toInt()
                                    .formatNumberingStyle(currency)
                        },
                        fieldEnable = fieldEnable
                    )


                        // Select Amount Type
                        FlowRow(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth(),
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 10.dp
                        ) {
                            Constants.PROFILE_AMOUNT_TYPE.forEach { amountTypes ->
                                CustomChip(
                                    title = amountTypes.name,
                                    selected = amountType,
                                    onSelected = {
                                        if (fieldEnable) {
                                            amountType = it
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


                    // Phone No
                    BasicTextBox(
                        fieldText = stringResource(id = languageText.phoneNo),
                        field = phNo,
                        onChange = { newText -> phNo = newText },
                        fieldEnable = fieldEnable
                    )

                    // Email
                    BasicTextBox(
                        fieldText = stringResource(id = languageText.email),
                        field = email,
                        onChange = { newText -> email = newText },
                        fieldEnable = fieldEnable
                    )

                    // place
                    BasicTextBox(
                        fieldText = stringResource(id = languageText.placeInfoTextField),
                        field = place,
                        onChange = { newText -> place = newText },
                        fieldEnable = fieldEnable
                    )

                    // extra info
                    BasicTextBox(
                        fieldText = stringResource(id = languageText.extraInfoTextField),
                        field = extraInfo,
                        onChange = { newText -> extraInfo = newText },
                        fieldEnable = fieldEnable
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (!fieldEnable) 40.dp else bottomHeight.height.dp / 2f + 10.dp)
                            .background(Color.Transparent)

                    )

                }

            }

            AnimatedVisibility(visible = fieldEnable) {


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
                                        onClick = {

                                            if (name.isNotEmpty()) {
                                                when (contactActionState) {
                                                    ContactActionState.CREATE -> profileViewModel.addProfile(
                                                        ProfileModel(
                                                            total_amount = if (amount.isEmpty()) 0 else amount.formatNumberingStyleToInt(),
                                                            name = name,
                                                            amount_type = amountType,
                                                            ph_no = phNo,
                                                            email = email,
                                                            extra_info = extraInfo,
                                                        )
                                                    )

                                                    ContactActionState.UPDATE -> profileViewModel.updateProfile(
                                                        ProfileModel(
                                                            id = id,
                                                            total_amount = if (amount.isEmpty()) 0 else amount.formatNumberingStyleToInt(),
                                                            name = name,
                                                            amount_type = amountType,
                                                            ph_no = phNo,
                                                            email = email,
                                                            extra_info = extraInfo,
                                                        )
                                                    )

                                                    else -> {}
                                                }
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Contact Saved",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()

                                                navController.popBackStack()
                                            } else {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Please Enter the Name",
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
    }

}

@Composable
fun BasicTextBox(
    fieldText: String,
    field: String,
    fieldIsInt: Boolean = false,
    fieldEnable: Boolean = true,
    onChange: (String) -> Unit,
) {

    // group of Name textField
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(start = 3.dp, bottom = 2.dp),
            text = fieldText,
            fontSize = TEXT_FIELD_SIZE,
            color = textColorBLG,
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(shape = RoundedCornerShape(15.dp), color = textBoxBackColor),
            value = field,
            onValueChange = {
                onChange(
                    if (fieldIsInt) it.convertStringToInt() else it.convertStringToAlphabets()
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = textColorBW,
                disabledIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                color = textColorBW,
                fontSize = TEXT_FIELD_SIZE
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (fieldIsInt) KeyboardType.Number else KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            enabled = fieldEnable,
        )
    }
}