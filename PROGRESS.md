# Nnads Project Progress Tracking

**Project**: Ad-blocking Android browser (Nnads)  
**Start Date**: 2026-09-12  
**Current Phase**: Discovery & Foundation

---

## Phase 1: Foundation (R1–R3)

### R1: Project Foundation

**Task**: Evaluate Brave Android, choose fork strategy, audit licenses.

| Item | Status | Notes |
|------|--------|-------|
| Brave-browser repo analyzed | ✅ Complete | MPL-2.0, clear build pipeline, adblock-rust integrated |
| Fork strategy confirmed | ✅ Complete | Fork brave-browser, customize for Nnads branding |
| Package ID decided | ✅ Complete | `com.nnads.browser` (not `com.brave.browser`) |
| License audit started | 🔶 In Progress | MPL-2.0 + Chromium/WebKit/FFmpeg deps |
| Brave services audit | 📋 Planned | Identify and disable Sync, Rewards, telemetry |

**Blockers**: None yet; awaiting environment setup.

---

### R2: Licensing & Identity

**Task**: Create license files, set up package identity, disable Brave-specific services.

| Item | Status | Notes |
|------|--------|-------|
| LICENSE.md created | 📋 Planned | MPL-2.0 text + preamble |
| ATTRIBUTION.md started | 📋 Planned | List Brave, Chromium, adblock-rust, Media3 |
| Branding assets prepared | 📋 Planned | App icon, splash, strings (en, ar) |
| Service disabling plan | 📋 Planned | Sync, Rewards, update checks, analytics |

**Blockers**: Requires source code access.

---

### R3: Build Environment

**Task**: Set up Linux/macOS build machine, install toolchain, verify compilation.

| Item | Status | Notes |
|------|--------|-------|
| OS: Ubuntu 20.04 LTS | 🔶 TBD | Minimum 150 GB disk, 16+ GB RAM |
| depot_tools cloned | 📋 Planned | Add to PATH |
| Android SDK 35+ installed | 📋 Planned | Via sdkmanager |
| Android NDK r27+ installed | 📋 Planned | C/C++ cross-compilation |
| JDK 11+ installed | 📋 Planned | Gradle build system |
| Rust + Android targets | 📋 Planned | `aarch64-linux-android` at minimum |
| gclient sync test | 📋 Planned | Fetch Brave source (dry run) |
| gn setup | 📋 Planned | Chromium GN build config |
| Test compile (debug) | 📋 Planned | Single module, not full Chromium |

**Blockers**: 
- Requires Linux machine with adequate disk/RAM.
- Chromium source fetch can take 1–2 hours on first pull.

---

## Phase 2: Ad-Blocking Core (R4–R10)

**Status**: Not started (awaiting Phase 1 completion).

### R4–R6: Architecture & Filtering

- [ ] App module structure (Browser, AdBlock, Media, History, UI).
- [ ] adblock-rust FFI integration (Rust → C++ → JNI → Kotlin).
- [ ] Filter list loading and updates (EasyList, EasyPrivacy, Arabic lists).

### R7–R9: Cosmetic & Site Rules

- [ ] CSS selector injection for ad hiding.
- [ ] Per-site exceptions and overrides.
- [ ] Popup and redirect blocking.

### R10: Metrics

- [ ] Real block counts (not placeholders).
- [ ] UI indicators (badge, history).

---

## Phase 3: Media Playback & Background (R11–R16)

**Status**: Not started.

### R11–R14: Player & Controls

- [ ] HTML5 player retention.
- [ ] ExoPlayer integration for external/HLS sources.
- [ ] Media Session API.
- [ ] Controls (play/pause, seek, skip).

### R15–R16: Background & Lifecycle

- [ ] Service with MediaPlayback foreground type.
- [ ] Background playback with notification.
- [ ] Picture-in-Picture support.
- [ ] Lifecycle testing (Home, Back, task kill, etc.).

---

## Phase 4: UI & Sessions (R17–R20)

**Status**: Not started.

### R17–R20: Phone UI, History, Tabs

- [ ] Omnibox (address bar + search).
- [ ] Tab management.
- [ ] Bookmarks and history.
- [ ] Settings UI.
- [ ] Dark mode + RTL (ar-SA).
- [ ] Session restore on crash/close.

---

## Phase 5: TV UI & Specific (R21–R23)

**Status**: Not started.

### TV Navigation & Layout

- [ ] D-pad focus system.
- [ ] TV-optimized grid/list layouts.
- [ ] Remote media buttons.
- [ ] Large-text labels and buttons.
- [ ] Testing on Android TV emulator.

---

## Phase 6: Hardening & Delivery (R24–R26)

**Status**: Not started.

### Build & Testing

- [ ] BUILD_REPORT.md (architecture, toolchain, APK size).
- [ ] Unit tests (filtering logic).
- [ ] Integration tests (page load + block).
- [ ] UI tests (phone + TV).
- [ ] APK debug build verified on device.

### Release

- [ ] APK signing key generated (not checked in).
- [ ] Release APK built.
- [ ] Gofile upload (with SHA-256 verification).

---

## Known Issues & Workarounds

| Issue | Impact | Workaround |
|-------|--------|-----------|
| Chromium build time | 4+ hours per full build | Incremental builds; goma/sccache for caching |
| APK size (100–150 MB) | Download/storage on older phones | ProGuard/R8 for release builds; module splits for Play Store |
| Media codec licensing | H.264 requires runtime negotiation | Prefer WebM/VP9; document requirements |
| Android TV hardware scarcity | Hard to test on real device | Emulator + best-effort documentation |

---

## Review & Continuation

**Last Session**: 2026-09-12 (Initial discovery)  
**Next Steps**:
1. Confirm Linux build machine specs and available disk/RAM.
2. Begin Phase 1: Fork Brave, set up build env.
3. Test `gclient sync` and single-module compile.

**Continuation Note**: Before resuming, read REQUIREMENTS.md and this file. Record:
- Current commit hash of Brave base.
- Build command sequence used.
- Any environment-specific overrides or patches.
