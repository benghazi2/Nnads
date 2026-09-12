# Nnads Android Ad-Blocking Browser — Requirements & Architecture

**Project**: Ad-blocking Android browser for phones, Android TV, and Google TV with background media playback, session history, and recovery.

**License**: MPL-2.0 (base from Brave Android modifications). All derived work respects upstream licenses and attribution.

---

## R1: Project Foundation — Brave Android Fork

**Status**: In Progress  
**Approach**: Fork Brave Android with license compliance and identity separation.

### Decisions

- **Base Project**: `brave/brave-browser` + `brave/brave-core` (Android)
  - Proven Chromium integration, ad-blocking engine (adblock-rust), and media playback support.
  - MPL-2.0 license permits derivative forks with proper attribution.
  
- **Key Upstreams**:
  - `brave/adblock-rust` — Core filtering engine (Rust FFI-exposed).
  - Chromium (via depot_tools) — Rendering, HTML5 media, WebView alternatives.
  
- **Fork Scope**:
  - New package ID: `com.nnads.browser` (not `com.brave.browser`).
  - New app name, icon, and splash screens.
  - Custom service endpoints (disable Brave analytics, rewards, etc.).
  - Android TV and Google TV UI overhaul.

### Files & Commits

- [ ] Skeleton clone from brave-browser tag (e.g., v1.72.x).
- [ ] License headers audit: MPL-2.0 + attribution in README.
- [ ] CODEOWNERS file with project maintainers.

---

## R2: Licensing & Identity

**Status**: Planned

### Deliverables

1. **LICENSE** file (MPL-2.0 full text).
2. **ATTRIBUTION.md**: Lists Brave, Chromium, adblock-rust, ExoPlayer, Media3, and other deps.
3. **Package Identity**:
   - Package: `com.nnads.browser`
   - App Name: "Nnads"
   - Version: 1.0.0
   - Min SDK: API 21 (Android 5.0) for broad TV support.
   - Target SDK: API 35+.

4. **Service Disabling**:
   - Brave Sync → disabled.
   - Brave Rewards → disabled.
   - Telemetry endpoints → redirected or nullified.
   - Update checks → no-op (manual updates only).

---

## R3: Build Environment

**Status**: Discovery Required

### Platform Requirements

- **Host OS**: Linux (Ubuntu 20.04+ recommended) or macOS.
- **Disk**: ~150 GB (Chromium source + build artifacts).
- **RAM**: 16 GB minimum; 32 GB recommended.
- **Tools**:
  - depot_tools (gclient, gn, ninja).
  - Android SDK 35+.
  - Android NDK r27+ (C/C++/Rust interop).
  - JDK 11+.
  - Rust toolchain (1.70+) with Android targets.
  - Python 3.10+.
  - git, curl, wget.

### Setup Commands (Placeholder)

```bash
# 1. Clone depot_tools
git clone https://chromium.googlesource.com/chromium/tools/depot_tools.git
export PATH=depot_tools:$PATH

# 2. Fetch Brave source (placeholder tag)
mkdir brave-src && cd brave-src
gclient config --name=src --unmanaged https://github.com/brave/brave-browser.git
gclient sync -D

# 3. Install NDK, SDK
~/Android/Sdk/cmdline-tools/latest/bin/sdkmanager "ndk;27.0.12077973"

# 4. Rust Android targets
rustup target add aarch64-linux-android armv7-linux-android x86_64-linux-android
```

### Verification Checklist

- [ ] `depot_tools` in PATH; `gclient` version confirmed.
- [ ] Android SDK/NDK installed and linked.
- [ ] JDK 11+ on PATH.
- [ ] Rust toolchain with Android targets.
- [ ] Build system (gn, ninja) available.

---

## Implementation Phases

### Phase 1 (R1–R3): Foundation
- Repo fork, licensing audit, build environment setup.
- Expected duration: 1–2 weeks.

### Phase 2 (R4–R10): Ad-Blocking Core
- App architecture, adblock-rust integration, filter lists, rule evaluation.
- Expected duration: 2–3 weeks.

### Phase 3 (R11–R16): Media Playback & Background
- HTML5 player, ExoPlayer integration, Media Session, background service.
- Expected duration: 2–3 weeks.

### Phase 4 (R17–R20): UI & Sessions
- Phone UI, history, bookmarks, tab management, session persistence.
- Expected duration: 2 weeks.

### Phase 5 (R21–R23): TV UI & TV-Specific
- TV navigation (D-pad, focus, remote), layout, testing.
- Expected duration: 2 weeks.

### Phase 6 (R24–R26): Hardening & Delivery
- Integration tests, performance profiling, APK build, Gofile upload.
- Expected duration: 1–2 weeks.

---

## Success Criteria

- [ ] APK builds, installs, and runs on Android 5.0+.
- [ ] Single ad-blocking filter list loads and blocks sample ads.
- [ ] History and session recovery work.
- [ ] Fakespot test shows 50%+ request reduction.
- [ ] TV UI navigable with D-pad on Android TV emulator.
- [ ] Background media playback continues when app backgrounded.

---

## Known Constraints

1. **Chromium Build Scale**: Compiling Chromium takes 1–4 hours depending on parallelism.
2. **APK Size**: Likely 100–150 MB (unoptimized); further optimization possible.
3. **Arm64 Only**: Initial build targets arm64-v8a; additional architectures require extra build time.
4. **Media Licensing**: Some H.264/AAC formats require runtime negotiation; WebM/VP9 more portable.
5. **TV Device Scarcity**: Testing on real Android TV hardware is ideal but not always available; emulator used as fallback.

---

**Last Updated**: 2026-09-12  
**Next Review**: After environment setup confirmation.
