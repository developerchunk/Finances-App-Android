package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.transactionTypeToSymbol

@Composable
fun ViewTransactionItem(
    date: String,
    category: String,
    amount: String,
    transactionType: String,
    currency: String
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 20.dp),
        color = MaterialTheme.colors.contentColorLBLD,
        shape = RoundedCornerShape(10.dp),
        elevation = TASK_ITEM_ELEVATION,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {

            Text(
                text = "Date:$date",
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_SMALL_TEXT_SIZE
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                    text = category,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = TEXT_FIELD_SIZE
                )
                Row {
                    Text(
                        text = "${transactionTypeToSymbol(transactionType = transactionType)} $amount ${currency.last()}",
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = TEXT_FIELD_SIZE
                    )
                }
            }

        }
    }

}