package com.developerstring.finspare.screen.navscreens.profile

import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.R
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.CategoryModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.state.RequestState
import kotlinx.coroutines.delay

@Composable
fun EditCategoryScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController,
) {

    val categoryModel by profileViewModel.allCategories.collectAsState()

    val categoryDelete by profileViewModel.deleteCategoryState
    profileViewModel.categoryAction(action = categoryDelete)

    var addError by remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var launched by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        launched = true
    }

    val scrollState = rememberScrollState()

    Scaffold(topBar = {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(TOP_APP_BAR_HEIGHT)
                .background(backgroundColor),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "close",
                    tint = textColorBW
                )
            }

            Text(
                text = stringResource(id = R.string.categories),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW,
                maxLines = 1
            )

            AddCategoryIcon(
                categoryModel = categoryModel,
                profileViewModel = profileViewModel,
                navController = navController,
                addError = { errorState ->
                    addError = errorState
                }
            )

        }

    }) {

        if (addError) {
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.add_error_category),
                Toast.LENGTH_SHORT
            ).show()
            addError = false
        }

        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {

            if (launched) {
                CategoryScreenContent(
                    categoryModel = categoryModel,
                    profileViewModel = profileViewModel,
                    interactionSource = interactionSource,
                    scrollState = scrollState,
                    onClick = {id ->
                        profileViewModel.categoryId.value = id
                        profileViewModel.getSelectedCategories(id = id)
                        navController.navigate(route = NavRoute.EditCategoryDetailScreen.route)
                    }
                )
            }

        }


    }

}


@Composable
fun AddCategoryIcon(
    categoryModel: RequestState<List<CategoryModel>>,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    addError: (Boolean) -> Unit,
) {

    IconButton(onClick = {
        if (categoryModel is RequestState.Success) {
            if (categoryModel.data.size <= 30) {
                profileViewModel.addCategory(
                    categoryModel = CategoryModel(
                        category = "Category Name",
                        subCategory = "Sub Category-1"
                    )
                )
                profileViewModel.categoryId.value = 0
                navController.navigate(route = NavRoute.EditCategoryDetailScreen.route)
            } else {
                addError(true)
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

@Composable
fun CategoryScreenContent(
    categoryModel: RequestState<List<CategoryModel>>,
    profileViewModel: ProfileViewModel,
    interactionSource: MutableInteractionSource,
    scrollState: ScrollState,
    onClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            )
            if (categoryModel is RequestState.Success) {

                profileViewModel.categoriesSize.value = categoryModel.data.size

                CategoryScreenContent(
                    categories = categoryModel.data,
                    interactionSource = interactionSource,
                    onClick = {
                        onClick(it)
                    }
                )

            }
        }
    }

}

@Composable
fun CategoryScreenContent(
    categories: List<CategoryModel>,
    interactionSource: MutableInteractionSource,
    onClick: (Int) -> Unit,
) {
    categories.forEach { value ->
        CategoryItem(
            categoryModel = value,
            interactionSource = interactionSource,
            onClick = { id ->
                onClick(id)
            }
        )
    }
}

@Composable
fun CategoryItem(
    categoryModel: CategoryModel,
    interactionSource: MutableInteractionSource,
    onClick: (Int) -> Unit
) {

    var animated by remember {
        mutableStateOf(false)
    }

    val color = remember {
        Animatable(colorList.random())
    }
    val color2 = remember {
        Animatable(colorList.random())
    }

    val barHeight =
        animateDpAsState(
            targetValue = if (animated) 60.dp else 0.dp,
            animationSpec = tween(durationMillis = 2000)
        )

    LaunchedEffect(key1 = true) {
        delay(300)
        animated = true
        color.animateTo(
            colorList.random(),
            animationSpec = tween(durationMillis = 2000, delayMillis = 0)
        )
    }

    Surface(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, bottom = 25.dp, top = 5.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(categoryModel.id)
                }),
        elevation = 5.dp,
        color = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(20.dp),
                )
                .fillMaxWidth()
                .background(contentColorCard),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .height(60.dp)
                    .width(15.dp)
                    .background(color2.value)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(barHeight.value)
                        .background(color.value)
                )
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth(0.8f),
                    text = categoryModel.category, fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = TEXT_FIELD_SIZE,
                    color = textColorBW,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(28.dp),
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "arrow",
                    tint = textColorBW
                )

            }

        }

    }

}