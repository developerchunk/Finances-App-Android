package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.developerstring.financesapp.R
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.LanguageText
import com.developerstring.financesapp.util.convertStringToAlphabets
import com.developerstring.financesapp.util.randomCaptcha

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    captchaVerification: Boolean = false,
    onCloseClicked: () -> Unit,
    onYesClicked: () -> Unit,
    languageText: LanguageText
) {

    var confirmEnable by remember {
        mutableStateOf(!captchaVerification)
    }

    var captchaEntered by remember {
        mutableStateOf("")
    }

    var captchaValue by rememberSaveable {
        mutableStateOf(randomCaptcha(6))
    }

    fun reCaptcha(){
        captchaEntered = ""
        captchaValue = randomCaptcha(6)
        confirmEnable = false
    }

    if (openDialog) {
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10),
            title = {
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = title,
                    fontFamily = fontOpenSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = LARGE_TEXT_SIZE,
                    color = textColorBW
                )
            },
            text = {

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.padding(bottom = 10.dp),
                        text = message,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = MEDIUM_TEXT_SIZE,
                        color = textColorBW
                    )

                    if (captchaVerification) {

                        Column(modifier = Modifier.fillMaxWidth()) {

                            Box(
                                modifier = Modifier
                                    .padding(start = 10.dp, end = 10.dp, top = 20.dp, bottom = 10.dp)
                                    .fillMaxWidth()
                                    .background(color = backgroundColorBW, shape = RoundedCornerShape(3.dp))
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center

                            ) {
                                Text(
                                    text = captchaValue,
                                    fontFamily = fontOpenSans,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = textColorBW
                                )
                            }

                            // group of Captcha textField
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 3.dp, bottom = 2.dp),
                                    text = stringResource(id = R.string.enter_captcha),
                                    fontSize = TEXT_FIELD_SIZE,
                                    color = textColorBLG,
                                    fontFamily = fontInter,
                                    fontWeight = FontWeight.Medium
                                )

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(55.dp)
                                        .border(
                                            width = 1.8.dp,
                                            color = textColorBLG,
                                            shape = RoundedCornerShape(15.dp)
                                        ),
                                    value = captchaEntered,
                                    onValueChange = {
                                        captchaEntered = it.convertStringToAlphabets(10)
                                        confirmEnable = captchaValue == captchaEntered
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
                                    singleLine = true
                                )
                            }


                        }

                    }
                }


            },
            confirmButton = {
                Button(
                    modifier = Modifier.padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor =
                        if (confirmEnable) LightestBlue
                        else textColorBW.copy(alpha = 0.4f)
                    ),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {
                        if (confirmEnable) {
                            reCaptcha()
                            onYesClicked()
                            onCloseClicked()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = languageText.yes),
                        color = if (confirmEnable) {
                            UIBlue
                        } else Color.Black.copy(alpha = 0.4f),
                        fontSize = LARGE_TEXT_SIZE,
                        fontFamily = fontOpenSans,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                Button(
                    modifier = Modifier.padding(end = 10.dp, bottom = 30.dp),
                    onClick = {
                        reCaptcha()
                        onCloseClicked()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    shape = CircleShape,
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    Text(
                        text = stringResource(id = languageText.no),
                        fontFamily = fontOpenSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = LARGE_TEXT_SIZE,
                        color = textColorBW
                    )
                }
            },
            onDismissRequest = {
                onCloseClicked()
            }
        )
    }

}