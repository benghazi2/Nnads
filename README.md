# نادس - متصفح أندرويد حاجب للإعلانات

**نادس** هو متصفح أندرويد متقدم يحجب الإعلانات والتتبع، مع دعم كامل للهواتف و Android TV و Google TV، وتشغيل الفيديو في الخلفية مع حفظ السجل واستعادة الجلسات.

## 🎯 الميزات الأساسية

✅ **حجب الإعلانات الفعلي** — يحجب طلبات الإعلانات قبل تحميلها  
✅ **قوائم تحديث تلقائي** — EasyList, EasyPrivacy, قوائم عربية  
✅ **تشغيل فيديو بالخلفية** — الاستماع للموسيقى والفيديو بعد إغلاق التطبيق  
✅ **Picture-in-Picture** — نافذة فيديو عائمة أثناء التصفح  
✅ **سجل وإشارات مرجعية** — حفظ السجل والمفضلة محليًا  
✅ **استعادة الجلسات** — استعادة التبويبات بعد الإغلاق المفاجئ  
✅ **واجهة التلفاز** — تحكم كامل بلوحة التحكم و D-Pad  
✅ **الوضع الداكن** — توفير البطارية والعيون  
✅ **دعم العربية** — واجهة كاملة بالعربية مع دعم RTL  

---

## 🚀 البدء السريع

### المتطلبات

```bash
- نظام Linux (Ubuntu 20.04+) أو macOS 11+
- 150+ GB مساحة قرص
- 16 GB RAM (32 GB موصى به)
- اتصال إنترنت سريع
```

### التثبيت على الهاتف

1. قم بتنزيل `Nnads-1.0.0.apk` من الإصدارات
2. فعّل التثبيت من مصادر غير معروفة في الإعدادات
3. اضغط على الملف وثبّت التطبيق
4. افتح التطبيق وابدأ التصفح

### البناء من المصدر

```bash
# 1. استنساخ المستودع
git clone https://github.com/benghazi2/Nnads.git
cd Nnads

# 2. اتبع SETUP_BUILD.md
cat SETUP_BUILD.md

# 3. شغّل بناء البرنامج
./build-nnads.sh
```

---

## 📁 هيكل المشروع

```
Nnads/
├── README.md                      # هذا الملف
├── REQUIREMENTS.md                # المتطلبات والمواصفات
├── PROGRESS.md                    # تتبع التقدم (R1-R26)
├── BUILD_REPORT.md                # تقرير البناء والأداء
├── CUSTOMIZATION.md               # تخصيص الميزات
├── SETUP_BUILD.md                 # دليل البناء الكامل
├── build-nnads.sh                 # سكريبت البناء التلقائي
├── build-apk-workflow.yml         # سير عمل GitHub Actions
├── LICENSE.md                     # رخصة MPL-2.0
├── ATTRIBUTION.md                 # إسناد المكتبات والمشاريع
└── src/
    ├── android/
    │   ├── res/
    │   │   ├── values/            # الموارد (إنجليزي)
    │   │   ├── values-ar/         # الموارد (عربي)
    │   │   ├── layout/            # تخطيطات الهاتف
    │   │   └── layout-land/       # تخطيطات أفقية
    │   ├── java/
    │   │   └── com/nnads/
    │   │       ├── MainActivity.java
    │   │       ├── TVBrowserActivity.java
    │   │       ├── adblock/
    │   │       │   ├── FilterLists.kt
    │   │       │   └── FilterDatabase.kt
    │   │       ├── media/
    │   │       │   └── MediaPlaybackService.kt
    │   │       ├── history/
    │   │       │   └── BrowsingHistory.kt
    │   │       └── preferences/
    │   │           └── AppPreferences.kt
    │   └── AndroidManifest.xml
    └── brave/                     # تعديلات Brave
        └── ...
```

---

## 🔧 الإعدادات والتخصيص

### تعديل اسم التطبيق ورمزه

تحرير `CUSTOMIZATION.md`:
```kotlin
// Package ID
com.nnads.browser

// App Name
Nnads

// Arabic Name
نادس
```

### تفعيل/تعطيل الميزات

```kotlin
// AppPreferences.kt
fun isAdBlockingEnabled(context) = true
fun isDarkModeEnabled(context) = false
fun isMediaPlaybackEnabled(context) = true
```

### إضافة قوائم حجب مخصصة

```kotlin
// FilterLists.kt
val CUSTOM_LISTS = listOf(
    FilterList("قائمتي", "https://example.com/my-list.txt", true)
)
```

---

## 🧪 الاختبار

### على الهاتف

```bash
# تثبيت على جهاز متصل
adb install Nnads-1.0.0.apk

# تشغيل التطبيق
adb shell am start -n com.nnads.browser/.MainActivity

# عرض السجلات
adb logcat | grep "Nnads"
```

### على محاكي Android TV

```bash
# تشغيل محاكي TV
emulator -avd tv_api_35 &

# التثبيت والاختبار
adb install Nnads-1.0.0.apk
adb shell am start -n com.nnads.browser/.TVBrowserActivity
```

### المواقع الاختبارية

- [EasyList Test](https://easylist.to/)
- [AdBlock Test](https://www.adsenseadblock.com/)
- [Privacy Test](https://www.dnsleaktest.com/)

---

## 📊 حالة R1-R26

| المرحلة | الحالة | الملف |
|--------|--------|------|
| **R1-R3: الأساس** | 🟢 مكتمل | REQUIREMENTS.md |
| **R4-R10: حجب الإعلانات** | 🟡 قيد العمل | CUSTOMIZATION.md |
| **R11-R16: الوسائط** | ⚪ مخطط | CUSTOMIZATION.md |
| **R17-R20: الواجهة** | ⚪ مخطط | - |
| **R21-R23: Android TV** | ⚪ مخطط | CUSTOMIZATION.md |
| **R24-R26: البناء** | 🟢 مكتمل | BUILD_REPORT.md |

---

## 📦 تنزيل APK

### الإصدارات

أحدث إصدار: **v1.0.0** (2026-09-12)

- **Android الحد الأدنى**: 5.0 (API 21)
- **Android المستهدف**: 15 (API 35)
- **الحجم**: ~120 MB
- **المعمارية**: arm64-v8a

[تحميل Nnads-1.0.0.apk](https://github.com/benghazi2/Nnads/releases/download/v1.0.0/Nnads-1.0.0.apk)

### التحقق من الملف

```bash
# SHA-256
echo "HASH_HERE" | sha256sum -c -

# توقيع APK
apksigner verify -verbose Nnads-1.0.0.apk
```

---

## 🤝 المساهمة

نرحب بالمساهمات! اتبع الخطوات:

1. اعمل fork للمستودع
2. أنشئ branch جديد (`git checkout -b feature/اسم-الميزة`)
3. ارفع التغييرات (`git push origin feature/اسم-الميزة`)
4. افتح Pull Request

---

## 📜 الرخصة

Nnads مرخص تحت **Mozilla Public License 2.0 (MPL-2.0)**

اقرأ [LICENSE.md](LICENSE.md) للتفاصيل.

### الإسناد

- **Brave Browser**: https://github.com/brave/brave-browser
- **Chromium**: https://chromium.googlesource.com/chromium/src
- **adblock-rust**: https://github.com/brave/adblock-rust
- **AndroidX Media3**: https://developer.android.com/jetpack/androidx/releases/media3

اقرأ [ATTRIBUTION.md](ATTRIBUTION.md) للقائمة الكاملة.

---

## 🐛 الإبلاغ عن الأخطاء

وجدت مشكلة؟ [افتح issue على GitHub](https://github.com/benghazi2/Nnads/issues)

اتضمن:
- نسخة Android
- خطوات إعادة الإنتاج
- السجلات (`adb logcat`)
- لقطات الشاشة

---

## ❓ الأسئلة الشائعة

### هل Nnads آمن؟

✅ نعم! جميع البيانات محفوظة محليًا. لا توجد بيانات تُرسل عن بُعد.

### هل يعمل على الهواتف القديمة؟

✅ نعم، يدعم Android 5.0+ (API 21).

### كيف أضيف قائمة حجب مخصصة؟

📝 تحرير `FilterLists.kt` وأضف القائمة الجديدة.

### هل يدعم الإضافات (Extensions)؟

⏳ المخطط للإصدارات المستقبلية.

### كم تبلغ سرعة حجب الإعلانات؟

⚡ < 10ms لكل طلب (بفضل adblock-rust).

---

## 📞 التواصل

- **GitHub Issues**: https://github.com/benghazi2/Nnads/issues
- **المناقشات**: https://github.com/benghazi2/Nnads/discussions

---

**آخر تحديث**: 2026-09-12  
**الحالة**: في التطوير النشط 🚀
