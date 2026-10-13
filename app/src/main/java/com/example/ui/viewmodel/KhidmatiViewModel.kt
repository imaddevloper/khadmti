package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Appointment
import com.example.data.model.ProviderProfile
import com.example.data.model.QuoteType
import com.example.data.model.ReportStatus
import com.example.data.model.ServiceRequest
import com.example.data.model.UserAccount
import com.example.data.model.UserRole
import com.example.data.model.VerificationStatus
import com.example.data.repository.KhidmatiRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppDestination {
    HOME,
    SEARCH,
    REQUESTS,
    APPOINTMENTS,
    MESSAGES,
    FAVORITES,
    PROVIDER_DASHBOARD,
    ADMIN_CENTER,
    CREATE_REQUEST,
    PROVIDER_DETAIL,
    REQUEST_DETAIL,
    SEND_QUOTE,
    WRITE_REVIEW,
    VERIFICATION_CENTER
}

data class KhidmatiUiState(
    val currentDestination: AppDestination = AppDestination.HOME,
    val selectedProviderId: String? = null,
    val selectedRequestId: String? = null,
    val selectedAppointmentId: String? = null,
    val searchQuery: String = "",
    val selectedCategoryFilterId: String? = null,
    val selectedWilayaFilterCode: Int? = null,
    val showOnlyVerified: Boolean = false,
    val showPersonaSwitcher: Boolean = false,
    val bannerNotice: String? = null
)

class KhidmatiViewModel(
    val repository: KhidmatiRepository = KhidmatiRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(KhidmatiUiState())
    val uiState: StateFlow<KhidmatiUiState> = _uiState.asStateFlow()

    private val _snackbarEvents = MutableSharedFlow<String>()
    val snackbarEvents: SharedFlow<String> = _snackbarEvents.asSharedFlow()

    val currentUser = repository.currentUser
    val currentLanguage = repository.currentLanguage
    val categories = repository.categories
    val wilayas = repository.wilayas
    val providers = repository.providers
    val requests = repository.requests
    val quotes = repository.quotes
    val appointments = repository.appointments
    val reviews = repository.reviews
    val conversations = repository.conversations
    val messages = repository.messages
    val reports = repository.reports
    val auditLogs = repository.auditLogs

    fun navigateTo(destination: AppDestination) {
        _uiState.update { it.copy(currentDestination = destination) }
    }

    fun openProviderDetail(providerId: String) {
        _uiState.update {
            it.copy(
                currentDestination = AppDestination.PROVIDER_DETAIL,
                selectedProviderId = providerId
            )
        }
    }

    fun openRequestDetail(requestId: String) {
        _uiState.update {
            it.copy(
                currentDestination = AppDestination.REQUEST_DETAIL,
                selectedRequestId = requestId
            )
        }
    }

    fun openSendQuote(requestId: String) {
        _uiState.update {
            it.copy(
                currentDestination = AppDestination.SEND_QUOTE,
                selectedRequestId = requestId
            )
        }
    }

    fun openWriteReview(appointmentId: String) {
        _uiState.update {
            it.copy(
                currentDestination = AppDestination.WRITE_REVIEW,
                selectedAppointmentId = appointmentId
            )
        }
    }

    fun toggleLanguage() {
        val current = currentLanguage.value
        val next = if (current == "ar") "en" else "ar"
        repository.setLanguage(next)
        emitMessage(if (next == "ar") "تم تفعيل اللغة العربية" else "English language selected")
    }

    fun showPersonaSwitcher(show: Boolean) {
        _uiState.update { it.copy(showPersonaSwitcher = show) }
    }

    fun switchUserRole(role: UserRole) {
        val target = when (role) {
            UserRole.CUSTOMER -> repository.customerUser
            UserRole.PROVIDER -> repository.providerUser
            UserRole.ADMIN -> repository.adminUser
            UserRole.GUEST -> repository.guestUser
        }
        repository.switchUser(target)
        _uiState.update { it.copy(showPersonaSwitcher = false) }

        // Route to default view suitable for persona
        when (role) {
            UserRole.PROVIDER -> navigateTo(AppDestination.PROVIDER_DASHBOARD)
            UserRole.ADMIN -> navigateTo(AppDestination.ADMIN_CENTER)
            else -> navigateTo(AppDestination.HOME)
        }

        val lang = currentLanguage.value
        val msg = if (lang == "ar") "تم التبديل إلى حساب: ${target.name}" else "Switched to persona: ${target.name}"
        emitMessage(msg)
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setCategoryFilter(categoryId: String?) {
        _uiState.update { it.copy(selectedCategoryFilterId = categoryId) }
    }

    fun setWilayaFilter(wilayaCode: Int?) {
        _uiState.update { it.copy(selectedWilayaFilterCode = wilayaCode) }
    }

    fun toggleOnlyVerified(only: Boolean) {
        _uiState.update { it.copy(showOnlyVerified = only) }
    }

    fun resetFilters() {
        _uiState.update {
            it.copy(
                searchQuery = "",
                selectedCategoryFilterId = null,
                selectedWilayaFilterCode = null,
                showOnlyVerified = false
            )
        }
    }

    fun toggleFavorite(providerId: String) {
        repository.toggleFavorite(providerId)
    }

    fun createRequest(
        title: String,
        description: String,
        categoryId: String,
        wilayaCode: Int,
        communeName: String,
        requiresInspection: Boolean,
        preferredDate: String,
        budgetDzd: Int?
    ) {
        val newReq = repository.createServiceRequest(
            title = title,
            description = description,
            categoryId = categoryId,
            wilayaCode = wilayaCode,
            communeName = communeName,
            requiresInspection = requiresInspection,
            preferredDateWindow = preferredDate,
            budgetDzd = budgetDzd
        )
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم نشر طلب الخدمة بنجاح، ستصلك عروض الأسعار قريباً" else "Request published! You will receive quotes shortly.")
        openRequestDetail(newReq.id)
    }

    fun cancelRequest(requestId: String, reason: String) {
        repository.cancelServiceRequest(requestId, reason)
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم إلغاء الطلب" else "Request cancelled")
    }

    fun submitQuote(
        requestId: String,
        quoteType: QuoteType,
        amountDzd: Int,
        scopeOfWork: String,
        includedMaterials: String,
        excludedMaterials: String,
        proposedSchedule: String,
        validityDays: Int,
        notes: String
    ) {
        repository.sendQuote(
            requestId = requestId,
            quoteType = quoteType,
            amountDzd = amountDzd,
            scopeOfWork = scopeOfWork,
            includedMaterials = includedMaterials,
            excludedMaterials = excludedMaterials,
            proposedSchedule = proposedSchedule,
            validityDays = validityDays,
            notes = notes
        )
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم إرسال عرض السعر للزبون بنجاح" else "Quote sent to customer successfully.")
        openRequestDetail(requestId)
    }

    fun acceptQuote(quoteId: String) {
        val appointment = repository.acceptQuote(quoteId)
        val lang = currentLanguage.value
        if (appointment != null) {
            emitMessage(if (lang == "ar") "تم قبول العرض وتثبيت الموعد بنجاح!" else "Quote accepted! Appointment confirmed.")
            navigateTo(AppDestination.APPOINTMENTS)
        } else {
            emitMessage(if (lang == "ar") "حدث خطأ أثناء معالجة العرض" else "Error accepting quote.")
        }
    }

    fun completeAppointment(appointmentId: String) {
        repository.completeAppointment(appointmentId)
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم تأكيد إتمام العمل بنجاح. يمكنك الآن كتابة تقييم موثق!" else "Work completed! You can now write a verified review.")
    }

    fun submitReview(appointmentId: String, rating: Int, comment: String) {
        val success = repository.submitReview(appointmentId, rating, comment)
        val lang = currentLanguage.value
        if (success) {
            emitMessage(if (lang == "ar") "شكراً لك! تم نشر تقييمك الموثق لدعم المجتمع" else "Thank you! Your verified review has been published.")
            navigateTo(AppDestination.APPOINTMENTS)
        } else {
            emitMessage(if (lang == "ar") "لا يمكن إضافة تقييم إلا للمواعيد المكتملة بالفعل" else "Reviews can only be submitted for completed services.")
        }
    }

    fun sendMessage(conversationId: String, text: String) {
        if (text.isNotBlank()) {
            repository.sendMessage(conversationId, text.trim())
        }
    }

    fun submitVerification(providerId: String) {
        repository.submitProviderVerification(providerId)
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم إرسال وثائق التوثيق للمراجعة الإدارية" else "Verification documents submitted for review.")
    }

    fun adminDecideVerification(providerId: String, status: VerificationStatus, notes: String) {
        repository.adminDecideVerification(providerId, status, notes)
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم تحديث حالة التوثيق: ${status.name}" else "Verification status updated: ${status.name}")
    }

    fun submitReport(targetType: String, targetId: String, title: String, reason: String, details: String) {
        repository.submitReport(targetType, targetId, title, reason, details)
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "شكراً لتعاونك، تم استلام البلاغ وسيقوم المشرف بمراجعته" else "Report received and sent to moderation.")
    }

    fun adminResolveReport(reportId: String, status: ReportStatus, notes: String) {
        repository.adminResolveReport(reportId, status, notes)
        val lang = currentLanguage.value
        emitMessage(if (lang == "ar") "تم اتخاذ الإجراء في البلاغ" else "Report status updated.")
    }

    private fun emitMessage(msg: String) {
        viewModelScope.launch {
            _snackbarEvents.emit(msg)
        }
    }
}
