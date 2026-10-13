package com.example.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.data.model.UserRole
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.viewmodel.AppDestination

data class NavItem(
    val destination: AppDestination,
    val icon: ImageVector,
    val labelKey: (String) -> String,
    val testTag: String
)

@Composable
fun KhidmatiBottomNav(
    currentDestination: AppDestination,
    currentRole: UserRole,
    currentLanguage: String,
    onNavigate: (AppDestination) -> Unit
) {
    val items = when (currentRole) {
        UserRole.PROVIDER -> listOf(
            NavItem(AppDestination.PROVIDER_DASHBOARD, Icons.Default.Dashboard, { AppStrings.navProviderDashboard(it) }, "nav_pro_dashboard"),
            NavItem(AppDestination.REQUESTS, Icons.Default.ListAlt, { AppStrings.navRequests(it) }, "nav_requests"),
            NavItem(AppDestination.APPOINTMENTS, Icons.Default.CalendarMonth, { AppStrings.navAppointments(it) }, "nav_appointments"),
            NavItem(AppDestination.MESSAGES, Icons.Default.ChatBubbleOutline, { AppStrings.navMessages(it) }, "nav_messages")
        )
        UserRole.ADMIN -> listOf(
            NavItem(AppDestination.HOME, Icons.Default.Home, { AppStrings.navHome(it) }, "nav_home"),
            NavItem(AppDestination.SEARCH, Icons.Default.Search, { AppStrings.navSearch(it) }, "nav_search"),
            NavItem(AppDestination.ADMIN_CENTER, Icons.Default.AdminPanelSettings, { AppStrings.navAdminCenter(it) }, "nav_admin"),
            NavItem(AppDestination.MESSAGES, Icons.Default.ChatBubbleOutline, { AppStrings.navMessages(it) }, "nav_messages")
        )
        else -> listOf(
            NavItem(AppDestination.HOME, Icons.Default.Home, { AppStrings.navHome(it) }, "nav_home"),
            NavItem(AppDestination.SEARCH, Icons.Default.Search, { AppStrings.navSearch(it) }, "nav_search"),
            NavItem(AppDestination.REQUESTS, Icons.Default.ListAlt, { AppStrings.navRequests(it) }, "nav_requests"),
            NavItem(AppDestination.APPOINTMENTS, Icons.Default.CalendarMonth, { AppStrings.navAppointments(it) }, "nav_appointments"),
            NavItem(AppDestination.MESSAGES, Icons.Default.ChatBubbleOutline, { AppStrings.navMessages(it) }, "nav_messages")
        )
    }

    NavigationBar(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            val isSelected = currentDestination == item.destination
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.destination) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.labelKey(currentLanguage)
                    )
                },
                label = {
                    Text(
                        text = item.labelKey(currentLanguage),
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = KhidmatiDarkGreen,
                    selectedTextColor = KhidmatiDarkGreen,
                    indicatorColor = KhidmatiLightGreen
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}
