package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.res.painterResource
import com.example.R
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.model.Wilaya
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
    onLoginWorker: (name: String, phone: String, tradeCategoryId: String, wilayaCode: Int) -> Unit
) {
    val isArabic = currentLanguage == "ar"

    // Selected Role: 0 for Customer, 1 for Worker, or null initially
    var selectedRoleIndex by remember { mutableStateOf<Int?>(null) }

    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var selectedWilaya by remember { mutableStateOf(wilayas.find { it.code == 16 } ?: wilayas.first()) }
    var wilayaExpanded by remember { mutableStateOf(false) }

    var selectedTradeCategory by remember { mutableStateOf(categories.first()) }
    var tradeExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .testTag("login_role_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(14.dp))

            // Language Toggle bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
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
                            text = if (isArabic) "عربي / English" else "English / عربي",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KhidmatiPrimaryHover
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Hero Brand Identity
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFF0F5132)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mihnati_app_icon_1791931865125),
                    contentDescription = "Mihnati Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isArabic) "مهنتي | Mihnati" else "Mihnati | مهنتي",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = if (isArabic)
                    "سوق الحرفيين والخدمات المنزلية في الجزائر عبر 69 ولاية"
                else
                    "Algerian Craftsmen & Home Services Marketplace across 69 Wilayas",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = KhidmatiPrimarySurface,
                border = BorderStroke(1.dp, Color(0xFFA7F3D0))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = KhidmatiPrimary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isArabic) "متاح في جميع 69 ولاية جزائرية" else "Available in all 69 Algerian Wilayas",
                        color = KhidmatiPrimaryHover,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Section Title: Select Role
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = if (isArabic) "اختر نوع حسابك للمتابعة:" else "Select your account type:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (isArabic)
                        "حدد إن كنت تبحث عن خدمة لمنزلك أو تقدم خدماتك كعامل وحرفي"
                    else
                        "Choose whether you are booking services or offering professional trade work",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // CARD 1: أنا زبون (Customer)
        item {
            val isSelected = selectedRoleIndex == 0
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { selectedRoleIndex = 0 }
                    .testTag("role_card_customer"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) KhidmatiPrimarySurface else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) KhidmatiPrimary else MaterialTheme.colorScheme.outline
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
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
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) KhidmatiPrimary else MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = if (isArabic) "أنا زبون" else "I am a Client",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = if (isSelected) KhidmatiPrimaryHover else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isArabic) "أبحث عن حرفيين لبيتي وطلب خدمات" else "Looking to hire verified craftsmen",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = KhidmatiPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isArabic)
                            "• تصفح الحرفيين في ولايتك • نشر طلبات مجانية • مقارنة عروض الأسعار • تقييمات موثقة"
                        else
                            "• Browse local pros • Post requests • Compare quotes in DZD • Verified reviews",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // CARD 2: أنا عامل (Worker / Craftsman)
        item {
            val isSelected = selectedRoleIndex == 1
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { selectedRoleIndex = 1 }
                    .testTag("role_card_worker"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) KhidmatiSecondarySurface else MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) KhidmatiSecondary else MaterialTheme.colorScheme.outline
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
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
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) KhidmatiSecondary else MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Engineering,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = if (isArabic) "أنا عامل" else "I am a Worker",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    color = if (isSelected) KhidmatiSecondary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (isArabic) "أريد تقديم خدماتي واستقبال طلبات الزبائن" else "Offer trade services & get hired",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = KhidmatiSecondary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isArabic)
                            "• استقبال طلبات العمل في تخصصك • تقديم عروض أسعار • إدارة المواعيد • توثيق الحساب"
                        else
                            "• Inbound client jobs • Submit quotes • Manage appointments • Professional badge",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Animated Info Form revealed upon selecting a role
        item {
            AnimatedVisibility(
                visible = selectedRoleIndex != null,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = if (selectedRoleIndex == 0)
                                (if (isArabic) "بيانات الزبون والموقع" else "Client Information")
                            else
                                (if (isArabic) "بيانات العامل والتخصص" else "Worker & Trade Details"),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Full Name
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text(if (isArabic) "الاسم واللقب" else "Full Name") },
                            placeholder = {
                                Text(
                                    if (selectedRoleIndex == 0)
                                        (if (isArabic) "مثال: كريم بن علي" else "e.g. Karim Benali")
                                    else
                                        (if (isArabic) "مثال: ياسين منصوري" else "e.g. Yacine Mansouri")
                                )
                            },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = KhidmatiPrimary) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_login_name")
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Phone
                        OutlinedTextField(
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text(if (isArabic) "رقم الهاتف (الجزائر)" else "Phone Number") },
                            placeholder = { Text("0550 12 34 56") },
                            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = KhidmatiPrimary) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("input_login_phone")
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Wilaya Selector (All 69 Wilayas)
                        ExposedDropdownMenuBox(
                            expanded = wilayaExpanded,
                            onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                        ) {
                            OutlinedTextField(
                                value = if (isArabic) selectedWilaya.nameAr else "${selectedWilaya.code} - ${selectedWilaya.nameEn}",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text(if (isArabic) "الولاية (اختر من بين 69 ولاية)" else "Wilaya (from 69 Wilayas)") },
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
                                        text = { Text(text = if (isArabic) w.nameAr else "${w.code} - ${w.nameEn}") },
                                        onClick = {
                                            selectedWilaya = w
                                            wilayaExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        // If Worker, show Trade Category
                        if (selectedRoleIndex == 1) {
                            Spacer(modifier = Modifier.height(12.dp))
                            ExposedDropdownMenuBox(
                                expanded = tradeExpanded,
                                onExpandedChange = { tradeExpanded = !tradeExpanded }
                            ) {
                                OutlinedTextField(
                                    value = if (isArabic) selectedTradeCategory.nameAr else selectedTradeCategory.nameEn,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(if (isArabic) "تخصصك المهني والحرفي" else "Trade Profession") },
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
                                            text = { Text(if (isArabic) cat.nameAr else cat.nameEn) },
                                            onClick = {
                                                selectedTradeCategory = cat
                                                tradeExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Primary Action: Login
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
                                    (if (isArabic) "تسجيل الدخول كزبون ومتابعة" else "Enter as Client")
                                else
                                    (if (isArabic) "تسجيل الدخول كعامل ومتابعة" else "Enter as Worker"),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
