package com.developerstring.finspare.screen.navscreens.profile

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.ProfileModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.ui.components.NoDataAvailable
import com.developerstring.finspare.ui.theme.Dark
import com.developerstring.finspare.ui.theme.EXTRA_LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.EXTRA_SMALL_TEXT_SIZE
import com.developerstring.finspare.ui.theme.LighterBlue
import com.developerstring.finspare.ui.theme.TEXT_FIELD_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.colorLightGray
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textBoxBackColor
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.LanguageText
import com.developerstring.finspare.util.keyToTransactionType
import com.developerstring.finspare.util.state.ContactActionState
import com.developerstring.finspare.util.state.RequestState

@Composable
fun EditContactsScreen(
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val allProfiles by profileViewModel.allProfiles.collectAsState()
    val contactActionState by profileViewModel.contactActionState

    val interactionSource = remember {
        MutableInteractionSource()
    }

    val scrollState = rememberScrollState()

    var allContactSize by rememberSaveable {
        mutableStateOf(0)
    }

    val context = LocalContext.current

    val languageText = LanguageText(LANGUAGE)

    profileViewModel.contactAction(contactActionState)

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
                text = stringResource(id = languageText.contacts),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_LARGE_TEXT_SIZE,
                color = textColorBW,
                maxLines = 1
            )

            IconButton(onClick = {

                if (allContactSize <= 30) {
                    profileViewModel.contactID.value = 0
                    profileViewModel.contact.value = ProfileModel()
                    profileViewModel.contactActionState.value = ContactActionState.CREATE
                    navController.navigate(NavRoute.EditContactsDetailScreen.route)
                } else {
                    Toast.makeText(context, "Max 30 Contacts", Toast.LENGTH_SHORT).show()
                }

            }) {

                Icon(
                    modifier = Modifier.size(28.dp),
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add contact button",
                    tint = textColorBW
                )

            }


        }

    }) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            ContactsScreenContent(
                profileModel = allProfiles,
                interactionSource = interactionSource,
                scrollState = scrollState,
                languageText = languageText,
                allContactSize = { size ->
                     allContactSize = size
                },
                onClick = { contact ->
                    profileViewModel.contact.value = contact
                    profileViewModel.contactID.value = contact.id
                    profileViewModel.contactActionState.value = ContactActionState.UPDATE
                    navController.navigate(route = NavRoute.EditContactsDetailScreen.route)
                }
            )

        }

    }

}

@Composable
fun ContactsScreenContent(
    profileModel: RequestState<List<ProfileModel>>,
    interactionSource: MutableInteractionSource,
    scrollState: ScrollState,
    languageText: LanguageText,
    allContactSize: (Int) -> Unit,
    onClick: (ProfileModel) -> Unit
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
            if (profileModel is RequestState.Success) {

                ContactScreenContent(
                    contactProfiles = profileModel.data,
                    interactionSource = interactionSource,
                    languageText = languageText,
                    allContactSize = {
                               allContactSize(it)
                    },
                    onClick = {
                        onClick(it)
                    }
                )

            }
        }
    }

}

@Composable
fun ContactScreenContent(
    contactProfiles: List<ProfileModel>,
    interactionSource: MutableInteractionSource,
    languageText: LanguageText,
    allContactSize: (Int) -> Unit,
    onClick: (ProfileModel) -> Unit,
) {

    allContactSize(contactProfiles.size)
    
    if (contactProfiles.size>1) {
        contactProfiles.drop(1).forEach { value ->

            ContactItem(
                profileModel = value,
                interactionSource = interactionSource,
                languageText = languageText,
                onClick = { id ->
                    onClick(id)
                }
            )

        }
    } else {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.fillMaxWidth().height(100.dp))
            NoDataAvailable("Create new Contacts to\nsee them!")

        }
    }

    
}

@Composable
fun ContactItem(
    profileModel: ProfileModel,
    languageText: LanguageText,
    interactionSource: MutableInteractionSource,
    onClick: (ProfileModel) -> Unit
) {

    Surface(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, bottom = 5.dp, top = 5.dp)
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = interactionSource,
                onClick = {
                    onClick(profileModel)
                }),
        elevation = 0.dp,
        color = Color.Transparent,
        shape = RoundedCornerShape(20.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .clip(
                    shape = RoundedCornerShape(20.dp),
                )
                .fillMaxWidth()
                .background(textBoxBackColor),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp, bottom = 10.dp)
                    .size(60.dp)
                    .clip(RoundedCornerShape(100))
                    .background(LighterBlue),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = profileModel.name.first().toString(),
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Dark
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(0.7f)) {
                    Text(
                        modifier = Modifier,
                        text = profileModel.name, fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = TEXT_FIELD_SIZE,
                        color = textColorBW,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = "${stringResource(id = languageText.amountTextField).filterNot { it=='*' }}: ${profileModel.total_amount}",
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = EXTRA_SMALL_TEXT_SIZE,
                        color = colorLightGray,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = "${stringResource(id = languageText.lend)}: ${profileModel.amount_type.keyToTransactionType()}",
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = EXTRA_SMALL_TEXT_SIZE,
                        color = colorLightGray,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }

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

