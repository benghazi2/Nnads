# Nnads APK Build Report

**Project**: Nnads Android Ad-Blocking Browser  
**Status**: Build environment setup in progress.

---

## Environment Details

### Host Machine

- **OS**: TBD (Ubuntu 20.04 LTS targeted)
- **CPU**: TBD
- **RAM**: TBD (16+ GB required)
- **Disk**: TBD (150 GB available required)
- **Toolchain Version**: TBD

### Toolchain Versions

| Tool | Version | Notes |
|------|---------|-------|
| depot_tools | TBD | Via chromium.googlesource.com |
| Android SDK | 35+ | Target API level |
| Android NDK | r27+ | C/C++ build; Rust interop |
| JDK | 11+ | Gradle, javac |
| Rust | 1.70+ | adblock-rust FFI |
| Python | 3.10+ | Build scripts |
| Git | 2.25+ | VCS |

### Build Command Sequence

```bash
# (Placeholder; to be filled after Phase 1)
# 1. Clone and fetch
# 2. Apply Nnads patches
# 3. gn gen
# 4. ninja
# 5. Gradle assemble
```

---

## APK Build Artifacts

### Current Build

- **APK Name**: `Nnads-debug.apk` (TBD)
- **Build Type**: Debug (TBD → Release)
- **Architecture(s)**: arm64-v8a (TBD: add armeabi-v7a, x86_64)
- **Min API**: 21 (Android 5.0)
- **Target API**: 35+
- **APK Size**: TBD (100–150 MB estimated, unoptimized)
- **SHA-256**: TBD

### Signing

- **Debug Key**: Android SDK default (`debug.keystore`).
- **Release Key**: TBD (to be generated, never checked in).

---

## Compilation & Linking

### Build System

- **Primary**: GN (Chromium) + Ninja for native code.
- **Secondary**: Gradle for APK assembly and Kotlin/Java.
- **Optional**: CMake (for additional JNI modules if needed).

### Modules Built

- [ ] Chromium core (rendering, networking, security).
- [ ] Brave patches (UI, settings, branding).
- [ ] adblock-rust (Rust → C++ → JNI bridge).
- [ ] ExoPlayer (media playback).
- [ ] Media3 (media sessions).
- [ ] Room (local database for history).
- [ ] DataStore (preferences).

### Build Time (Estimate)

- **First build (full)**: 2–4 hours (parallelism dependent).
- **Incremental**: 10–30 minutes.
- **APK assembly**: 2–5 minutes.

---

## Test Coverage

### Automated Tests

- [ ] Unit: Filtering logic, session persistence.
- [ ] Integration: Page load → block → metrics.
- [ ] UI: Navigation, settings, tabs (Espresso/Compose).
- [ ] TV: D-pad, focus, remote controls (TV tests).

### Manual Testing (Checklist)

**Phone (API 21+)**
- [ ] App launches, loads homepage.
- [ ] Single site visited, ad requests logged.
- [ ] Filter list downloaded and activated.
- [ ] Ad-blocking visible on test page.
- [ ] History saved and searchable.
- [ ] Settings accessible.

**Android TV / Google TV Emulator (API 32+)**
- [ ] TV UI renders (no phone layout).
- [ ] D-pad navigation works.
- [ ] Focus visible on elements.
- [ ] Remote media buttons functional.

**Media Playback**
- [ ] HTML5 video plays inline.
- [ ] Background playback continues after Home/Back.
- [ ] Notification controls (play/pause) work.
- [ ] Picture-in-Picture activates (if supported).

**Session Recovery**
- [ ] Tabs restored after force-close.
- [ ] History persists across app restarts.
- [ ] Cookies and cache isolated as expected.

---

## Known Limitations & Caveats

1. **APK Size**: Full Chromium + Brave + ad-blocking ~100–150 MB unoptimized.
   - Mitigation: ProGuard/R8 dex optimization for release.

2. **Media Codec Support**: H.264 requires runtime licensing; WebM more portable.
   - Documented in media playback section.

3. **TV Testing**: Real Android TV hardware not yet available for testing.
   - Testing on emulator; hardware validation TBD.

4. **Incremental Builds**: Chromium full rebuilds take hours; use incremental + Sccache/Goma for speed.

5. **Memory Usage**: 16 GB RAM minimum; 32 GB strongly recommended for build parallelism.

---

## Release APK Checklist

- [ ] Debug build verified on test devices.
- [ ] All R1–R26 requirements met and tested.
- [ ] Code reviewed and linted.
- [ ] Signing key generated (RSA 2048 or stronger).
- [ ] Release APK built with ProGuard/R8 enabled.
- [ ] APK size acceptable (< 200 MB recommended for Play Store).
- [ ] Signature verified (zipalign, apksigner).
- [ ] Gofile upload attempted with verification.

---

## Continuation

**To Resume Build Work**:
1. Check environment against checklist above.
2. Confirm depot_tools, SDK, NDK, JDK, Rust versions.
3. Verify build machine disk/RAM available.
4. Run test compile (single module) before full build.
5. Record actual build times and artifact sizes.
6. Update this report with real data; reference git commit hash.

**Last Updated**: 2026-09-12 (Placeholder)  
**Next Update**: After Phase 1 environment setup.
