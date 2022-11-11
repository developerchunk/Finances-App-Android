package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.RequestState
import com.developerstring.financesapp.util.SearchBarState
import com.developerstring.financesapp.util.TrailingIconStateSearch

@Composable
fun TopAppBarHistory(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    searchBarState: SearchBarState,
    searchBarText: String,
    allTransactions: RequestState<List<TransactionModel>>,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.backgroundColor)
    ) {

        when (searchBarState) {
            SearchBarState.DELETE -> {
                DefaultTopAppBarHistory(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )
            }

            else -> {
                SearchedTopAppBarHistory(
                    text = searchBarText,
                    sharedViewModel = sharedViewModel,
                    navController = navController,
                    onTextChange = {
                        sharedViewModel.searchBarText.value = it
                    },
                    onSearchClicked = {
                        sharedViewModel.getSearchedTransactions(searchQuery = "%$searchBarText%")
                        sharedViewModel.searchBarState.value = SearchBarState.TRIGGERED
                    },
                    onCloseClicked = {
                        sharedViewModel.searchBarState.value = SearchBarState.DELETE
                    }
                )
            }
        }
    }

}

@Composable
fun DefaultTopAppBarHistory(
    navController: NavController,
    sharedViewModel: SharedViewModel
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
            text = stringResource(id = R.string.history),
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium,
            fontSize = EXTRA_LARGE_TEXT_SIZE,
            color = MaterialTheme.colors.textColorBW
        )

        IconButton(onClick = {
            sharedViewModel.searchBarState.value = SearchBarState.OPENED
        }) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = "search",
                tint = MaterialTheme.colors.textColorBW
            )
        }

    }

}

@Composable
fun SearchedTopAppBarHistory(
    text: String,
    sharedViewModel: SharedViewModel,
    navController: NavController,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {

    var trailingIconStateSearch by remember {
        mutableStateOf(TrailingIconStateSearch.DELETE_TEXT)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.search_placeholder),
                    color = Color.White,
                    fontSize = TEXT_FIELD_SIZE
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.textColorBW,
                fontSize = TEXT_FIELD_SIZE
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        onSearchClicked(text)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.textColorBW
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {

                        when (trailingIconStateSearch) {
                            TrailingIconStateSearch.DELETE_TEXT -> {

                                trailingIconStateSearch = if (text.isNotEmpty()) {
                                    onTextChange("")
                                    TrailingIconStateSearch.CLOSE_TOP_BAR
                                } else {
                                    onCloseClicked()
                                    TrailingIconStateSearch.DELETE_TEXT
                                }
                            }
                            TrailingIconStateSearch.CLOSE_TOP_BAR -> {
                                if (text.isNotEmpty()) {
                                    onTextChange("")
                                } else {
                                    onCloseClicked()
                                    trailingIconStateSearch = TrailingIconStateSearch.DELETE_TEXT
                                }
                            }
                        }

//                        when (trailingIconState) {
//                            TrailingIconState.READY_TO_DELETE -> {
//                                onTextChange("")
//                                trailingIconState = TrailingIconState.READY_TO_CLOSE
//                            }
//                            TrailingIconState.READY_TO_CLOSE -> {
//                                if (text.isNotEmpty()) {
//                                    onTextChange("")
//                                } else {
//                                    onCloseClicked()
//                                    trailingIconState = TrailingIconState.READY_TO_DELETE
//                                }
//                            }
//                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = MaterialTheme.colors.textColorBW
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.textColorBW,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )

    }

}