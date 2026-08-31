package com.shrihari.smartcampusnavigator.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import com.shrihari.smartcampusnavigator.ui.theme.TracerTheme

/**
 * Reusable Bottom Navigation Bar for Tracer.
 *
 * Reports the real screen bounds of every navigation item.
 *
 * These bounds are later used by the first-launch tutorial
 * to position the spotlight accurately.
 */
@Composable
fun BottomNavBar(
    selectedItem: BottomNavItem?,
    onItemSelected: (BottomNavItem) -> Unit,
    onItemBoundsChanged: (
        BottomNavItem,
        Rect
    ) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {

    /*
     * Keep the navigation items local.
     *
     * We intentionally do not use BottomNavItem.items.
     * This avoids any class-initialization issue and makes
     * every navigation object explicit.
     */
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Scan,
        BottomNavItem.Navigate,
        BottomNavItem.Settings
    )

    NavigationBar(
        modifier = modifier
    ) {

        items.forEach { item ->

            NavigationBarItem(

                modifier = Modifier
                    .onGloballyPositioned { coordinates ->

                        onItemBoundsChanged(
                            item,
                            coordinates.boundsInRoot()
                        )
                    },

                selected = selectedItem === item,

                onClick = {
                    onItemSelected(item)
                },

                icon = {

                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },

                label = {

                    Text(
                        text = item.label
                    )
                }
            )
        }
    }
}


// =============================================================
// PREVIEWS
// =============================================================

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