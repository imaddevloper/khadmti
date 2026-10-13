package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiAmberLight
import com.example.ui.theme.KhidmatiBorder
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiPrimaryHover
import com.example.ui.theme.KhidmatiPrimarySurface
import com.example.ui.theme.KhidmatiRed
import com.example.ui.theme.KhidmatiRedLight
import com.example.ui.theme.KhidmatiSecondary
import com.example.ui.theme.KhidmatiSecondarySurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KhidmatiTopBar(
    title: String,
    currentLanguage: String,
    currentRole: UserRole,
    userName: String,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    onToggleLanguage: () -> Unit,
    onOpenRoleSwitcher: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val isArabic = currentLanguage == "ar"

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color(0xFF0F172A)
        ),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (currentRole == UserRole.PROVIDER) KhidmatiSecondary else KhidmatiPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "خ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color(0xFF0F172A)
                )
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color(0xFF0F172A)
                    )
                }
            }
        },
        actions = {
            // Language Toggle
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, KhidmatiBorder),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onToggleLanguage() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Language",
                        modifier = Modifier.size(14.dp),
                        tint = KhidmatiPrimaryHover
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isArabic) "عربي" else "EN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155)
                    )
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Role Badge & Switcher Button
            val (roleColor, roleBg, roleLabel) = when (currentRole) {
                UserRole.CUSTOMER -> Triple(KhidmatiPrimaryHover, KhidmatiPrimarySurface, if (isArabic) "حساب زبون" else "Client")
                UserRole.PROVIDER -> Triple(KhidmatiSecondary, KhidmatiSecondarySurface, if (isArabic) "حساب عامل" else "Worker")
                UserRole.ADMIN -> Triple(KhidmatiRed, KhidmatiRedLight, if (isArabic) "مشرف" else "Admin")
                UserRole.GUEST -> Triple(Color(0xFF475569), Color(0xFFF1F5F9), if (isArabic) "زائر" else "Guest")
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = roleBg,
                border = BorderStroke(1.dp, roleColor.copy(alpha = 0.3f)),
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onOpenRoleSwitcher() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(roleColor)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = roleLabel,
                        color = roleColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(
                        imageVector = Icons.Default.SyncAlt,
                        contentDescription = "Switch",
                        tint = roleColor,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            // Logout icon button to return to the selection/login screen
            IconButton(
                onClick = onLogoutClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))
        }
    )
}
