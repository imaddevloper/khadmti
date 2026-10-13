package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ProviderProfile
import com.example.data.model.ServiceCategory
import com.example.data.model.VerificationStatus
import com.example.data.model.Wilaya
import com.example.ui.components.EmptyStateView
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    currentLanguage: String,
    providers: List<ProviderProfile>,
    categories: List<ServiceCategory>,
    wilayas: List<Wilaya>,
    searchQuery: String,
    selectedCategoryFilterId: String?,
    selectedWilayaFilterCode: Int?,
    showOnlyVerified: Boolean,
    onQueryChange: (String) -> Unit,
    onSelectCategoryFilter: (String?) -> Unit,
    onSelectWilayaFilter: (Int?) -> Unit,
    onToggleOnlyVerified: (Boolean) -> Unit,
    onResetFilters: () -> Unit,
    onProviderClick: (String) -> Unit
) {
    var wilayaMenuExpanded by remember { mutableStateOf(false) }

    // Filter providers
    val filteredProviders = providers.filter { provider ->
        val matchesQuery = searchQuery.isBlank() ||
                provider.displayName.contains(searchQuery, ignoreCase = true) ||
                provider.bioEn.contains(searchQuery, ignoreCase = true) ||
                provider.bioAr.contains(searchQuery, ignoreCase = true) ||
                provider.communeNameEn.contains(searchQuery, ignoreCase = true) ||
                provider.communeNameAr.contains(searchQuery, ignoreCase = true) ||
                provider.wilayaNameEn.contains(searchQuery, ignoreCase = true) ||
                provider.wilayaNameAr.contains(searchQuery, ignoreCase = true)

        val matchesCategory = selectedCategoryFilterId == null ||
                provider.primaryCategoryId == selectedCategoryFilterId ||
                provider.additionalCategoryIds.contains(selectedCategoryFilterId)

        val matchesWilaya = selectedWilayaFilterCode == null ||
                provider.wilayaCode == selectedWilayaFilterCode

        val matchesVerified = !showOnlyVerified || provider.verificationStatus == VerificationStatus.APPROVED

        matchesQuery && matchesCategory && matchesWilaya && matchesVerified
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("search_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Search bar
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = {
                        Text(
                            text = if (currentLanguage == "ar") "ابحث عن خدمة (سباك، كهربائي، تكييف، بلدية...)" else "Search provider, service, or city...",
                            fontSize = 13.sp
                        )
                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_input_field")
                )
            }
        }

        // Filters Row
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (currentLanguage == "ar") "تصفية النتائج" else "Filters",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    if (searchQuery.isNotBlank() || selectedCategoryFilterId != null || selectedWilayaFilterCode != null || showOnlyVerified) {
                        TextButton(onClick = onResetFilters) {
                            Text(
                                text = AppStrings.btnReset(currentLanguage),
                                color = KhidmatiPrimaryGreen,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Wilaya Filter Dropdown
                ExposedDropdownMenuBox(
                    expanded = wilayaMenuExpanded,
                    onExpandedChange = { wilayaMenuExpanded = !wilayaMenuExpanded }
                ) {
                    val currentWilaya = wilayas.find { it.code == selectedWilayaFilterCode }
                    OutlinedTextField(
                        value = currentWilaya?.let { if (currentLanguage == "ar") "${it.code} - ${it.nameAr}" else "${it.code} - ${it.nameEn}" }
                            ?: (if (currentLanguage == "ar") "كل الولايات (الجزائر)" else "All Wilayas (Algeria)"),
                        onValueChange = {},
                        readOnly = true,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = KhidmatiDarkGreen)
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaMenuExpanded)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = wilayaMenuExpanded,
                        onDismissRequest = { wilayaMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(if (currentLanguage == "ar") "كل الولايات" else "All Wilayas") },
                            onClick = {
                                onSelectWilayaFilter(null)
                                wilayaMenuExpanded = false
                            }
                        )
                        wilayas.forEach { w ->
                            DropdownMenuItem(
                                text = { Text(if (currentLanguage == "ar") "${w.code} - ${w.nameAr}" else "${w.code} - ${w.nameEn}") },
                                onClick = {
                                    onSelectWilayaFilter(w.code)
                                    wilayaMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Only verified toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (currentLanguage == "ar") "عرض الحرفيين الموثقين فقط" else "Only Verified Professionals",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Switch(
                        checked = showOnlyVerified,
                        onCheckedChange = onToggleOnlyVerified,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = KhidmatiPrimaryGreen,
                            checkedTrackColor = KhidmatiLightGreen
                        )
                    )
                }
            }
        }

        // Category Filter Chips Carousel
        item {
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedCategoryFilterId == null,
                        onClick = { onSelectCategoryFilter(null) },
                        label = { Text(if (currentLanguage == "ar") "الكل" else "All") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KhidmatiLightGreen,
                            selectedLabelColor = KhidmatiDarkGreen
                        )
                    )
                }
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCategoryFilterId == cat.id,
                        onClick = {
                            onSelectCategoryFilter(if (selectedCategoryFilterId == cat.id) null else cat.id)
                        },
                        label = { Text(if (currentLanguage == "ar") cat.nameAr else cat.nameEn) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = KhidmatiLightGreen,
                            selectedLabelColor = KhidmatiDarkGreen
                        )
                    )
                }
            }
        }

        // Results count header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (currentLanguage == "ar")
                        "النتائج المتاحة (${filteredProviders.size})"
                    else
                        "Available Results (${filteredProviders.size})",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Results list
        if (filteredProviders.isEmpty()) {
            item {
                EmptyStateView(
                    icon = Icons.Default.SearchOff,
                    title = if (currentLanguage == "ar") "لم نجد حرفيين مطابقين لمعاييرك" else "No matching providers found",
                    description = if (currentLanguage == "ar")
                        "جرب تغيير الولاية المحددة أو إزالة خيارات التصفية لعرض المزيد من المهنيين المسجلين."
                    else
                        "Try broadening your search or resetting filters to see more professionals.",
                    actionButtonText = AppStrings.btnReset(currentLanguage),
                    onActionClick = onResetFilters
                )
            }
        } else {
            items(filteredProviders) { provider ->
                ProviderSummaryCard(
                    provider = provider,
                    currentLanguage = currentLanguage,
                    onClick = { onProviderClick(provider.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
