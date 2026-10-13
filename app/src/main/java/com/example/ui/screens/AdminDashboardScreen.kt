package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.AuditEvent
import com.example.data.model.ProviderProfile
import com.example.data.model.ReportStatus
import com.example.data.model.UserReport
import com.example.data.model.VerificationStatus
import com.example.ui.components.VerificationBadge
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed
import com.example.ui.theme.KhidmatiRedLight

@Composable
fun AdminDashboardScreen(
    providers: List<ProviderProfile>,
    reports: List<UserReport>,
    auditLogs: List<AuditEvent>,
    currentLanguage: String,
    onAdminDecideVerification: (providerId: String, status: VerificationStatus, notes: String) -> Unit,
    onAdminResolveReport: (reportId: String, status: ReportStatus, notes: String) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    var decisionDialogProvider by remember { mutableStateOf<ProviderProfile?>(null) }
    var decisionNotes by remember { mutableStateOf("") }
    var chosenStatus by remember { mutableStateOf(VerificationStatus.APPROVED) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_dashboard_screen")
    ) {
        // Tab row
        val tabs = listOf(
            if (currentLanguage == "ar") "طابور التوثيق" else "Verifications",
            if (currentLanguage == "ar") "البلاغات والإشراف" else "Reports",
            if (currentLanguage == "ar") "سجل التدقيق والأمان" else "Audit Logs"
        )

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
                )
            }
        }

        when (selectedTab) {
            0 -> {
                // Verification Queue
                val pendingProviders = providers.filter { it.verificationStatus != VerificationStatus.NOT_SUBMITTED }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = if (currentLanguage == "ar") "مراجعة ملفات التوثيق المهني للحرفيين" else "Provider Verification Review",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (currentLanguage == "ar") "التحقق من الهوية الوطنية (CNI) وشهادات التأهيل المهني." else "Review National ID (CNI) and professional certificates.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(pendingProviders) { prov ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = prov.displayName, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                                    VerificationBadge(status = prov.verificationStatus, currentLanguage = currentLanguage)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${prov.communeNameAr}، ${prov.wilayaNameAr}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                if (prov.verificationReviewerNotes.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "${if (currentLanguage == "ar") "ملاحظات المشرف:" else "Reviewer notes:"} ${prov.verificationReviewerNotes}",
                                        fontSize = 11.sp,
                                        color = KhidmatiDarkGreen
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = {
                                            decisionDialogProvider = prov
                                            chosenStatus = VerificationStatus.APPROVED
                                            decisionNotes = "Checked CNI & vocational certificate"
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(if (currentLanguage == "ar") "اعتماد رسمي" else "Approve", fontSize = 12.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            decisionDialogProvider = prov
                                            chosenStatus = VerificationStatus.NEEDS_MORE_INFO
                                            decisionNotes = "Please upload a clearer scan of trade qualification"
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(if (currentLanguage == "ar") "طلب وثائق" else "Request Info", fontSize = 11.sp)
                                    }

                                    Button(
                                        onClick = {
                                            decisionDialogProvider = prov
                                            chosenStatus = VerificationStatus.REJECTED
                                            decisionNotes = "Document expired or unreadable"
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = KhidmatiRed),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(if (currentLanguage == "ar") "رفض" else "Reject", fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            1 -> {
                // Reports & Moderation
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = if (currentLanguage == "ar") "بلاغات المستخدمين والمحتوى المشبوه" else "Moderation Reports Queue",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    items(reports) { rep ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = rep.targetTitle, fontWeight = FontWeight.Bold)
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (rep.status == ReportStatus.PENDING) KhidmatiRedLight else KhidmatiLightGreen
                                    ) {
                                        Text(
                                            text = rep.status.name,
                                            fontSize = 10.sp,
                                            color = if (rep.status == ReportStatus.PENDING) KhidmatiRed else KhidmatiDarkGreen,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${if (currentLanguage == "ar") "السبب:" else "Reason:"} ${rep.reason}",
                                    fontSize = 12.sp,
                                    color = KhidmatiRed
                                )
                                Text(
                                    text = rep.details,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                if (rep.status == ReportStatus.PENDING) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { onAdminResolveReport(rep.id, ReportStatus.RESOLVED, "Warning issued & policy enforced") },
                                            colors = ButtonDefaults.buttonColors(containerColor = KhidmatiDarkGreen),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(if (currentLanguage == "ar") "معالجة واتخاذ إجراء" else "Resolve & Act", fontSize = 11.sp)
                                        }
                                        OutlinedButton(
                                            onClick = { onAdminResolveReport(rep.id, ReportStatus.DISMISSED, "No violation found") },
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(if (currentLanguage == "ar") "تجاهل البلاغ" else "Dismiss", fontSize = 11.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            2 -> {
                // Real Audit Logs
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = if (currentLanguage == "ar") "سجل التدقيق الأمني والعمليات الحساسة" else "Immutable Audit Log",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (currentLanguage == "ar") "تسجيل تلقائي لقرارات التوثيق، قبول عروض الأسعار، وتغيير الحالات." else "Automatic tamper-evident log of sensitive actions.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    items(auditLogs) { log ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = log.action, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = KhidmatiDarkGreen)
                                    Text(text = "${log.actorRole.name} • ${log.actorName}", fontSize = 11.sp)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = log.details, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }

    // Verification Decision Modal
    if (decisionDialogProvider != null) {
        val prov = decisionDialogProvider!!
        AlertDialog(
            onDismissRequest = { decisionDialogProvider = null },
            title = {
                Text(
                    text = "${if (currentLanguage == "ar") "قرار التوثيق للحرفي:" else "Verification Decision:"} ${prov.displayName}",
                    style = MaterialTheme.typography.titleSmall
                )
            },
            text = {
                Column {
                    Text(
                        text = "${if (currentLanguage == "ar") "القرار المختار:" else "Decision:"} ${chosenStatus.name}",
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = decisionNotes,
                        onValueChange = { decisionNotes = it },
                        label = { Text(if (currentLanguage == "ar") "ملاحظات المشرف / السبب" else "Reviewer Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAdminDecideVerification(prov.id, chosenStatus, decisionNotes)
                        decisionDialogProvider = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen)
                ) {
                    Text(if (currentLanguage == "ar") "تثبيت القرار" else "Confirm Decision")
                }
            },
            dismissButton = {
                TextButton(onClick = { decisionDialogProvider = null }) {
                    Text(AppStrings.btnCancel(currentLanguage))
                }
            }
        )
    }
}
