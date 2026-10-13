package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material.icons.filled.WaterDrop
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProviderProfile
import com.example.data.model.ServiceCategory
import com.example.data.model.UserRole
import com.example.data.model.Wilaya
import com.example.ui.components.VerificationBadge
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiBorder
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiPrimaryHover
import com.example.ui.theme.KhidmatiPrimarySurface
import com.example.ui.theme.KhidmatiSecondary
import com.example.ui.theme.KhidmatiSecondarySurface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentLanguage: String,
    currentRole: UserRole,
    categories: List<ServiceCategory>,
    wilayas: List<Wilaya>,
    providers: List<ProviderProfile>,
    onSelectCategory: (String) -> Unit,
    onSelectWilaya: (Int) -> Unit,
    onSearchClick: () -> Unit,
    onCreateRequestClick: () -> Unit,
    onProviderClick: (String) -> Unit,
    onJoinProviderClick: () -> Unit
) {
    var selectedWilaya by remember { mutableStateOf<Wilaya?>(null) }
    var wilayaExpanded by remember { mutableStateOf(false) }

    val isArabic = currentLanguage == "ar"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Modern Light Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(Color(0xFF047857), Color(0xFF059669), Color(0xFF10B981))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        // Top Pill
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shield,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isArabic) "منصة موثوقة في 69 ولاية جزائرية" else "Covering 69 Algerian Wilayas",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isArabic)
                                "خدمات الصيانة المنزلية بأيدي حرفيين موثوقين"
                            else
                                "Quality Home & Local Services by Verified Pros",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                                lineHeight = 28.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (isArabic)
                                "اطلب كهربائي، سباك، دهان، ميكانيكي أو مصلح أجهزة. قارن الأسعار واختر الأفضل."
                            else
                                "Book electricians, plumbers, painters & technicians. Compare quotes & ratings.",
                            color = Color.White.copy(alpha = 0.9f),
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Wilaya Selector (All 69 Wilayas)
                        ExposedDropdownMenuBox(
                            expanded = wilayaExpanded,
                            onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedWilaya?.let { if (isArabic) it.nameAr else "${it.code} - ${it.nameEn}" }
                                    ?: (if (isArabic) "اختر ولايتك (جميع 69 ولاية جزائرية)" else "Select from 69 Wilayas"),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = KhidmatiPrimary
                                    )
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedTextColor = Color(0xFF0F172A),
                                    unfocusedTextColor = Color(0xFF0F172A)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = wilayaExpanded,
                                onDismissRequest = { wilayaExpanded = false }
                            ) {
                                wilayas.forEach { w ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = if (isArabic) w.nameAr else "${w.code} - ${w.nameEn}",
                                                fontSize = 13.sp
                                            )
                                        },
                                        onClick = {
                                            selectedWilaya = w
                                            wilayaExpanded = false
                                            onSelectWilaya(w.code)
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Main action buttons
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = onCreateRequestClick,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color(0xFF047857)
                                ),
                                shape = RoundedCornerShape(12.dp),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_home_create_request")
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = AppStrings.btnCreateRequest(currentLanguage),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            OutlinedButton(
                                onClick = onSearchClick,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                                border = BorderStroke(1.5.dp, Color.White),
                                modifier = Modifier.testTag("btn_home_browse_search")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isArabic) "دليل الحرفيين" else "Explore",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section Title: Categories
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = if (isArabic) "التخصصات والخدمات المتاحة" else "Services & Trades",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = if (isArabic) "اختر المجال الذي تحتاجه في منزلك" else "Pick the trade you need today",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = KhidmatiPrimarySurface
                ) {
                    Text(
                        text = "${categories.size} ${if (isArabic) "تخصصات" else "trades"}",
                        color = KhidmatiPrimaryHover,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Categories Grid (2 Columns, beautiful cards with icon badges)
        items(categories.chunked(2)) { rowCategories ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowCategories.forEach { cat ->
                    ModernCategoryCard(
                        category = cat,
                        currentLanguage = currentLanguage,
                        modifier = Modifier.weight(1f),
                        onClick = { onSelectCategory(cat.id) }
                    )
                }
                if (rowCategories.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Algerian Guarantee & Trust Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, KhidmatiBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(KhidmatiPrimarySurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = KhidmatiPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isArabic) "ضمان الجودة والثقة في خدمتي" else "Khidmati Quality & Trust Guarantee",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = if (isArabic) "معايير صارمة لحماية الزبون والحرفي" else "Strict standards for customer and craftsman",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    TrustRow(
                        title = if (isArabic) "توثيق الهوية والشهادة" else "ID & Diploma Verification",
                        subtitle = if (isArabic) "فحص وثائق الهوية الوطنية والشهادات المهنية من قبل الإدارة" else "CNI national card and vocational diplomas verified",
                        color = KhidmatiPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustRow(
                        title = if (isArabic) "تقييمات حقيقية بدون تزييف" else "100% Genuine Reviews",
                        subtitle = if (isArabic) "التقييم يفتح فقط بعد إنجاز الخدمة وتأكيد الموعد" else "Reviews unlock exclusively after completed jobs",
                        color = KhidmatiSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TrustRow(
                        title = if (isArabic) "أسعار واضحة بالدينار (DZD)" else "Transparent Pricing in DZD",
                        subtitle = if (isArabic) "عروض أسعار مفصلة تشمل المواد واليد العاملة" else "Quotes detail labour and materials breakdown",
                        color = KhidmatiAmber
                    )
                }
            }
        }

        // Section Title: Featured Craftsmen
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (isArabic) "أفضل الحرفيين تقييماً" else "Top-Rated Professionals",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = if (isArabic) "أصحاب الخبرة المعتمدة في مختلف ولايات الجزائر" else "Experienced craftsmen across Algerian Wilayas",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        // Featured Providers List
        items(providers.take(3)) { provider ->
            ModernProviderCard(
                provider = provider,
                currentLanguage = currentLanguage,
                onClick = { onProviderClick(provider.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ModernCategoryCard(
    category: ServiceCategory,
    currentLanguage: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isArabic = currentLanguage == "ar"
    val (icon, iconColor, bgColor) = when (category.iconName) {
        "Bolt" -> Triple(Icons.Default.Bolt, Color(0xFFEAB308), Color(0xFFFEF9C3))
        "WaterDrop" -> Triple(Icons.Default.WaterDrop, Color(0xFF0284C7), Color(0xFFE0F2FE))
        "HomeRepairService" -> Triple(Icons.Default.HomeRepairService, Color(0xFF059669), Color(0xFFD1FAE5))
        "AcUnit" -> Triple(Icons.Default.AcUnit, Color(0xFF06B6D4), Color(0xFFCFFAFE))
        "FormatPaint" -> Triple(Icons.Default.FormatPaint, Color(0xFF8B5CF6), Color(0xFFEDE9FE))
        "Handyman" -> Triple(Icons.Default.Handyman, Color(0xFFD97706), Color(0xFFFEF3C7))
        "DirectionsCar" -> Triple(Icons.Default.DirectionsCar, Color(0xFFDC2626), Color(0xFFFEE2E2))
        "VpnKey" -> Triple(Icons.Default.VpnKey, Color(0xFF475569), Color(0xFFF1F5F9))
        else -> Triple(Icons.Default.Handyman, KhidmatiPrimary, KhidmatiPrimarySurface)
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("cat_card_${category.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, KhidmatiBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (isArabic) category.nameAr else category.nameEn,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF0F172A),
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = if (isArabic) category.descriptionAr else category.descriptionEn,
                fontSize = 11.sp,
                color = Color(0xFF64748B),
                maxLines = 2,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun TrustRow(title: String, subtitle: String, color: Color) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 3.dp)
                .size(16.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(11.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF1E293B)
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = Color(0xFF64748B),
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
fun ModernProviderCard(
    provider: ProviderProfile,
    currentLanguage: String,
    onClick: () -> Unit
) {
    val isArabic = currentLanguage == "ar"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("modern_pro_card_${provider.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, KhidmatiBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar with initial
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(KhidmatiPrimarySurface),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = provider.displayName.take(1),
                        fontWeight = FontWeight.Bold,
                        color = KhidmatiPrimaryHover,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = provider.displayName,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF0F172A)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (isArabic) "${provider.communeNameAr}، ${provider.wilayaNameAr}" else "${provider.communeNameEn}, ${provider.wilayaNameEn}",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                VerificationBadge(
                    status = provider.verificationStatus,
                    currentLanguage = currentLanguage
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = if (isArabic) provider.bioAr else provider.bioEn,
                fontSize = 12.sp,
                color = Color(0xFF475569),
                maxLines = 2,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rating & completed jobs
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFFEF3C7)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${provider.averageRating}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF92400E)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "(${provider.reviewCount} ${if (isArabic) "تقييم" else "reviews"})",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = KhidmatiPrimarySurface
                ) {
                    Text(
                        text = "${provider.completedJobsCount} ${if (isArabic) "عمل منجز" else "jobs done"}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = KhidmatiPrimaryHover,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProviderSummaryCard(
    provider: ProviderProfile,
    currentLanguage: String,
    onClick: () -> Unit
) {
    ModernProviderCard(provider = provider, currentLanguage = currentLanguage, onClick = onClick)
}
