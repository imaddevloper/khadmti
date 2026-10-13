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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuoteType
import com.example.data.model.ServiceRequest
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendQuoteScreen(
    request: ServiceRequest,
    currentLanguage: String,
    onSubmitQuote: (
        requestId: String,
        quoteType: QuoteType,
        amountDzd: Int,
        scopeOfWork: String,
        includedMaterials: String,
        excludedMaterials: String,
        proposedSchedule: String,
        validityDays: Int,
        notes: String
    ) -> Unit
) {
    var selectedQuoteType by remember { mutableStateOf(QuoteType.FIXED) }
    var typeExpanded by remember { mutableStateOf(false) }

    var amountText by remember { mutableStateOf(request.customerBudgetDzd?.toString() ?: "3000") }
    var scopeOfWork by remember { mutableStateOf("") }
    var includedMaterials by remember { mutableStateOf(if (currentLanguage == "ar") "أدوات الفحص والربط وتجهيزات التركيب القياسية" else "Standard fittings & mounting accessories") }
    var excludedMaterials by remember { mutableStateOf(if (currentLanguage == "ar") "قطع الغيار الكبرى أو الأجهزة الجديدة" else "Major spare parts") }
    var proposedSchedule by remember { mutableStateOf(if (currentLanguage == "ar") "غداً صباحاً عند 10:00" else "Tomorrow morning at 10:00") }
    var validityDays by remember { mutableIntStateOf(5) }
    var notes by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("send_quote_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (currentLanguage == "ar") "تقديم عرض سعر رسمي" else "Submit a Formal Quote",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = if (currentLanguage == "ar")
                    "الطلب: ${request.title} (${request.communeName}، ${request.wilayaName})"
                else
                    "Request: ${request.title} (${request.communeName}, ${request.wilayaName})",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Quote Type
        item {
            ExposedDropdownMenuBox(
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = !typeExpanded }
            ) {
                OutlinedTextField(
                    value = AppStrings.quoteType(selectedQuoteType, currentLanguage),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(if (currentLanguage == "ar") "نوع عرض السعر" else "Quote Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeExpanded) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .testTag("quote_type_picker")
                )
                ExposedDropdownMenu(
                    expanded = typeExpanded,
                    onDismissRequest = { typeExpanded = false }
                ) {
                    QuoteType.values().forEach { t ->
                        DropdownMenuItem(
                            text = { Text(AppStrings.quoteType(t, currentLanguage)) },
                            onClick = {
                                selectedQuoteType = t
                                typeExpanded = false
                            }
                        )
                    }
                }
            }
        }

        // Amount in DZD
        item {
            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it.filter { char -> char.isDigit() }
                    errorMessage = null
                },
                label = { Text(if (currentLanguage == "ar") "المبلغ بالدينار الجزائري (DZD) *" else "Total Amount in DZD *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_quote_amount")
            )
        }

        // Scope of Work
        item {
            OutlinedTextField(
                value = scopeOfWork,
                onValueChange = {
                    scopeOfWork = it
                    errorMessage = null
                },
                label = { Text(if (currentLanguage == "ar") "تفاصيل نطاق العمل والإصلاح *" else "Scope of Work *") },
                placeholder = {
                    Text(
                        if (currentLanguage == "ar")
                            "وضح بالضبط ما الذي ستصلحه أو تركبه لضمان عدم وجود لبس مع الزبون..."
                        else
                            "Specify the repair tasks and steps included..."
                    )
                },
                minLines = 3,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_quote_scope")
            )
        }

        // Included & Excluded Materials
        item {
            OutlinedTextField(
                value = includedMaterials,
                onValueChange = { includedMaterials = it },
                label = { Text(if (currentLanguage == "ar") "المواد والقطع المشمولة بالسعر" else "Included Materials") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = excludedMaterials,
                onValueChange = { excludedMaterials = it },
                label = { Text(if (currentLanguage == "ar") "المستثنى من السعر (يوفره الزبون)" else "Excluded Materials") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Schedule
        item {
            OutlinedTextField(
                value = proposedSchedule,
                onValueChange = { proposedSchedule = it },
                label = { Text(if (currentLanguage == "ar") "الموعد المقترح للتدخل" else "Proposed Date & Time") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Notes
        item {
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text(if (currentLanguage == "ar") "ملاحظات إضافية للزبون" else "Additional Notes") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage!!,
                    color = KhidmatiRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Button(
                onClick = {
                    val amount = amountText.toIntOrNull()
                    if (amount == null || amount <= 0) {
                        errorMessage = if (currentLanguage == "ar") "يرجى إدخال مبلغ صحيح بالدينار" else "Please enter a valid amount"
                        return@Button
                    }
                    if (scopeOfWork.isBlank()) {
                        errorMessage = if (currentLanguage == "ar") "يرجى تحديد نطاق العمل" else "Please specify scope of work"
                        return@Button
                    }
                    onSubmitQuote(
                        request.id,
                        selectedQuoteType,
                        amount,
                        scopeOfWork.trim(),
                        includedMaterials.trim(),
                        excludedMaterials.trim(),
                        proposedSchedule.trim(),
                        validityDays,
                        notes.trim()
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_confirm_send_quote")
            ) {
                Text(
                    text = if (currentLanguage == "ar") "إرسال عرض السعر للزبون" else "Submit Quote",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
