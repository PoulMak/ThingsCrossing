package com.app.thingscrossing.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.thingscrossing.core.presentation.components.BottomNavigationBar
import com.app.thingscrossing.feature_advertisement.presentation.search.SearchViewModel
import com.app.thingscrossing.feature_advertisement.presentation.search.components.FilterBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreenWithBottomNavigation(
    navController: NavHostController,
    searchViewModel: SearchViewModel,
    content: @Composable () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
        sheetContent = {
            FilterBottomSheet()
        }
    ) {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController, searchViewModel)
            },
        ) { paddingValues ->
            Box(Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}