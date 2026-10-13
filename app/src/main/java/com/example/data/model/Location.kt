package com.example.data.model

data class Wilaya(
    val code: Int,
    val nameEn: String,
    val nameAr: String,
    val communes: List<Commune>
)

data class Commune(
    val id: String,
    val nameEn: String,
    val nameAr: String
)

object AlgerianLocations {
    // All 69 Wilayas of Algeria (58 historical wilayas + 11 delegated/promoted administrative wilayas)
    val allWilayas: List<Wilaya> = listOf(
        Wilaya(1, "Adrar", "01 - أدرار", listOf(Commune("01-01", "Adrar", "أدرار"), Commune("01-02", "Reggane", "رقان"), Commune("01-03", "Tsabit", "تسابيت"))),
        Wilaya(2, "Chlef", "02 - الشلف", listOf(Commune("02-01", "Chlef", "الشلف"), Commune("02-02", "Ténès", "تنس"), Commune("02-03", "Oued Fodda", "وادي الفضة"))),
        Wilaya(3, "Laghouat", "03 - الأغواط", listOf(Commune("03-01", "Laghouat", "الأغواط"), Commune("03-02", "Bellil", "بليل"), Commune("03-03", "Ksar El Hirane", "قصر الحيران"))),
        Wilaya(4, "Oum El Bouaghi", "04 - أم البواقي", listOf(Commune("04-01", "Oum El Bouaghi", "أم البواقي"), Commune("04-02", "Aïn Beïda", "عين البيضاء"), Commune("04-03", "Aïn M'lila", "عين مليلة"))),
        Wilaya(5, "Batna", "05 - باتنة", listOf(Commune("05-01", "Batna City", "باتنة المركز"), Commune("05-02", "Ain Touta", "عين التوتة"), Commune("05-03", "Tazoult", "تازولت"))),
        Wilaya(6, "Béjaïa", "06 - بجاية", listOf(Commune("06-01", "Béjaïa", "بجاية"), Commune("06-02", "Akbou", "أقبو"), Commune("06-03", "Amizour", "أميزور"), Commune("06-04", "El Kseur", "القصر"))),
        Wilaya(7, "Biskra", "07 - بسكرة", listOf(Commune("07-01", "Biskra", "بسكرة"), Commune("07-02", "Tolga", "طولقة"), Commune("07-03", "Sidi Okba", "سيدي عقبة"))),
        Wilaya(8, "Béchar", "08 - بشار", listOf(Commune("08-01", "Béchar", "بشار"), Commune("08-02", "Kenadsa", "القنادسة"), Commune("08-03", "Taghit", "تاغيت"))),
        Wilaya(9, "Blida", "09 - البليدة", listOf(Commune("09-01", "Blida City", "البليدة المركز"), Commune("09-02", "Boufarik", "بوفاريك"), Commune("09-03", "Ouled Yaïch", "أولاد يعيش"), Commune("09-04", "Bougara", "بوقرة"))),
        Wilaya(10, "Bouira", "10 - البويرة", listOf(Commune("10-01", "Bouira", "البويرة"), Commune("10-02", "Lakhdaria", "الأخضرية"), Commune("10-03", "Sour El Ghozlane", "سور الغزلان"))),
        Wilaya(11, "Tamanrasset", "11 - تمنراست", listOf(Commune("11-01", "Tamanrasset", "تمنراست"), Commune("11-02", "Abalessa", "أباليسا"))),
        Wilaya(12, "Tébessa", "12 - تبسة", listOf(Commune("12-01", "Tébessa", "تبسة"), Commune("12-02", "Cheria", "الشريعة"), Commune("12-03", "El Kouif", "الكويف"))),
        Wilaya(13, "Tlemcen", "13 - تلمسان", listOf(Commune("13-01", "Tlemcen City", "تلمسان المركز"), Commune("13-02", "Mansourah", "المنصورة"), Commune("13-03", "Maghnia", "مغنية"), Commune("13-04", "Remchi", "الرمشي"))),
        Wilaya(14, "Tiaret", "14 - تيارت", listOf(Commune("14-01", "Tiaret", "تيارت"), Commune("14-02", "Sougueur", "السوقر"), Commune("14-03", "Frenda", "فرندة"))),
        Wilaya(15, "Tizi Ouzou", "15 - تيزي وزو", listOf(Commune("15-01", "Tizi Ouzou", "تيزي وزو"), Commune("15-02", "Azazga", "عزازقة"), Commune("15-03", "Draa Ben Khedda", "ذراع بن خدة"), Commune("15-04", "Boghni", "بوغني"))),
        Wilaya(16, "Algiers", "16 - الجزائر", listOf(Commune("16-01", "Bab El Oued", "باب الوادي"), Commune("16-02", "Hydra", "حيدرة"), Commune("16-03", "Kouba", "القبة"), Commune("16-04", "Sidi M'Hamed", "سيدي امحمد"), Commune("16-05", "Rouiba", "الرويبة"), Commune("16-06", "Zeralda", "زرالدة"), Commune("16-07", "Bir Mourad Raïs", "بئر مراد رايس"), Commune("16-08", "Hussein Dey", "حسين داي"))),
        Wilaya(17, "Djelfa", "17 - الجلفة", listOf(Commune("17-01", "Djelfa", "الجلفة"), Commune("17-02", "Hassi Bahbah", "حاسي بحبح"), Commune("17-03", "Dar Chioukh", "دار الشيوخ"))),
        Wilaya(18, "Jijel", "18 - جيجل", listOf(Commune("18-01", "Jijel", "جيجل"), Commune("18-02", "Taher", "الطاهير"), Commune("18-03", "El Milia", "الميلية"))),
        Wilaya(19, "Sétif", "19 - سطيف", listOf(Commune("19-01", "Sétif City", "سطيف المركز"), Commune("19-02", "Ain Arnat", "عين أرنات"), Commune("19-03", "Ain Oulmene", "عين ولمان"), Commune("19-04", "Bougaa", "بوقاعة"))),
        Wilaya(20, "Saïda", "20 - سعيدة", listOf(Commune("20-01", "Saïda", "سعيدة"), Commune("20-02", "Youb", "يوب"), Commune("20-03", "Ain El Hadjar", "عين الحجر"))),
        Wilaya(21, "Skikda", "21 - سكيكدة", listOf(Commune("21-01", "Skikda", "سكيكدة"), Commune("21-02", "El Harrouch", "الحروش"), Commune("21-03", "Collo", "القل"), Commune("21-04", "Azzaba", "عزابة"))),
        Wilaya(22, "Sidi Bel Abbès", "22 - سيدي بلعباس", listOf(Commune("22-01", "Sidi Bel Abbès", "سيدي بلعباس"), Commune("22-02", "Sfisef", "سفيزف"), Commune("22-03", "Telagh", "تلاغ"))),
        Wilaya(23, "Annaba", "23 - عنابة", listOf(Commune("23-01", "Annaba City", "عنابة المركز"), Commune("23-02", "El Bouni", "البوني"), Commune("23-03", "Sidi Amar", "سيدي عمار"), Commune("23-04", "Berrahal", "برحال"))),
        Wilaya(24, "Guelma", "24 - قالمة", listOf(Commune("24-01", "Guelma", "قالمة"), Commune("24-02", "Bouchegouf", "بوشقوف"), Commune("24-03", "Oued Zenati", "وادي الزناتي"))),
        Wilaya(25, "Constantine", "25 - قسنطينة", listOf(Commune("25-01", "Constantine City", "قسنطينة المركز"), Commune("25-02", "El Khroub", "الخروب"), Commune("25-03", "Hamma Bouziane", "حامة بوزيان"), Commune("25-04", "Ali Mendjeli", "علي منجلي"))),
        Wilaya(26, "Médéa", "26 - المدية", listOf(Commune("26-01", "Médéa", "المدية"), Commune("26-02", "Berrouaghia", "البرواقية"), Commune("26-03", "Tablat", "تابلاط"))),
        Wilaya(27, "Mostaganem", "27 - مستغانم", listOf(Commune("27-01", "Mostaganem", "مستغانم"), Commune("27-02", "Ain Tedles", "عين تادلس"), Commune("27-03", "Sidi Ali", "سيدي علي"))),
        Wilaya(28, "M'Sila", "28 - المسيلة", listOf(Commune("28-01", "M'Sila", "المسيلة"), Commune("28-02", "Sidi Aissa", "سيدي عيسى"), Commune("28-03", "Magra", "مقرة"))),
        Wilaya(29, "Mascara", "29 - معسكر", listOf(Commune("29-01", "Mascara", "معسكر"), Commune("29-02", "Sig", "سيق"), Commune("29-03", "Tighennif", "تيغنيف"))),
        Wilaya(30, "Ouargla", "30 - ورقلة", listOf(Commune("30-01", "Ouargla", "ورقلة"), Commune("30-02", "Hassi Messaoud", "حاسي مسعود"), Commune("30-03", "Rouissat", "الرويسات"))),
        Wilaya(31, "Oran", "31 - وهران", listOf(Commune("31-01", "Oran City", "وهران المركز"), Commune("31-02", "Es Senia", "السانية"), Commune("31-03", "Bir El Djir", "بئر الجير"), Commune("31-04", "Aïn El Turk", "عين الترك"), Commune("31-05", "Arzew", "أرزيو"))),
        Wilaya(32, "El Bayadh", "32 - البيض", listOf(Commune("32-01", "El Bayadh", "البيض"), Commune("32-02", "Labiodh Sidi Cheikh", "الأبيض سيدي الشيخ"), Commune("32-03", "Brezina", "بريزينة"))),
        Wilaya(33, "Illizi", "33 - إليزي", listOf(Commune("33-01", "Illizi", "إليزي"), Commune("33-02", "In Amenas", "إن أمناس"))),
        Wilaya(34, "Bordj Bou Arréridj", "34 - برج بوعريريج", listOf(Commune("34-01", "BBA", "برج بوعريريج"), Commune("34-02", "Ras El Oued", "رأس الوادي"), Commune("34-03", "Bordj Zemoura", "برج زمورة"))),
        Wilaya(35, "Boumerdès", "35 - بومرداس", listOf(Commune("35-01", "Boumerdès", "بومرداس"), Commune("35-02", "Khemis El Khechna", "خميس الخشنة"), Commune("35-03", "Boudouaou", "بودواو"), Commune("35-04", "Dellys", "دلس"))),
        Wilaya(36, "El Tarf", "36 - الطارف", listOf(Commune("36-01", "El Tarf", "الطارف"), Commune("36-02", "El Kala", "القالة"), Commune("36-03", "Ben M'Hidi", "ابن مهيدي"))),
        Wilaya(37, "Tindouf", "37 - تندوف", listOf(Commune("37-01", "Tindouf", "تندوف"), Commune("37-02", "Oum El Assel", "أم العسل"))),
        Wilaya(38, "Tissemsilt", "38 - تيسمسيلت", listOf(Commune("38-01", "Tissemsilt", "تيسمسيلت"), Commune("38-02", "Theniet El Had", "ثنية الحد"), Commune("38-03", "Bordj Bounaama", "برج بونعامة"))),
        Wilaya(39, "El Oued", "39 - الوادي", listOf(Commune("39-01", "El Oued", "الوادي"), Commune("39-02", "Guemar", "قمار"), Commune("39-03", "Robbah", "رباح"))),
        Wilaya(40, "Khenchela", "40 - خنشلة", listOf(Commune("40-01", "Khenchela", "خنشلة"), Commune("40-02", "Chechar", "ششار"), Commune("40-03", "Kais", "قايس"))),
        Wilaya(41, "Souk Ahras", "41 - سوق أهراس", listOf(Commune("41-01", "Souk Ahras", "سوق أهراس"), Commune("41-02", "Sedrata", "سدراتة"), Commune("41-03", "M'Daourouch", "مداوروش"))),
        Wilaya(42, "Tipaza", "42 - تيبازة", listOf(Commune("42-01", "Tipaza City", "تيبازة المركز"), Commune("42-02", "Koléa", "القليعة"), Commune("42-03", "Cherchell", "شرشال"), Commune("42-04", "Bou Ismail", "بواسماعيل"))),
        Wilaya(43, "Mila", "43 - ميلة", listOf(Commune("43-01", "Mila", "ميلة"), Commune("43-02", "Tadjenanet", "تاجنانت"), Commune("43-03", "Ferdjioua", "فرجيوة"))),
        Wilaya(44, "Aïn Defla", "44 - عين الدفلى", listOf(Commune("44-01", "Aïn Defla", "عين الدفلى"), Commune("44-02", "Khemis Miliana", "خميس مليانة"), Commune("44-03", "Miliana", "مليانة"))),
        Wilaya(45, "Naâma", "45 - النعامة", listOf(Commune("45-01", "Naâma", "النعامة"), Commune("45-02", "Mécheria", "مشرية"), Commune("45-03", "Aïn Séfra", "عين الصفراء"))),
        Wilaya(46, "Aïn Témouchent", "46 - عين تموشنت", listOf(Commune("46-01", "Aïn Témouchent", "عين تموشنت"), Commune("46-02", "Hammam Bou Hadjar", "حمام بوحجر"), Commune("46-03", "Beni Saf", "بني صاف"))),
        Wilaya(47, "Ghardaïa", "47 - غرداية", listOf(Commune("47-01", "Ghardaïa", "غرداية"), Commune("47-02", "Metlili", "متليلي"), Commune("47-03", "El Guerrara", "القرارة"), Commune("47-04", "Béni Isguen", "بني يزقن"))),
        Wilaya(48, "Relizane", "48 - غليزان", listOf(Commune("48-01", "Relizane", "غليزان"), Commune("48-02", "Oued Rhiou", "وادي ارهيو"), Commune("48-03", "Mazouna", "مازونة"))),
        Wilaya(49, "Timimoun", "49 - تيميمون", listOf(Commune("49-01", "Timimoun", "تيميمون"), Commune("49-02", "Aougrout", "أوقروت"))),
        Wilaya(50, "Bordj Badji Mokhtar", "50 - برج باجي مختار", listOf(Commune("50-01", "Bordj Badji Mokhtar", "برج باجي مختار"), Commune("50-02", "Timiaouine", "تيمياوين"))),
        Wilaya(51, "Ouled Djellal", "51 - أولاد جلال", listOf(Commune("51-01", "Ouled Djellal", "أولاد جلال"), Commune("51-02", "Sidi Khaled", "سيدي خالد"))),
        Wilaya(52, "Béni Abbès", "52 - بني عباس", listOf(Commune("52-01", "Béni Abbès", "بني عباس"), Commune("52-02", "Kerzaz", "كرزاز"), Commune("52-03", "Igli", "إقلي"))),
        Wilaya(53, "In Salah", "53 - عين صالح", listOf(Commune("53-01", "In Salah", "عين صالح"), Commune("53-02", "In Ghar", "إن غار"))),
        Wilaya(54, "In Guezzam", "54 - عين قزام", listOf(Commune("54-01", "In Guezzam", "عين قزام"), Commune("54-02", "Tin Zaouatine", "تين زواتين"))),
        Wilaya(55, "Touggourt", "55 - تقرت", listOf(Commune("55-01", "Touggourt", "تقرت"), Commune("55-02", "Temacine", "تماسين"), Commune("55-03", "Megarine", "المقارين"))),
        Wilaya(56, "Djanet", "56 - جانت", listOf(Commune("56-01", "Djanet", "جانت"), Commune("56-02", "Bordj El Haouas", "برج الحواس"))),
        Wilaya(57, "El M'Ghair", "57 - المغير", listOf(Commune("57-01", "El M'Ghair", "المغير"), Commune("57-02", "Djamaa", "جامعة"))),
        Wilaya(58, "El Meniaa", "58 - المنيعة", listOf(Commune("58-01", "El Meniaa", "المنيعة"), Commune("58-02", "Hassi Gara", "حاسي القارة"))),
        // Delegated and promoted administrative wilayas completing the 69 Wilayas of Algeria
        Wilaya(59, "Barika", "59 - بريكة", listOf(Commune("59-01", "Barika", "بريكة"), Commune("59-02", "Bitam", "بيطام"), Commune("59-03", "M'doukel", "أمدوكال"))),
        Wilaya(60, "El Kantara", "60 - القنطرة", listOf(Commune("60-01", "El Kantara", "القنطرة"), Commune("60-02", "Ain Zaatout", "عين زعطوط"))),
        Wilaya(61, "Bou Saâda", "61 - بوسعادة", listOf(Commune("61-01", "Bou Saada", "بوسعادة"), Commune("61-02", "El Hamel", "الهامل"), Commune("61-03", "Oultene", "ولتام"))),
        Wilaya(62, "Bir El Ater", "62 - بئر العاتر", listOf(Commune("62-01", "Bir El Ater", "بئر العاتر"), Commune("62-02", "El Ogla", "العقلة"))),
        Wilaya(63, "Ksar Chellala", "63 - قصر الشلالة", listOf(Commune("63-01", "Ksar Chellala", "قصر الشلالة"), Commune("63-02", "Serghine", "سرغين"))),
        Wilaya(64, "Aïn Oussera", "64 - عين وسارة", listOf(Commune("64-01", "Ain Oussera", "عين وسارة"), Commune("64-02", "Guernini", "القرنيني"))),
        Wilaya(65, "Messaad", "65 - مسعد", listOf(Commune("65-01", "Messaad", "مسعد"), Commune("65-02", "Deldoul", "دلدول"))),
        Wilaya(66, "Ksar El Boukhari", "66 - قصر البخاري", listOf(Commune("66-01", "Ksar El Boukhari", "قصر البخاري"), Commune("66-02", "Saneg", "السانق"))),
        Wilaya(67, "El Eulma", "67 - العلمة", listOf(Commune("67-01", "El Eulma", "العلمة"), Commune("67-02", "Bazer Sakhra", "بازر سكرة"), Commune("67-03", "Guelta Zerka", "القلتة الزرقاء"))),
        Wilaya(68, "Chelghoum Laïd", "68 - شلغوم العيد", listOf(Commune("68-01", "Chelghoum Laid", "شلغوم العيد"), Commune("68-02", "Teleghma", "تلاغمة"))),
        Wilaya(69, "Aflou", "69 - آفلو", listOf(Commune("69-01", "Aflou", "آفلو"), Commune("69-02", "Sebgag", "سبقاق"), Commune("69-03", "Sidi Bouzid", "سيدي بوزيد")))
    )

    fun getWilaya(code: Int): Wilaya? = allWilayas.find { it.code == code }
}
