package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.KhidmatiPrimary
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
    // Hide navigation bar on the Login / Role selection screen
    if (currentDestination == AppDestination.LOGIN_ROLE) {
        return
    }

    val isArabic = currentLanguage == "ar"

    // Strictly two user types: Worker (عامل) and Customer (زبون)
    val items = when (currentRole) {
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
                labelText = if (isArabic) "مواعيدي" else "Schedule",
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
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 12.dp,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
    ) {
        NavigationBar(
            modifier = Modifier.navigationBarsPadding(),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp
        ) {
            val activeColor = if (currentRole == UserRole.PROVIDER) KhidmatiSecondary else KhidmatiPrimary
            val activeContainer = if (currentRole == UserRole.PROVIDER)
                KhidmatiSecondary.copy(alpha = 0.15f)
            else
                KhidmatiPrimary.copy(alpha = 0.15f)

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
                        selectedIconColor = activeColor,
                        selectedTextColor = activeColor,
                        indicatorColor = activeContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.testTag(item.testTag)
                )
            }
        }
    }
}
