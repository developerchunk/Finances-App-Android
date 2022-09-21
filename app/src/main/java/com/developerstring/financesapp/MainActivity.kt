package com.developerstring.financesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.developerstring.financesapp.navigation.RootNavGraph
import com.developerstring.financesapp.sharedviewmodel.ProfileViewModel
import com.developerstring.financesapp.sharedviewmodel.SharedViewModel
import com.developerstring.financesapp.ui.theme.FinancesAppTheme
import com.developerstring.financesapp.ui.theme.backgroundColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private val profileViewModel: ProfileViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinancesAppTheme {
                // A surface container using the 'background' color from the theme
                navController = rememberNavController()
                RootNavGraph(
                    navController = navController,
                    profileViewModel = profileViewModel,
                    sharedViewModel = sharedViewModel
                )

                val systemController = rememberSystemUiController()
                systemController.setSystemBarsColor(
                    color = MaterialTheme.colors.backgroundColor
                )
            }
        }
    }
}