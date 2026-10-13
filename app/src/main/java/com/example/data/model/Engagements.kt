package com.example.data.model

enum class AppointmentStatus {
    PROPOSED,
    CONFIRMED,
    RESCHEDULE_REQUESTED,
    COMPLETED,
    CANCELLED
}

data class Appointment(
    val id: String,
    val requestId: String,
    val quoteId: String,
    val customerId: String,
    val customerName: String,
    val providerId: String,
    val providerName: String,
    val categoryName: String,
    val scheduledDate: String, // e.g. "2026-10-18"
    val scheduledTimeSlot: String, // e.g. "10:00 - 12:00"
    val addressOrArea: String,
    val status: AppointmentStatus,
    val agreedAmountDzd: Int,
    val notes: String = "",
    val hasReview: Boolean = false
)

data class Review(
    val id: String,
    val appointmentId: String,
    val providerId: String,
    val customerId: String,
    val customerName: String,
    val rating: Int, // 1 to 5
    val comment: String,
    val serviceCategoryName: String,
    val dateText: String,
    val isVerifiedEligible: Boolean = true
)

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestampMillis: Long,
    val isFromCustomer: Boolean
)

data class Conversation(
    val id: String,
    val requestId: String,
    val requestTitle: String,
    val customerId: String,
    val customerName: String,
    val providerId: String,
    val providerName: String,
    val lastMessage: String,
    val lastMessageTimeMillis: Long,
    val unreadCount: Int = 0
)

enum class ReportStatus {
    PENDING,
    REVIEWED,
    RESOLVED,
    DISMISSED
}

data class UserReport(
    val id: String,
    val reporterId: String,
    val reporterName: String,
    val targetType: String, // "PROVIDER", "CUSTOMER", "REVIEW"
    val targetId: String,
    val targetTitle: String,
    val reason: String,
    val details: String,
    val status: ReportStatus,
    val createdAtMillis: Long,
    val adminNotes: String = ""
)

data class AuditEvent(
    val id: String,
    val actorRole: UserRole,
    val actorName: String,
    val action: String,
    val targetType: String,
    val targetId: String,
    val details: String,
    val timestampMillis: Long
)
