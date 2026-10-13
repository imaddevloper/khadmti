package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppointmentStatus
import com.example.data.model.QuoteStatus
import com.example.data.model.RequestStatus
import com.example.data.model.VerificationStatus
import com.example.ui.i18n.AppStrings
import com.example.ui.theme.KhidmatiAmber
import com.example.ui.theme.KhidmatiAmberLight
import com.example.ui.theme.KhidmatiBlue
import com.example.ui.theme.KhidmatiBlueLight
import com.example.ui.theme.KhidmatiDarkGreen
import com.example.ui.theme.KhidmatiLightGreen
import com.example.ui.theme.KhidmatiPrimaryGreen
import com.example.ui.theme.KhidmatiRed
import com.example.ui.theme.KhidmatiRedLight

@Composable
fun VerificationBadge(
    status: VerificationStatus,
    currentLanguage: String,
    modifier: Modifier = Modifier,
    showDetailsOnClick: Boolean = true
) {
    var showExplanationDialog by remember { mutableStateOf(false) }

    val (badgeText, badgeBg, badgeTextColor, badgeIcon) = when (status) {
        VerificationStatus.APPROVED -> Quad(
            if (currentLanguage == "ar") "موثق رسمي" else "Verified Pro",
            KhidmatiLightGreen,
            KhidmatiDarkGreen,
            Icons.Default.CheckCircle
        )
        VerificationStatus.PENDING -> Quad(
            if (currentLanguage == "ar") "قيد التوثيق" else "Under Review",
            KhidmatiAmberLight,
            Color(0xFFB45309),
            Icons.Default.HourglassTop
        )
        VerificationStatus.NEEDS_MORE_INFO -> Quad(
            if (currentLanguage == "ar") "مطلوب وثائق" else "Info Needed",
            KhidmatiBlueLight,
            KhidmatiBlue,
            Icons.Default.Info
        )
        VerificationStatus.REJECTED -> Quad(
            if (currentLanguage == "ar") "توثيق مرفوض" else "Rejected",
            KhidmatiRedLight,
            KhidmatiRed,
            Icons.Default.Warning
        )
        VerificationStatus.NOT_SUBMITTED -> Quad(
            if (currentLanguage == "ar") "غير موثق" else "Unverified",
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            Icons.Default.Shield
        )
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = badgeBg,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(if (showDetailsOnClick) Modifier.clickable { showExplanationDialog = true } else Modifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = badgeIcon,
                contentDescription = null,
                tint = badgeTextColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = badgeText,
                color = badgeTextColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if (showExplanationDialog) {
        AlertDialog(
            onDismissRequest = { showExplanationDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = KhidmatiPrimaryGreen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (currentLanguage == "ar") "معايير توثيق الحرفيين" else "Provider Verification Standards",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = AppStrings.trustBadgeExplanation(currentLanguage),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (currentLanguage == "ar")
                            "الحالة الحالية: ${AppStrings.verificationStatus(status, currentLanguage)}"
                        else
                            "Current Status: ${AppStrings.verificationStatus(status, currentLanguage)}",
                        fontWeight = FontWeight.Bold,
                        color = badgeTextColor
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showExplanationDialog = false }) {
                    Text(if (currentLanguage == "ar") "حسناً، فهمت" else "Understood")
                }
            }
        )
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun RequestStatusBadge(
    status: RequestStatus,
    currentLanguage: String
) {
    val (bg, textColor) = when (status) {
        RequestStatus.OPEN -> Pair(KhidmatiBlueLight, KhidmatiBlue)
        RequestStatus.RECEIVING_QUOTES -> Pair(KhidmatiAmberLight, Color(0xFFB45309))
        RequestStatus.QUOTE_SELECTED -> Pair(KhidmatiLightGreen, KhidmatiDarkGreen)
        RequestStatus.SCHEDULED -> Pair(KhidmatiLightGreen, KhidmatiPrimaryGreen)
        RequestStatus.IN_PROGRESS -> Pair(KhidmatiBlueLight, KhidmatiBlue)
        RequestStatus.COMPLETED -> Pair(KhidmatiLightGreen, KhidmatiDarkGreen)
        RequestStatus.CANCELLED -> Pair(KhidmatiRedLight, KhidmatiRed)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = AppStrings.requestStatus(status, currentLanguage),
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun QuoteStatusBadge(
    status: QuoteStatus,
    currentLanguage: String
) {
    val (bg, textColor) = when (status) {
        QuoteStatus.DRAFT -> Pair(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
        QuoteStatus.SENT -> Pair(KhidmatiBlueLight, KhidmatiBlue)
        QuoteStatus.ACCEPTED -> Pair(KhidmatiLightGreen, KhidmatiDarkGreen)
        QuoteStatus.REJECTED -> Pair(KhidmatiRedLight, KhidmatiRed)
        QuoteStatus.WITHDRAWN -> Pair(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
        QuoteStatus.EXPIRED -> Pair(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = AppStrings.quoteStatus(status, currentLanguage),
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AppointmentStatusBadge(
    status: AppointmentStatus,
    currentLanguage: String
) {
    val (bg, textColor) = when (status) {
        AppointmentStatus.PROPOSED -> Pair(KhidmatiAmberLight, Color(0xFFB45309))
        AppointmentStatus.CONFIRMED -> Pair(KhidmatiLightGreen, KhidmatiDarkGreen)
        AppointmentStatus.RESCHEDULE_REQUESTED -> Pair(KhidmatiAmberLight, KhidmatiAmber)
        AppointmentStatus.COMPLETED -> Pair(KhidmatiLightGreen, KhidmatiDarkGreen)
        AppointmentStatus.CANCELLED -> Pair(KhidmatiRedLight, KhidmatiRed)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = AppStrings.appointmentStatus(status, currentLanguage),
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
