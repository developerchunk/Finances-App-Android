package com.developerstring.financesapp.sharedviewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.developerstring.financesapp.roomdatabase.models.TransactionModel
import com.developerstring.financesapp.util.state.MessageBarState

class PublicSharedViewModel : ViewModel() {

    var messageBarState: MutableState<MessageBarState> = mutableStateOf(
        MessageBarState.CLOSED
    )

    var messageShow: MutableState<Boolean> = mutableStateOf(false)

}