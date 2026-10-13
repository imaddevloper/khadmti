package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceCategory
import com.example.data.model.UserRole
import com.example.data.model.Wilaya
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiBorder
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiPrimaryHover
import com.example.ui.theme.KhidmatiPrimarySurface
import com.example.ui.theme.KhidmatiSecondary
import com.example.ui.theme.KhidmatiSecondarySurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRoleScreen(
    currentLanguage: String,
    wilayas: List<Wilaya>,
    categories: List<ServiceCategory>,
    onToggleLanguage: () -> Unit,
    onLoginCustomer: (name: String, phone: String, wilayaCode: Int) -> Unit,
    onLoginWorker: (name: String, phone: String, tradeCategoryId: String, wilayaCode: Int) -> Unit,
    onLoginAdmin: () -> Unit
) {
    // Selected Role: 0 for Customer, 1 for Worker
    var selectedRoleIndex by remember { mutableIntStateOf(0) }

    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var selectedWilaya by remember { mutableStateOf(wilayas.find { it.code == 16 } ?: wilayas.first()) }
    var wilayaExpanded by remember { mutableStateOf(false) }

    var selectedTradeCategory by remember { mutableStateOf(categories.first()) }
    var tradeExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(horizontal = 20.dp)
            .testTag("login_role_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Language Toggle bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, KhidmatiBorder),
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onToggleLanguage() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = KhidmatiPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentLanguage == "ar") "عربي / Français" else "English / عربي",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KhidmatiPrimaryHover
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Hero Brand Identity
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(KhidmatiPrimary, KhidmatiPrimaryHover)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "خ",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (currentLanguage == "ar") "خدمتي | KHIDMATI" else "Khidmati | خدمتي",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = Color(0xFF0F172A)
            )

            Text(
                text = if (currentLanguage == "ar")
                    "المنصة العصرية لربط الزبائن بأمهر الحرفيين في الجزائر"
                else
                    "Modern Local Services & Craftsmen Marketplace in Algeria",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF475569),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // 69 Wilayas Badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = KhidmatiPrimarySurface,
                border = BorderStroke(1.dp, Color(0xFFA7F3D0))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = KhidmatiPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (currentLanguage == "ar")
                            "تغطية شاملة لجميع الـ 69 ولاية جزائرية"
                        else
                            "Covering all 69 Algerian Wilayas",
                        color = KhidmatiPrimaryHover,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Section Title: Select Role
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (currentLanguage == "ar") "حدد صنف حسابك للدخول:" else "Select your role to continue:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = if (currentLanguage == "ar")
                        "تختلف القائمة والخصائص بحسب اختيارك زبون أو عامل"
                    else
                        "The app menus and features adapt specifically to your chosen role",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B)
                )
            }
        }

        // ROLE CARD 1: Customer (زبون)
        item {
            val isSelected = selectedRoleIndex == 0
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { selectedRoleIndex = 0 }
                    .testTag("role_card_customer"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFFF0FDF4) else Color.White
                ),
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) KhidmatiPrimary else KhidmatiBorder
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) KhidmatiPrimary else Color(0xFFE2E8F0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else Color(0xFF475569),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (currentLanguage == "ar") "أنا زبون (طالب خدمة)" else "I am a Client (Seeking Service)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = if (isSelected) KhidmatiPrimaryHover else Color(0xFF0F172A)
                                )
                                Text(
                                    text = if (currentLanguage == "ar") "أبحث عن حرفيين ومهنيين لبيتي" else "Looking for verified craftspeople",
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = KhidmatiPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Feature highlights
                    RoleBullet(
                        text = if (currentLanguage == "ar") "تصفح الحرفيين في ولايتك وحسب بلديتك" else "Browse verified pros in your Wilaya & Commune",
                        color = KhidmatiPrimary
                    )
                    RoleBullet(
                        text = if (currentLanguage == "ar") "انشر طلبات الإصلاح وقارن عروض الأسعار" else "Post repair requests and compare formal quotes",
                        color = KhidmatiPrimary
                    )
                    RoleBullet(
                        text = if (currentLanguage == "ar") "تثبيت المواعيد وتقييم الخدمة بعد الانتهاء" else "Confirm appointments & post verified reviews",
                        color = KhidmatiPrimary
                    )
                }
            }
        }

        // ROLE CARD 2: Worker / Craftsman (عامل / صاحب عمل)
        item {
            val isSelected = selectedRoleIndex == 1
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { selectedRoleIndex = 1 }
                    .testTag("role_card_worker"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFFF0F9FF) else Color.White
                ),
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) KhidmatiSecondary else KhidmatiBorder
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) KhidmatiSecondary else Color(0xFFE2E8F0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Engineering,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else Color(0xFF475569),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (currentLanguage == "ar") "أنا عامل / حرفي (مقدم خدمة)" else "I am a Worker / Pro (Offering Service)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = if (isSelected) KhidmatiSecondary else Color(0xFF0F172A)
                                )
                                Text(
                                    text = if (currentLanguage == "ar") "استقبل طلبات الزبائن وزد دخلك" else "Receive client jobs & send quotes",
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = KhidmatiSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    RoleBullet(
                        text = if (currentLanguage == "ar") "استقبال إشعارات بطلبات العمل في تخصصك" else "Receive instant notifications for jobs in your trade",
                        color = KhidmatiSecondary
                    )
                    RoleBullet(
                        text = if (currentLanguage == "ar") "تقديم عروض أسعار مفصلة بالدينار الجزائري" else "Submit formal structured quotes in DZD",
                        color = KhidmatiSecondary
                    )
                    RoleBullet(
                        text = if (currentLanguage == "ar") "إدارة جدول التدخلات والحصول على شارة التوثيق" else "Manage schedule & gain verified professional badge",
                        color = KhidmatiSecondary
                    )
                }
            }
        }

        // Credentials / Details Form
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, KhidmatiBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (currentLanguage == "ar") "معلومات الدخول والموقع" else "Account Details & Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Name
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text(if (currentLanguage == "ar") "الاسم واللقب" else "Full Name") },
                        placeholder = {
                            Text(
                                if (selectedRoleIndex == 0)
                                    (if (currentLanguage == "ar") "مثال: كريم بن علي" else "e.g. Karim Benali")
                                else
                                    (if (currentLanguage == "ar") "مثال: ياسين منصوري" else "e.g. Yacine Mansouri")
                            )
                        },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF64748B)) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_login_name")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Phone
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text(if (currentLanguage == "ar") "رقم الهاتف (الجزائر)" else "Phone Number") },
                        placeholder = { Text("0550 12 34 56") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF64748B)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_login_phone")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Wilaya Selector (All 69 Wilayas)
                    ExposedDropdownMenuBox(
                        expanded = wilayaExpanded,
                        onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedWilaya.let { if (currentLanguage == "ar") it.nameAr else "${it.code} - ${it.nameEn}" },
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(if (currentLanguage == "ar") "الولاية (اختر من 69 ولاية)" else "Wilaya (Select from 69 Wilayas)") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = KhidmatiPrimary) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("dropdown_login_wilaya")
                        )
                        ExposedDropdownMenu(
                            expanded = wilayaExpanded,
                            onDismissRequest = { wilayaExpanded = false }
                        ) {
                            wilayas.forEach { w ->
                                DropdownMenuItem(
                                    text = { Text(text = if (currentLanguage == "ar") w.nameAr else "${w.code} - ${w.nameEn}") },
                                    onClick = {
                                        selectedWilaya = w
                                        wilayaExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // If Worker, ask for Trade Category
                    AnimatedVisibility(visible = selectedRoleIndex == 1) {
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            ExposedDropdownMenuBox(
                                expanded = tradeExpanded,
                                onExpandedChange = { tradeExpanded = !tradeExpanded }
                            ) {
                                OutlinedTextField(
                                    value = if (currentLanguage == "ar") selectedTradeCategory.nameAr else selectedTradeCategory.nameEn,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(if (currentLanguage == "ar") "تخصصك المهني والحرفي" else "Your Trade / Profession") },
                                    leadingIcon = { Icon(Icons.Default.Handyman, contentDescription = null, tint = KhidmatiSecondary) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tradeExpanded) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor()
                                        .testTag("dropdown_worker_trade")
                                )
                                ExposedDropdownMenu(
                                    expanded = tradeExpanded,
                                    onDismissRequest = { tradeExpanded = false }
                                ) {
                                    categories.forEach { cat ->
                                        DropdownMenuItem(
                                            text = { Text(if (currentLanguage == "ar") cat.nameAr else cat.nameEn) },
                                            onClick = {
                                                selectedTradeCategory = cat
                                                tradeExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Primary Action: Submit Login
        item {
            Button(
                onClick = {
                    if (selectedRoleIndex == 0) {
                        onLoginCustomer(
                            fullName.ifBlank { "كريم بن علي" },
                            phoneNumber.ifBlank { "0550 12 34 56" },
                            selectedWilaya.code
                        )
                    } else {
                        onLoginWorker(
                            fullName.ifBlank { "ياسين منصوري (حرفي)" },
                            phoneNumber.ifBlank { "0661 98 76 54" },
                            selectedTradeCategory.id,
                            selectedWilaya.code
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRoleIndex == 0) KhidmatiPrimary else KhidmatiSecondary
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_submit_login_role")
            ) {
                Text(
                    text = if (selectedRoleIndex == 0)
                        (if (currentLanguage == "ar") "الدخول وتصفح الخدمات كزبون" else "Enter as Client")
                    else
                        (if (currentLanguage == "ar") "الدخول كعامل وحرفي مهني" else "Enter as Worker / Pro"),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Admin Access Link
        item {
            OutlinedButton(
                onClick = onLoginAdmin,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btn_admin_login")
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF475569)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (currentLanguage == "ar") "دخول المشرفين والإدارة (مركز الاعتماد والرقابة)" else "Admin / Moderation Access",
                    color = Color(0xFF475569),
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun RoleBullet(text: String, color: Color) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color(0xFF334155),
            lineHeight = 16.sp
        )
    }
}
