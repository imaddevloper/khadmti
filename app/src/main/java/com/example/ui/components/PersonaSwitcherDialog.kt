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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Person
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
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiSecondary

@Composable
fun PersonaSwitcherDialog(
    currentRole: UserRole,
    currentLanguage: String,
    onDismiss: () -> Unit,
    onSelectRole: (UserRole) -> Unit
) {
    val isArabic = currentLanguage == "ar"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isArabic) "تبديل صنف الحساب" else "Switch Account Role",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column {
                Text(
                    text = if (isArabic)
                        "اختر صنف الحساب (زبون يبحث عن خدمات أو عامل / صاحب مهنة يقدم خدماته):"
                    else
                        "Select your active account role (Customer seeking services or Worker providing services):",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                PersonaOptionItem(
                    title = if (isArabic) "أنا زبون (كريم بن علي)" else "Customer (Karim Benali)",
                    subtitle = if (isArabic) "طلب خدمات وحرفيين، مقارنة العروض، والمواعيد في الـ 69 ولاية" else "Request services, compare quotes, book appointments",
                    icon = Icons.Default.Person,
                    accentColor = KhidmatiPrimary,
                    isSelected = currentRole == UserRole.CUSTOMER,
                    onClick = { onSelectRole(UserRole.CUSTOMER) },
                    testTag = "persona_customer"
                )

                Spacer(modifier = Modifier.height(12.dp))

                PersonaOptionItem(
                    title = if (isArabic) "أنا عامل / حرفي (ياسين منصوري)" else "Worker (Yacine Mansouri)",
                    subtitle = if (isArabic) "استقبال طلبات الزبائن، تقديم العروض، إدارة الأعمال والمواعيد" else "Receive job requests, submit quotes, manage schedule",
                    icon = Icons.Default.Engineering,
                    accentColor = KhidmatiSecondary,
                    isSelected = currentRole == UserRole.PROVIDER,
                    onClick = { onSelectRole(UserRole.PROVIDER) },
                    testTag = "persona_provider"
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isArabic) "إغلاق" else "Close")
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
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) accentColor.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant,
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, accentColor) else null,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
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
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
