package com.developerstring.finspare.screen.autopayment

import com.developerstring.finspare.util.Constants.ADD_FUND
import com.developerstring.finspare.util.Constants.SPENT

data class BankTransaction(
    val amount: Int,
    val smsTransactionType: String,
    val date: String,
    val timeValues: TimeValues
)

fun convertToBankTransaction(message: String, dateLong: Long): BankTransaction? {
    val keywordBank = "bank"

    val keywordDebit = setOf(
        "debit", "debited", "spent", "sent", "transfer", "transferred"
    )
    val keywordCredit = setOf(
        "credit", "credited"
    )

    // Convert the message to lowercase for case-insensitive checks
    val lowerCaseMessage = message.lowercase()

    // Check if the message contains the keyword "bank"
    if (lowerCaseMessage.contains(keywordBank)) {
        // Check if it mentions "debit" or "credit"
        val smsTransactionType = when {
            keywordDebit.any { it in lowerCaseMessage } -> SPENT
            keywordCredit.any { it in lowerCaseMessage } -> ADD_FUND
            else -> "" // If neither "debit" nor "credit" is found, return none
        }

        // Extract the amount from the message
        val regexAmount = Regex("""[+-]?\d+\.\d+""") // Regular expression to match a decimal number
        val matchResult = regexAmount.find(lowerCaseMessage)
        val amount = matchResult?.value?.toDoubleOrNull() ?: return null

        // You can extract and format the date/time here based on the message content
        val currentDate = dateLong.dateConverter()
        val timeValues = TimeValues(
            day = dateLong.dateToDayConverter(),
            month = dateLong.dateToMonthConverter(),
            year = dateLong.dateToYearConverter()
        )

        // Create and return the BankTransaction object
        return BankTransaction(amount.toInt(), smsTransactionType, currentDate, timeValues)
    }

    return null // Return null if the message does not contain the keyword "bank"
}

