package com.shrihari.smartcampusnavigator.ui.screens.navigate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.ui.components.BottomNavBar
import com.shrihari.smartcampusnavigator.ui.components.BottomNavItem
import com.shrihari.smartcampusnavigator.ui.components.PrimaryButton
import com.shrihari.smartcampusnavigator.ui.components.TracerTopBar
import com.shrihari.smartcampusnavigator.ui.navigation.Screen
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.DestinationCategoryCard
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.DestinationListCard
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.DestinationSearchBar
import com.shrihari.smartcampusnavigator.ui.screens.navigate.components.ZoomableMap
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.DestinationCategory
import com.shrihari.smartcampusnavigator.ui.screens.navigate.model.DestinationData
import android.util.Log

@Composable
fun NavigateScreen(
    navController: NavController,
    openRecent: Boolean = false,
    viewModel: NavigateViewModel = hiltViewModel(),
    deepLinkDestination: String?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(openRecent) {

        if (openRecent) {

            viewModel.startRecentNavigation()

        }

    }

    LaunchedEffect(deepLinkDestination) {

        if (!deepLinkDestination.isNullOrBlank()) {

            val normalizedDeepLink = deepLinkDestination
                .lowercase()
                .replace(".", "")
                .replace("-", " ")
                .replace("&", "and")
                .trim()
                .replace(Regex("\\\\s+"), "_")

            val matchedDestination = DestinationData.destinations.find { destination ->

                val destinationId = destination.name
                    .lowercase()
                    .replace(".", "")
                    .replace("-", " ")
                    .replace("&", "and")
                    .trim()
                    .replace(Regex("\\\\s+"), "_")

                destinationId == normalizedDeepLink
            }

            Log.d(
                "TracerDeepLink",
                "Normalized=$normalizedDeepLink, Match=${matchedDestination?.name}"
            )

            matchedDestination?.let {
                viewModel.selectDestination(it)
                viewModel.startNavigation()
            }
        }
    }

    NavigateScreenContent(

        navController = navController,
        uiState = uiState,
        viewModel = viewModel
    )

}

@Composable
private fun NavigateScreenContent(
    navController: NavController,
    uiState: NavigateUiState,
    viewModel: NavigateViewModel
) {

    var searchQuery by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()
    LaunchedEffect(uiState.isNavigationActive) {
        if (uiState.isNavigationActive) {
            scrollState.animateScrollTo(700)
        }
    }
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val filteredDestinations = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            DestinationData.destinations
        } else {
            DestinationData.destinations.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val facultyDestinations = filteredDestinations.filter {
        it.category == DestinationCategory.FACULTY
    }

    val laboratoryDestinations = filteredDestinations.filter {
        it.category == DestinationCategory.LABORATORY
    }

    val classroomDestinations = filteredDestinations.filter {
        it.category == DestinationCategory.CLASSROOM
    }

    val officeDestinations = filteredDestinations.filter {
        it.category == DestinationCategory.OFFICE
    }

    val facilityDestinations = filteredDestinations.filter {
        it.category == DestinationCategory.FACILITY
    }

    var expandedCategory by rememberSaveable {
        mutableStateOf<DestinationCategory?>(null)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = BottomNavItem.Navigate,
                onItemSelected = { item ->
                    when (item) {
                        BottomNavItem.Home ->
                            navController.navigate(Screen.Home.route)

                        BottomNavItem.Scan ->
                            navController.navigate(Screen.Scan.route)

                        BottomNavItem.Navigate ->
                            navController.navigate(Screen.Navigate.route)

                        BottomNavItem.Settings ->
                            navController.navigate(Screen.Settings.route)
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            TracerTopBar(
                title = "Tracer",
                subtitle = "Indoor Navigation for Smart Campuses",
                logo = painterResource(id = R.drawable.tracer_logo)
            )

            DestinationSearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                }
            )

            // ---------------- Faculty ----------------

            if (facultyDestinations.isNotEmpty()) {

                DestinationCategoryCard(
                    title = "Faculty & Staff",
                    icon = "👥",
                    expanded = expandedCategory == DestinationCategory.FACULTY,
                    onClick = {
                        expandedCategory =
                            if (expandedCategory == DestinationCategory.FACULTY)
                                null
                            else
                                DestinationCategory.FACULTY
                    }
                )

                if (expandedCategory == DestinationCategory.FACULTY) {

                    DestinationListCard(
                        destinations = facultyDestinations,
                        selectedDestination = uiState.selectedDestination,
                        onDestinationSelected = {
                            viewModel.selectDestination(it)
                        }
                    )
                }
            }

            // ---------------- Laboratories ----------------

            if (laboratoryDestinations.isNotEmpty()) {

                DestinationCategoryCard(
                    title = "Laboratories",
                    icon = "🧪",
                    expanded = expandedCategory == DestinationCategory.LABORATORY,
                    onClick = {
                        expandedCategory =
                            if (expandedCategory == DestinationCategory.LABORATORY)
                                null
                            else
                                DestinationCategory.LABORATORY
                    }
                )

                if (expandedCategory == DestinationCategory.LABORATORY) {

                    DestinationListCard(
                        destinations = laboratoryDestinations,
                        selectedDestination = uiState.selectedDestination,
                        onDestinationSelected = {
                            viewModel.selectDestination(it)
                        }
                    )
                }
            }

            // ---------------- Classrooms ----------------

            if (classroomDestinations.isNotEmpty()) {

                DestinationCategoryCard(
                    title = "Classrooms",
                    icon = "📚",
                    expanded = expandedCategory == DestinationCategory.CLASSROOM,
                    onClick = {
                        expandedCategory =
                            if (expandedCategory == DestinationCategory.CLASSROOM)
                                null
                            else
                                DestinationCategory.CLASSROOM
                    }
                )

                if (expandedCategory == DestinationCategory.CLASSROOM) {

                    DestinationListCard(
                        destinations = classroomDestinations,
                        selectedDestination = uiState.selectedDestination,
                        onDestinationSelected = {
                            viewModel.selectDestination(it)
                        }
                    )
                }
            }

            // ---------------- Offices ----------------

            if (officeDestinations.isNotEmpty()) {

                DestinationCategoryCard(
                    title = "Offices",
                    icon = "🏢",
                    expanded = expandedCategory == DestinationCategory.OFFICE,
                    onClick = {
                        expandedCategory =
                            if (expandedCategory == DestinationCategory.OFFICE)
                                null
                            else
                                DestinationCategory.OFFICE
                    }
                )

                if (expandedCategory == DestinationCategory.OFFICE) {

                    DestinationListCard(
                        destinations = officeDestinations,
                        selectedDestination = uiState.selectedDestination,
                        onDestinationSelected = {
                            viewModel.selectDestination(it)
                        }
                    )
                }
            }

            // ---------------- Facilities ----------------

            if (facilityDestinations.isNotEmpty()) {

                DestinationCategoryCard(
                    title = "Facilities",
                    icon = "📍",
                    expanded = expandedCategory == DestinationCategory.FACILITY,
                    onClick = {
                        expandedCategory =
                            if (expandedCategory == DestinationCategory.FACILITY)
                                null
                            else
                                DestinationCategory.FACILITY
                    }
                )

                if (expandedCategory == DestinationCategory.FACILITY) {

                    DestinationListCard(
                        destinations = facilityDestinations,
                        selectedDestination = uiState.selectedDestination,
                        onDestinationSelected = {
                            viewModel.selectDestination(it)
                        }
                    )
                }
            }

            // ---------------- Map ----------------

            Column(
                modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester)
            ) {
                ZoomableMap(
                    route = uiState.route,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // ---------------- Start Navigation ----------------

            PrimaryButton(
                text = "Start Navigation",
                onClick = {
                    viewModel.startNavigation()
                }
            )
        }
    }

}
