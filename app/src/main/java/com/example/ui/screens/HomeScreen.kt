package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
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
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiOutline
import com.example.ui.theme.KhidmatiPrimaryGreen

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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(KhidmatiDarkGreen, KhidmatiPrimaryGreen)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = Color(0xFF86EFAC),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (currentLanguage == "ar") "سوق الحرفيين المعتمدين في الجزائر" else "Verified Algerian Pro Marketplace",
                                color = Color(0xFF86EFAC),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (currentLanguage == "ar")
                                "ابحث عن أفضل الحرفيين والمهنيين لبيتك بثقة وأمان"
                            else
                                "Find Trusted Home & Local Service Professionals in Algeria",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                lineHeight = 28.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (currentLanguage == "ar")
                                "قارن عروض الأسعار، تثبيت المواعيد بدون عمولات مخفية، وتقييمات موثقة بعد إنجاز العمل."
                            else
                                "Compare structured quotes, book verified appointments with zero hidden fees, and read genuine reviews.",
                            color = Color.White.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Quick Wilaya Selector
                        ExposedDropdownMenuBox(
                            expanded = wilayaExpanded,
                            onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                        ) {
                            OutlinedTextField(
                                value = selectedWilaya?.let { if (currentLanguage == "ar") "${it.code} - ${it.nameAr}" else "${it.code} - ${it.nameEn}" }
                                    ?: (if (currentLanguage == "ar") "اختر ولايتك (58 ولاية)" else "Select Wilaya (Algeria)"),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = KhidmatiDarkGreen
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedTextColor = Color(0xFF18221C),
                                    unfocusedTextColor = Color(0xFF18221C)
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
                                                text = if (currentLanguage == "ar") "${w.code} - ${w.nameAr}" else "${w.code} - ${w.nameEn}"
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

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Button(
                                onClick = onCreateRequestClick,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = KhidmatiDarkGreen
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("btn_home_create_request")
                            ) {
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
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.7f)),
                                modifier = Modifier.testTag("btn_home_browse_search")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = AppStrings.navSearch(currentLanguage), fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }

        // Categories Section Header
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (currentLanguage == "ar") "تصفح حسب فئات الخدمات" else "Service Categories",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (currentLanguage == "ar") "اختر التخصص المطلوب لاستعراض الحرفيين المتوفرين" else "Select a trade to view available professionals in your area",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Categories Grid (2 Columns)
        items(categories.chunked(2)) { pair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pair.forEach { cat ->
                    CategoryCard(
                        category = cat,
                        currentLanguage = currentLanguage,
                        modifier = Modifier.weight(1f),
                        onClick = { onSelectCategory(cat.id) }
                    )
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Trust & Quality Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(KhidmatiLightGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = KhidmatiDarkGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = AppStrings.trustHeadline(currentLanguage),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    TrustPill(
                        text = if (currentLanguage == "ar")
                            "توثيق رسمي: فحص بطاقة الهوية الوطنية والشهادة المهنية للحرفيين"
                        else
                            "Verified ID & Trade Qualifications checked by moderators"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TrustPill(
                        text = if (currentLanguage == "ar")
                            "تقييمات حقيقية 100%: مسموحة فقط بعد انتهاء الموعد المنجز فعلياً"
                        else
                            "100% Genuine Reviews: unlocked only after completed appointments"
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    TrustPill(
                        text = if (currentLanguage == "ar")
                            "عروض أسعار واضحة: تفصيل المواد المشمولة والمستثناة بالدينار الجزائري"
                        else
                            "Clear Quotes: transparent scope & materials breakdown in DZD"
                    )
                }
            }
        }

        // Featured Verified Providers Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (currentLanguage == "ar") "حرفيون موثوقون بالقرب منك" else "Featured Verified Professionals",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (currentLanguage == "ar") "تقييمات عالية وسجل إنجاز مثبت" else "High ratings with proven track records",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(providers.take(3)) { provider ->
            ProviderSummaryCard(
                provider = provider,
                currentLanguage = currentLanguage,
                onClick = { onProviderClick(provider.id) }
            )
        }

        // Provider CTA Banner
        if (currentRole != UserRole.PROVIDER) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = KhidmatiLightGreen)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (currentLanguage == "ar") "هل أنت حرفي أو فني مهني في الجزائر؟" else "Are you a professional service provider?",
                            fontWeight = FontWeight.Bold,
                            color = KhidmatiDarkGreen,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (currentLanguage == "ar")
                                "انضم لمنصة خدمتي، استقبل طلبات الزبائن في ولايتك، وقدم عروض أسعارك باحترافية."
                            else
                                "Join Khidmati to receive local client requests and submit quotes.",
                            fontSize = 12.sp,
                            color = KhidmatiDarkGreen.copy(alpha = 0.85f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = onJoinProviderClick,
                            colors = ButtonDefaults.buttonColors(containerColor = KhidmatiDarkGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.testTag("btn_join_as_provider")
                        ) {
                            Text(text = AppStrings.btnJoinAsProvider(currentLanguage), fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CategoryCard(
    category: ServiceCategory,
    currentLanguage: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val icon = when (category.iconName) {
        "Bolt" -> Icons.Default.Bolt
        "WaterDrop" -> Icons.Default.WaterDrop
        "HomeRepairService" -> Icons.Default.HomeRepairService
        "AcUnit" -> Icons.Default.AcUnit
        "FormatPaint" -> Icons.Default.FormatPaint
        "Handyman" -> Icons.Default.Handyman
        "DirectionsCar" -> Icons.Default.DirectionsCar
        "VpnKey" -> Icons.Default.VpnKey
        else -> Icons.Default.Handyman
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("category_card_${category.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(KhidmatiLightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = KhidmatiDarkGreen,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (currentLanguage == "ar") category.nameAr else category.nameEn,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (currentLanguage == "ar") category.descriptionAr else category.descriptionEn,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun TrustPill(text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = KhidmatiPrimaryGreen,
            modifier = Modifier
                .size(16.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 16.sp
        )
    }
}

@Composable
fun ProviderSummaryCard(
    provider: ProviderProfile,
    currentLanguage: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag("provider_card_${provider.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(KhidmatiLightGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = provider.displayName.take(1),
                        fontWeight = FontWeight.Bold,
                        color = KhidmatiDarkGreen,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = provider.displayName,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (currentLanguage == "ar") "${provider.communeNameAr}، ${provider.wilayaNameAr}" else "${provider.communeNameEn}, ${provider.wilayaNameEn}",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
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
                text = if (currentLanguage == "ar") provider.bioAr else provider.bioEn,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = KhidmatiAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${provider.averageRating}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "(${provider.reviewCount} ${if (currentLanguage == "ar") "تقييم" else "reviews"})",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${provider.completedJobsCount} ${if (currentLanguage == "ar") "عمل منجز" else "jobs completed"}",
                    fontSize = 12.sp,
                    color = KhidmatiDarkGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
