package com.developerstring.finspare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.developerstring.finspare.navigation.RootNavGraph
import com.developerstring.finspare.sharedviewmodel.ProfileViewModel
import com.developerstring.finspare.sharedviewmodel.PublicSharedViewModel
import com.developerstring.finspare.sharedviewmodel.SharedViewModel
import com.developerstring.finspare.ui.theme.FinancesAppTheme
import com.developerstring.finspare.ui.theme.backgroundColorBW
import com.developerstring.finspare.util.Constants.DARK_THEME
import com.developerstring.finspare.util.Constants.DARK_THEME_ENABLE
import com.developerstring.finspare.util.Constants.LIGHT_THEME
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private val profileViewModel: ProfileViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    private val publicSharedViewModel: PublicSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinancesAppTheme {


                val darkThemeEnable by profileViewModel.profileTheme.collectAsState()
                DARK_THEME_ENABLE = when(darkThemeEnable) {
                    DARK_THEME -> true
                    LIGHT_THEME -> false
                    else -> true
                }

                // A surface container using the 'background' color from the theme
                navController = rememberNavController()
                RootNavGraph(
                    navController = navController,
                    profileViewModel = profileViewModel,
                    sharedViewModel = sharedViewModel,
                    publicSharedViewModel = publicSharedViewModel
                )

                val systemController = rememberSystemUiController()
                systemController.setSystemBarsColor(
                    color = backgroundColorBW
                )
            }
        }
    }
}