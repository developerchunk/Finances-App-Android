package com.developerstring.finspare.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.FILTER_NAME
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.filterListText
import com.developerstring.finspare.util.state.FilterTransactionState
import com.developerstring.finspare.util.state.RequestState
import com.developerstring.finspare.util.state.SearchBarState
import com.developerstring.finspare.util.state.TrailingIconStateSearch
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TopAppBarHistory(
    sharedViewModel: SharedViewModel,
    categoriesModels: RequestState<List<CategoryModel>>,
    navController: NavController,
    searchBarState: SearchBarState,
    searchBarText: String,
    languageText: LanguageText
) {

    var categories by remember {
        mutableStateOf(listOf<String>())
    }

    if (categoriesModels is RequestState.Success) {
        categories = categoriesModels.data.map { it.category }.sorted()
    }

    var filter by sharedViewModel.filterState
    var filterText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        filter = FilterTransactionState.CLOSED
        sharedViewModel.searchBarState.value = SearchBarState.DELETE
        sharedViewModel.searchBarText.value = ""
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = backgroundColorBW
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(bottom = 10.dp)
        ) {

            when (searchBarState) {
                SearchBarState.DELETE -> {
                    DefaultTopAppBarHistory(
                        navController = navController,
                        sharedViewModel = sharedViewModel,
                        languageText = languageText
                    )
                }

                else -> {
                    SearchedTopAppBarHistory(
                        text = searchBarText,
                        onTextChange = {
                            sharedViewModel.searchBarText.value = it
                        },
                        onSearchClicked = {
                            sharedViewModel.searchBarState.value = SearchBarState.TRIGGERED
                            if (filter == FilterTransactionState.OPENED) {
                                sharedViewModel.getFilterSearchedTransactions(
                                    searchQuery = "%$searchBarText%",
                                    filterQuery = "%${filterText.filterListText()}%"
                                )
                            } else {
                                sharedViewModel.getSearchedTransactions(searchQuery = "%$searchBarText%")
                            }
                        },
                        onCloseClicked = {
                            sharedViewModel.searchBarText.value = ""
                            if (filter == FilterTransactionState.OPENED) {
                                sharedViewModel.getSearchedTransactions(searchQuery = "%${filterText.filterListText()}%")
                            }
                            sharedViewModel.searchBarState.value = SearchBarState.DELETE
                        },
                        languageText = languageText
                    )
                }
            }



            TopAppBarFilterContent(
                categories = categories,
                selectedList = {
                    filterText = it
                    if (filterText.isNotEmpty()) {
                        filter = FilterTransactionState.OPENED
                        if (filter == FilterTransactionState.OPENED && searchBarState == SearchBarState.TRIGGERED) {
                            sharedViewModel.getFilterSearchedTransactions(
                                searchQuery = "%$searchBarText%",
                                filterQuery = "%${filterText.filterListText()}%"
                            )
                        } else {
                            sharedViewModel.getSearchedTransactions(searchQuery = "%${filterText.filterListText()}%")
                            sharedViewModel.searchBarState.value = SearchBarState.DELETE
                        }
                    } else {
                        filter = FilterTransactionState.CLOSED
                    }
                }
            )

        }
    }

}

@Composable
fun TopAppBarFilterContent(
    categories: List<String>,
    selectedList: (String) -> Unit,
) {

    val filterList = FILTER_NAME.plus(categories)


    val expandBackground = Brush.horizontalGradient(
        colors = listOf(
            Color.Transparent,
            backgroundColorBW,
        ),
    )

    var expanded by remember {
        mutableStateOf(false)
    }

    var selected by remember {
        mutableStateOf("")
    }


    Box(
        modifier = Modifier
            .padding(start = 10.dp, end = 20.dp, top = 15.dp, bottom = 4.dp)
            .fillMaxWidth(),
    ) {

        Row(
            modifier = Modifier
                .padding(start = 60.dp)
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 500,
                    )
                )
        ) {
            when (expanded) {
                true -> {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .verticalScroll(
                                rememberScrollState()
                            ),
                        mainAxisSpacing = 16.dp,
                        crossAxisSpacing = 16.dp,
                    ) {
                        filterList.forEach { mList ->
                            SearchChip(
                                title = mList,
                                selected = selected,
                                onSelected = { text ->
                                    selected = if (text == selected) "" else text
                                    selectedList(selected)
                                })
                        }
                    }
                }

                else -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        filterList.forEach { mList ->
                            SearchChip(
                                title = mList,
                                selected = selected,
                                onSelected = { text ->
                                    selected = if (text == selected) "" else text
                                    selectedList(selected)
                                })
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .height(40.dp)
                .width(50.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.TopStart
        ) {

            IconButton(
                onClick = {
                    expanded = !expanded
                }
            ) {

                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "expand",
                    tint = textColorBW
                )

            }

        }

        if (!expanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(50.dp)
                        .background(expandBackground)
                )
            }
        }

    }


}

@Composable
fun DefaultTopAppBarHistory(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    languageText: LanguageText
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
            text = stringResource(id = languageText.history),
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium,
            fontSize = EXTRA_LARGE_TEXT_SIZE,
            color = textColorBW
        )

        IconButton(onClick = {
            sharedViewModel.searchBarState.value = SearchBarState.OPENED
        }) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Rounded.Search,
                contentDescription = "search",
                tint = textColorBW
            )
        }

    }

}

@Composable
fun SearchedTopAppBarHistory(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit,
    languageText: LanguageText
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
                    text = stringResource(id = languageText.searchPlaceholder),
                    color = textColorBW,
                    fontSize = TEXT_FIELD_SIZE
                )
            },
            textStyle = TextStyle(
                color = textColorBW,
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
                        contentDescription = null,
                        tint = textColorBW
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
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = textColorBW
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
                cursorColor = textColorBW,
                focusedIndicatorColor = textColorBW,
                disabledIndicatorColor = textColorBW,
                unfocusedIndicatorColor = textColorBW,
                backgroundColor = Color.Transparent
            )
        )

    }

}