package com.developerstring.financesapp.screen.profilecreate

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.navigation.setupnav.SetUpNavRoute
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.ui.theme.*

@Composable
fun CreateProfileScreen2(
    navController: NavController,
    profileViewModel: ProfileViewModel
) {

    //context
    val context = LocalContext.current

    val spending = remember { mutableStateOf("") }
    val savings = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.backgroundColor)
            .verticalScroll(state = ScrollState(1))
        ,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                text = stringResource(id = R.string.profile),
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = MaterialTheme.colors.textColorBW,
                fontFamily = fontOpenSans,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .padding(top = 50.dp, start = 30.dp),
                text = stringResource(id = R.string.create_profile_screen_text_2),
                fontSize = MEDIUM_TEXT_SIZE,
                color = MaterialTheme.colors.colorGray,
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
                    text = stringResource(id = R.string.create_profile_screen_2_spending),
                    fontSize = TEXT_FIELD_SIZE,
                    color = MaterialTheme.colors.textColorBLG,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            width = 1.8.dp,
                            color = MaterialTheme.colors.textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = spending.value,
                    onValueChange = {
                        spending.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colors.textColorBLG
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
            // savings
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 30.dp)
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
                        .height(55.dp)
                        .border(
                            width = 1.8.dp,
                            color = MaterialTheme.colors.textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = savings.value,
                    onValueChange = {
                        savings.value = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colors.textColorBLG
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.textColorBW,
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
            modifier = Modifier.padding(top = 30.dp,bottom = 30.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .width(220.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.contentBackgroundColor),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (spending.value.isNotEmpty()
                    ) {
                        profileViewModel.saveProfileDetail2(
                            context = context,
                            spending = spending.value.toInt(),
                            savings =
                            if (savings.value.isEmpty()) {
                                (spending.value.toInt() / 2)
                            } else {
                                savings.value.toInt()
                            }
                        )
                        navController.popBackStack()
                        navController.navigate(SetUpNavRoute.MainSetUpNavRoute.route)
                    } else {
                        Toast.makeText(context, "Please enter spending amount", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            ) {
                Text(text = "Finish", color = Color.White, fontSize = 20.sp)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f)
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun CreateProfileScreen2Preview() {
    CreateProfileScreen2(
        navController = rememberNavController(),
        profileViewModel = ProfileViewModel()
    )
}