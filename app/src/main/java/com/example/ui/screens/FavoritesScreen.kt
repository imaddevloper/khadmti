package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.ProviderProfile
import com.example.ui.components.EmptyStateView
import com.example.ui.i18n.AppStrings

@Composable
fun FavoritesScreen(
    providers: List<ProviderProfile>,
    currentLanguage: String,
    onProviderClick: (String) -> Unit,
    onExploreClick: () -> Unit
) {
    val favoriteProviders = providers.filter { it.isFavorite }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("favorites_screen"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = if (currentLanguage == "ar") "الحرفيون المفضلون" else "Saved Professionals",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = if (currentLanguage == "ar") "الوصول السريع للمهنيين الذين قمت بحفظهم للرجوع إليهم." else "Quick access to professionals you saved for later.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (favoriteProviders.isEmpty()) {
            item {
                EmptyStateView(
                    icon = Icons.Default.BookmarkBorder,
                    title = if (currentLanguage == "ar") "لا يوجد حرفيون في المفضلة" else "No saved professionals",
                    description = if (currentLanguage == "ar")
                        "اضغط على أيقونة الحفظ في صفحة أي حرفي لإضافته إلى قائمتك المفضلة."
                    else
                        "Tap the bookmark icon on any provider's profile to save them here.",
                    actionButtonText = AppStrings.btnFindService(currentLanguage),
                    onActionClick = onExploreClick
                )
            }
        } else {
            items(favoriteProviders) { prov ->
                ProviderSummaryCard(
                    provider = prov,
                    currentLanguage = currentLanguage,
                    onClick = { onProviderClick(prov.id) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
