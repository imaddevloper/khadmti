package com.example.data.model

enum class QuoteStatus {
    DRAFT,
    SENT,
    ACCEPTED,
    REJECTED,
    WITHDRAWN,
    EXPIRED
}

enum class QuoteType {
    FIXED,
    ESTIMATE,
    HOURLY,
    INSPECTION_REQUIRED
}

data class Quote(
    val id: String,
    val requestId: String,
    val providerId: String,
    val providerName: String,
    val providerRating: Double,
    val quoteType: QuoteType,
    val amountDzd: Int, // in DZD
    val currency: String = "DZD",
    val scopeOfWork: String,
    val includedMaterials: String,
    val excludedMaterials: String,
    val proposedSchedule: String,
    val validityPeriodDays: Int,
    val notes: String,
    val status: QuoteStatus,
    val createdAtMillis: Long
)
