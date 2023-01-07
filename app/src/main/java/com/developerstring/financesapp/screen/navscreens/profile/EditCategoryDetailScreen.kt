package com.developerstring.financesapp.screen.navscreens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.Constants.SEPARATOR_LIST
import com.developerstring.financesapp.util.convertStringToAlphabets
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditCategoryDetailScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController,
) {

    profileViewModel.getSelectedCategories()
    val categoryModel by profileViewModel.selectedCategories.collectAsState()

    var category by remember {
        mutableStateOf("")
    }
    var subCategories = mutableListOf("")
    var subCategorySelected by mutableStateOf(0)
    var subCategory by mutableStateOf("")

    val interactionSource = remember {

        MutableInteractionSource()
//
    }

    try {
        subCategories = categoryModel!!.subCategory.split(SEPARATOR_LIST) as MutableList<String>
        category = categoryModel!!.category
    } catch (_: Exception) {

    }

    val scrollState = rememberScrollState()

    var launched by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        launched = true
    }

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp)
            ) {

                TextField(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.8.dp,
                            color = textColorBLG,
                            shape = RoundedCornerShape(15.dp)
                        ),
                    value = subCategory,
                    onValueChange = {
                        subCategory = it
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textColorBW,
                    ),
                    textStyle = TextStyle(
                        color = textColorBW,
                        fontSize = LARGE_TEXT_SIZE,
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        IconButton(onClick = {
                            subCategories.set(index = subCategorySelected, element = subCategory)
                            profileViewModel.updateSubCategoryName(
                                id = categoryModel!!.id,
                                subCategory = subCategories.joinToString(SEPARATOR_LIST)
                            )
                            scope.launch {
                                sheetState.collapse()
                            }
                            subCategory = ""
                        }) {

                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = Icons.Rounded.Check,
                                contentDescription = "check",
                                tint = textColorBW
                            )

                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_subcategory),
                            fontSize = TEXT_FIELD_SIZE,
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            color = colorGray
                        )
                    },
                    singleLine = false
                )

            }

        },
        sheetBackgroundColor = contentColorCard,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 10.dp,
        sheetPeekHeight = 0.dp,
        sheetGesturesEnabled = true,
    ) {
        Scaffold(topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(TOP_APP_BAR_HEIGHT)
                    .background(backgroundColor)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = {
                            scope.launch {
                                if (sheetState.isExpanded) {
                                    sheetState.collapse()
                                }
                            }
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                     onClick = {
//                        navController.popBackStack()
                    }) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource,
                                onClick = {
                                    if (sheetState.isExpanded) {
                                        scope.launch {

                                            sheetState.collapse()
                                        }
                                    } else {
                                        navController.popBackStack()
                                    }
                                }
                            ),
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = "close",
                        tint = textColorBW
                    )
                }

                Text(
                    text = stringResource(id = R.string.category_edit),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = EXTRA_LARGE_TEXT_SIZE,
                    color = textColorBW,
                    maxLines = 1
                )

                IconButton(onClick = {
                    scope.launch {
                        if (sheetState.isCollapsed) {
                            subCategory = ""
                            subCategorySelected = subCategories.size
                            subCategories.add(index = subCategories.size, element = "")
                            sheetState.expand()
                        } else {
                            sheetState.collapse()
                        }
                    }
                }) {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add",
                        tint = textColorBW
                    )
                }

            }
        }) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .clickable(
                        indication = null,
                        interactionSource = interactionSource,
                        onClick = {
                            scope.launch {
                                if (sheetState.isExpanded) {
                                    sheetState.collapse()
                                }
                            }
                        }
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(backgroundColor)
                ) {

                    Card(
                        modifier = Modifier
                            .padding(vertical = 20.dp, horizontal = 30.dp)
                            .fillMaxWidth(),
                        elevation = 5.dp,
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = contentColorCard
                    ) {

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        scope.launch {
                                            if (sheetState.isExpanded) {
                                                sheetState.collapse()
                                            }
                                        }
                                    }
                                ),
                            value = category,
                            onValueChange = {
                                category = it.convertStringToAlphabets()
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
                                fontSize = LARGE_TEXT_SIZE,
                                fontFamily = fontInter,
                                fontWeight = FontWeight.Medium
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = false,
                            enabled = sheetState.isCollapsed
                        )

                    }

                    AnimatedVisibility(
                        visible = launched,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                        exit = fadeOut(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = LinearOutSlowInEasing
                            )
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 100.dp)
                        ) {
                            subCategories.forEachIndexed { index, value ->
                                SubCategoryItem(
                                    subCategory = value,
                                    index = index,
                                    interactionSource = interactionSource,
                                    onClick = { i ->
                                        scope.launch {
                                            if (sheetState.isCollapsed) {
                                                subCategorySelected = i
                                                subCategory = subCategories[i]
                                                sheetState.expand()
                                            } else {
                                                sheetState.collapse()
                                            }
                                        }
                                    },
                                    onDelete = { i ->

                                        subCategories.removeAt(i)
                                        profileViewModel.updateSubCategoryName(
                                            id = categoryModel!!.id,
                                            subCategory = subCategories.joinToString(SEPARATOR_LIST)
                                        )

                                    })
                            }
                        }
                    }
                }

            }
        }

    }


}

@Composable
fun SubCategoryItem(
    subCategory: String,
    index: Int,
    interactionSource: MutableInteractionSource,
    onDelete: (Int) -> Unit,
    onClick: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(index)
                }
            ),
    ) {

        Row(
            modifier = Modifier
                .padding(start = 25.dp, top = 10.dp, end = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = subCategory,
                onValueChange = {

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    color = textColorBW,
                    fontSize = LARGE_TEXT_SIZE,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = false,
                enabled = false,
                trailingIcon = {

                    IconButton(onClick = {
                        onDelete(index)
                    }) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "delete",
                            tint = textColorBW
                        )
                    }
                }
            )

        }

        Box(
            modifier = Modifier
                .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .height(2.dp)
                .background(textColorBW)
        )
    }

}