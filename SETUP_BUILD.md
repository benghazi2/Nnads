# Nnads Android Ad-Blocking Browser — Setup & Build Guide

This document provides the complete step-by-step process to fork, configure, and build the Nnads ad-blocking browser based on Brave Android.

## Quick Start

### 1. Prerequisites

Ensure your system has:
- **OS**: Ubuntu 20.04+ LTS or macOS 11+
- **Disk**: 150+ GB free
- **RAM**: 16 GB minimum, 32 GB recommended
- **Internet**: High-speed connection (40+ GB initial fetch)

### 2. Install System Dependencies

#### Ubuntu 20.04 LTS

```bash
sudo apt-get update
sudo apt-get install -y \
  git curl wget python3 python3-pip \
  build-essential clang libc6-dev \
  libxrandr-dev libglu1-mesa-dev \
  openjdk-11-jdk-headless \
  ninja-build \
  nodejs npm
```

#### macOS

```bash
# Install Xcode Command Line Tools
xcode-select --install

# Install Homebrew dependencies
brew install git python3 openjdk@11 ninja node
```

### 3. Set Up depot_tools

```bash
cd ~
git clone https://chromium.googlesource.com/chromium/tools/depot_tools.git
export PATH="$HOME/depot_tools:$PATH"

# Add to ~/.bashrc or ~/.zshrc for persistence
echo 'export PATH="$HOME/depot_tools:$PATH"' >> ~/.bashrc
source ~/.bashrc
```

### 4. Install Android SDK & NDK

```bash
# Create Android directory
mkdir -p ~/Android/Sdk

# Install cmdline-tools
cd ~/Android/Sdk
wget https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip
unzip commandlinetools-linux-9477386_latest.zip
rm commandlinetools-linux-9477386_latest.zip

# Set up SDK
export ANDROID_SDK_ROOT=$HOME/Android/Sdk
export ANDROID_HOME=$HOME/Android/Sdk

# Install SDK components
$ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools/bin/sdkmanager \
  "platforms;android-35" \
  "build-tools;35.0.0" \
  "ndk;27.0.12077973"

export ANDROID_NDK_HOME=$HOME/Android/Sdk/ndk/27.0.12077973
```

### 5. Install Rust & Android Targets

```bash
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
source $HOME/.cargo/env

rustup target add \
  aarch64-linux-android \
  armv7-linux-android \
  x86_64-linux-android \
  i686-linux-android

# Install cargo-ndk for cross-compilation
cargo install cargo-ndk
```

### 6. Clone Brave Browser

```bash
cd ~
mkdir nnads-build && cd nnads-build

# Clone Brave browser repo (or your fork)
git clone https://github.com/brave/brave-browser.git src

cd src
```

### 7. Sync Dependencies

```bash
npm install
npm run sync
# This will take 30+ minutes and ~40 GB
```

### 8. Create Nnads Build Configuration

Create `.nnads-build-config.env`:

```bash
export NNADS_PACKAGE_ID="com.nnads.browser"
export NNADS_APP_NAME="Nnads"
export NNADS_VERSION="1.0.0"
export NNADS_MIN_SDK=21
export NNADS_TARGET_SDK=35
export NNADS_BUILD_DIR="$PWD/out/Release"
```

Load it:
```bash
source .nnads-build-config.env
```

### 9. Configure GN Build

```bash
cd src
gn gen out/Release --args='
target_os="android"
target_cpu="arm64"
is_debug=false
is_official_build=false
proprietary_codecs=true
ffmpeg_branding="Chrome"
enable_remoting=false
disable_ftp_support=true
disable_file_support=false
enable_websockets=true
enable_media_stream=true
blink_symbol_level=0
symbol_level=0
'
```

### 10. Build APK

```bash
ninja -C out/Release -j4 chrome_public_apk
```

### 11. Rename & Sign

```bash
APK_SOURCE="out/Release/apks/ChromePublic.apk"
APK_DEST="Nnads-unsigned-1.0.0.apk"

if [ -f "$APK_SOURCE" ]; then
  cp "$APK_SOURCE" "$APK_DEST"
  echo "✅ APK created: $APK_DEST"
else
  echo "❌ APK build failed"
  exit 1
fi
```

### 12. Generate Release Key (One-Time)

```bash
keytool -genkey -v \
  -keystore nnads-release.keystore \
  -keyalg RSA -keysize 2048 \
  -validity 10000 \
  -alias nnads-key \
  -storepass nnads_release_password \
  -keypass nnads_key_password
```

⚠️ **NEVER commit the keystore file!**

### 13. Sign & Align APK

```bash
# Sign
jarsigner -verbose \
  -sigalg SHA256withRSA \
  -digestalg SHA-256 \
  -keystore nnads-release.keystore \
  -storepass nnads_release_password \
  -keypass nnads_key_password \
  Nnads-unsigned-1.0.0.apk nnads-key

# Align
zipalign -v 4 Nnads-unsigned-1.0.0.apk Nnads-1.0.0-signed.apk
```

### 14. Verify APK

```bash
apksigner verify -verbose Nnads-1.0.0-signed.apk
sha256sum Nnads-1.0.0-signed.apk
```

---

## Build Automation Script

Save as `build-nnads.sh`:

```bash
#!/bin/bash
set -e

echo "🔨 Building Nnads APK..."

# Load config
source .nnads-build-config.env

# Ensure depot_tools in PATH
export PATH="$HOME/depot_tools:$PATH"

# GN config
echo "📋 Generating GN build..."
gn gen out/Release --args='
target_os="android"
target_cpu="arm64"
is_debug=false
proprietary_codecs=true
ffmpeg_branding="Chrome"
'

# Build
echo "⚙️  Building with Ninja..."
ninja -C out/Release -j4 chrome_public_apk

# Copy
echo "📦 Packaging APK..."
cp out/Release/apks/ChromePublic.apk Nnads-unsigned-1.0.0.apk

# Sign & Align
echo "🔐 Signing APK..."
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore nnads-release.keystore \
  -storepass nnads_release_password \
  -keypass nnads_key_password \
  Nnads-unsigned-1.0.0.apk nnads-key

zipalign -v 4 Nnads-unsigned-1.0.0.apk Nnads-1.0.0-signed.apk

# Verify
echo "✅ Verifying APK..."
apksigner verify -verbose Nnads-1.0.0-signed.apk
sha256sum Nnads-1.0.0-signed.apk

echo "🎉 Build complete: Nnads-1.0.0-signed.apk"
```

Run:
```bash
chmod +x build-nnads.sh
./build-nnads.sh
```

---

## Troubleshooting

### "gn: command not found"
Ensure depot_tools is in PATH:
```bash
export PATH="$HOME/depot_tools:$PATH"
```

### "Java not found"
Install JDK 11+:
```bash
sudo apt-get install openjdk-11-jdk
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
```

### Ninja slow / out of memory
Reduce parallelism:
```bash
ninja -C out/Release -j2 chrome_public_apk
```

### Disk full during sync
Free up space:
```bash
df -h
# If < 50 GB free, add external SSD or cloud storage
```

---

**Next**: Proceed to `CUSTOMIZATION.md` for branding and feature configuration.
