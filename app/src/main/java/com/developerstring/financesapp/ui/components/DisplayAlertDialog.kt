package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.developerstring.financesapp.R
import com.developerstring.financesapp.ui.theme.*

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onCloseClicked: () -> Unit,
    onYesClicked: () -> Unit
) {

    if (openDialog) {
        AlertDialog(
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
                Text(
                    modifier = Modifier.padding(bottom = 10.dp),
                    text = message,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = MEDIUM_TEXT_SIZE,
                    color = textColorBW
                )
            },
            confirmButton = {
                Button(
                    modifier = Modifier.padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = UIBlue),
                    shape = CircleShape,
                    onClick = {
                        onYesClicked()
                        onCloseClicked()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.yes),
                        color = Color.White,
                        fontSize = LARGE_TEXT_SIZE,
                        fontFamily = fontOpenSans,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    modifier = Modifier.padding(end = 30.dp, bottom = 30.dp),
                    onClick = {
                        onCloseClicked()
                    },
                    border = BorderStroke(1.dp,color = textColorBW),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                    shape = CircleShape
                ) {
                    Text(
                        text = stringResource(id = R.string.no),
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