package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.ServiceTaxonomy
import com.example.ui.components.KhidmatiBottomNav
import com.example.ui.components.KhidmatiTopBar
import com.example.ui.components.PersonaSwitcherDialog
import com.example.ui.i18n.AppStrings
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.AppointmentsScreen
import com.example.ui.screens.ConversationsScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProviderDashboardScreen
import com.example.ui.screens.ProviderDetailScreen
import com.example.ui.screens.RequestCreateScreen
import com.example.ui.screens.RequestDetailScreen
import com.example.ui.screens.RequestsListScreen
import com.example.ui.screens.ReviewCreateScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SendQuoteScreen
import com.example.ui.theme.KhidmatiTheme
import com.example.ui.viewmodel.AppDestination
import com.example.ui.viewmodel.KhidmatiViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KhidmatiApp()
        }
    }
}

@Composable
fun KhidmatiApp(
    viewModel: KhidmatiViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val currentLang by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val wilayas by viewModel.wilayas.collectAsStateWithLifecycle()
    val providers by viewModel.providers.collectAsStateWithLifecycle()
    val requests by viewModel.requests.collectAsStateWithLifecycle()
    val quotes by viewModel.quotes.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()
    val reviews by viewModel.reviews.collectAsStateWithLifecycle()
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val reports by viewModel.reports.collectAsStateWithLifecycle()
    val auditLogs by viewModel.auditLogs.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val layoutDir = AppStrings.getLayoutDirection(currentLang)

    LaunchedEffect(Unit) {
        viewModel.snackbarEvents.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // Back handling for sub-screens
    val isSubScreen = when (uiState.currentDestination) {
        AppDestination.PROVIDER_DETAIL,
        AppDestination.REQUEST_DETAIL,
        AppDestination.CREATE_REQUEST,
        AppDestination.SEND_QUOTE,
        AppDestination.WRITE_REVIEW,
        AppDestination.VERIFICATION_CENTER -> true
        else -> false
    }

    BackHandler(enabled = isSubScreen) {
        when (uiState.currentDestination) {
            AppDestination.SEND_QUOTE -> {
                uiState.selectedRequestId?.let { viewModel.openRequestDetail(it) } ?: viewModel.navigateTo(AppDestination.REQUESTS)
            }
            AppDestination.WRITE_REVIEW -> viewModel.navigateTo(AppDestination.APPOINTMENTS)
            else -> viewModel.navigateTo(AppDestination.HOME)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDir) {
        KhidmatiTheme {
            Scaffold(
                topBar = {
                    KhidmatiTopBar(
                        title = AppStrings.appName(currentLang),
                        currentLanguage = currentLang,
                        currentRole = currentUser.role,
                        userName = currentUser.name,
                        canNavigateBack = isSubScreen,
                        onNavigateBack = {
                            when (uiState.currentDestination) {
                                AppDestination.SEND_QUOTE -> {
                                    uiState.selectedRequestId?.let { viewModel.openRequestDetail(it) } ?: viewModel.navigateTo(AppDestination.REQUESTS)
                                }
                                AppDestination.WRITE_REVIEW -> viewModel.navigateTo(AppDestination.APPOINTMENTS)
                                else -> viewModel.navigateTo(AppDestination.HOME)
                            }
                        },
                        onToggleLanguage = { viewModel.toggleLanguage() },
                        onOpenRoleSwitcher = { viewModel.showPersonaSwitcher(true) }
                    )
                },
                bottomBar = {
                    KhidmatiBottomNav(
                        currentDestination = uiState.currentDestination,
                        currentRole = currentUser.role,
                        currentLanguage = currentLang,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                },
                snackbarHost = { SnackbarHost(snackbarHostState) },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (uiState.currentDestination) {
                        AppDestination.HOME -> {
                            HomeScreen(
                                currentLanguage = currentLang,
                                currentRole = currentUser.role,
                                categories = categories,
                                wilayas = wilayas,
                                providers = providers,
                                onSelectCategory = { catId ->
                                    viewModel.setCategoryFilter(catId)
                                    viewModel.navigateTo(AppDestination.SEARCH)
                                },
                                onSelectWilaya = { wCode ->
                                    viewModel.setWilayaFilter(wCode)
                                    viewModel.navigateTo(AppDestination.SEARCH)
                                },
                                onSearchClick = { viewModel.navigateTo(AppDestination.SEARCH) },
                                onCreateRequestClick = { viewModel.navigateTo(AppDestination.CREATE_REQUEST) },
                                onProviderClick = { viewModel.openProviderDetail(it) },
                                onJoinProviderClick = { viewModel.switchUserRole(com.example.data.model.UserRole.PROVIDER) }
                            )
                        }

                        AppDestination.SEARCH -> {
                            SearchScreen(
                                currentLanguage = currentLang,
                                providers = providers,
                                categories = categories,
                                wilayas = wilayas,
                                searchQuery = uiState.searchQuery,
                                selectedCategoryFilterId = uiState.selectedCategoryFilterId,
                                selectedWilayaFilterCode = uiState.selectedWilayaFilterCode,
                                showOnlyVerified = uiState.showOnlyVerified,
                                onQueryChange = { viewModel.setSearchQuery(it) },
                                onSelectCategoryFilter = { viewModel.setCategoryFilter(it) },
                                onSelectWilayaFilter = { viewModel.setWilayaFilter(it) },
                                onToggleOnlyVerified = { viewModel.toggleOnlyVerified(it) },
                                onResetFilters = { viewModel.resetFilters() },
                                onProviderClick = { viewModel.openProviderDetail(it) }
                            )
                        }

                        AppDestination.REQUESTS -> {
                            RequestsListScreen(
                                requests = requests,
                                categories = categories,
                                currentRole = currentUser.role,
                                currentLanguage = currentLang,
                                onCreateRequestClick = { viewModel.navigateTo(AppDestination.CREATE_REQUEST) },
                                onRequestClick = { viewModel.openRequestDetail(it) }
                            )
                        }

                        AppDestination.APPOINTMENTS -> {
                            AppointmentsScreen(
                                appointments = appointments,
                                currentLanguage = currentLang,
                                onCompleteAppointment = { viewModel.completeAppointment(it) },
                                onWriteReviewClick = { viewModel.openWriteReview(it) }
                            )
                        }

                        AppDestination.MESSAGES -> {
                            ConversationsScreen(
                                conversations = conversations,
                                messages = messages,
                                currentUser = currentUser,
                                currentLanguage = currentLang,
                                onSendMessage = { convId, text -> viewModel.sendMessage(convId, text) }
                            )
                        }

                        AppDestination.FAVORITES -> {
                            FavoritesScreen(
                                providers = providers,
                                currentLanguage = currentLang,
                                onProviderClick = { viewModel.openProviderDetail(it) },
                                onExploreClick = { viewModel.navigateTo(AppDestination.SEARCH) }
                            )
                        }

                        AppDestination.PROVIDER_DASHBOARD -> {
                            val prov = providers.find { it.userId == currentUser.id } ?: providers.first()
                            ProviderDashboardScreen(
                                provider = prov,
                                requests = requests,
                                quotes = quotes,
                                appointments = appointments,
                                currentLanguage = currentLang,
                                onSendQuoteClick = { viewModel.openSendQuote(it) },
                                onRequestClick = { viewModel.openRequestDetail(it) },
                                onSubmitVerificationClick = { viewModel.submitVerification(prov.id) }
                            )
                        }

                        AppDestination.ADMIN_CENTER -> {
                            AdminDashboardScreen(
                                providers = providers,
                                reports = reports,
                                auditLogs = auditLogs,
                                currentLanguage = currentLang,
                                onAdminDecideVerification = { pId, status, notes ->
                                    viewModel.adminDecideVerification(pId, status, notes)
                                },
                                onAdminResolveReport = { rId, status, notes ->
                                    viewModel.adminResolveReport(rId, status, notes)
                                }
                            )
                        }

                        AppDestination.CREATE_REQUEST -> {
                            RequestCreateScreen(
                                currentLanguage = currentLang,
                                categories = categories,
                                wilayas = wilayas,
                                preselectedCategoryId = uiState.selectedCategoryFilterId,
                                preselectedWilayaCode = uiState.selectedWilayaFilterCode,
                                onSubmitRequest = { title, desc, catId, wCode, commune, inspect, date, budget ->
                                    viewModel.createRequest(title, desc, catId, wCode, commune, inspect, date, budget)
                                }
                            )
                        }

                        AppDestination.PROVIDER_DETAIL -> {
                            val provider = providers.find { it.id == uiState.selectedProviderId } ?: providers.first()
                            ProviderDetailScreen(
                                provider = provider,
                                reviews = reviews,
                                currentLanguage = currentLang,
                                onToggleFavorite = { viewModel.toggleFavorite(provider.id) },
                                onRequestQuoteClick = {
                                    viewModel.setCategoryFilter(provider.primaryCategoryId)
                                    viewModel.setWilayaFilter(provider.wilayaCode)
                                    viewModel.navigateTo(AppDestination.CREATE_REQUEST)
                                },
                                onReportSubmit = { reason, details ->
                                    viewModel.submitReport("PROVIDER", provider.id, provider.displayName, reason, details)
                                }
                            )
                        }

                        AppDestination.REQUEST_DETAIL -> {
                            val request = requests.find { it.id == uiState.selectedRequestId } ?: requests.first()
                            val category = ServiceTaxonomy.getCategory(request.categoryId)
                            RequestDetailScreen(
                                request = request,
                                category = category,
                                quotes = quotes,
                                currentRole = currentUser.role,
                                currentLanguage = currentLang,
                                onAcceptQuote = { viewModel.acceptQuote(it) },
                                onCancelRequest = { reqId, reason -> viewModel.cancelRequest(reqId, reason) },
                                onSendQuoteClick = { viewModel.openSendQuote(request.id) },
                                onOpenProviderDetail = { viewModel.openProviderDetail(it) }
                            )
                        }

                        AppDestination.SEND_QUOTE -> {
                            val request = requests.find { it.id == uiState.selectedRequestId } ?: requests.first()
                            SendQuoteScreen(
                                request = request,
                                currentLanguage = currentLang,
                                onSubmitQuote = { rId, qType, amt, scope, inc, exc, sched, valid, notes ->
                                    viewModel.submitQuote(rId, qType, amt, scope, inc, exc, sched, valid, notes)
                                }
                            )
                        }

                        AppDestination.WRITE_REVIEW -> {
                            val apt = appointments.find { it.id == uiState.selectedAppointmentId } ?: appointments.first()
                            ReviewCreateScreen(
                                appointment = apt,
                                currentLanguage = currentLang,
                                onSubmitReview = { aptId, rating, comment ->
                                    viewModel.submitReview(aptId, rating, comment)
                                }
                            )
                        }

                        else -> {}
                    }
                }
            }

            if (uiState.showPersonaSwitcher) {
                PersonaSwitcherDialog(
                    currentRole = currentUser.role,
                    currentLanguage = currentLang,
                    onDismiss = { viewModel.showPersonaSwitcher(false) },
                    onSelectRole = { viewModel.switchUserRole(it) }
                )
            }
        }
    }
}
