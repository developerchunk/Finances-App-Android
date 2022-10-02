package com.developerstring.financesapp.ui.components

import androidx.compose.foundation.clickable
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
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.ui.theme.*
import com.developerstring.financesapp.util.transactionTypeToSymbol

@Composable
fun ViewTransactionItem(
    transactionModel: TransactionModel,
    currency: String,
    navigateToDetails: (id: Int) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 20.dp)
            .clickable(onClick = {
                navigateToDetails(transactionModel.id)
            })
        ,
        color = MaterialTheme.colors.contentColorLBLD,
        shape = RoundedCornerShape(10.dp),
        elevation = TASK_ITEM_ELEVATION,
    ) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 10.dp)
                .fillMaxWidth(),
        ) {

            Text(
                text = "Date:${transactionModel.date}",
                fontFamily = fontInter,
                fontWeight = FontWeight.Medium,
                fontSize = EXTRA_SMALL_TEXT_SIZE,
                color = MaterialTheme.colors.textColorBW
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 5.dp),
                    text = transactionModel.category,
                    fontFamily = fontInter,
                    fontWeight = FontWeight.Medium,
                    fontSize = TEXT_FIELD_SIZE,
                    color = MaterialTheme.colors.textColorBW
                )
                Row {
                    Text(
                        text = "${transactionTypeToSymbol(transactionType = transactionModel.transaction_type)} ${transactionModel.amount} ${currency.last()}",
                        fontFamily = fontInter,
                        fontWeight = FontWeight.Medium,
                        fontSize = TEXT_FIELD_SIZE,
                        color = MaterialTheme.colors.textColorBW
                    )
                }
            }

        }
    }

}