package com.example.data.repository

import com.example.data.model.AlgerianLocations
import com.example.data.model.Appointment
import com.example.data.model.AppointmentStatus
import com.example.data.model.AuditEvent
import com.example.data.model.ChatMessage
import com.example.data.model.Conversation
import com.example.data.model.PortfolioItem
import com.example.data.model.PriceType
import com.example.data.model.ProviderProfile
import com.example.data.model.Quote
import com.example.data.model.QuoteStatus
import com.example.data.model.QuoteType
import com.example.data.model.ReportStatus
import com.example.data.model.RequestStatus
import com.example.data.model.Review
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceListing
import com.example.data.model.ServiceRequest
import com.example.data.model.ServiceTaxonomy
import com.example.data.model.UserAccount
import com.example.data.model.UserReport
import com.example.data.model.UserRole
import com.example.data.model.VerificationStatus
import com.example.data.model.Wilaya
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class KhidmatiRepository {

    // Default Demo Users
    val customerUser = UserAccount(
        id = "usr_customer_karim",
        name = "Karim Benali",
        emailOrPhone = "0550 12 34 56",
        role = UserRole.CUSTOMER,
        preferredLanguage = "ar",
        avatarInitials = "ك"
    )

    val providerUser = UserAccount(
        id = "usr_pro_yacine",
        name = "Yacine Mansouri (Plombier)",
        emailOrPhone = "0661 98 76 54",
        role = UserRole.PROVIDER,
        preferredLanguage = "ar",
        avatarInitials = "ي"
    )

    val adminUser = UserAccount(
        id = "usr_admin_amina",
        name = "Amina Khedim (Admin/Mod)",
        emailOrPhone = "admin@khidmati.dz",
        role = UserRole.ADMIN,
        preferredLanguage = "ar",
        avatarInitials = "أ"
    )

    val guestUser = UserAccount(
        id = "usr_guest",
        name = "Guest Visitor",
        emailOrPhone = "",
        role = UserRole.GUEST,
        preferredLanguage = "ar",
        avatarInitials = "ز"
    )

    // Current State
    private val _currentUser = MutableStateFlow(customerUser)
    val currentUser: StateFlow<UserAccount> = _currentUser.asStateFlow()

    private val _currentLanguage = MutableStateFlow("ar")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _categories = MutableStateFlow(ServiceTaxonomy.categories)
    val categories: StateFlow<List<ServiceCategory>> = _categories.asStateFlow()

    private val _wilayas = MutableStateFlow(AlgerianLocations.allWilayas)
    val wilayas: StateFlow<List<Wilaya>> = _wilayas.asStateFlow()

    private val _providers = MutableStateFlow(initialProviders())
    val providers: StateFlow<List<ProviderProfile>> = _providers.asStateFlow()

    private val _requests = MutableStateFlow(initialRequests())
    val requests: StateFlow<List<ServiceRequest>> = _requests.asStateFlow()

    private val _quotes = MutableStateFlow(initialQuotes())
    val quotes: StateFlow<List<Quote>> = _quotes.asStateFlow()

    private val _appointments = MutableStateFlow(initialAppointments())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _reviews = MutableStateFlow(initialReviews())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    private val _conversations = MutableStateFlow(initialConversations())
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _messages = MutableStateFlow(initialMessages())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _reports = MutableStateFlow(initialReports())
    val reports: StateFlow<List<UserReport>> = _reports.asStateFlow()

    private val _auditLogs = MutableStateFlow(initialAuditLogs())
    val auditLogs: StateFlow<List<AuditEvent>> = _auditLogs.asStateFlow()

    fun setLanguage(lang: String) {
        _currentLanguage.value = lang
    }

    fun switchUser(user: UserAccount) {
        _currentUser.value = user
        logAudit(
            action = "SWITCH_USER_PERSONA",
            targetType = "USER",
            targetId = user.id,
            details = "Switched active test actor to ${user.name} (${user.role.name})"
        )
    }

    fun updateUserAccount(name: String, phone: String, emoji: String, wilayaCode: Int) {
        _currentUser.update {
            it.copy(
                name = name,
                emailOrPhone = phone,
                avatarEmoji = emoji,
                wilayaCode = wilayaCode,
                avatarInitials = if (name.isNotBlank()) name.take(1) else it.avatarInitials
            )
        }
        logAudit("UPDATE_PROFILE", "USER", _currentUser.value.id, "Updated profile: $name, $phone, Wilaya $wilayaCode")
    }

    // Toggle favorite
    fun toggleFavorite(providerId: String) {
        _providers.update { list ->
            list.map { if (it.id == providerId) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }

    // Create Service Request
    fun createServiceRequest(
        title: String,
        description: String,
        categoryId: String,
        wilayaCode: Int,
        communeName: String,
        requiresInspection: Boolean,
        preferredDateWindow: String,
        budgetDzd: Int?
    ): ServiceRequest {
        val user = _currentUser.value
        val wilaya = AlgerianLocations.getWilaya(wilayaCode)
        val wilayaName = wilaya?.nameAr ?: "الجزائر"

        val newRequest = ServiceRequest(
            id = "req_${System.currentTimeMillis()}",
            customerId = user.id,
            customerName = user.name,
            categoryId = categoryId,
            title = title,
            description = description,
            wilayaCode = wilayaCode,
            wilayaName = wilayaName,
            communeName = communeName,
            requiresInspectionFirst = requiresInspection,
            preferredDateWindow = preferredDateWindow,
            customerBudgetDzd = budgetDzd,
            status = RequestStatus.OPEN,
            createdAtMillis = System.currentTimeMillis()
        )

        _requests.update { listOf(newRequest) + it }
        logAudit("CREATE_REQUEST", "SERVICE_REQUEST", newRequest.id, "Customer created request '${title}' in $communeName, $wilayaName")
        return newRequest
    }

    // Cancel Service Request
    fun cancelServiceRequest(requestId: String, reason: String) {
        _requests.update { list ->
            list.map { req ->
                if (req.id == requestId) req.copy(status = RequestStatus.CANCELLED, cancellationReason = reason)
                else req
            }
        }
        logAudit("CANCEL_REQUEST", "SERVICE_REQUEST", requestId, "Request cancelled: $reason")
    }

    // Provider sends a Quote
    fun sendQuote(
        requestId: String,
        quoteType: QuoteType,
        amountDzd: Int,
        scopeOfWork: String,
        includedMaterials: String,
        excludedMaterials: String,
        proposedSchedule: String,
        validityDays: Int,
        notes: String
    ): Quote {
        val user = _currentUser.value
        val currentProvider = _providers.value.find { it.userId == user.id } ?: _providers.value.first()

        val newQuote = Quote(
            id = "qt_${System.currentTimeMillis()}",
            requestId = requestId,
            providerId = currentProvider.id,
            providerName = currentProvider.displayName,
            providerRating = currentProvider.averageRating,
            quoteType = quoteType,
            amountDzd = amountDzd,
            scopeOfWork = scopeOfWork,
            includedMaterials = includedMaterials,
            excludedMaterials = excludedMaterials,
            proposedSchedule = proposedSchedule,
            validityPeriodDays = validityDays,
            notes = notes,
            status = QuoteStatus.SENT,
            createdAtMillis = System.currentTimeMillis()
        )

        _quotes.update { listOf(newQuote) + it }

        // Update request status to RECEIVING_QUOTES if currently OPEN
        _requests.update { list ->
            list.map { req ->
                if (req.id == requestId && req.status == RequestStatus.OPEN) {
                    req.copy(status = RequestStatus.RECEIVING_QUOTES)
                } else req
            }
        }

        logAudit("SEND_QUOTE", "QUOTE", newQuote.id, "Provider ${currentProvider.displayName} submitted quote for $amountDzd DZD")
        return newQuote
    }

    // Customer Accepts Quote (Transactional Booking Creation)
    fun acceptQuote(quoteId: String): Appointment? {
        val quote = _quotes.value.find { it.id == quoteId } ?: return null
        val request = _requests.value.find { it.id == quote.requestId } ?: return null
        val category = ServiceTaxonomy.getCategory(request.categoryId)

        // 1. Mark this quote as ACCEPTED, others for this request as REJECTED
        _quotes.update { list ->
            list.map { q ->
                when {
                    q.id == quoteId -> q.copy(status = QuoteStatus.ACCEPTED)
                    q.requestId == quote.requestId && q.status == QuoteStatus.SENT -> q.copy(status = QuoteStatus.REJECTED)
                    else -> q
                }
            }
        }

        // 2. Mark request as QUOTE_SELECTED / SCHEDULED
        _requests.update { list ->
            list.map { req ->
                if (req.id == request.id) {
                    req.copy(status = RequestStatus.SCHEDULED, selectedQuoteId = quoteId)
                } else req
            }
        }

        // 3. Create confirmed appointment
        val newAppointment = Appointment(
            id = "apt_${System.currentTimeMillis()}",
            requestId = request.id,
            quoteId = quote.id,
            customerId = request.customerId,
            customerName = request.customerName,
            providerId = quote.providerId,
            providerName = quote.providerName,
            categoryName = category?.nameAr ?: "خدمة منزلية",
            scheduledDate = quote.proposedSchedule,
            scheduledTimeSlot = "10:00 - 13:00 (وقت الجزائر)",
            addressOrArea = "${request.communeName}, ${request.wilayaName}",
            status = AppointmentStatus.CONFIRMED,
            agreedAmountDzd = quote.amountDzd,
            notes = quote.scopeOfWork
        )

        _appointments.update { listOf(newAppointment) + it }

        // Create or update conversation
        val convId = "conv_${request.id}"
        if (_conversations.value.none { it.id == convId }) {
            val conv = Conversation(
                id = convId,
                requestId = request.id,
                requestTitle = request.title,
                customerId = request.customerId,
                customerName = request.customerName,
                providerId = quote.providerId,
                providerName = quote.providerName,
                lastMessage = "تم قبول عرض السعر وتأكيد الموعد.",
                lastMessageTimeMillis = System.currentTimeMillis()
            )
            _conversations.update { listOf(conv) + it }
        }

        logAudit("ACCEPT_QUOTE", "QUOTE", quoteId, "Quote accepted. Generated Appointment ${newAppointment.id} for ${quote.amountDzd} DZD")
        return newAppointment
    }

    // Complete Appointment (Unlocks Review)
    fun completeAppointment(appointmentId: String) {
        val apt = _appointments.value.find { it.id == appointmentId } ?: return

        _appointments.update { list ->
            list.map { if (it.id == appointmentId) it.copy(status = AppointmentStatus.COMPLETED) else it }
        }

        _requests.update { list ->
            list.map { if (it.id == apt.requestId) it.copy(status = RequestStatus.COMPLETED) else it }
        }

        logAudit("COMPLETE_APPOINTMENT", "APPOINTMENT", appointmentId, "Work verified and completed by provider & customer")
    }

    // Submit Review (Only for eligible completed appointments)
    fun submitReview(appointmentId: String, rating: Int, comment: String): Boolean {
        val apt = _appointments.value.find { it.id == appointmentId } ?: return false
        if (apt.status != AppointmentStatus.COMPLETED || apt.hasReview) return false

        val newReview = Review(
            id = "rev_${System.currentTimeMillis()}",
            appointmentId = appointmentId,
            providerId = apt.providerId,
            customerId = apt.customerId,
            customerName = apt.customerName,
            rating = rating,
            comment = comment,
            serviceCategoryName = apt.categoryName,
            dateText = "اليوم",
            isVerifiedEligible = true
        )

        _reviews.update { listOf(newReview) + it }

        // Mark appointment as reviewed
        _appointments.update { list ->
            list.map { if (it.id == appointmentId) it.copy(hasReview = true) else it }
        }

        // Recalculate provider rating
        _providers.update { list ->
            list.map { prov ->
                if (prov.id == apt.providerId) {
                    val provReviews = _reviews.value.filter { it.providerId == prov.id }
                    val avg = if (provReviews.isNotEmpty()) provReviews.map { it.rating }.average() else 5.0
                    prov.copy(averageRating = (avg * 10).toInt() / 10.0, reviewCount = provReviews.size)
                } else prov
            }
        }

        logAudit("SUBMIT_REVIEW", "REVIEW", newReview.id, "Verified review submitted for provider ${apt.providerName} with rating $rating★")
        return true
    }

    // Send In-App Chat Message
    fun sendMessage(conversationId: String, text: String) {
        val user = _currentUser.value
        val isCustomer = user.role == UserRole.CUSTOMER

        val newMsg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderId = user.id,
            senderName = user.name,
            text = text,
            timestampMillis = System.currentTimeMillis(),
            isFromCustomer = isCustomer
        )

        _messages.update { it + newMsg }

        _conversations.update { list ->
            list.map { conv ->
                if (conv.id == conversationId) {
                    conv.copy(lastMessage = text, lastMessageTimeMillis = System.currentTimeMillis())
                } else conv
            }
        }
    }

    // Provider Verification Submission
    fun submitProviderVerification(providerId: String) {
        _providers.update { list ->
            list.map { prov ->
                if (prov.id == providerId) {
                    prov.copy(verificationStatus = VerificationStatus.PENDING)
                } else prov
            }
        }
        logAudit("SUBMIT_VERIFICATION", "PROVIDER", providerId, "Provider submitted national ID & qualification certificates for verification")
    }

    // Admin Verification Review Decision
    fun adminDecideVerification(providerId: String, newStatus: VerificationStatus, reviewerNotes: String) {
        _providers.update { list ->
            list.map { prov ->
                if (prov.id == providerId) {
                    prov.copy(verificationStatus = newStatus, verificationReviewerNotes = reviewerNotes)
                } else prov
            }
        }
        logAudit("ADMIN_VERIFICATION_DECISION", "PROVIDER", providerId, "Admin decision: ${newStatus.name}. Notes: $reviewerNotes")
    }

    // Submit Report
    fun submitReport(targetType: String, targetId: String, targetTitle: String, reason: String, details: String) {
        val user = _currentUser.value
        val report = UserReport(
            id = "rep_${System.currentTimeMillis()}",
            reporterId = user.id,
            reporterName = user.name,
            targetType = targetType,
            targetId = targetId,
            targetTitle = targetTitle,
            reason = reason,
            details = details,
            status = ReportStatus.PENDING,
            createdAtMillis = System.currentTimeMillis()
        )
        _reports.update { listOf(report) + it }
        logAudit("SUBMIT_REPORT", targetType, targetId, "Report filed by ${user.name}: $reason")
    }

    // Admin Resolve Report
    fun adminResolveReport(reportId: String, status: ReportStatus, notes: String) {
        _reports.update { list ->
            list.map { if (it.id == reportId) it.copy(status = status, adminNotes = notes) else it }
        }
        logAudit("RESOLVE_REPORT", "REPORT", reportId, "Admin resolved report to ${status.name}: $notes")
    }

    private fun logAudit(action: String, targetType: String, targetId: String, details: String) {
        val user = _currentUser.value
        val event = AuditEvent(
            id = "aud_${System.currentTimeMillis()}",
            actorRole = user.role,
            actorName = user.name,
            action = action,
            targetType = targetType,
            targetId = targetId,
            details = details,
            timestampMillis = System.currentTimeMillis()
        )
        _auditLogs.update { listOf(event) + it }
    }

    // Initial realistic data
    private fun initialProviders(): List<ProviderProfile> = listOf(
        ProviderProfile(
            id = "prov_yacine",
            userId = "usr_pro_yacine",
            displayName = "Yacine Mansouri",
            bioEn = "Certified master plumber with 12 years experience in Algiers. Specialist in Chauffe-bain repairs, sanitary plumbing, pipe relining, and emergency water leaks.",
            bioAr = "حرفي ترصيص صحي وسباكة معتمد بخبرة 12 سنة بالجزائر العاصمة. مختص في صيانة سخانات الماء، معالجة التسربات وتركيب التجهيزات الصحية.",
            wilayaCode = 16,
            wilayaNameEn = "Algiers",
            wilayaNameAr = "الجزائر",
            communeNameEn = "Kouba",
            communeNameAr = "القبة",
            primaryCategoryId = "cat_plumber",
            additionalCategoryIds = listOf("cat_hvac"),
            verificationStatus = VerificationStatus.APPROVED,
            verificationReviewerNotes = "Verified CNI + Certificat de qualification professionnelle (Plomberie sanitaire)",
            averageRating = 4.9,
            reviewCount = 38,
            completedJobsCount = 142,
            services = listOf(
                ServiceListing("srv_1", "cat_plumber", "Chauffe-bain Diagnostic & Repair", "صيانة سخان الماء المنزلي", PriceType.ESTIMATE, 3000, "Includes leak check, burner cleaning & diaphragm", "فحص الشعلة، تنظيف الكلس وتغيير الغشاء المطاطي"),
                ServiceListing("srv_2", "cat_plumber", "Sanitary Drain Unclogging", "تسليك المجاري وشبكات الصرف", PriceType.FIXED, 2500, "High-pressure mechanical snake unclogging", "تسليك احترافي بدون تكسير للأنابيب"),
                ServiceListing("srv_3", "cat_plumber", "Complete Bathroom Plumbing", "تركيب شبكة حمام متكاملة", PriceType.INSPECTION_REQUIRED, null, "PEX / Multilayer pipes after home inspection", "أنابيب ملتي كوش وتركيب الدوش والمغاسل بعد المعاينة")
            ),
            portfolio = listOf(
                PortfolioItem("port_1", "Chauffe-bain Juncker Restoration", "Plumbing", "Replaced safety thermocouple and cleaned main burner in Hydra apartment.", "2026-09"),
                PortfolioItem("port_2", "Modern Shower Valve Installation", "Plumbing", "Concealed mixer valve with PEX piping in Bab El Oued.", "2026-08")
            ),
            workingHoursEn = "Sat - Thu: 08:00 - 18:00",
            workingHoursAr = "السبت - الخميس: 08:00 - 18:00",
            isAvailable = true,
            isFavorite = true
        ),
        ProviderProfile(
            id = "prov_amine",
            userId = "usr_pro_amine",
            displayName = "Amine Djelloul",
            bioEn = "Professional licensed electrician in Oran. Home automation, 220V/380V panels, LED lighting, and short-circuit troubleshooting.",
            bioAr = "كهربائي معماري وصناعي معتمد بوهران. لوحات التحكم، تصليح انقطاع الكهرباء، التمديدات الحديثة والإنارة الذكية.",
            wilayaCode = 31,
            wilayaNameEn = "Oran",
            wilayaNameAr = "وهران",
            communeNameEn = "Es Senia",
            communeNameAr = "السانية",
            primaryCategoryId = "cat_electrician",
            additionalCategoryIds = listOf("cat_locksmith"),
            verificationStatus = VerificationStatus.APPROVED,
            verificationReviewerNotes = "Verified national diploma in electrical installations",
            averageRating = 4.8,
            reviewCount = 27,
            completedJobsCount = 89,
            services = listOf(
                ServiceListing("srv_4", "cat_electrician", "Electrical Panel Upgrade", "تجديد وتأمين لوحة القواطع الكهربائية", PriceType.FIXED, 6000, "Differential breakers & earth grounding test", "تركيب قواطع تفاضلية مع فحص التأريض"),
                ServiceListing("srv_5", "cat_electrician", "Emergency Short Circuit Visit", "تدخل طارئ لانقطاع الكهرباء", PriceType.ESTIMATE, 2000, "Diagnostic and immediate circuit isolation", "تحديد مكان الشورت وإعادة التيار بأمان")
            ),
            portfolio = listOf(
                PortfolioItem("port_3", "Villa 3-Phase Panel Upgrade", "Electrician", "Installed Legrand breakers with surge protector in Bir El Djir.", "2026-09")
            ),
            workingHoursEn = "Daily: 08:00 - 20:00",
            workingHoursAr = "يومياً: 08:00 - 20:00",
            isAvailable = true,
            isFavorite = false
        ),
        ProviderProfile(
            id = "prov_sofiane",
            userId = "usr_pro_sofiane",
            displayName = "Sofiane Belkacem",
            bioEn = "HVAC cooling & refrigeration technician in Constantine. AC unit split installation, seasonal maintenance, and refrigerant gas charge (R410A / R32).",
            bioAr = "تقني تبريد وتكييف بقسنطينة. تركيب المكيفات الهوائية (سبليت)، شحن الغاز والتنظيف الموسمي ومكافحة الروائح.",
            wilayaCode = 25,
            wilayaNameEn = "Constantine",
            wilayaNameAr = "قسنطينة",
            communeNameEn = "Ali Mendjeli",
            communeNameAr = "علي منجلي",
            primaryCategoryId = "cat_hvac",
            additionalCategoryIds = listOf("cat_appliance"),
            verificationStatus = VerificationStatus.PENDING,
            verificationReviewerNotes = "Identity submitted; diploma verification currently under review",
            averageRating = 4.7,
            reviewCount = 19,
            completedJobsCount = 54,
            services = listOf(
                ServiceListing("srv_6", "cat_hvac", "Split AC Installation (9000-18000 BTU)", "تركيب مكيف هوائي سبليت", PriceType.FIXED, 4500, "Standard mounting with copper pipe flare checks", "تثبيت الوحدتين وتفريغ الهواء والتأكد من الغاز"),
                ServiceListing("srv_7", "cat_hvac", "R410A / R32 Gas Recharge", "شحن غاز التبريد الأصلي", PriceType.ESTIMATE, 3500, "Vacuum pump test + manifold pressure calibration", "فحص التسربات وشحن الغاز بميزان دقيق")
            ),
            portfolio = listOf(
                PortfolioItem("port_4", "Dual Inverter AC Mount", "HVAC", "Clean concealed installation in modern apartment in El Khroub.", "2026-07")
            ),
            workingHoursEn = "Sat - Thu: 09:00 - 19:00",
            workingHoursAr = "السبت - الخميس: 09:00 - 19:00",
            isAvailable = true,
            isFavorite = false
        ),
        ProviderProfile(
            id = "prov_tarek",
            userId = "usr_pro_tarek",
            displayName = "Tarek Brahimi",
            bioEn = "Master painter & gypsum artisan in Sétif. High-end satin paint, moisture barrier treatments, and BA13 false ceiling decoration.",
            bioAr = "حرفي دهان وديكور جبس بسطيف. دهان ساتيني حريري، معالجة الرطوبة والتشققات، وديكورات الأسقف المعلقة BA13.",
            wilayaCode = 19,
            wilayaNameEn = "Sétif",
            wilayaNameAr = "سطيف",
            communeNameEn = "El Eulma",
            communeNameAr = "العلمة",
            primaryCategoryId = "cat_painter",
            additionalCategoryIds = listOf("cat_carpenter"),
            verificationStatus = VerificationStatus.APPROVED,
            verificationReviewerNotes = "Verified artisan chamber card (Chambre de l'artisanat et des métiers)",
            averageRating = 5.0,
            reviewCount = 14,
            completedJobsCount = 41,
            services = listOf(
                ServiceListing("srv_8", "cat_painter", "Interior Living Room Paint", "دهان صالون أو شقة متكاملة", PriceType.INSPECTION_REQUIRED, null, "Surface preparation, sanding, primer + 2 top coats", "معجون تسوية، سنفرة، وطبقتين طلاء عالي الجودة")
            ),
            portfolio = listOf(
                PortfolioItem("port_5", "Living Room Satin Finishing", "Painter", "Smooth Venetian stucco accent wall with warm ambient cove lighting.", "2026-09")
            ),
            workingHoursEn = "Sat - Wed: 08:00 - 17:00",
            workingHoursAr = "السبت - الأربعاء: 08:00 - 17:00",
            isAvailable = true,
            isFavorite = false
        )
    )

    private fun initialRequests(): List<ServiceRequest> = listOf(
        ServiceRequest(
            id = "req_101",
            customerId = "usr_customer_karim",
            customerName = "Karim Benali",
            categoryId = "cat_plumber",
            title = "Chauffe-bain leak & burner extinguishing",
            description = "My 10L gas water heater in the kitchen keeps turning off when hot water is opened and there is a small water drip underneath the cold water valve.",
            wilayaCode = 16,
            wilayaName = "الجزائر",
            communeName = "القبة (Kouba)",
            requiresInspectionFirst = false,
            preferredDateWindow = "Next 2 days (Morning preferred)",
            customerBudgetDzd = 3500,
            status = RequestStatus.RECEIVING_QUOTES,
            createdAtMillis = System.currentTimeMillis() - 86400000L
        ),
        ServiceRequest(
            id = "req_102",
            customerId = "usr_customer_karim",
            customerName = "Karim Benali",
            categoryId = "cat_electrician",
            title = "Living room lighting & additional 220V sockets",
            description = "Need to add 3 double sockets behind the TV unit and install 4 recessed spotlights in the false ceiling.",
            wilayaCode = 16,
            wilayaName = "الجزائر",
            communeName = "حيدرة (Hydra)",
            requiresInspectionFirst = true,
            preferredDateWindow = "Weekend",
            customerBudgetDzd = 8000,
            status = RequestStatus.OPEN,
            createdAtMillis = System.currentTimeMillis() - 172800000L
        )
    )

    private fun initialQuotes(): List<Quote> = listOf(
        Quote(
            id = "qt_201",
            requestId = "req_101",
            providerId = "prov_yacine",
            providerName = "Yacine Mansouri",
            providerRating = 4.9,
            quoteType = QuoteType.FIXED,
            amountDzd = 3000,
            scopeOfWork = "Full chauffe-bain diagnostic, thermocouple swap, burner descaling, and valve O-ring gasket replacement.",
            includedMaterials = "New original silicone gaskets, burner chemical descaler.",
            excludedMaterials = "Copper pipes or brand new safety valve if cracked.",
            proposedSchedule = "Tomorrow at 10:00",
            validityPeriodDays = 5,
            notes = "Will bring genuine spare parts for Juncker / Sonaric models.",
            status = QuoteStatus.SENT,
            createdAtMillis = System.currentTimeMillis() - 43200000L
        ),
        Quote(
            id = "qt_202",
            requestId = "req_101",
            providerId = "prov_amine",
            providerName = "Amine Djelloul (Multi-Services)",
            providerRating = 4.8,
            quoteType = QuoteType.ESTIMATE,
            amountDzd = 3500,
            scopeOfWork = "Inspection of water heater ignition, gas pressure check and water leak repair.",
            includedMaterials = "Standard fittings and teflon tape.",
            excludedMaterials = "Replacement membrane or main thermocouple.",
            proposedSchedule = "Thursday afternoon",
            validityPeriodDays = 3,
            notes = "Price may adjust slightly depending on membrane condition.",
            status = QuoteStatus.SENT,
            createdAtMillis = System.currentTimeMillis() - 36000000L
        )
    )

    private fun initialAppointments(): List<Appointment> = listOf(
        Appointment(
            id = "apt_301",
            requestId = "req_099",
            quoteId = "qt_199",
            customerId = "usr_customer_karim",
            customerName = "Karim Benali",
            providerId = "prov_yacine",
            providerName = "Yacine Mansouri",
            categoryName = "ترصيص صحي وسباكة",
            scheduledDate = "2026-10-10",
            scheduledTimeSlot = "14:00 - 16:00",
            addressOrArea = "Kouba, Algiers",
            status = AppointmentStatus.COMPLETED,
            agreedAmountDzd = 2500,
            notes = "Kitchen sink drain siphon replaced and tested for leaks.",
            hasReview = true
        )
    )

    private fun initialReviews(): List<Review> = listOf(
        Review(
            id = "rev_401",
            appointmentId = "apt_301",
            providerId = "prov_yacine",
            customerId = "usr_customer_karim",
            customerName = "Karim Benali",
            rating = 5,
            comment = "حرفي محترف ومتقن لعمله، وصل في الوقت المحدد وأصلح السخان دون أي فوضى. أنصح به بشدة!",
            serviceCategoryName = "ترصيص صحي",
            dateText = "منذ 3 أيام",
            isVerifiedEligible = true
        )
    )

    private fun initialConversations(): List<Conversation> = listOf(
        Conversation(
            id = "conv_req_101",
            requestId = "req_101",
            requestTitle = "Chauffe-bain leak & burner extinguishing",
            customerId = "usr_customer_karim",
            customerName = "Karim Benali",
            providerId = "prov_yacine",
            providerName = "Yacine Mansouri",
            lastMessage = "السلام عليكم، لقد أرسلت لك عرض سعر يشمل قطع الغيار الأصلية وسأكون متوفراً غداً صباحاً.",
            lastMessageTimeMillis = System.currentTimeMillis() - 40000000L,
            unreadCount = 1
        )
    )

    private fun initialMessages(): List<ChatMessage> = listOf(
        ChatMessage(
            id = "msg_501",
            conversationId = "conv_req_101",
            senderId = "usr_customer_karim",
            senderName = "Karim Benali",
            text = "السلام عليكم، هل يمكن أن تأتي غداً صباحاً؟ السخان ينطفئ باستمرار.",
            timestampMillis = System.currentTimeMillis() - 50000000L,
            isFromCustomer = true
        ),
        ChatMessage(
            id = "msg_502",
            conversationId = "conv_req_101",
            senderId = "prov_yacine",
            senderName = "Yacine Mansouri",
            text = "وعليكم السلام، نعم بالطبع. لقد أرسلت لك عرض سعر مفصل ويمكنني القدوم عند الساعة العاشرة صباحاً.",
            timestampMillis = System.currentTimeMillis() - 40000000L,
            isFromCustomer = false
        )
    )

    private fun initialReports(): List<UserReport> = listOf(
        UserReport(
            id = "rep_601",
            reporterId = "usr_customer_karim",
            reporterName = "Karim Benali",
            targetType = "PROVIDER",
            targetId = "prov_unverified_sample",
            targetTitle = "Suspicious Contact Outside App",
            reason = "Off-platform payment demand",
            details = "Requested advance wire transfer before inspection contrary to Khidmati policy.",
            status = ReportStatus.PENDING,
            createdAtMillis = System.currentTimeMillis() - 72000000L
        )
    )

    private fun initialAuditLogs(): List<AuditEvent> = listOf(
        AuditEvent(
            id = "aud_01",
            actorRole = UserRole.ADMIN,
            actorName = "Amina Khedim (Admin)",
            action = "APPROVE_VERIFICATION",
            targetType = "PROVIDER",
            targetId = "prov_yacine",
            details = "Approved professional accreditation documents for Yacine Mansouri",
            timestampMillis = System.currentTimeMillis() - 864000000L
        ),
        AuditEvent(
            id = "aud_02",
            actorRole = UserRole.CUSTOMER,
            actorName = "Karim Benali",
            action = "CREATE_REQUEST",
            targetType = "SERVICE_REQUEST",
            targetId = "req_101",
            details = "Created plumbing repair request in Kouba, Algiers",
            timestampMillis = System.currentTimeMillis() - 86400000L
        )
    )
}
