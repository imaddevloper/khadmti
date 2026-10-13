package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserAccount
import com.example.data.model.Wilaya
import com.example.ui.theme.KhidmatiPrimary
import com.example.ui.theme.KhidmatiPrimarySurface
import com.example.ui.theme.KhidmatiRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    currentUser: UserAccount,
    wilayas: List<Wilaya>,
    currentLanguage: String,
    onSaveProfile: (name: String, phone: String, emoji: String, wilayaCode: Int) -> Unit
) {
    val isArabic = currentLanguage == "ar"

    var name by remember { mutableStateOf(currentUser.name) }
    var phone by remember { mutableStateOf(currentUser.emailOrPhone) }
    var selectedEmoji by remember { mutableStateOf(currentUser.avatarEmoji) }
    var selectedWilaya by remember {
        mutableStateOf(wilayas.find { it.code == currentUser.wilayaCode } ?: wilayas.first())
    }
    var wilayaExpanded by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val avatarOptions = listOf("👤", "👨‍🔧", "👩‍🔧", "⚡", "🪚", "🎨", "🔧", "🚗", "🏠", "⭐", "💼")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("edit_profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = if (isArabic) "تعديل الملف الشخصي" else "Edit Personal Profile",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = if (isArabic) "حدّث اسمك، رقم هاتفك، صورتك الرمزية وولايتك" else "Update your display name, phone, avatar & Wilaya",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Avatar Selection
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(KhidmatiPrimarySurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = selectedEmoji, fontSize = 38.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isArabic) "اختر صورتك أو شارتك الرمزية" else "Choose your profile avatar",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(avatarOptions) { emoji ->
                            val isSelected = emoji == selectedEmoji
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) KhidmatiPrimarySurface else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .clickable { selectedEmoji = emoji }
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = emoji, fontSize = 22.sp)
                            }
                        }
                    }
                }
            }
        }

        // Form Fields
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                            errorMessage = null
                        },
                        label = { Text(if (isArabic) "الاسم الكامل *" else "Full Name *") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = KhidmatiPrimary) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_edit_name")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Phone
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            errorMessage = null
                        },
                        label = { Text(if (isArabic) "رقم الهاتف للتواصل *" else "Phone Number *") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = KhidmatiPrimary) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_edit_phone")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Wilaya Dropdown (69 Wilayas)
                    ExposedDropdownMenuBox(
                        expanded = wilayaExpanded,
                        onExpandedChange = { wilayaExpanded = !wilayaExpanded }
                    ) {
                        OutlinedTextField(
                            value = if (isArabic) selectedWilaya.nameAr else "${selectedWilaya.code} - ${selectedWilaya.nameEn}",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(if (isArabic) "الولاية (من بين 69 ولاية)" else "Wilaya (from 69 Wilayas)") },
                            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, tint = KhidmatiPrimary) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = wilayaExpanded) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                                .testTag("dropdown_edit_wilaya")
                        )
                        ExposedDropdownMenu(
                            expanded = wilayaExpanded,
                            onDismissRequest = { wilayaExpanded = false }
                        ) {
                            wilayas.forEach { w ->
                                DropdownMenuItem(
                                    text = { Text(text = if (isArabic) w.nameAr else "${w.code} - ${w.nameEn}") },
                                    onClick = {
                                        selectedWilaya = w
                                        wilayaExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (errorMessage != null) {
            item {
                Text(
                    text = errorMessage!!,
                    color = KhidmatiRed,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        // Save Button
        item {
            Button(
                onClick = {
                    if (name.isBlank()) {
                        errorMessage = if (isArabic) "يرجى كتابة الاسم" else "Please enter your name"
                        return@Button
                    }
                    if (phone.isBlank()) {
                        errorMessage = if (isArabic) "يرجى كتابة رقم الهاتف" else "Please enter your phone number"
                        return@Button
                    }
                    onSaveProfile(name.trim(), phone.trim(), selectedEmoji, selectedWilaya.code)
                },
                colors = ButtonDefaults.buttonColors(containerColor = KhidmatiPrimary),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("btn_save_profile")
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = if (isArabic) "حفظ التعديلات" else "Save Changes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
    }
}
