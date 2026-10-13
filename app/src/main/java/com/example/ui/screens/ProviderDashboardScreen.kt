package com.example.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Appointment
import com.example.data.model.ProviderProfile
import com.example.data.model.Quote
import com.example.data.model.ServiceRequest
import com.example.data.model.VerificationStatus
import com.example.ui.components.AppointmentStatusBadge
import com.example.ui.components.QuoteStatusBadge
import com.example.ui.components.RequestStatusBadge
import com.example.ui.components.VerificationBadge
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen

@Composable
fun ProviderDashboardScreen(
    provider: ProviderProfile,
    requests: List<ServiceRequest>,
    quotes: List<Quote>,
    appointments: List<Appointment>,
    currentLanguage: String,
    onSendQuoteClick: (String) -> Unit,
    onRequestClick: (String) -> Unit,
    onSubmitVerificationClick: () -> Unit
) {
    val incomingRequests = requests.filter { it.categoryId == provider.primaryCategoryId }
    val myQuotes = quotes.filter { it.providerId == provider.id }
    val myAppointments = appointments.filter { it.providerId == provider.id }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("provider_dashboard_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Provider Status Header Card
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
                        Column {
                            Text(
                                text = provider.displayName,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "${provider.communeNameAr}، ${provider.wilayaNameAr}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        VerificationBadge(
                            status = provider.verificationStatus,
                            currentLanguage = currentLanguage
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Metrics Strip
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricBox(
                            value = "${provider.averageRating}★",
                            label = if (currentLanguage == "ar") "متوسط التقييم" else "Avg Rating"
                        )
                        MetricBox(
                            value = "${provider.completedJobsCount}",
                            label = if (currentLanguage == "ar") "أعمال مكتملة" else "Completed"
                        )
                        MetricBox(
                            value = "${myQuotes.size}",
                            label = if (currentLanguage == "ar") "عروض مرسلة" else "Sent Quotes"
                        )
                        MetricBox(
                            value = "${myAppointments.size}",
                            label = if (currentLanguage == "ar") "مواعيد نشطة" else "Appointments"
                        )
                    }

                    if (provider.verificationStatus != VerificationStatus.APPROVED) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = KhidmatiLightGreen,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (currentLanguage == "ar") "توثيق حسابك المهني" else "Professional Verification",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = KhidmatiDarkGreen
                                    )
                                    Text(
                                        text = if (currentLanguage == "ar") "أرسل بطاقة التعريف الوطنية والشهادة المهنية للحصول على شارة الثقة" else "Submit CNI and trade qualification to get verified",
                                        fontSize = 11.sp,
                                        color = KhidmatiDarkGreen.copy(alpha = 0.8f)
                                    )
                                }
                                Button(
                                    onClick = onSubmitVerificationClick,
                                    colors = ButtonDefaults.buttonColors(containerColor = KhidmatiDarkGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.testTag("btn_submit_verification")
                                ) {
                                    Text(text = if (currentLanguage == "ar") "طلب التوثيق" else "Submit ID", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Incoming Client Requests
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (currentLanguage == "ar") "طلبات الزبائن في منطقتك (${incomingRequests.size})" else "Incoming Local Requests (${incomingRequests.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = if (currentLanguage == "ar") "طلبات تطابق تخصصك (ترصيص وسباكة). اضغط لتقديم عرض سعر." else "Requests matching your trade. Tap to quote.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(incomingRequests) { req ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onRequestClick(req.id) }
                    .testTag("pro_request_${req.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RequestStatusBadge(status = req.status, currentLanguage = currentLanguage)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(13.dp), tint = KhidmatiDarkGreen)
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(text = "${req.communeName}، ${req.wilayaName}", fontSize = 11.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = req.title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = req.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (req.customerBudgetDzd != null) {
                            Text(
                                text = "${if (currentLanguage == "ar") "ميزانية الزبون:" else "Budget:"} ${AppStrings.formatDzd(req.customerBudgetDzd, currentLanguage)}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = KhidmatiDarkGreen
                            )
                        }
                        Button(
                            onClick = { onSendQuoteClick(req.id) },
                            colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("btn_pro_quote_${req.id}")
                        ) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = AppStrings.btnSendQuote(currentLanguage), fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Sent Quotes Overview
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = if (currentLanguage == "ar") "عروض أسعاري المرسلة (${myQuotes.size})" else "My Sent Quotes (${myQuotes.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        items(myQuotes) { q ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        QuoteStatusBadge(status = q.status, currentLanguage = currentLanguage)
                        Text(
                            text = AppStrings.formatDzd(q.amountDzd, currentLanguage),
                            fontWeight = FontWeight.Bold,
                            color = KhidmatiDarkGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = q.scopeOfWork, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${if (currentLanguage == "ar") "الموعد المقترح:" else "Proposed:"} ${q.proposedSchedule}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MetricBox(value: String, label: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = KhidmatiDarkGreen)
            Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
