package com.developerstring.financesapp.screen.navscreens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.financesapp.R
import com.developerstring.financesapp.roomdatabase.models.CategoryModel
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.state.RequestState

@Composable
fun EditCategoryScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    profileViewModel.getAllCategories()
    val categoryModel by profileViewModel.allCategories.collectAsState()

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
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close",
                    tint = textColorBW
                )
            }

            Text(
                text = stringResource(id = R.string.categories_edit),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW,
                maxLines = 1
            )

            IconButton(onClick = {

            }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "check",
                    tint = textColorBW
                )
            }

        }

    }) {
        Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
            CategoryScreenContent(categoryModel = categoryModel)

        }

    }


}

@Composable
fun CategoryScreenContent(
    categoryModel: RequestState<List<CategoryModel>>
) {

    val scrollState = rememberScrollState()

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        if (categoryModel is RequestState.Success) {
            categoryModel.data.forEach { value ->
                CategoryItem(categoryModel = value, interactionSource = interactionSource)
            }
        }
    }
}

@Composable
fun CategoryItem(
    categoryModel: CategoryModel,
    interactionSource: MutableInteractionSource
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 25.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {

                }),
        elevation = 5.dp,
        color = backgroundColor
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
                    .width(15.dp)
                    .height(60.dp)
                    .background(colorList.random())
            )

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

@Composable
fun SubCategoryItem(
    subCategory: String
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(1.dp))
                .height(40.dp)
                .width(2.dp)
                .background(colorGray)
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(1.dp))
                .height(2.dp)
                .width(15.dp)
                .background(colorGray)
        )
        Text(
            modifier = Modifier
                .padding(start = 15.dp),
            text = subCategory,
            fontSize = MEDIUM_TEXT_SIZE,
            fontFamily = fontOpenSans,
            color = textColorBW,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }

}