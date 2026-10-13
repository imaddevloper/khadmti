package com.example.data.model

enum class UserRole {
    GUEST,
    CUSTOMER,
    PROVIDER,
    ADMIN
}

data class UserAccount(
    val id: String,
    val name: String,
    val emailOrPhone: String,
    val role: UserRole,
    val preferredLanguage: String = "ar", // "ar" or "en"
    val avatarInitials: String = "خ",
    val wilayaCode: Int = 16,
    val avatarEmoji: String = "👤"
)
