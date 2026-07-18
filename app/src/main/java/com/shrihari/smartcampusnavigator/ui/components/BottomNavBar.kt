package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.BluetoothSearching
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme

/**
 * Represents an item in the [BottomNavBar].
 *
 * @param route The unique identifier for the navigation destination.
 * @param label The localized string to be displayed below the icon.
 * @param icon The graphical representation of the navigation destination.
 */
sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem(
        route = "home",
        label = "Home",
        icon = Icons.Rounded.Home
    )

    object Scan : BottomNavItem(
        route = "scan",
        label = "Scan",
        icon = Icons.AutoMirrored.Rounded.BluetoothSearching
    )

    object Navigate : BottomNavItem(
        route = "navigate",
        label = "Navigate",
        icon = Icons.Rounded.Explore
    )

    object Settings : BottomNavItem(
        route = "settings",
        label = "Settings",
        icon = Icons.Rounded.Settings
    )

    companion object {
        /**
         * List of all navigation items to be displayed in the [BottomNavBar].
         */
        val items = listOf(
            Home,
            Scan,
            Navigate,
            Settings
        )
    }
}

/**
 * A reusable, stateless Bottom Navigation Bar for the Tracer application.
 *
 * This composable only renders the navigation UI and emits selection events. 
 * It does not perform navigation itself. It is intended to be used with 
 * a NavHost and NavController.
 *
 * @param selectedItem The currently active [BottomNavItem].
 * @param onItemSelected Callback invoked when a different [BottomNavItem] is tapped.
 * @param modifier The modifier to be applied to the navigation bar.
 */


 @Composable
fun BottomNavBar(
    selectedItem: BottomNavItem,
    onItemSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Scan,
        BottomNavItem.Navigate,
        BottomNavItem.Settings
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.route == item.route,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BottomNavBarHomePreview() {
    TracerTheme {
        BottomNavBar(
            selectedItem = BottomNavItem.Home,
            onItemSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarScanPreview() {
    TracerTheme {
        BottomNavBar(
            selectedItem = BottomNavItem.Scan,
            onItemSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarNavigatePreview() {
    TracerTheme {
        BottomNavBar(
            selectedItem = BottomNavItem.Navigate,
            onItemSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarSettingsPreview() {
    TracerTheme {
        BottomNavBar(
            selectedItem = BottomNavItem.Settings,
            onItemSelected = {}
        )
    }
}
