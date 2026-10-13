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
    val allWilayas: List<Wilaya> = listOf(
        Wilaya(16, "Algiers", "الجزائر", listOf(
            Commune("16-01", "Bab El Oued", "باب الوادي"),
            Commune("16-02", "Hydra", "حيدرة"),
            Commune("16-03", "Kouba", "القبة"),
            Commune("16-04", "Sidi M'Hamed", "سيدي امحمد"),
            Commune("16-05", "Rouiba", "الرويبة"),
            Commune("16-06", "Zeralda", "زرالدة"),
            Commune("16-07", "Bir Mourad Raïs", "بئر مراد رايس"),
            Commune("16-08", "Hussein Dey", "حسين داي")
        )),
        Wilaya(31, "Oran", "وهران", listOf(
            Commune("31-01", "Oran City", "وهران المركز"),
            Commune("31-02", "Es Senia", "السانية"),
            Commune("31-03", "Bir El Djir", "بئر الجير"),
            Commune("31-04", "Aïn El Turk", "عين الترك"),
            Commune("31-05", "Arzew", "أرزيو")
        )),
        Wilaya(25, "Constantine", "قسنطينة", listOf(
            Commune("25-01", "Constantine City", "قسنطينة المركز"),
            Commune("25-02", "El Khroub", "الخروب"),
            Commune("25-03", "Hamma Bouziane", "حامة بوزيان"),
            Commune("25-04", "Ali Mendjeli", "علي منجلي")
        )),
        Wilaya(19, "Sétif", "سطيف", listOf(
            Commune("19-01", "Sétif City", "سطيف المركز"),
            Commune("19-02", "El Eulma", "العلمة"),
            Commune("19-03", "Aïn Arnat", "عين أرنات"),
            Commune("19-04", "Aïn Oulmene", "عين ولمان")
        )),
        Wilaya(9, "Blida", "البليدة", listOf(
            Commune("9-01", "Blida City", "البليدة المركز"),
            Commune("9-02", "Boufarik", "بوفاريك"),
            Commune("9-03", "Ouled Yaïch", "أولاد يعيش"),
            Commune("9-04", "Bougara", "بوقرة")
        )),
        Wilaya(23, "Annaba", "عنابة", listOf(
            Commune("23-01", "Annaba City", "عنابة المركز"),
            Commune("23-02", "El Bouni", "البوني"),
            Commune("23-03", "Sidi Amar", "سيدي عمار"),
            Commune("23-04", "Berrahal", "برحال")
        )),
        Wilaya(13, "Tlemcen", "تلمسان", listOf(
            Commune("13-01", "Tlemcen City", "تلمسان المركز"),
            Commune("13-02", "Mansourah", "المنصورة"),
            Commune("13-03", "Chetouane", "شتوان"),
            Commune("13-04", "Maghnia", "مغنية")
        )),
        Wilaya(5, "Batna", "باتنة", listOf(
            Commune("5-01", "Batna City", "باتنة المركز"),
            Commune("5-02", "Barika", "بريكة"),
            Commune("5-03", "Ain Touta", "عين التوتة")
        )),
        Wilaya(6, "Béjaïa", "بجاية", listOf(
            Commune("6-01", "Béjaïa City", "بجاية المركز"),
            Commune("6-02", "Akbou", "أقبو"),
            Commune("6-03", "Amizour", "أميزور")
        )),
        Wilaya(35, "Boumerdès", "بومرداس", listOf(
            Commune("35-01", "Boumerdès City", "بومرداس المركز"),
            Commune("35-02", "Khemis El Khechna", "خميس الخشنة"),
            Commune("35-03", "Boudouaou", "بودواو")
        )),
        Wilaya(42, "Tipaza", "تيبازة", listOf(
            Commune("42-01", "Tipaza City", "تيبازة المركز"),
            Commune("42-02", "Koléa", "القليعة"),
            Commune("42-03", "Cherchell", "شرشال")
        )),
        Wilaya(15, "Tizi Ouzou", "تيزي وزو", listOf(
            Commune("15-01", "Tizi Ouzou City", "تيزي وزو المركز"),
            Commune("15-02", "Azazga", "عزازقة"),
            Commune("15-03", "Draa Ben Khedda", "ذراع بن خدة")
        )),
        Wilaya(30, "Ouargla", "ورقلة", listOf(
            Commune("30-01", "Ouargla City", "ورقلة المركز"),
            Commune("30-02", "Hassi Messaoud", "حاسي مسعود"),
            Commune("30-03", "Rouissat", "الرويسات")
        )),
        Wilaya(7, "Biskra", "بسكرة", listOf(
            Commune("7-01", "Biskra City", "بسكرة المركز"),
            Commune("7-02", "Tolga", "طولقة"),
            Commune("7-03", "Sidi Okba", "سيدي عقبة")
        ))
    )

    fun getWilaya(code: Int): Wilaya? = allWilayas.find { it.code == code }
}
