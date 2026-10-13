package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiBorder
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiPrimaryHover
import com.example.ui.theme.KhidmatiPrimarySurface
import com.example.ui.theme.KhidmatiSecondary
import com.example.ui.theme.KhidmatiSecondarySurface
import com.example.ui.viewmodel.AppDestination

data class NavItem(
    val destination: AppDestination,
    val icon: ImageVector,
    val labelText: String,
    val testTag: String
)

@Composable
fun KhidmatiBottomNav(
    currentDestination: AppDestination,
    currentRole: UserRole,
    currentLanguage: String,
    onNavigate: (AppDestination) -> Unit
) {
    // Hide navigation bar on the initial Login & Role selection screen
    if (currentDestination == AppDestination.LOGIN_ROLE) {
        return
    }

    val isArabic = currentLanguage == "ar"

    val items = when (currentRole) {
        // Dedicated Menu for Workers / Providers (العامل وصاحب العمل)
        UserRole.PROVIDER -> listOf(
            NavItem(
                destination = AppDestination.PROVIDER_DASHBOARD,
                icon = Icons.Default.Dashboard,
                labelText = if (isArabic) "لوحة العمل" else "Dashboard",
                testTag = "nav_worker_dashboard"
            ),
            NavItem(
                destination = AppDestination.REQUESTS,
                icon = Icons.Default.ListAlt,
                labelText = if (isArabic) "طلبات الزبائن" else "Jobs",
                testTag = "nav_worker_requests"
            ),
            NavItem(
                destination = AppDestination.APPOINTMENTS,
                icon = Icons.Default.CalendarMonth,
                labelText = if (isArabic) "المواعيد" else "Schedule",
                testTag = "nav_worker_appointments"
            ),
            NavItem(
                destination = AppDestination.MESSAGES,
                icon = Icons.Default.ChatBubbleOutline,
                labelText = if (isArabic) "الرسائل" else "Messages",
                testTag = "nav_worker_messages"
            ),
            NavItem(
                destination = AppDestination.PROVIDER_DETAIL,
                icon = Icons.Default.Badge,
                labelText = if (isArabic) "ملفي المهني" else "Pro Profile",
                testTag = "nav_worker_profile"
            )
        )

        // Admin Menu
        UserRole.ADMIN -> listOf(
            NavItem(
                destination = AppDestination.HOME,
                icon = Icons.Default.Home,
                labelText = if (isArabic) "الرئيسية" else "Home",
                testTag = "nav_admin_home"
            ),
            NavItem(
                destination = AppDestination.SEARCH,
                icon = Icons.Default.Search,
                labelText = if (isArabic) "الحرفيين" else "Pros",
                testTag = "nav_admin_search"
            ),
            NavItem(
                destination = AppDestination.ADMIN_CENTER,
                icon = Icons.Default.AdminPanelSettings,
                labelText = if (isArabic) "مركز الرقابة" else "Admin",
                testTag = "nav_admin_center"
            ),
            NavItem(
                destination = AppDestination.MESSAGES,
                icon = Icons.Default.ChatBubbleOutline,
                labelText = if (isArabic) "الرسائل" else "Messages",
                testTag = "nav_admin_messages"
            )
        )

        // Dedicated Menu for Customers (الزبون)
        else -> listOf(
            NavItem(
                destination = AppDestination.HOME,
                icon = Icons.Default.Home,
                labelText = if (isArabic) "الرئيسية" else "Home",
                testTag = "nav_customer_home"
            ),
            NavItem(
                destination = AppDestination.SEARCH,
                icon = Icons.Default.Search,
                labelText = if (isArabic) "بحث حرفي" else "Search",
                testTag = "nav_customer_search"
            ),
            NavItem(
                destination = AppDestination.REQUESTS,
                icon = Icons.Default.ListAlt,
                labelText = if (isArabic) "طلباتي" else "Requests",
                testTag = "nav_customer_requests"
            ),
            NavItem(
                destination = AppDestination.APPOINTMENTS,
                icon = Icons.Default.CalendarMonth,
                labelText = if (isArabic) "مواعيدي" else "Appointments",
                testTag = "nav_customer_appointments"
            ),
            NavItem(
                destination = AppDestination.MESSAGES,
                icon = Icons.Default.ChatBubbleOutline,
                labelText = if (isArabic) "محادثاتي" else "Messages",
                testTag = "nav_customer_messages"
            )
        )
    }

    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        border = BorderStroke(1.dp, KhidmatiBorder)
    ) {
        NavigationBar(
            modifier = Modifier.navigationBarsPadding(),
            containerColor = Color.White,
            tonalElevation = 0.dp
        ) {
            items.forEach { item ->
                val isSelected = currentDestination == item.destination
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigate(item.destination) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.labelText
                        )
                    },
                    label = {
                        Text(
                            text = item.labelText,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = if (currentRole == UserRole.PROVIDER) KhidmatiSecondary else KhidmatiPrimaryHover,
                        selectedTextColor = if (currentRole == UserRole.PROVIDER) KhidmatiSecondary else KhidmatiPrimaryHover,
                        indicatorColor = if (currentRole == UserRole.PROVIDER) KhidmatiSecondarySurface else KhidmatiPrimarySurface,
                        unselectedIconColor = Color(0xFF64748B),
                        unselectedTextColor = Color(0xFF64748B)
                    ),
                    modifier = Modifier.testTag(item.testTag)
                )
            }
        }
    }
}
