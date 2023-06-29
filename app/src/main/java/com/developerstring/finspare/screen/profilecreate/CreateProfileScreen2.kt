package com.developerstring.finspare.screen.profilecreate

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.setupnav.SetUpNavRoute
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.theme.MAX_TEXT_SIZE
import com.developerstring.finspare.ui.theme.MEDIUM_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.colorGray
import com.developerstring.finspare.ui.theme.contentBackgroundColor
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.fontOpenSans
import com.developerstring.finspare.ui.theme.textColorBLG
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.convertStringToInt
import com.developerstring.finspare.util.state.RequestState

@Composable
fun CreateProfileScreen2(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    //context
    val context = LocalContext.current

    val spending = remember { mutableStateOf("") }
    val savings = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val language = profileViewModel.language
    val languageText = LanguageText(language = language)

    val time24Hours by remember {
        mutableStateOf(false)
    }

    profileViewModel.getAllCategories()
    val categories by profileViewModel.allCategories.collectAsState()

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
                text = stringResource(id = languageText.createProfileScreenText2),
                fontSize = MEDIUM_TEXT_SIZE,
                color = colorGray,
                fontFamily = fontInter,
                fontWeight = FontWeight.Normal
            )

            // spending
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp)
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
                        .heightIn(min = 55.dp)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = spending.value,
                    onValueChange = {
                        spending.value = it.convertStringToInt()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBLG
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
            // savings
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp)
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
                        .heightIn(min = 55.dp)
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = savings.value,
                    onValueChange = {
                        savings.value = it.convertStringToInt()
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBLG
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
        }

        Column(
            modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(220.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(contentBackgroundColor),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (spending.value.isNotEmpty()
                    ) {
                        profileViewModel.saveProfileDetail2(
                            context = context,
                            spending = spending.value.toInt(),
                            savings =
                            if (savings.value.isEmpty()) {
                                (spending.value.toInt() / 3)
                            } else {
                                savings.value.toInt()
                            },
                            time24Hours = time24Hours
                        )

                        addCategoriesToDB(
                            categories = categories,
                            profileViewModel = profileViewModel
                        )

                        navController.popBackStack()
                        navController.navigate(SetUpNavRoute.SplashScreenSetUpNavRoute.route)
                    } else {
                        Toast.makeText(context, "Please enter spending amount", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            ) {
                Text(
                    text = stringResource(id = languageText.finish),
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
            )

        }

    }

}

fun addCategoriesToDB(
    categories: RequestState<List<CategoryModel>>,
    profileViewModel: ProfileViewModel
) {

    if (categories is RequestState.Success) {
        if (categories.data.isEmpty())
            Constants.SUB_CATEGORY.forEach {
                profileViewModel.addCategory(
                    CategoryModel(
                        category = it.key,
                        subCategory = it.value.joinToString(Constants.SEPARATOR_LIST)
                    )
                )
            }
    }

}