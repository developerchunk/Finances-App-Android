package com.developerstring.finspare.screen.navscreens.profile

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.developerstring.finspare.ui.theme.*
import com.developerstring.finspare.util.Constants.LANGUAGE
import com.developerstring.finspare.util.Constants.TERMS_AND_CONDITION_URL
import com.developerstring.finspare.util.LanguageText

@Composable
fun TermsAndConditionsScreen(
    navController: NavController
) {

    val languageText = LanguageText(LANGUAGE)

    Scaffold(topBar = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ExtraDark)
                .height(TOP_APP_BAR_HEIGHT)
                .padding(end = 5.dp),
            contentAlignment = Alignment.CenterStart
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
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = languageText.termsAndConditions),
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = TEXT_FIELD_SIZE,
                color = textColorBW,
                textAlign = TextAlign.Center
            )

        }
    })
    {
        Column(modifier = Modifier.padding(it).fillMaxSize()) {
            // Adding a WebView inside AndroidView
            // with layout as full screen
            AndroidView(factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(TERMS_AND_CONDITION_URL)
                }
            }, update = { web ->
                web.loadUrl(TERMS_AND_CONDITION_URL)
            })
        }
    }




}