package com.example.contactapp.ui_layer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.ui_layer.screen.AddEditScreenUI
import com.example.contactapp.ui_layer.screen.HomeScreenUI
import com.example.contactapp.ui_layer.viewModel.ContactAppViewModel


@Composable
fun AppNavigation(modifier: Modifier = Modifier , viewModel: ContactAppViewModel = hiltViewModel() ) {
    val navController = rememberNavController()


    val state  = viewModel.state.collectAsState()
    NavHost(navController = navController, startDestination = HomeScreen , ) {

        composable<HomeScreen> {
            HomeScreenUI(navController = navController  , state = state.value )
        }

        composable<AddEditScreen> {
            AddEditScreenUI(navController = navController  , state = state.value ,
                onEvent = { viewModel.upsertContact() })

        }

    }
}