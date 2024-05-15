package com.developerstring.finspare.screen.autopayment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Telephony
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.developerstring.finspare.navigation.navgraph.NavRoute
import com.developerstring.finspare.roomdatabase.models.TransactionModel
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.LARGE_TEXT_SIZE
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_ELEVATION
import com.developerstring.finspare.ui.theme.TOP_APP_BAR_HEIGHT
import com.developerstring.finspare.ui.theme.backgroundColor
import com.developerstring.finspare.ui.theme.backgroundColorBW
import com.developerstring.finspare.ui.theme.fontInter
import com.developerstring.finspare.ui.theme.textColorBW
import com.developerstring.finspare.util.dataclass.MessageItem
import com.developerstring.finspare.util.stringToSet
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AutoPaymentScreen(
    sharedViewModel: SharedViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController
) {

    val currency by profileViewModel.profileCurrency.collectAsState()

    var messages by remember { mutableStateOf(emptyList<MessageItem>()) }

    val context = LocalContext.current

    profileViewModel.messageScanDetails(context = context)
    val allMessageIDs by profileViewModel.messageScanIDs.collectAsState()

    var permissionGranted by remember {
        mutableStateOf(false)
    }

    var permissionRequested by remember {
        mutableStateOf(false)
    }

    val messageScanEnable by profileViewModel.messageScanEnable.collectAsState()

    if (Build.VERSION.SDK_INT >= 23) {

        // Permission request launcher
        val requestPermission =
            rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    // Permission is granted, read messages
                    messages = readMessages(context = context)
                    permissionGranted = true
                } else {
                    // Permission denied, handle accordingly
                    // You might want to show a warning or take appropriate action
                }
            }

        // Check for READ_SMS permission on app launch
        DisposableEffect(context) {
            if (checkPermission(Manifest.permission.READ_SMS, context = context)) {
                messages = readMessages(context)
                permissionGranted = true
            }

            onDispose { }
        }

        if (permissionRequested) {
            // Check for READ_SMS permission on app next button clicked
            DisposableEffect(context) {
                if (checkPermission(Manifest.permission.READ_SMS, context = context)) {
                    messages = readMessages(context)
                    permissionGranted = true
                    profileViewModel.saveMessageScanEnable(context = context, enable = true)
                } else {
                    requestPermission.launch(Manifest.permission.READ_SMS)
                }
                onDispose { }
            }
        }

        if (!permissionGranted || messageScanEnable) {
            MessagePermissionScreen(
                navController = navController, permissionRequested = {
                    permissionRequested = it
                }
            )
        }

        // if sms permission is granted
        if (permissionGranted) {

            messages.filter { it.message.contains("Bank") }

            SmsToPaymentsScreen(
                messages = messages,
                navController = navController,
                currency = currency,
                messagesIDS = allMessageIDs.stringToSet(),
            ) { transactionModel, messageId ->

                sharedViewModel.messageID.value = messageId
                sharedViewModel.addTransactionModel.value = transactionModel
                navController.navigate(NavRoute.AddTransactionScreen.route)

            }
        }
    }
}

@Composable
fun SmsToPaymentsScreen(
    messages: List<MessageItem>,
    navController: NavController,
    currency: String,
    messagesIDS: Set<String>,
    onClick: (TransactionModel, messageID: String) -> Unit
) {

    var expandedLaunched by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = TOP_APP_BAR_ELEVATION,
                color = backgroundColorBW
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TOP_APP_BAR_HEIGHT)
                        .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(24.dp),
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "back_arrow",
                                tint = textColorBW
                            )
                        }
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "Payment Messages",
                            fontFamily = fontInter,
                            fontWeight = FontWeight.Medium,
                            fontSize = LARGE_TEXT_SIZE,
                            color = textColorBW,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2
                        )
                    }

                    IconButton(onClick = {
                        navController.navigate(NavRoute.MessagePaymentInfoScreen.route)
                    }) {
                        Icon(
                            modifier = Modifier
                                .width(28.dp)
                                .height(24.dp),
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "back_arrow",
                            tint = textColorBW
                        )
                    }

                }

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(backgroundColor)
        ) {

            LazyColumn {
                items(messages) { item ->

                    val bankTransaction =
                        convertToBankTransaction(message = item.message, dateLong = item.date)
                    if (bankTransaction != null) {

                        val transactionModel = TransactionModel(
                            amount = bankTransaction.amount,
                            transaction_type = bankTransaction.smsTransactionType,
                            date = bankTransaction.date,
                            day = bankTransaction.timeValues.day,
                            month = bankTransaction.timeValues.month,
                            year = bankTransaction.timeValues.year,
                        )

                        MessageItemView(
                            bankTransaction = bankTransaction,
                            currency = currency,
                            navigateToDetails = {
                                onClick(transactionModel, item.id)
                            },
                            launched = true,
                            expandedLaunched = expandedLaunched,
                            message = item.message,
                            checked = messagesIDS.contains(item.id)
                        )


                    }

                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        expandedLaunched = true
    }


}

@RequiresApi(Build.VERSION_CODES.M)
fun checkPermission(permission: String, context: Context): Boolean {
    // Placeholder function to check if a permission is granted
    return context.checkSelfPermission(permission) ==
            PackageManager.PERMISSION_GRANTED
}

@RequiresApi(Build.VERSION_CODES.M)
fun readMessages(context: Context): List<MessageItem> {

    val messagesList = mutableListOf<MessageItem>()
    val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2023-11-01")
    val endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse("2024-12-31")

    if (checkPermission(Manifest.permission.READ_SMS, context = context)) {
        val cursor = context.contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.DATE,
                Telephony.Sms._ID
            ),
            "${Telephony.Sms.BODY} LIKE ? AND ${Telephony.Sms.DATE} BETWEEN ? AND ?",
            arrayOf("%Bank%", startDate?.time.toString(), endDate?.time.toString()),
            Telephony.Sms.DEFAULT_SORT_ORDER
        )

        cursor?.use {
            val addressIndex = it.getColumnIndex(Telephony.Sms.ADDRESS)
            val bodyIndex = it.getColumnIndex(Telephony.Sms.BODY)
            val timestampIndex = it.getColumnIndex(Telephony.Sms.DATE)
            val idIndex = it.getColumnIndex(Telephony.Sms._ID)

            while (it.moveToNext()) {
                val sender = if (addressIndex != -1) it.getString(addressIndex) else ""
                val message = if (bodyIndex != -1) it.getString(bodyIndex) else ""
                val timestamp = if (timestampIndex != -1) it.getLong(timestampIndex) else 0
                val id = if (idIndex != -1) it.getString(idIndex) else ""

                // Use Timestamp class to get Calendar instance
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timestamp

                val date = calendar.time

                // Check if the message is within the specified date range
                if (date in startDate..endDate) {
                    // Do something with the message within the date range
                    messagesList.add(MessageItem(sender, message, timestamp, id))
                }
            }
        }
    }

    return messagesList
}

fun Long.dateConverter(): String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(this))
}

fun Long.dateToDayConverter(): Short {
    return SimpleDateFormat("dd", Locale.getDefault()).format(Date(this)).toShort()
}

fun Long.dateToMonthConverter(): Short {
    return SimpleDateFormat("MM", Locale.getDefault()).format(Date(this)).toShort()
}

fun Long.dateToYearConverter(): Short {
    return SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(this)).toShort()
}
