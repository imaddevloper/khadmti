package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceCategory
import com.example.data.model.Wilaya
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestCreateScreen(
    currentLanguage: String,
    categories: List<ServiceCategory>,
    wilayas: List<Wilaya>,
    preselectedCategoryId: String?,
    preselectedWilayaCode: Int?,
    onSubmitRequest: (
        title: String,
        description: String,
        categoryId: String,
        wilayaCode: Int,
        communeName: String,
        requiresInspection: Boolean,
        preferredDate: String,
        budgetDzd: Int?
    ) -> Unit
) {
    var selectedCategory by remember {
        mutableStateOf(categories.find { it.id == preselectedCategoryId } ?: categories.first())
    }
    var categoryExpanded by remember { mutableStateOf(false) }

    var selectedWilaya by remember {
        mutableStateOf(wilayas.find { it.code == preselectedWilayaCode } ?: wilayas.first())
    }
    var wilayaExpanded by remember { mutableStateOf(false) }

    var communeName by remember {
        mutableStateOf(selectedWilaya.communes.firstOrNull()?.let { if (currentLanguage == "ar") it.nameAr else it.nameEn } ?: "المركز")
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var requiresInspection by remember { mutableStateOf(false) }
    var preferredDateWindow by remember { mutableStateOf(if (currentLanguage == "ar") "خلال الـ 48 ساعة القادمة" else "Within 48 hours") }
    var budgetDzdText by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("request_create_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (currentLanguage == "ar") "طلب خدمة جديدة ومقارنة عروض الأسعار" else "Post a New Service Request",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (currentLanguage == "ar")
                    "صف المشكلة بوضوح لتتلقى عروض أسعار دقيقة ومفصلة من الحرفيين المعتمدين في ولايتك."
                else
                    "Describe your issue clearly to receive accurate quotes from verified pros in your Wilaya.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Category selection
        item {
            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = !categoryExpanded }
            ) {
                OutlinedTextField(
                    value = if (currentLanguage == "ar") selectedCategory.nameAr else selectedCategory.nameEn,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(if (currentLanguage == "ar") "فئة الخدمة" else "Service Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .testTag("input_request_category")
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    categories.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(if (currentLanguage == "ar") cat.nameAr else cat.nameEn) },
                            onClick = {
                                selectedCategory = cat
                                categoryExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // Title
        item {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    errorMessage = null
                },
                label = { Text(if (currentLanguage == "ar") "عنوان مختصر للطلب *" else "Short Title *") },
                placeholder = {
                    Text(
                        if (currentLanguage == "ar")
                            "مثال: تسرب في سخان الماء Juncker، أو تصليح انقطاع كهرباء"
                        else
                            "e.g. Water heater leak, or living room painting"
                    )
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_request_title")
            )
        }

        // Description
        item {
            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    errorMessage = null
                },
                label = { Text(if (currentLanguage == "ar") "تفاصيل المشكلة والعمل المطلوب *" else "Detailed Description *") },
                placeholder = {
                    Text(
                        if (currentLanguage == "ar")
                            "اشرح المشكلة بالتفصيل، الطابق، الأعراض، وهل لديك قطع غيار متوفرة أم يحضرها الحرفي..."
                        else
                            "Explain the problem, floor, symptoms, and whether you provide parts..."
                    )
                },
                minLines = 4,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_request_description")
            )
        }

        // Location selection (Wilaya + Commune)
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = if (currentLanguage == "ar") "الموقع الجغرافي (ولاية وبلدية)" else "Service Location",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Wilaya Dropdown
                    ExposedDropdownMenuBox(
                        expanded = wilayaExpanded,
                        onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                    ) {
                        OutlinedTextField(
                            value = if (currentLanguage == "ar") "${selectedWilaya.code} - ${selectedWilaya.nameAr}" else "${selectedWilaya.code} - ${selectedWilaya.nameEn}",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(if (currentLanguage == "ar") "الولاية" else "Wilaya") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("input_request_wilaya")
                        )
                        ExposedDropdownMenu(
                            expanded = wilayaExpanded,
                            onDismissRequest = { wilayaExpanded = false }
                        ) {
                            wilayas.forEach { w ->
                                DropdownMenuItem(
                                    text = { Text(if (currentLanguage == "ar") "${w.code} - ${w.nameAr}" else "${w.code} - ${w.nameEn}") },
                                    onClick = {
                                        selectedWilaya = w
                                        communeName = w.communes.firstOrNull()?.let { if (currentLanguage == "ar") it.nameAr else it.nameEn } ?: "المركز"
                                        wilayaExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Commune
                    OutlinedTextField(
                        value = communeName,
                        onValueChange = { communeName = it },
                        label = { Text(if (currentLanguage == "ar") "البلدية أو الحي" else "Commune / Neighborhood") },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_request_commune")
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (currentLanguage == "ar")
                            "ملاحظة أمان: لن يظهر عنوانك الدقيق للعامة، بل يُشارك فقط مع الحرفي الذي تقبل عرضه رسمياً."
                        else
                            "Privacy notice: Your exact home address is only shared with the provider you accept.",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Preferred Date Window
        item {
            OutlinedTextField(
                value = preferredDateWindow,
                onValueChange = { preferredDateWindow = it },
                label = { Text(if (currentLanguage == "ar") "الموعد أو الوقت المفضل" else "Preferred Date/Time Window") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_request_preferred_date")
            )
        }

        // Budget DZD (Optional)
        item {
            OutlinedTextField(
                value = budgetDzdText,
                onValueChange = { budgetDzdText = it.filter { char -> char.isDigit() } },
                label = { Text(if (currentLanguage == "ar") "الميزانية المتوقعة بالدينار (اختياري)" else "Estimated Budget in DZD (Optional)") },
                placeholder = { Text("مثال: 3000 د.ج") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_request_budget")
            )
        }

        // Requires Inspection Toggle
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = requiresInspection,
                        onCheckedChange = { requiresInspection = it },
                        colors = CheckboxDefaults.colors(checkedColor = KhidmatiPrimaryGreen),
                        modifier = Modifier.testTag("checkbox_requires_inspection")
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (currentLanguage == "ar") "يتطلب معاينة منزلية مسبقة قبل الاتفاق النهائي" else "Requires home inspection before final quote",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Text(
                            text = if (currentLanguage == "ar") "مفيد للأعمال المعقدة أو السباكة/الكهرباء غير الواضحة" else "Recommended for complex diagnostics",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Error message if any
        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage!!,
                    color = KhidmatiRed,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Submit Button
        item {
            Button(
                onClick = {
                    if (title.isBlank()) {
                        errorMessage = if (currentLanguage == "ar") "يرجى إدخال عنوان للطلب" else "Please enter a title"
                        return@Button
                    }
                    if (description.isBlank()) {
                        errorMessage = if (currentLanguage == "ar") "يرجى كتابة وصف للخدمة المطلوبة" else "Please provide a description"
                        return@Button
                    }
                    val budget = budgetDzdText.toIntOrNull()
                    onSubmitRequest(
                        title.trim(),
                        description.trim(),
                        selectedCategory.id,
                        selectedWilaya.code,
                        communeName.trim(),
                        requiresInspection,
                        preferredDateWindow.trim(),
                        budget
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_submit_request")
            ) {
                Text(
                    text = AppStrings.btnSubmit(currentLanguage),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
