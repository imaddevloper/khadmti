package com.example.ui.i18n

import androidx.compose.ui.unit.LayoutDirection
import com.example.data.model.AppointmentStatus
import com.example.data.model.PriceType
import com.example.data.model.QuoteStatus
import com.example.data.model.QuoteType
import com.example.data.model.RequestStatus
import com.example.data.model.VerificationStatus

object AppStrings {

    fun isRtl(lang: String): Boolean = lang.lowercase() == "ar"

    fun getLayoutDirection(lang: String): LayoutDirection =
        if (isRtl(lang)) LayoutDirection.Rtl else LayoutDirection.Ltr

    fun appName(lang: String): String =
        if (lang == "ar") "خدمتي" else "Khidmati"

    fun tagline(lang: String): String =
        if (lang == "ar") "سوق الخدمات المحلية الموثوق في الجزائر" else "Trusted Local Services Marketplace in Algeria"

    // Navigation
    fun navHome(lang: String): String = if (lang == "ar") "الرئيسية" else "Home"
    fun navSearch(lang: String): String = if (lang == "ar") "البحث" else "Search"
    fun navRequests(lang: String): String = if (lang == "ar") "طلباتي" else "Requests"
    fun navAppointments(lang: String): String = if (lang == "ar") "المواعيد" else "Appointments"
    fun navMessages(lang: String): String = if (lang == "ar") "المحادثات" else "Messages"
    fun navProfile(lang: String): String = if (lang == "ar") "الحساب" else "Profile"
    fun navFavorites(lang: String): String = if (lang == "ar") "المفضلة" else "Favorites"
    fun navProviderDashboard(lang: String): String = if (lang == "ar") "لوحة الحرفي" else "Pro Dashboard"
    fun navAdminCenter(lang: String): String = if (lang == "ar") "مركز الإشراف" else "Admin Center"

    // Actions & Buttons
    fun btnCreateRequest(lang: String): String = if (lang == "ar") "طلب خدمة جديدة" else "Request a Service"
    fun btnFindService(lang: String): String = if (lang == "ar") "ابحث عن حرفي" else "Find a Provider"
    fun btnJoinAsProvider(lang: String): String = if (lang == "ar") "انضم كحرفي مهني" else "Join as Provider"
    fun btnSubmit(lang: String): String = if (lang == "ar") "تأكيد وإرسال" else "Submit"
    fun btnCancel(lang: String): String = if (lang == "ar") "إلغاء" else "Cancel"
    fun btnSave(lang: String): String = if (lang == "ar") "حفظ" else "Save"
    fun btnCompareQuotes(lang: String): String = if (lang == "ar") "مقارنة عروض الأسعار" else "Compare Quotes"
    fun btnAcceptQuote(lang: String): String = if (lang == "ar") "قبول العرض وتثبيت الموعد" else "Accept Quote & Schedule"
    fun btnSendQuote(lang: String): String = if (lang == "ar") "تقديم عرض سعر" else "Send a Quote"
    fun btnWriteReview(lang: String): String = if (lang == "ar") "كتابة تقييم موثق" else "Write Verified Review"
    fun btnReport(lang: String): String = if (lang == "ar") "إبلاغ عن محتوى" else "Report Content"
    fun btnFilter(lang: String): String = if (lang == "ar") "تصفية" else "Filter"
    fun btnReset(lang: String): String = if (lang == "ar") "إعادة ضبط" else "Reset"
    fun btnSwitchRole(lang: String): String = if (lang == "ar") "تبديل الحساب التجريبي" else "Switch Demo Persona"

    // Currency & Formatting
    fun formatDzd(amount: Int?, lang: String): String {
        if (amount == null) return if (lang == "ar") "حسب المعاينة" else "Upon Inspection"
        return if (lang == "ar") "$amount د.ج" else "$amount DZD"
    }

    // Status translations
    fun requestStatus(status: RequestStatus, lang: String): String = when (status) {
        RequestStatus.OPEN -> if (lang == "ar") "مفتوح لاستقبال العروض" else "Open for Quotes"
        RequestStatus.RECEIVING_QUOTES -> if (lang == "ar") "وصلت عروض أسعار" else "Quotes Received"
        RequestStatus.QUOTE_SELECTED -> if (lang == "ar") "تم اختيار العرض" else "Quote Selected"
        RequestStatus.SCHEDULED -> if (lang == "ar") "الموعد مؤكد" else "Scheduled"
        RequestStatus.IN_PROGRESS -> if (lang == "ar") "قيد الإنجاز" else "In Progress"
        RequestStatus.COMPLETED -> if (lang == "ar") "مكتمل بنجاح" else "Completed"
        RequestStatus.CANCELLED -> if (lang == "ar") "ملغى" else "Cancelled"
    }

    fun quoteStatus(status: QuoteStatus, lang: String): String = when (status) {
        QuoteStatus.DRAFT -> if (lang == "ar") "مسودة" else "Draft"
        QuoteStatus.SENT -> if (lang == "ar") "مرسل للزبون" else "Sent"
        QuoteStatus.ACCEPTED -> if (lang == "ar") "مقبول" else "Accepted"
        QuoteStatus.REJECTED -> if (lang == "ar") "مرفوض" else "Rejected"
        QuoteStatus.WITHDRAWN -> if (lang == "ar") "مسحوب" else "Withdrawn"
        QuoteStatus.EXPIRED -> if (lang == "ar") "منتهي الصلاحية" else "Expired"
    }

    fun quoteType(type: QuoteType, lang: String): String = when (type) {
        QuoteType.FIXED -> if (lang == "ar") "سعر ثابت شامل" else "Fixed Price"
        QuoteType.ESTIMATE -> if (lang == "ar") "تقدير تقريبي" else "Estimate"
        QuoteType.HOURLY -> if (lang == "ar") "سعر بالساعة" else "Hourly Rate"
        QuoteType.INSPECTION_REQUIRED -> if (lang == "ar") "يتطلب معاينة مسبقة" else "Inspection Required"
    }

    fun priceType(type: PriceType, lang: String): String = when (type) {
        PriceType.FIXED -> if (lang == "ar") "سعر محدد" else "Fixed"
        PriceType.ESTIMATE -> if (lang == "ar") "تقديري" else "Estimated"
        PriceType.HOURLY -> if (lang == "ar") "بالساعة" else "Hourly"
        PriceType.INSPECTION_REQUIRED -> if (lang == "ar") "بعد المعاينة" else "After Inspection"
    }

    fun verificationStatus(status: VerificationStatus, lang: String): String = when (status) {
        VerificationStatus.NOT_SUBMITTED -> if (lang == "ar") "غير مقدم للتوثيق" else "Not Submitted"
        VerificationStatus.PENDING -> if (lang == "ar") "قيد المراجعة الإدارية" else "Pending Review"
        VerificationStatus.NEEDS_MORE_INFO -> if (lang == "ar") "معلومات إضافية مطلوبة" else "Info Needed"
        VerificationStatus.APPROVED -> if (lang == "ar") "حرفي موثق رسمياً" else "Verified Pro"
        VerificationStatus.REJECTED -> if (lang == "ar") "مرفوض" else "Rejected"
    }

    fun appointmentStatus(status: AppointmentStatus, lang: String): String = when (status) {
        AppointmentStatus.PROPOSED -> if (lang == "ar") "مقترح" else "Proposed"
        AppointmentStatus.CONFIRMED -> if (lang == "ar") "مؤكد" else "Confirmed"
        AppointmentStatus.RESCHEDULE_REQUESTED -> if (lang == "ar") "طلب إعادة جدولة" else "Reschedule Requested"
        AppointmentStatus.COMPLETED -> if (lang == "ar") "مكتمل" else "Completed"
        AppointmentStatus.CANCELLED -> if (lang == "ar") "ملغى" else "Cancelled"
    }

    // Trust & Safety
    fun trustHeadline(lang: String): String =
        if (lang == "ar") "لماذا يثق الجزائريون بمنصة خدمتي؟" else "Why Algerians Trust Khidmati"

    fun trustBadgeExplanation(lang: String): String =
        if (lang == "ar") "شارة التوثيق تعني التحقق من الهوية الوطنية (CNI) والشهادة المهنية أو السجل التجاري للحرفي من قبل المشرفين."
        else "The verification badge indicates national identity & professional trade certificate review by platform moderators."

    fun reviewEligibilityNotice(lang: String): String =
        if (lang == "ar") "التقييمات مسموحة فقط بعد إتمام الموعد الفعلي بين الزبون والحرفي لمنع التقييمات المزيفة."
        else "Reviews are strictly unlocked only after genuine completed appointments to eliminate fake ratings."
}
