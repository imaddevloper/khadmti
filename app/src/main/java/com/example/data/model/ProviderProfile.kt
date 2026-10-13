package com.example.data.model

enum class VerificationStatus {
    NOT_SUBMITTED,
    PENDING,
    NEEDS_MORE_INFO,
    APPROVED,
    REJECTED
}

enum class PriceType {
    FIXED,
    ESTIMATE,
    HOURLY,
    INSPECTION_REQUIRED
}

data class ServiceListing(
    val id: String,
    val categoryId: String,
    val titleEn: String,
    val titleAr: String,
    val priceType: PriceType,
    val basePriceDzd: Int?, // in Algerian Dinars (DZD)
    val priceDescriptionEn: String,
    val priceDescriptionAr: String
)

data class PortfolioItem(
    val id: String,
    val title: String,
    val categoryName: String,
    val description: String,
    val dateText: String
)

data class ProviderProfile(
    val id: String,
    val userId: String,
    val displayName: String,
    val bioEn: String,
    val bioAr: String,
    val wilayaCode: Int,
    val wilayaNameEn: String,
    val wilayaNameAr: String,
    val communeNameEn: String,
    val communeNameAr: String,
    val primaryCategoryId: String,
    val additionalCategoryIds: List<String>,
    val verificationStatus: VerificationStatus,
    val verificationReviewerNotes: String = "",
    val averageRating: Double,
    val reviewCount: Int,
    val completedJobsCount: Int,
    val services: List<ServiceListing>,
    val portfolio: List<PortfolioItem>,
    val workingHoursEn: String,
    val workingHoursAr: String,
    val isAvailable: Boolean = true,
    val isFavorite: Boolean = false,
    val memberSinceYear: Int = 2024
)
