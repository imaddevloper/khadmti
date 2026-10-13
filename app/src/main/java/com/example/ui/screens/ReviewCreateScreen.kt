package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Appointment
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed

@Composable
fun ReviewCreateScreen(
    appointment: Appointment,
    currentLanguage: String,
    onSubmitReview: (appointmentId: String, rating: Int, comment: String) -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("review_create_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (currentLanguage == "ar") "كتابة تقييم موثق للحرفي" else "Write a Verified Review",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = if (currentLanguage == "ar")
                    "تقييمك يساعد باقي الجزائريين على اختيار الحرفي المناسب ويكافئ الإتقان المهني."
                else
                    "Your verified review helps fellow Algerians choose the best craftsperson.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Service Summary Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "${appointment.categoryName}: ${appointment.providerName}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${if (currentLanguage == "ar") "الموعد المكتمل:" else "Completed service:"} ${appointment.scheduledDate} (${appointment.addressOrArea})",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${if (currentLanguage == "ar") "المبلغ المدفوع:" else "Paid amount:"} ${AppStrings.formatDzd(appointment.agreedAmountDzd, currentLanguage)}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = KhidmatiDarkGreen
                    )
                }
            }
        }

        // Star Rating Selector
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (currentLanguage == "ar") "ما هو تقييمك لجودة الخدمة والالتزام؟" else "Rate service quality and punctuality",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "$i stars",
                                tint = KhidmatiAmber,
                                modifier = Modifier
                                    .size(42.dp)
                                    .clickable { rating = i }
                                    .padding(4.dp)
                                    .testTag("star_rating_$i")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    val ratingLabel = when (rating) {
                        5 -> if (currentLanguage == "ar") "ممتاز ومتقن للعمل" else "Excellent & highly skilled"
                        4 -> if (currentLanguage == "ar") "جيد جداً" else "Very good"
                        3 -> if (currentLanguage == "ar") "مقبول" else "Average"
                        2 -> if (currentLanguage == "ar") "دون المستوى المطلوب" else "Below expectations"
                        else -> if (currentLanguage == "ar") "غير راضٍ" else "Unsatisfied"
                    }

                    Text(
                        text = ratingLabel,
                        fontWeight = FontWeight.Bold,
                        color = KhidmatiDarkGreen,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Written Comment
        item {
            OutlinedTextField(
                value = comment,
                onValueChange = {
                    comment = it
                    errorMessage = null
                },
                label = { Text(if (currentLanguage == "ar") "تعليقك وتجربتك بالتفصيل *" else "Detailed feedback *") },
                placeholder = {
                    Text(
                        if (currentLanguage == "ar")
                            "هل وصل في الموعد؟ هل كانت التكلفة مطابقة للاتفاق؟ هل نظف مكان العمل؟"
                        else
                            "Was the provider on time? Did the price match the quote?"
                    )
                },
                minLines = 4,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("input_review_comment")
            )
        }

        // Guidelines Notice
        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = KhidmatiLightGreen
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = KhidmatiDarkGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (currentLanguage == "ar") "ميثاق التقييمات في خدمتي" else "Khidmati Review Guidelines",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = KhidmatiDarkGreen
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (currentLanguage == "ar")
                            "• التقييم مخصص فقط للأشخاص الذين أتموا الخدمة فعلياً.\n• يمنع استخدام لغة مسيئة أو التشهير الشخصي.\n• النقد البناء يساعد الحرفي على تحسين خدماته."
                        else
                            "• Only clients who actually completed the appointment may review.\n• Respectful language is required.\n• Constructive feedback helps professionals improve.",
                        fontSize = 11.sp,
                        color = KhidmatiDarkGreen,
                        lineHeight = 16.sp
                    )
                }
            }
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

        // Submit Button
        item {
            Button(
                onClick = {
                    if (comment.isBlank()) {
                        errorMessage = if (currentLanguage == "ar") "يرجى كتابة تعليق يصف تجربتك" else "Please write a comment"
                        return@Button
                    }
                    onSubmitReview(appointment.id, rating, comment.trim())
                },
                colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimaryGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_submit_verified_review")
            ) {
                Text(
                    text = if (currentLanguage == "ar") "نشر التقييم الموثق" else "Publish Verified Review",
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
