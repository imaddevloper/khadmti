package com.example.data.model

enum class RequestStatus {
    OPEN,
    RECEIVING_QUOTES,
    QUOTE_SELECTED,
    SCHEDULED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

data class ServiceRequest(
    val id: String,
    val customerId: String,
    val customerName: String,
    val categoryId: String,
    val title: String,
    val description: String,
    val wilayaCode: Int,
    val wilayaName: String,
    val communeName: String,
    val requiresInspectionFirst: Boolean,
    val preferredDateWindow: String,
    val customerBudgetDzd: Int?, // Optional budget in DZD
    val status: RequestStatus,
    val createdAtMillis: Long,
    val selectedQuoteId: String? = null,
    val cancellationReason: String? = null
)
