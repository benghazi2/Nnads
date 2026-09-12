# الإسناد والشكر - Nnads

هذا المشروع يعتمد على عشرات المشاريع مفتوحة المصدر. فيما يلي قائمة كاملة بالمشاريع والمكتبات والإسناديات.

---

## المشاريع الأساسية

### 1. Brave Browser
- **الموقع**: https://github.com/brave/brave-browser
- **الرخصة**: Mozilla Public License 2.0 (MPL-2.0)
- **المالك**: Brave Software, Inc.
- **الاستخدام**: البنية الأساسية، إدارة التبويبات، واجهة الإعدادات
- **الإصدار المستخدم**: v1.72.x (2026)

### 2. Brave Core
- **الموقع**: https://github.com/brave/brave-core
- **الرخصة**: MPL-2.0
- **المالك**: Brave Software, Inc.
- **الاستخدام**: محرك التصفح، معالجة الطلبات، تكامل Chromium
- **الملفات المعدلة**: `src/brave/browser/`, `src/brave/components/`

### 3. Chromium
- **الموقع**: https://chromium.googlesource.com/chromium/src
- **الرخصة**: BSD و رخص أخرى متعددة
- **المالك**: Google LLC
- **الاستخدام**: محرك التصفح الأساسي، WebKit، V8
- **الإصدار**: ~127.x (2026)

### 4. adblock-rust
- **الموقع**: https://github.com/brave/adblock-rust
- **الرخصة**: MPL-2.0
- **المالك**: Brave Software, Inc.
- **الاستخدام**: محرك حجب الإعلانات الأساسي
- **اللغة**: Rust + FFI (C++)
- **الإصدار**: 1.x

---

## المكتبات والمكونات

### Android Framework
- **androidx.appcompat** - توافقية Android قديم
- **androidx.preference** - إدارة الإعدادات
- **androidx.work** - جدولة المهام الخلفية
- **androidx.room** - قاعدة بيانات محلية

### Media & Playback
- **Media3 (androidx.media3)** - إدارة جلسات الوسائط
- **ExoPlayer** - محرك تشغيل الفيديو والصوت
- **Jetpack Compose** - واجهة مستخدم حديثة

### Networking
- **OkHttp** - عميل HTTP
- **Retrofit** - نقل بيانات REST
- **Moshi** - معالجة JSON

### Async & Reactive
- **Kotlin Coroutines** - برمجة غير متزامنة
- **RxJava3** - البرمجة التفاعلية
- **Flow** - تدفقات بيانات Kotlin

### Logging & Debugging
- **Timber** - تسجيل الأحداث
- **Sentry** - تقارير الأخطاء (اختياري)

---

## مشاريع التحكم والأدوات

### قوائم حجب الإعلانات

#### EasyList
- **الموقع**: https://easylist.to/
- **المالك**: The EasyList Authors
- **الرخصة**: GPL 3.0
- **الاستخدام**: قائمة الإعلانات الأساسية
- **التحديث**: يومي
- **الحجم**: ~250 KB

#### EasyPrivacy
- **الموقع**: https://easylist.to/easylist/easyprivacy.txt
- **المالك**: The EasyList Authors
- **الرخصة**: GPL 3.0
- **الاستخدام**: قائمة تتبع الخصوصية
- **التحديث**: يومي
- **الحجم**: ~150 KB

#### ABP Arabic List
- **الموقع**: https://www.fanboy.co.nz/arabicadblockplus.txt
- **المالك**: Fanboy (ABP)
- **الرخصة**: GPL 3.0
- **الاستخدام**: قائمة إعلانات عربية
- **التحديث**: أسبوعي

#### uBlock Origin Lists
- **الموقع**: https://github.com/uBlockOrigin/uAssets
- **الرخصة**: GPL 3.0
- **الاستخدام**: قوائم إضافية متقدمة
- **الحجم**: ~500 KB+

---

## Kotlin و Java Libraries

### Persistence
```
androidx.room:room-runtime:2.5.1
androidx.room:room-ktx:2.5.1
androidx.datastore:datastore-preferences:1.0.0
```

### UI & Layout
```
androidx.constraintlayout:constraintlayout:2.1.4
androidx.cardview:cardview:1.0.0
androidx.recyclerview:recyclerview:1.3.1
com.google.android.material:material:1.9.0
```

### Networking
```
com.squareup.okhttp3:okhttp:4.11.0
com.squareup.retrofit2:retrofit:2.9.0
com.squareup.moshi:moshi-kotlin:1.15.0
```

### Async
```
org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1
org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1
io.reactivex.rxjava3:rxjava:3.1.6
io.reactivex.rxjava3:rxandroid:3.0.0
```

---

## Chromium Components

### التصفح الأساسي
- **Blink** - محرك التخطيط و العرض
- **V8** - محرك JavaScript
- **WebKit** - معالجة HTML/CSS
- **Protocol Buffers** - تسلسل البيانات

### الأمان
- **BoringSSL** - مكتبة تشفير (من OpenSSL)
- **libc++** - مكتبة C++ القياسية
- **zlib** - ضغط البيانات

### الوسائط
- **FFmpeg** - معالجة الفيديو والصوت
- **WebRTC** - الاتصالات الحية
- **OpenH264** - ترميز H.264 (Cisco)

---

## أدوات البناء

### Java/Kotlin
```
JDK 11+
Gradle 8.x
Android Gradle Plugin 8.x
Kotlin Compiler 1.9.x
```

### Native (C/C++)
```
Android NDK r27+
Clang (LLVM)
Ninja Build System
GN (Generate Ninja)
CMake 3.20+
```

### Rust
```
Rust 1.70+
cargo-ndk (Rust Android)
Targets:
  - aarch64-linux-android
  - armv7-linux-android
  - x86_64-linux-android
  - i686-linux-android
```

### موارد
```
depot_tools (Chromium)
Python 3.10+
Node.js 18+
Git 2.25+
```

---

## خدمات الإنترنت والموارد

### تحديثات قوائم الحجب
- EasyList CDN - https://easylist.to/
- ABP CDN - https://adblockplus.org/
- GitHub Raw - https://raw.githubusercontent.com/

### أنظمة الاختبار
- Browserstack (اختياري) - اختبار الأجهزة
- Android Emulator - محاكاة
- GitHub Actions - CI/CD

---

## شكر خاص

### Contributors و Maintainers
- فريق Brave Browser
- مجتمع Chromium
- مطورو adblock-rust
- مجتمع Kotlin و Android

### Open Source Community
شكراً لجميع مطوري البرامج مفتوحة المصدر الذين يسهمون في هذا النظام البيئي.

---

## ملاحظات الرخصة

### الامتثال
- ✅ جميع الملفات تحتوي على رؤوس الرخصة المناسبة
- ✅ تم الحفاظ على إشعارات حقوق الملكية
- ✅ الأعمال المشتقة مرخصة بموجب MPL-2.0
- ✅ تم الإفصاح عن التعديلات

### التوزيع
- ✅ النص الكامل للرخص متاح في المستودع
- ✅ THIRD_PARTY_LICENSES.md متوفر
- ✅ قائمة ATTRIBUTION.md محفوظة

---

## إجراءات الترخيص

إذا كنت تستخدم Nnads أو تعدله:

1. **احترم جميع الرخص** - خاصة MPL-2.0 و GPL 3.0
2. **احفظ الإسناديات** - لا تحذف رؤوس الرخصة
3. **أفصح عن التعديلات** - إذا عدلت الكود
4. **وفر النص الكامل** - للملفات الأصلية
5. **اتبع الشروط** - لكل رخصة مستخدمة

---

## معلومات الإصدار

**إصدار المشروع**: 1.0.0  
**تاريخ الإنشاء**: 2026-09-12  
**آخر تحديث**: 2026-09-12  

**قائمة المكتبات محدثة لـ**:
- Brave Browser: v1.72.x
- Chromium: v127.x
- Android API: 35

---

**للتفاصيل الكاملة عن رخصة كل مشروع، يرجى مراجعة ملفات LICENSE في كل مستودع أصلي.**
