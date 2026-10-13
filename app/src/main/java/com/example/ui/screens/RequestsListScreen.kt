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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceRequest
import com.example.data.model.UserRole
import com.example.ui.components.EmptyStateView
import com.example.ui.components.RequestStatusBadge
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiPrimaryGreen

@Composable
fun RequestsListScreen(
    requests: List<ServiceRequest>,
    categories: List<ServiceCategory>,
    currentRole: UserRole,
    currentLanguage: String,
    onCreateRequestClick: () -> Unit,
    onRequestClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("requests_list_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = if (currentLanguage == "ar") "طلبات الخدمات" else "Service Requests",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = if (currentRole == UserRole.PROVIDER)
                            (if (currentLanguage == "ar") "طلبات الزبائن المتاحة لتقديم عروض الأسعار" else "Client requests open for quotes")
                        else
                            (if (currentLanguage == "ar") "متابعة حالة طلباتك وعروض الأسعار المستلمة" else "Track your requests and received quotes"),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (currentRole != UserRole.PROVIDER) {
                    Button(
                        onClick = onCreateRequestClick,
                        colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("btn_requests_new")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        Text(text = if (currentLanguage == "ar") "طلب جديد" else "New", fontSize = 12.sp)
                    }
                }
            }
        }

        if (requests.isEmpty()) {
            item {
                EmptyStateView(
                    icon = Icons.Default.ListAlt,
                    title = if (currentLanguage == "ar") "لا توجد طلبات مسجلة حالياً" else "No requests found",
                    description = if (currentLanguage == "ar")
                        "قم بإنشاء أول طلب خدمة لتلقي عروض الأسعار التنافسية من الحرفيين المعتمدين."
                    else
                        "Create your first service request to start receiving quotes from verified pros.",
                    actionButtonText = if (currentRole != UserRole.PROVIDER) AppStrings.btnCreateRequest(currentLanguage) else null,
                    onActionClick = if (currentRole != UserRole.PROVIDER) onCreateRequestClick else null
                )
            }
        } else {
            items(requests) { req ->
                val cat = categories.find { it.id == req.categoryId }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onRequestClick(req.id) }
                        .testTag("request_item_${req.id}"),
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
                            RequestStatusBadge(status = req.status, currentLanguage = currentLanguage)
                            Text(
                                text = if (currentLanguage == "ar") cat?.nameAr ?: "" else cat?.nameEn ?: "",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = KhidmatiDarkGreen
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = req.title,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = req.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "${req.communeName}، ${req.wilayaName}",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            if (req.customerBudgetDzd != null) {
                                Text(
                                    text = AppStrings.formatDzd(req.customerBudgetDzd, currentLanguage),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = KhidmatiDarkGreen
                                )
                            }
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
