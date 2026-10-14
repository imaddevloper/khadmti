import React, { useMemo, useState } from 'react';
import {
  Alert, Pressable, SafeAreaView, ScrollView, StatusBar, StyleSheet,
  Switch, Text, TextInput, View,
} from 'react-native';

type Language = 'ar' | 'en';
type Appearance = 'system' | 'light' | 'dark';
type Screen =
  | 'splash' | 'login' | 'signup' | 'home' | 'explore' | 'category' | 'professional'
  | 'createRequest' | 'requests' | 'requestDetails' | 'appointments' | 'provider'
  | 'profile' | 'settings' | 'notifications' | 'help' | 'about' | 'forgotPassword';

const categories = [
  { id: 'plumbing', icon: '🔧', en: 'Plumbing', ar: 'السباكة' },
  { id: 'electric', icon: '⚡', en: 'Electrical', ar: 'الكهرباء' },
  { id: 'cleaning', icon: '🧹', en: 'Cleaning', ar: 'التنظيف' },
  { id: 'painting', icon: '🖌️', en: 'Painting', ar: 'الطلاء' },
  { id: 'repair', icon: '🛠️', en: 'Appliance repair', ar: 'تصليح الأجهزة' },
  { id: 'maintenance', icon: '🏠', en: 'Home maintenance', ar: 'صيانة المنزل' },
];

const professionals = [
  { id: '1', name: 'Karim B.', category: 'plumbing', serviceEn: 'Plumbing specialist', serviceAr: 'مختص في السباكة', cityEn: 'Tlemcen', cityAr: 'تلمسان', rating: '4.8', price: '2,000–5,000 DZD', initials: 'KB', bioEn: 'Home plumbing, leak repair and fixture installation.', bioAr: 'أعمال السباكة المنزلية وإصلاح التسربات وتركيب التجهيزات.' },
  { id: '2', name: 'Nadia M.', category: 'cleaning', serviceEn: 'Home cleaning', serviceAr: 'تنظيف المنازل', cityEn: 'Oran', cityAr: 'وهران', rating: '4.9', price: '1,500–3,500 DZD', initials: 'NM', bioEn: 'Careful, detailed home cleaning for everyday needs.', bioAr: 'تنظيف منزلي دقيق ومنظم للاحتياجات اليومية.' },
  { id: '3', name: 'Yacine R.', category: 'electric', serviceEn: 'Electrical technician', serviceAr: 'تقني كهرباء', cityEn: 'Tlemcen', cityAr: 'تلمسان', rating: '4.7', price: '2,000–6,000 DZD', initials: 'YR', bioEn: 'Electrical troubleshooting and safe home installations.', bioAr: 'تشخيص أعطال الكهرباء وتركيب التجهيزات المنزلية بأمان.' },
  { id: '4', name: 'Amine S.', category: 'painting', serviceEn: 'Painter', serviceAr: 'دهان', cityEn: 'Sidi Bel Abbès', cityAr: 'سيدي بلعباس', rating: '4.6', price: 'By estimate', initials: 'AS', bioEn: 'Interior painting, wall preparation and finishing.', bioAr: 'طلاء داخلي وتحضير الجدران وأعمال التشطيب.' },
];

const copy = {
  en: {
    home: 'Home', explore: 'Explore', requests: 'Requests', appointments: 'Appointments', profile: 'Profile',
    greeting: 'What can we help you with?', subtitle: 'Find trusted local professionals for your home.',
    search: 'Search services or professionals', categories: 'Browse categories', popular: 'Recommended near you',
    seeAll: 'See all', demo: 'DEMO DATA', location: 'Tlemcen, Algeria', notifications: 'Notifications',
    settings: 'Settings', language: 'Language', appearance: 'Appearance', system: 'System', light: 'Light', dark: 'Dark',
    about: 'About Mihnati', help: 'Help & support', provider: 'Become a professional', create: 'Create a request',
    requestService: 'Request service', contact: 'Contact', save: 'Save', cancel: 'Cancel', back: 'Back',
    noResults: 'No results found. Try another search.', all: 'All', rating: 'Rating', city: 'Wilaya / city',
    details: 'Details', offers: 'Sample offers', appointmentsTitle: 'Your appointments', requestsTitle: 'Your requests',
    profileTitle: 'Your profile', signIn: 'Sign in', signUp: 'Create account', emailPhone: 'Email or phone',
    password: 'Password', name: 'Full name', confirmPassword: 'Confirm password', forgot: 'Forgot password?',
    browse: 'Continue browsing', welcome: 'Welcome to Mihnati', demoLogin: 'Demo sign-in only — no account is verified.',
    title: 'Request title', description: 'Describe what you need', serviceType: 'Service type', date: 'Preferred date',
    address: 'Wilaya / commune', submit: 'Preview request', pending: 'Pending', offersReceived: 'Offers received',
    accepted: 'Accepted', completed: 'Completed', draft: 'Draft', upcoming: 'Upcoming', previous: 'Previous',
    profileSetup: 'Professional profile setup', bio: 'Short biography', offered: 'Services offered',
    notificationsToggle: 'Notifications', locationPref: 'Location preference', reset: 'Reset demo data',
    resetConfirm: 'Reset local demo changes?', resetDone: 'Demo data reset.', read: 'Mark as read',
    supportText: 'This prototype has no connected support service. Contact details will be added in a future release.',
    aboutText: 'Mihnati helps people in Algeria discover local services and connect with professionals. This version is a UI prototype.',
    localOnly: 'Prototype only', emptyAppointments: 'No appointments to show yet.', emptyRequests: 'Your sample requests will appear here.',
    send: 'Continue', price: 'Indicative price', reviews: 'sample reviews', chooseOffer: 'Choose offer',
  },
  ar: {
    home: 'الرئيسية', explore: 'استكشف', requests: 'طلباتي', appointments: 'المواعيد', profile: 'حسابي',
    greeting: 'كيف يمكننا مساعدتك؟', subtitle: 'اعثر على مهنيين محليين لخدمات منزلك.',
    search: 'ابحث عن خدمة أو مهني', categories: 'تصفح الخدمات', popular: 'مهنيون مقترحون',
    seeAll: 'عرض الكل', demo: 'بيانات تجريبية', location: 'تلمسان، الجزائر', notifications: 'الإشعارات',
    settings: 'الإعدادات', language: 'اللغة', appearance: 'المظهر', system: 'حسب النظام', light: 'فاتح', dark: 'داكن',
    about: 'حول مهنتي', help: 'المساعدة والدعم', provider: 'انضم كمهني', create: 'إنشاء طلب خدمة',
    requestService: 'اطلب الخدمة', contact: 'اتصال', save: 'حفظ', cancel: 'إلغاء', back: 'رجوع',
    noResults: 'لا توجد نتائج. جرّب البحث بكلمات أخرى.', all: 'الكل', rating: 'التقييم', city: 'الولاية / المدينة',
    details: 'التفاصيل', offers: 'عروض تجريبية', appointmentsTitle: 'مواعيدك', requestsTitle: 'طلباتك',
    profileTitle: 'ملفك الشخصي', signIn: 'تسجيل الدخول', signUp: 'إنشاء حساب', emailPhone: 'البريد أو رقم الهاتف',
    password: 'كلمة المرور', name: 'الاسم الكامل', confirmPassword: 'تأكيد كلمة المرور', forgot: 'نسيت كلمة المرور؟',
    browse: 'المتابعة دون تسجيل', welcome: 'مرحبًا بك في مهنتي', demoLogin: 'تسجيل تجريبي فقط — لن يتم التحقق من أي حساب.',
    title: 'عنوان الطلب', description: 'اشرح ما تحتاج إليه', serviceType: 'نوع الخدمة', date: 'التاريخ المناسب',
    address: 'الولاية / البلدية', submit: 'معاينة الطلب', pending: 'قيد الانتظار', offersReceived: 'عروض مستلمة',
    accepted: 'مقبول', completed: 'مكتمل', draft: 'مسودة', upcoming: 'القادمة', previous: 'السابقة',
    profileSetup: 'إعداد الملف المهني', bio: 'نبذة قصيرة', offered: 'الخدمات المقدمة',
    notificationsToggle: 'الإشعارات', locationPref: 'تفضيل الموقع', reset: 'إعادة ضبط البيانات التجريبية',
    resetConfirm: 'هل تريد إعادة ضبط التغييرات المحلية؟', resetDone: 'تمت إعادة ضبط البيانات التجريبية.', read: 'تحديد كمقروء',
    supportText: 'هذه النسخة لا تتصل بخدمة دعم فعلية. ستضاف معلومات التواصل في إصدار لاحق.',
    aboutText: 'يساعدك تطبيق مهنتي على اكتشاف الخدمات المحلية والتواصل مع المهنيين في الجزائر. هذه نسخة تجريبية للواجهة.',
    localOnly: 'نسخة تجريبية فقط', emptyAppointments: 'لا توجد مواعيد لعرضها حاليًا.', emptyRequests: 'ستظهر طلباتك التجريبية هنا.',
    send: 'متابعة', price: 'السعر التقريبي', reviews: 'تقييمات تجريبية', chooseOffer: 'اختيار العرض',
  },
};

export default function App() {
  const [language, setLanguage] = useState<Language>('ar');
  const [appearance, setAppearance] = useState<Appearance>('system');
  const [screen, setScreen] = useState<Screen>('splash');
  const [history, setHistory] = useState<Screen[]>([]);
  const [selectedCategory, setSelectedCategory] = useState('plumbing');
  const [selectedProfessional, setSelectedProfessional] = useState(professionals[0]);
  const [query, setQuery] = useState('');
  const [notificationsOn, setNotificationsOn] = useState(true);
  const [readNotifications, setReadNotifications] = useState<string[]>([]);
  const [requestTitle, setRequestTitle] = useState('');
  const [requestDescription, setRequestDescription] = useState('');
  const [requestCity, setRequestCity] = useState('');
  const [providerName, setProviderName] = useState('');
  const [providerBio, setProviderBio] = useState('');
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [darkOverride, setDarkOverride] = useState(false);
  const t = copy[language];
  const isRTL = language === 'ar';
  const dark = appearance === 'dark' || (appearance === 'system' && darkOverride);
  const c = dark ? palette.dark : palette.light;
  const direction = isRTL ? 'rtl' : 'ltr';

  const navigate = (next: Screen) => {
    setHistory((old) => [...old, screen]);
    setScreen(next);
  };
  const goBack = () => {
    const previous = history[history.length - 1];
    setScreen(previous ?? 'home');
    setHistory((old) => old.slice(0, -1));
  };
  const tab = (next: Screen) => { setHistory([]); setScreen(next); };
  const visibleProfessionals = useMemo(() => professionals.filter((p) => {
    const haystack = [p.name, p.serviceEn, p.serviceAr, p.cityEn, p.cityAr].join(' ').toLowerCase();
    return haystack.includes(query.toLowerCase());
  }), [query]);

  React.useEffect(() => {
    const timer = setTimeout(() => setScreen((current) => current === 'splash' ? 'home' : current), 850);
    return () => clearTimeout(timer);
  }, []);

  const label = (text: string, size: number = 14, weight: '400' | '500' | '600' | '700' = '400', color: string = c.text) =>
    <Text style={{ color, fontSize: size, fontWeight: weight, textAlign: isRTL ? 'right' : 'left', writingDirection: direction }}>{text}</Text>;
  const button = (text: string, onPress: () => void, secondary = false) => (
    <Pressable onPress={onPress} style={({ pressed }) => [styles.button, { backgroundColor: secondary ? c.card : c.primary, borderColor: secondary ? c.border : c.primary, opacity: pressed ? 0.78 : 1 }]}>
      <Text style={{ color: secondary ? c.text : '#FFFFFF', fontWeight: '700', textAlign: 'center', writingDirection: direction }}>{text}</Text>
    </Pressable>
  );
  const field = (placeholder: string, value: string, onChangeText: (text: string) => void, multiline = false) => (
    <TextInput
      accessibilityLabel={placeholder}
      placeholder={placeholder}
      placeholderTextColor={c.muted}
      value={value}
      onChangeText={onChangeText}
      multiline={multiline}
      secureTextEntry={placeholder === t.password && !passwordVisible}
      textAlign={isRTL ? 'right' : 'left'}
      style={[styles.input, { backgroundColor: c.input, color: c.text, borderColor: c.border, writingDirection: direction }, multiline && { minHeight: 100, textAlignVertical: 'top' }]}
    />
  );
  const card = (children: React.ReactNode) => <View style={[styles.card, { backgroundColor: c.card, borderColor: c.border }]}>{children}</View>;
  const heading = (title: string, subtitle?: string) => <View style={{ marginBottom: 18, gap: 5 }}>{label(title, 24, '700')}{subtitle ? label(subtitle, 14, '400', c.muted) : null}</View>;
  const topBar = (title: string) => <View style={[styles.topBar, { flexDirection: isRTL ? 'row-reverse' : 'row' }]}><Pressable onPress={goBack} style={styles.back}><Text style={{ color: c.primary, fontSize: 15, fontWeight: '700' }}>‹ {t.back}</Text></Pressable><View style={{ flex: 1 }}>{label(title, 17, '700')}</View></View>;
  const professionalCard = (p: typeof professionals[number]) => (
    <Pressable key={p.id} onPress={() => { setSelectedProfessional(p); navigate('professional'); }} style={[styles.proCard, { backgroundColor: c.card, borderColor: c.border, flexDirection: isRTL ? 'row-reverse' : 'row' }]}>
      <View style={[styles.avatar, { backgroundColor: c.tint }]}><Text style={{ color: c.primary, fontWeight: '700', fontSize: 16 }}>{p.initials}</Text></View>
      <View style={{ flex: 1, gap: 4 }}>
        {label(p.name, 15, '700')}
        {label(isRTL ? p.serviceAr : p.serviceEn, 13, '400', c.muted)}
        {label('★ ' + p.rating + '  ·  ' + (isRTL ? p.cityAr : p.cityEn), 12, '600', c.primary)}
        {label(t.demo, 10, '500', c.muted)}
      </View>
      <Text style={{ color: c.muted, fontSize: 20 }}>{isRTL ? '‹' : '›'}</Text>
    </Pressable>
  );

  const renderScreen = () => {
    if (screen === 'splash') return <View style={[styles.splash, { backgroundColor: c.bg }]}><View style={[styles.logo, { backgroundColor: c.primary }]}><Text style={{ color: '#fff', fontSize: 34 }}>م</Text></View>{label('مهنتي', 32, '700', c.primary)}{label('Mihnati', 16, '600')}{label(t.localOnly, 12, '400', c.muted)}</View>;

    if (screen === 'home') return <>
      <View style={[styles.homeHeader, { flexDirection: isRTL ? 'row-reverse' : 'row' }]}>
        <View style={{ flex: 1, gap: 5 }}>{label('مهنتي  ·  Mihnati', 18, '700', c.primary)}{label(t.location, 12, '500', c.muted)}</View>
        <Pressable onPress={() => navigate('notifications')} style={[styles.iconButton, { backgroundColor: c.card, borderColor: c.border }]}><Text style={{ fontSize: 18 }}>♧</Text></Pressable>
        <Pressable onPress={() => navigate('settings')} style={[styles.iconButton, { backgroundColor: c.card, borderColor: c.border }]}><Text style={{ fontSize: 18 }}>⚙</Text></Pressable>
      </View>
      <View style={{ marginTop: 20, marginBottom: 14 }}>{label(t.greeting, 26, '700')}{label(t.subtitle, 14, '400', c.muted)}</View>
      {field(t.search, query, setQuery)}
      <View style={styles.sectionHead}>{label(t.categories, 18, '700')}{button(t.seeAll, () => navigate('explore'), true)}</View>
      <View style={styles.categoryGrid}>{categories.map((cat) => <Pressable key={cat.id} onPress={() => { setSelectedCategory(cat.id); navigate('category'); }} style={[styles.category, { backgroundColor: c.card, borderColor: c.border }]}><Text style={{ fontSize: 24 }}>{cat.icon}</Text>{label(isRTL ? cat.ar : cat.en, 12, '600')}</Pressable>)}</View>
      <View style={styles.sectionHead}>{label(t.popular, 18, '700')}{button(t.seeAll, () => navigate('explore'), true)}</View>
      {visibleProfessionals.slice(0, 3).map(professionalCard)}
      {button(t.create, () => navigate('createRequest'))}
      {button(t.provider, () => navigate('provider'), true)}
    </>;

    if (screen === 'explore' || screen === 'category') {
      const currentCat = categories.find((cat) => cat.id === selectedCategory);
      const list = (screen === 'category' ? visibleProfessionals.filter((p) => p.category === selectedCategory) : visibleProfessionals);
      return <>{topBar(screen === 'category' ? (isRTL ? currentCat?.ar ?? '' : currentCat?.en ?? '') : t.explore)}
        {heading(screen === 'category' ? (isRTL ? currentCat?.ar ?? '' : currentCat?.en ?? '') : t.explore, t.demo)}
        {field(t.search, query, setQuery)}
        <View style={styles.chips}>{categories.map((cat) => <Pressable key={cat.id} onPress={() => { setSelectedCategory(cat.id); if (screen !== 'category') setScreen('category'); }} style={[styles.chip, { borderColor: selectedCategory === cat.id ? c.primary : c.border, backgroundColor: selectedCategory === cat.id ? c.tint : c.card }]}>{label(isRTL ? cat.ar : cat.en, 12, '600')}</Pressable>)}</View>
        {list.length ? list.map(professionalCard) : <View style={styles.empty}>{label(t.noResults, 15, '600', c.muted)}{button(t.all, () => setQuery(''), true)}</View>}
      </>;
    }

    if (screen === 'professional') return <>{topBar(selectedProfessional.name)}
      <View style={[styles.profileHero, { backgroundColor: c.tint }]}><View style={[styles.avatar, styles.avatarLarge, { backgroundColor: c.card }]}><Text style={{ color: c.primary, fontSize: 24, fontWeight: '700' }}>{selectedProfessional.initials}</Text></View>{label(selectedProfessional.name, 22, '700')}{label(isRTL ? selectedProfessional.serviceAr : selectedProfessional.serviceEn, 14, '500')}{label('★ ' + selectedProfessional.rating + ' · ' + (isRTL ? selectedProfessional.cityAr : selectedProfessional.cityEn), 13, '600', c.primary)}{label(t.demo, 11, '400', c.muted)}</View>
      {heading(t.details)}{card(<>{label(isRTL ? selectedProfessional.bioAr : selectedProfessional.bioEn, 14)}<View style={{ height: 12 }}/>{label(t.price + ': ' + selectedProfessional.price, 14, '600')}{label(t.reviews, 11, '400', c.muted)}</>)}
      {button(t.requestService, () => { setRequestTitle((isRTL ? selectedProfessional.serviceAr : selectedProfessional.serviceEn)); navigate('createRequest'); })}
      {button(t.contact, () => Alert.alert(t.localOnly, t.demoLogin), true)}
    </>;

    if (screen === 'login' || screen === 'signup' || screen === 'forgotPassword') return <>{topBar(screen === 'signup' ? t.signUp : screen === 'forgotPassword' ? t.forgot : t.signIn)}
      {heading(screen === 'signup' ? t.signUp : screen === 'forgotPassword' ? t.forgot : t.welcome, t.demoLogin)}
      {screen === 'signup' ? field(t.name, '', () => {}) : null}
      {field(t.emailPhone, '', () => {})}
      {screen !== 'forgotPassword' ? field(t.password, '', () => {}) : null}
      {screen === 'signup' ? field(t.confirmPassword, '', () => {}) : null}
      {screen !== 'forgotPassword' ? <Pressable onPress={() => setPasswordVisible(!passwordVisible)}>{label(passwordVisible ? '◉' : '◎', 13, '600', c.primary)}</Pressable> : null}
      {button(screen === 'signup' ? t.signUp : screen === 'forgotPassword' ? t.send : t.signIn, () => Alert.alert(t.localOnly, t.demoLogin))}
      {button(screen === 'login' ? t.signUp : t.signIn, () => navigate(screen === 'login' ? 'signup' : 'login'), true)}
      {button(t.browse, () => tab('home'), true)}
    </>;

    if (screen === 'createRequest') return <>{topBar(t.create)}{heading(t.create, t.localOnly)}
      {label(t.serviceType, 14, '600')}{<View style={styles.chips}>{categories.map((cat) => <Pressable key={cat.id} onPress={() => setSelectedCategory(cat.id)} style={[styles.chip, { backgroundColor: selectedCategory === cat.id ? c.tint : c.card, borderColor: selectedCategory === cat.id ? c.primary : c.border }]}>{label(isRTL ? cat.ar : cat.en, 12, '600')}</Pressable>)}</View>}
      {field(t.title, requestTitle, setRequestTitle)}{field(t.description, requestDescription, setRequestDescription, true)}{field(t.address, requestCity, setRequestCity)}{field(t.date, '', () => {})}
      {button(t.submit, () => { if (!requestTitle.trim() || !requestDescription.trim() || !requestCity.trim()) { Alert.alert(t.create, isRTL ? 'يرجى ملء الحقول المطلوبة.' : 'Please complete the required fields.'); return; } Alert.alert(t.localOnly, isRTL ? 'تم إعداد معاينة محلية فقط؛ لم يتم إرسال الطلب.' : 'Local preview created only; nothing was submitted to a server.'); navigate('requests'); })}
    </>;

    if (screen === 'requests') return <>{heading(t.requestsTitle, t.demo)}{button(t.create, () => navigate('createRequest'))}
      {[{title: isRTL ? 'إصلاح صنبور المطبخ' : 'Kitchen tap repair', status: t.offersReceived, date: '12 Oct 2026'}, {title: isRTL ? 'صيانة كهربائية' : 'Electrical maintenance', status: t.pending, date: '15 Oct 2026'}, {title: isRTL ? 'طلاء غرفة' : 'Room painting', status: t.draft, date: '—'}].map((r) => <Pressable key={r.title} onPress={() => navigate('requestDetails')} style={{ marginBottom: 12 }}>{card(<>{label(r.title, 16, '700')}{label(r.date, 12, '400', c.muted)}<View style={[styles.status, { backgroundColor: c.tint }]}>{label(r.status, 12, '600', c.primary)}</View>{label(t.demo, 10, '400', c.muted)}</>)}</Pressable>)}
    </>;

    if (screen === 'requestDetails') return <>{topBar(t.details)}{heading(t.offers, t.demo)}
      {card(<>{label(isRTL ? 'إصلاح صنبور المطبخ' : 'Kitchen tap repair', 17, '700')}{label(isRTL ? 'تسرب ماء أسفل الحوض' : 'Water leak below the sink', 14)}{label('Tlemcen · ' + t.pending, 12, '400', c.muted)}</>)}
      {professionals.slice(0, 3).map((p, i) => <View key={p.id} style={{ marginBottom: 10 }}>{card(<>{label(p.name, 15, '700')}{label((2000 + i * 750).toLocaleString() + ' DZD', 17, '700', c.primary)}{label(isRTL ? 'المدة التقديرية: ساعة إلى ساعتين' : 'Estimated duration: 1–2 hours', 12, '400', c.muted)}{label(t.demo, 10, '400', c.muted)}{button(t.chooseOffer, () => Alert.alert(t.localOnly, isRTL ? 'تم اختيار العرض محليًا فقط.' : 'Offer selected locally only.'))}</>)}</View>)}
    </>;

    if (screen === 'appointments') return <>{heading(t.appointmentsTitle, t.demo)}
      {card(<>{label(isRTL ? 'زيارة صيانة منزلية' : 'Home maintenance visit', 16, '700')}{label('Karim B. · 18 Oct 2026 · 10:30', 13)}{label(t.upcoming, 12, '600', c.primary)}{label(t.demo, 10, '400', c.muted)}</>)}
      {card(<>{label(isRTL ? 'تنظيف المنزل' : 'Home cleaning', 16, '700')}{label('Nadia M. · 02 Oct 2026 · 14:00', 13)}{label(t.previous, 12, '600', c.muted)}{label(t.demo, 10, '400', c.muted)}</>)}
    </>;

    if (screen === 'provider') return <>{topBar(t.provider)}{heading(t.profileSetup, t.localOnly)}
      {label(isRTL ? 'عرّف العملاء بخبرتك والخدمات التي تقدمها.' : 'Introduce your skills and the services you provide.', 14, '400', c.muted)}
      {field(t.name, providerName, setProviderName)}{field(t.bio, providerBio, setProviderBio, true)}
      {label(t.serviceType, 14, '600')}{<View style={styles.chips}>{categories.map((cat) => <Pressable key={cat.id} onPress={() => setSelectedCategory(cat.id)} style={[styles.chip, { backgroundColor: selectedCategory === cat.id ? c.tint : c.card, borderColor: selectedCategory === cat.id ? c.primary : c.border }]}>{label(isRTL ? cat.ar : cat.en, 12, '600')}</Pressable>)}</View>}
      {field(t.address, requestCity, setRequestCity)}
      {button(t.save, () => { if (!providerName.trim() || !providerBio.trim()) { Alert.alert(t.provider, isRTL ? 'أدخل الاسم والنبذة.' : 'Enter your name and a short bio.'); return; } Alert.alert(t.localOnly, isRTL ? 'تم حفظ المعاينة محليًا فقط.' : 'Profile preview saved locally only.'); })}
    </>;

    if (screen === 'profile') return <>{heading(t.profileTitle, t.demo)}
      <View style={[styles.profileHero, { backgroundColor: c.tint }]}><View style={[styles.avatar, styles.avatarLarge, { backgroundColor: c.card }]}><Text style={{ color: c.primary, fontSize: 24, fontWeight: '700' }}>MH</Text></View>{label(isRTL ? 'مستخدم تجريبي' : 'Demo user', 20, '700')}{label(t.localOnly, 12, '400', c.muted)}</View>
      {button(t.login, () => navigate('login'), true)}{button(t.settings, () => navigate('settings'), true)}{button(t.provider, () => navigate('provider'), true)}{button(t.notifications, () => navigate('notifications'), true)}{button(t.help, () => navigate('help'), true)}{button(t.about, () => navigate('about'), true)}{button(isRTL ? 'تسجيل الخروج التجريبي' : 'Demo sign out', () => Alert.alert(t.localOnly, t.demoLogin), true)}
    </>;

    if (screen === 'settings') return <>{topBar(t.settings)}{heading(t.settings, t.localOnly)}
      {card(<>{label(t.language, 15, '700')}<View style={styles.chips}>{button('العربية', () => setLanguage('ar'), language !== 'ar')}{button('English', () => setLanguage('en'), language !== 'en')}</View></>)}
      {card(<>{label(t.appearance, 15, '700')}<View style={styles.chips}>{(['system', 'light', 'dark'] as Appearance[]).map((mode) => <Pressable key={mode} onPress={() => setAppearance(mode)} style={[styles.chip, { borderColor: appearance === mode ? c.primary : c.border, backgroundColor: appearance === mode ? c.tint : c.card }]}>{label(mode === 'system' ? t.system : mode === 'light' ? t.light : t.dark, 12, '600')}</Pressable>)}</View></>)}
      {card(<View style={[styles.settingRow, { flexDirection: isRTL ? 'row-reverse' : 'row' }]}>{label(t.notificationsToggle, 14, '600')}<Switch value={notificationsOn} onValueChange={setNotificationsOn} trackColor={{ true: c.primary }} /></View>)}
      {card(<>{label(t.locationPref, 14, '600')}{label(t.location + ' · ' + t.localOnly, 12, '400', c.muted)}</>)}
      {button(t.reset, () => Alert.alert(t.reset, t.resetConfirm, [{ text: t.cancel, style: 'cancel' }, { text: t.save, onPress: () => { setQuery(''); setReadNotifications([]); setRequestTitle(''); setRequestDescription(''); Alert.alert(t.resetDone); } }]), true)}
      {button(t.help, () => navigate('help'), true)}{button(t.about, () => navigate('about'), true)}
    </>;

    if (screen === 'notifications') return <>{topBar(t.notifications)}{heading(t.notifications, t.demo)}
      {[{id:'n1',title: isRTL ? 'عرض جديد على طلبك' : 'A new offer on your request', body: isRTL ? 'أرسل أحد المهنيين عرضًا تجريبيًا.' : 'A professional sent a sample offer.'}, {id:'n2',title: isRTL ? 'تذكير بموعد' : 'Appointment reminder',body: isRTL ? 'لديك موعد تجريبي قريبًا.' : 'You have a sample appointment coming up.'}].map((n) => <View key={n.id} style={{ marginBottom: 10 }}>{card(<>{label(n.title, 15, '700')}{label(n.body, 13, '400', c.muted)}{readNotifications.includes(n.id) ? label(isRTL ? 'مقروء' : 'Read', 11, '500', c.muted) : button(t.read, () => setReadNotifications((old) => [...old, n.id]), true)}</>)}</View>)}
    </>;

    if (screen === 'help' || screen === 'about') return <>{topBar(screen === 'help' ? t.help : t.about)}{heading(screen === 'help' ? t.help : t.about, 'Mihnati · v1.0.0')}{card(<>{label(screen === 'help' ? t.supportText : t.aboutText, 15)}<View style={{ height: 12 }}/>{label(t.localOnly, 12, '600', c.primary)}</>)}</>;

    return <>{topBar(t.profile)}{heading(t.profileTitle)}{button(t.home, () => tab('home'))}</>;
  };

  if (screen === 'splash') return <SafeAreaView style={[styles.safe, { backgroundColor: c.bg }]}><StatusBar barStyle={dark ? 'light-content' : 'dark-content'} />{renderScreen()}</SafeAreaView>;

  const tabs: { key: Screen; title: string; icon: string }[] = [
    { key: 'home', title: t.home, icon: '⌂' }, { key: 'explore', title: t.explore, icon: '⌕' },
    { key: 'requests', title: t.requests, icon: '▤' }, { key: 'appointments', title: t.appointments, icon: '▦' },
    { key: 'profile', title: t.profile, icon: '◉' },
  ];
  const showTabs = ['home','explore','requests','appointments','profile'].includes(screen);
  return (
    <SafeAreaView style={[styles.safe, { backgroundColor: c.bg }]}>
      <StatusBar barStyle={dark ? 'light-content' : 'dark-content'} backgroundColor={c.bg} />
      <View style={[styles.app, { backgroundColor: c.bg }]}>
        <ScrollView contentContainerStyle={[styles.content, { direction }]} keyboardShouldPersistTaps="handled">
          {renderScreen()}
          <View style={{ height: 18 }} />
        </ScrollView>
        {showTabs ? <View style={[styles.tabBar, { backgroundColor: c.card, borderColor: c.border, flexDirection: isRTL ? 'row-reverse' : 'row' }]}>
          {tabs.map((item) => <Pressable key={item.key} onPress={() => tab(item.key)} style={styles.tabItem}>
            <Text style={{ fontSize: 19, color: screen === item.key ? c.primary : c.muted }}>{item.icon}</Text>
            <Text numberOfLines={1} style={{ fontSize: 10, color: screen === item.key ? c.primary : c.muted, fontWeight: screen === item.key ? '700' : '500', writingDirection: direction }}>{item.title}</Text>
          </Pressable>)}
        </View> : null}
      </View>
    </SafeAreaView>
  );
}

const palette = {
  light: { bg: '#F5F7F2', card: '#FFFFFF', input: '#FFFFFF', text: '#1D2922', muted: '#68756D', border: '#E0E7DF', primary: '#21845A', tint: '#E4F3E9' },
  dark: { bg: '#151C18', card: '#202A24', input: '#202A24', text: '#F0F5F1', muted: '#A6B4AA', border: '#35443A', primary: '#62C992', tint: '#263F31' },
};

const styles = StyleSheet.create({
  safe: { flex: 1 },
  app: { flex: 1 },
  content: { paddingHorizontal: 18, paddingTop: 12, paddingBottom: 10, gap: 12 },
  splash: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 12 },
  logo: { width: 82, height: 82, borderRadius: 25, alignItems: 'center', justifyContent: 'center', marginBottom: 4 },
  homeHeader: { alignItems: 'center', gap: 8 },
  iconButton: { width: 42, height: 42, borderWidth: 1, borderRadius: 14, alignItems: 'center', justifyContent: 'center' },
  input: { borderWidth: 1, borderRadius: 13, paddingHorizontal: 14, paddingVertical: 13, fontSize: 14, minHeight: 48 },
  button: { borderWidth: 1, borderRadius: 13, paddingHorizontal: 14, paddingVertical: 12, alignItems: 'center', justifyContent: 'center', minHeight: 44, marginVertical: 3 },
  card: { borderWidth: 1, borderRadius: 16, padding: 15, gap: 8, marginBottom: 8 },
  proCard: { borderWidth: 1, borderRadius: 16, padding: 13, gap: 12, alignItems: 'center', marginBottom: 10 },
  avatar: { width: 54, height: 54, borderRadius: 17, alignItems: 'center', justifyContent: 'center' },
  avatarLarge: { width: 76, height: 76, borderRadius: 24 },
  categoryGrid: { flexDirection: 'row', flexWrap: 'wrap', gap: 10 },
  category: { width: '31%', minHeight: 98, borderRadius: 15, borderWidth: 1, alignItems: 'center', justifyContent: 'center', gap: 8, padding: 8 },
  sectionHead: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', marginTop: 12, marginBottom: 4 },
  topBar: { alignItems: 'center', gap: 12, marginBottom: 10, minHeight: 42 },
  back: { paddingVertical: 6, paddingRight: 4 },
  chips: { flexDirection: 'row', flexWrap: 'wrap', gap: 8, marginVertical: 7 },
  chip: { borderWidth: 1, borderRadius: 999, paddingHorizontal: 12, paddingVertical: 9 },
  profileHero: { borderRadius: 20, padding: 20, alignItems: 'center', gap: 8, marginBottom: 16 },
  empty: { alignItems: 'center', padding: 24, gap: 12 },
  status: { alignSelf: 'flex-start', borderRadius: 8, paddingHorizontal: 10, paddingVertical: 5 },
  settingRow: { alignItems: 'center', justifyContent: 'space-between' },
  tabBar: { minHeight: 64, borderTopWidth: 1, paddingHorizontal: 4, paddingTop: 8, paddingBottom: 4 },
  tabItem: { flex: 1, alignItems: 'center', justifyContent: 'center', gap: 3, paddingHorizontal: 2 },
});
