package com.example

import androidx.compose.ui.unit.LayoutDirection
import com.example.data.model.AlgerianLocations
import com.example.data.model.AppointmentStatus
import com.example.data.model.QuoteStatus
import com.example.data.model.QuoteType
import com.example.data.model.RequestStatus
import com.example.data.model.UserRole
import com.example.data.model.VerificationStatus
import com.example.data.repository.KhidmatiRepository
import com.example.ui.i18n.AppStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class KhidmatiCUJTest {

    private lateinit var repository: KhidmatiRepository

    @Before
    fun setUp() {
        repository = KhidmatiRepository()
    }

    @Test
    fun testAlgerianLocationsDataset() {
        val wilayas = AlgerianLocations.allWilayas
        assertEquals("Must include all 69 Algerian Wilayas", 69, wilayas.size)

        val algiers = AlgerianLocations.getWilaya(16)
        assertNotNull(algiers)
        assertEquals("Algiers", algiers!!.nameEn)
        assertTrue(algiers.nameAr.contains("الجزائر"))
        assertTrue(algiers.communes.any { it.nameEn == "Kouba" })
        assertTrue(algiers.communes.any { it.nameAr == "حيدرة" })

        val aflou = AlgerianLocations.getWilaya(69)
        assertNotNull(aflou)
        assertEquals("Aflou", aflou!!.nameEn)
        assertTrue(aflou.nameAr.contains("آفلو"))

        val oran = AlgerianLocations.getWilaya(31)
        assertNotNull(oran)
        assertEquals("Oran", oran!!.nameEn)
        assertTrue(oran.nameAr.contains("وهران"))
    }

    @Test
    fun testBilingualLayoutDirection() {
        assertEquals(LayoutDirection.Rtl, AppStrings.getLayoutDirection("ar"))
        assertEquals(LayoutDirection.Ltr, AppStrings.getLayoutDirection("en"))
        assertTrue(AppStrings.isRtl("AR"))
        assertFalse(AppStrings.isRtl("en"))
    }

    @Test
    fun testCustomerRequestCreationAndQuoteWorkflow() {
        // Customer Karim creates request
        repository.switchUser(repository.customerUser)
        val request = repository.createServiceRequest(
            title = "Fix electrical short circuit in kitchen",
            description = "Breaker trips whenever refrigerator starts",
            categoryId = "cat_electrician",
            wilayaCode = 16,
            communeName = "Hydra",
            requiresInspection = true,
            preferredDateWindow = "Today afternoon",
            budgetDzd = 4000
        )

        assertEquals(RequestStatus.OPEN, request.status)
        assertEquals("usr_customer_karim", request.customerId)

        // Switch to Provider Amine to submit quote
        repository.switchUser(repository.providerUser)
        val quote = repository.sendQuote(
            requestId = request.id,
            quoteType = QuoteType.FIXED,
            amountDzd = 3500,
            scopeOfWork = "Trace earth leakage, replace faulty 16A breaker",
            includedMaterials = "Original Hager 16A breaker",
            excludedMaterials = "Internal appliance motor repairs",
            proposedSchedule = "Today at 16:00",
            validityDays = 2,
            notes = "Will arrive in Kouba/Hydra within 1 hour"
        )

        assertEquals(QuoteStatus.SENT, quote.status)
        assertEquals(3500, quote.amountDzd)

        // Request transitions to RECEIVING_QUOTES
        val updatedReq = repository.requests.value.find { it.id == request.id }
        assertEquals(RequestStatus.RECEIVING_QUOTES, updatedReq?.status)

        // Customer Karim accepts quote transactionally
        repository.switchUser(repository.customerUser)
        val appointment = repository.acceptQuote(quote.id)

        assertNotNull("Accepting quote must generate confirmed appointment", appointment)
        assertEquals(AppointmentStatus.CONFIRMED, appointment!!.status)
        assertEquals(3500, appointment.agreedAmountDzd)

        // Quote is marked ACCEPTED
        val acceptedQuote = repository.quotes.value.find { it.id == quote.id }
        assertEquals(QuoteStatus.ACCEPTED, acceptedQuote?.status)

        // Request status transitions to SCHEDULED
        val scheduledReq = repository.requests.value.find { it.id == request.id }
        assertEquals(RequestStatus.SCHEDULED, scheduledReq?.status)
    }

    @Test
    fun testVerifiedReviewEligibilityEnforcement() {
        repository.switchUser(repository.customerUser)
        val appointment = repository.appointments.value.first() // apt_301 (completed)

        // 1. Submitting review on already completed appointment succeeds
        val canReview = repository.submitReview(
            appointmentId = appointment.id,
            rating = 5,
            comment = "ممتاز جداً وسريع في الحضور."
        )
        // Since apt_301 has already hasReview = true in seed, it returns false (duplicate prevention!)
        assertFalse("Cannot submit duplicate review for already reviewed appointment", canReview)

        // Create a new fresh appointment that is not yet completed
        val newApt = repository.acceptQuote("qt_201")
        assertNotNull(newApt)

        // Cannot review an uncompleted appointment
        val reviewBeforeCompletion = repository.submitReview(newApt!!.id, 5, "Too early review")
        assertFalse("Cannot review uncompleted appointment", reviewBeforeCompletion)

        // Now complete the appointment
        repository.completeAppointment(newApt.id)
        val completedApt = repository.appointments.value.find { it.id == newApt.id }
        assertEquals(AppointmentStatus.COMPLETED, completedApt?.status)

        // Now review succeeds!
        val reviewAfterCompletion = repository.submitReview(
            appointmentId = newApt.id,
            rating = 5,
            comment = "عمل متقن جداً وأخلاق عالية!"
        )
        assertTrue("Eligible review succeeds after genuine completion", reviewAfterCompletion)
    }

    @Test
    fun testAdminVerificationAndAuditLog() {
        repository.switchUser(repository.adminUser)
        val prov = repository.providers.value.first()

        repository.adminDecideVerification(
            providerId = prov.id,
            newStatus = VerificationStatus.APPROVED,
            reviewerNotes = "National ID & Trade Diploma verified"
        )

        val updatedProv = repository.providers.value.find { it.id == prov.id }
        assertEquals(VerificationStatus.APPROVED, updatedProv?.verificationStatus)

        // Audit log records action
        val lastAudit = repository.auditLogs.value.first()
        assertEquals("ADMIN_VERIFICATION_DECISION", lastAudit.action)
        assertEquals(UserRole.ADMIN, lastAudit.actorRole)
    }
}
