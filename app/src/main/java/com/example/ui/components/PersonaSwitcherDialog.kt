package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed

@Composable
fun PersonaSwitcherDialog(
    currentRole: UserRole,
    currentLanguage: String,
    onDismiss: () -> Unit,
    onSelectRole: (UserRole) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (currentLanguage == "ar") "تبديل الحساب التجريبي لاختبار المنصة" else "Switch Test Persona",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column {
                Text(
                    text = if (currentLanguage == "ar")
                        "اختر أحد الحسابات لاختبار أدوار المنصة وصلاحيات الزبون والحرفي والمشرف:"
                    else
                        "Select a persona to test customer, provider, admin, or guest journeys:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                PersonaOptionItem(
                    title = if (currentLanguage == "ar") "زبون: كريم بن علي" else "Customer: Karim Benali",
                    subtitle = if (currentLanguage == "ar") "طلب خدمات، مقارنة العروض، تأكيد المواعيد، التقييم الموثق" else "Request services, compare quotes, confirm appointments",
                    icon = Icons.Default.Person,
                    accentColor = KhidmatiPrimaryGreen,
                    isSelected = currentRole == UserRole.CUSTOMER,
                    onClick = { onSelectRole(UserRole.CUSTOMER) },
                    testTag = "persona_customer"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PersonaOptionItem(
                    title = if (currentLanguage == "ar") "حرفي: ياسين منصوري (سباك)" else "Provider: Yacine Mansouri (Plumber)",
                    subtitle = if (currentLanguage == "ar") "استقبال الطلبات، تقديم عروض الأسعار، إدارة المواعيد، التوثيق" else "View requests, send quotes, manage schedule, get verified",
                    icon = Icons.Default.Engineering,
                    accentColor = KhidmatiAmber,
                    isSelected = currentRole == UserRole.PROVIDER,
                    onClick = { onSelectRole(UserRole.PROVIDER) },
                    testTag = "persona_provider"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PersonaOptionItem(
                    title = if (currentLanguage == "ar") "مشرفة / مديرة: أمينة خديم" else "Admin: Amina Khedim",
                    subtitle = if (currentLanguage == "ar") "اعتماد التوثيق، مراجعة البلاغات، مراقبة سجل التدقيق والأمان" else "Verify pros, review reports, inspect audit trail & categories",
                    icon = Icons.Default.AdminPanelSettings,
                    accentColor = KhidmatiRed,
                    isSelected = currentRole == UserRole.ADMIN,
                    onClick = { onSelectRole(UserRole.ADMIN) },
                    testTag = "persona_admin"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PersonaOptionItem(
                    title = if (currentLanguage == "ar") "زائر عام (Guest)" else "Guest Visitor",
                    subtitle = if (currentLanguage == "ar") "تصفح الفئات، البحث عن الحرفيين، استعراض الملفات العامة" else "Browse categories, search pros, inspect public profiles",
                    icon = Icons.Default.Visibility,
                    accentColor = Color(0xFF6B7280),
                    isSelected = currentRole == UserRole.GUEST,
                    onClick = { onSelectRole(UserRole.GUEST) },
                    testTag = "persona_guest"
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(if (currentLanguage == "ar") "إغلاق" else "Close")
            }
        }
    )
}

@Composable
private fun PersonaOptionItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) accentColor.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
