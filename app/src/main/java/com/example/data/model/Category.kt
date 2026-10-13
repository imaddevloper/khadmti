package com.example.data.model

data class ServiceCategory(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val iconName: String,
    val descriptionEn: String,
    val descriptionAr: String,
    val popularTags: List<String>
)

object ServiceTaxonomy {
    val categories: List<ServiceCategory> = listOf(
        ServiceCategory(
            id = "cat_electrician",
            nameEn = "Electrician",
            nameAr = "كهربائي",
            iconName = "Bolt",
            descriptionEn = "Wiring, breaker panel, short circuits, outlets & home lighting installations.",
            descriptionAr = "تمديدات كهربائية، إصلاح الدوائر، تركيب الإضاءة والقواطع المنزلية.",
            popularTags = listOf("Short circuit", "Breaker panel", "Lighting", "220V/380V")
        ),
        ServiceCategory(
            id = "cat_plumber",
            nameEn = "Plumbing",
            nameAr = "ترصيص صحي وسباكة",
            iconName = "WaterDrop",
            descriptionEn = "Water leaks, water heater (chauffe-bain), drain unclogging & bathroom fixtures.",
            descriptionAr = "إصلاح تسربات المياه، سخان الماء، تسليك المجاري وتركيب التجهيزات الصحية.",
            popularTags = listOf("Water leak", "Chauffe-bain", "Clogged drain", "Sanitary")
        ),
        ServiceCategory(
            id = "cat_appliance",
            nameEn = "Appliance Repair",
            nameAr = "تصليح الأجهزة الكهرومنزلية",
            iconName = "HomeRepairService",
            descriptionEn = "Washing machines, refrigerators, microwave, oven & stove diagnostics.",
            descriptionAr = "تصليح الغسالات، الثلاجات، أفران الطهي وأجهزة المطبخ المختلفة.",
            popularTags = listOf("Washing machine", "Refrigerator", "Oven", "Diagnostic")
        ),
        ServiceCategory(
            id = "cat_hvac",
            nameEn = "Air Conditioning & HVAC",
            nameAr = "تكييف وتبريد",
            iconName = "AcUnit",
            descriptionEn = "AC installation, gas recharge (R410/R22/R32), seasonal cleaning & maintenance.",
            descriptionAr = "تركيب وصيانة مكيفات الهواء، شحن غاز التبريد والتنظيف الدوري.",
            popularTags = listOf("AC install", "Gas recharge", "Filter cleaning", "Climatiseur")
        ),
        ServiceCategory(
            id = "cat_painter",
            nameEn = "Painting & Plastering",
            nameAr = "دهان وديكور",
            iconName = "FormatPaint",
            descriptionEn = "Interior/exterior wall painting, plaster finishing, moisture treatment & decorative coats.",
            descriptionAr = "طلاء الجدران الداخلي والخارجي، معالجة الرطوبة والجبس والديكورات الحديثة.",
            popularTags = listOf("Interior paint", "Plaster / BA13", "Moisture repair", "Façade")
        ),
        ServiceCategory(
            id = "cat_carpenter",
            nameEn = "Carpentry & Aluminum",
            nameAr = "نجارة وألمنيوم",
            iconName = "Handyman",
            descriptionEn = "Custom closets, kitchen cabinets, wooden/PVC doors & aluminum window fittings.",
            descriptionAr = "تفصيل الخزائن، مطابخ الألمنيوم، الأبواب والنوافذ والستائر الخشبية.",
            popularTags = listOf("PVC / Aluminum", "Custom closet", "Door repair", "Kitchen")
        ),
        ServiceCategory(
            id = "cat_mechanic",
            nameEn = "Auto Mechanic & Towing",
            nameAr = "ميكانيك وسيارات",
            iconName = "DirectionsCar",
            descriptionEn = "Roadside assistance, oil change, brake pads, diagnostic scanner & engine tune-up.",
            descriptionAr = "ميكانيك سيارات، فحص بالسكانير، فرامل، وتدخلات الإنقاذ السريع.",
            popularTags = listOf("Scanner OBD", "Brakes", "Oil change", "Road assistance")
        ),
        ServiceCategory(
            id = "cat_locksmith",
            nameEn = "Locksmith & Security",
            nameAr = "أقفال وأمان",
            iconName = "VpnKey",
            descriptionEn = "Door lockout emergencies, cylinder changes, armored lock upgrades & intercoms.",
            descriptionAr = "فتح الأبواب العالقة، تغيير الأقفال، تقوية الأمان وتركيب أجهزة الإنترفون.",
            popularTags = listOf("Emergency unlock", "Cylinder swap", "Deadbolt", "Intercom")
        )
    )

    fun getCategory(id: String): ServiceCategory? = categories.find { it.id == id }
}
