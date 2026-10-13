package com.example.ui.screens

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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Quote
import com.example.data.model.QuoteStatus
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceRequest
import com.example.data.model.UserRole
import com.example.ui.components.QuoteStatusBadge
import com.example.ui.components.RequestStatusBadge
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiAmberLight
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed
import com.example.ui.theme.KhidmatiRedLight

@Composable
fun RequestDetailScreen(
    request: ServiceRequest,
    category: ServiceCategory?,
    quotes: List<Quote>,
    currentRole: UserRole,
    currentLanguage: String,
    onAcceptQuote: (String) -> Unit,
    onCancelRequest: (String, String) -> Unit,
    onSendQuoteClick: () -> Unit,
    onOpenProviderDetail: (String) -> Unit
) {
    var showCancelDialog by remember { mutableStateOf(false) }
    var cancelReason by remember { mutableStateOf("") }
    var showComparisonDialog by remember { mutableStateOf(false) }

    val requestQuotes = quotes.filter { it.requestId == request.id }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("request_detail_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Request Overview Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        RequestStatusBadge(status = request.status, currentLanguage = currentLanguage)
                        Text(
                            text = if (currentLanguage == "ar") category?.nameAr ?: "" else category?.nameEn ?: "",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = KhidmatiDarkGreen
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = request.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = request.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Location & Details
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = KhidmatiDarkGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${request.communeName}، ${request.wilayaName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = KhidmatiDarkGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${if (currentLanguage == "ar") "الموعد المفضل:" else "Preferred window:"} ${request.preferredDateWindow}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    if (request.customerBudgetDzd != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = KhidmatiDarkGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${if (currentLanguage == "ar") "الميزانية المتوقعة:" else "Expected budget:"} ${AppStrings.formatDzd(request.customerBudgetDzd, currentLanguage)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    if (request.requiresInspectionFirst) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KhidmatiAmberLight
                        ) {
                            Text(
                                text = if (currentLanguage == "ar") "يتطلب معاينة مسبقة قبل التثبيت النهائي" else "Requires home inspection before final quote",
                                color = Color(0xFFB45309),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Cancellation banner if cancelled
                    if (request.status == RequestStatus.CANCELLED) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KhidmatiRedLight
                        ) {
                            Text(
                                text = "${if (currentLanguage == "ar") "سبب الإلغاء:" else "Cancellation reason:"} ${request.cancellationReason ?: ""}",
                                color = KhidmatiRed,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // Action buttons
                    if (request.status == RequestStatus.OPEN || request.status == RequestStatus.RECEIVING_QUOTES) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            if (currentRole == UserRole.PROVIDER) {
                                Button(
                                    onClick = onSendQuoteClick,
                                    colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("btn_pro_send_quote")
                                ) {
                                    Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(text = AppStrings.btnSendQuote(currentLanguage))
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { showCancelDialog = true },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = KhidmatiRed),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.testTag("btn_cancel_request")
                                ) {
                                    Text(text = if (currentLanguage == "ar") "إلغاء هذا الطلب" else "Cancel Request")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quotes Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (currentLanguage == "ar") "عروض الأسعار المستلمة (${requestQuotes.size})" else "Received Quotes (${requestQuotes.size})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    if (requestQuotes.size > 1) {
                        Button(
                            onClick = { showComparisonDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = KhidmatiLightGreen, contentColor = KhidmatiDarkGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("btn_compare_quotes")
                        ) {
                            Icon(imageVector = Icons.Default.Compare, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = AppStrings.btnCompareQuotes(currentLanguage), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        if (requestQuotes.isEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(modifier = Modifier.padding(24.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (currentLanguage == "ar")
                                "لم تصل أي عروض أسعار حتى الآن. سيتم إشعارك فور قيام حرفي بتقديم عرضه."
                            else
                                "No quotes received yet. You will be notified as soon as a professional submits an offer.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(requestQuotes) { quote ->
                QuoteItemCard(
                    quote = quote,
                    currentLanguage = currentLanguage,
                    canAccept = request.status == RequestStatus.OPEN || request.status == RequestStatus.RECEIVING_QUOTES,
                    onAccept = { onAcceptQuote(quote.id) },
                    onProviderClick = { onOpenProviderDetail(quote.providerId) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Cancel Request Dialog
    if (showCancelDialog) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = {
                Text(
                    text = if (currentLanguage == "ar") "تأكيد إلغاء الطلب" else "Confirm Request Cancellation",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Column {
                    Text(
                        text = if (currentLanguage == "ar") "يرجى ذكر سبب الإلغاء:" else "Please state the reason:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = cancelReason,
                        onValueChange = { cancelReason = it },
                        placeholder = { Text(if (currentLanguage == "ar") "مثال: تم حل المشكلة، أو تغيير الموعد" else "e.g. Issue resolved") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onCancelRequest(request.id, cancelReason.ifBlank { "طلب الزبون" })
                        showCancelDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KhidmatiRed)
                ) {
                    Text(if (currentLanguage == "ar") "تأكيد الإلغاء" else "Confirm Cancel")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCancelDialog = false }) {
                    Text(AppStrings.btnCancel(currentLanguage))
                }
            }
        )
    }

    // Quote Comparison Modal Dialog
    if (showComparisonDialog) {
        AlertDialog(
            onDismissRequest = { showComparisonDialog = false },
            title = {
                Text(
                    text = if (currentLanguage == "ar") "مقارنة عروض الأسعار المفصلة" else "Detailed Quote Comparison",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(requestQuotes) { q ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = q.providerName, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = AppStrings.formatDzd(q.amountDzd, currentLanguage),
                                        fontWeight = FontWeight.Bold,
                                        color = KhidmatiDarkGreen
                                    )
                                }
                                Text(
                                    text = "${if (currentLanguage == "ar") "نوع السعر:" else "Price Type:"} ${AppStrings.quoteType(q.quoteType, currentLanguage)}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${if (currentLanguage == "ar") "المواد المشمولة:" else "Included:"} ${q.includedMaterials}",
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "${if (currentLanguage == "ar") "المستثناة:" else "Excluded:"} ${q.excludedMaterials}",
                                    fontSize = 11.sp,
                                    color = KhidmatiRed
                                )
                                Text(
                                    text = "${if (currentLanguage == "ar") "الموعد المقترح:" else "Proposed:"} ${q.proposedSchedule}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showComparisonDialog = false }) {
                    Text(if (currentLanguage == "ar") "إغلاق" else "Close")
                }
            }
        )
    }
}

@Composable
private fun QuoteItemCard(
    quote: Quote,
    currentLanguage: String,
    canAccept: Boolean,
    onAccept: () -> Unit,
    onProviderClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onProviderClick() }
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(KhidmatiLightGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = quote.providerName.take(1),
                            fontWeight = FontWeight.Bold,
                            color = KhidmatiDarkGreen
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = quote.providerName,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = KhidmatiAmber,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "${quote.providerRating}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = AppStrings.formatDzd(quote.amountDzd, currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = KhidmatiDarkGreen
                    )
                    QuoteStatusBadge(status = quote.status, currentLanguage = currentLanguage)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "${if (currentLanguage == "ar") "نوع العرض:" else "Type:"} ${AppStrings.quoteType(quote.quoteType, currentLanguage)}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${if (currentLanguage == "ar") "نطاق العمل:" else "Scope:"} ${quote.scopeOfWork}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${if (currentLanguage == "ar") "المواد المشمولة بالسعر:" else "Included materials:"} ${quote.includedMaterials}",
                style = MaterialTheme.typography.bodySmall,
                color = KhidmatiDarkGreen
            )

            if (quote.excludedMaterials.isNotBlank()) {
                Text(
                    text = "${if (currentLanguage == "ar") "المستثنى من السعر:" else "Excluded:"} ${quote.excludedMaterials}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${if (currentLanguage == "ar") "الموعد المقترح:" else "Proposed date:"} ${quote.proposedSchedule}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )

            if (quote.status == QuoteStatus.SENT && canAccept) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onAccept,
                    colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("btn_accept_quote_${quote.id}")
                ) {
                    Text(
                        text = AppStrings.btnAcceptQuote(currentLanguage),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
