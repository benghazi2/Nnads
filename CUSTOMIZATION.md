# Nnads Customization & Configuration

This guide covers branding, feature toggles, and Android TV adaptations for Nnads.

---

## 1. Package Identity

### Change Package ID

File: `src/android/java/AndroidManifest.xml` (or similar):

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.nnads.browser"
    android:versionCode="1"
    android:versionName="1.0.0">

    <uses-sdk
        android:minSdkVersion="21"
        android:targetSdkVersion="35" />
    
    <application
        android:allowBackup="true"
        android:debuggable="false"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
        
        <activity
            android:name=".MainActivity"
            android:configChanges="orientation|screenSize"
            android:launchMode="singleTop">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Media playback service -->
        <service
            android:name=".media.MediaPlaybackService"
            android:exported="false">
            <intent-filter>
                <action android:name="android.media.browse.MediaBrowserService" />
            </intent-filter>
        </service>

    </application>

</manifest>
```

### String Resources

File: `src/android/res/values/strings.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">Nnads</string>
    <string name="app_description">Ad-blocking browser for all screens</string>
    <string name="menu_settings">Settings</string>
    <string name="menu_history">History</string>
    <string name="menu_bookmarks">Bookmarks</string>
    <string name="ad_block_enabled">Ad Blocking Enabled</string>
    <string name="requests_blocked">%d requests blocked</string>
</resources>
```

### Arabic Strings

File: `src/android/res/values-ar/strings.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">نادس</string>
    <string name="app_description">متصفح حاجب للإعلانات على كل الأجهزة</string>
    <string name="menu_settings">الإعدادات</string>
    <string name="menu_history">السجل</string>
    <string name="menu_bookmarks">المفضلة</string>
    <string name="ad_block_enabled">حجب الإعلانات مفعّل</string>
    <string name="requests_blocked">تم حجب %d طلب</string>
</resources>
```

---

## 2. Disable Brave Services

### Brave Sync Disable

File: `src/brave/browser/sync/brave_sync_service.cc`:

```cpp
// Disable Brave Sync by default
void BraveSyncService::Initialize() {
  // DISABLED: Brave Sync not used in Nnads
  // sync_->InitializeSync(model_type_store);
}
```

### Brave Rewards Disable

File: `src/brave/browser/ui/android/BraveRewardsFragment.java`:

```java
public class BraveRewardsFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Rewards service disabled in Nnads
        super.onViewCreated(view, savedInstanceState);
    }
}
```

### Analytics/Telemetry Disable

File: `src/brave/browser/metrics/brave_metrics_service.cc`:

```cpp
// Override telemetry endpoints
std::string GetMetricsServerUrl() {
  // Return null/empty to disable telemetry
  return "";
}
```

---

## 3. Android TV Support

### Detect TV Mode

File: `src/nnads/android/TVUtils.java`:

```java
package com.nnads.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class TVUtils {
    public static boolean isAndroidTV(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_TELEVISION)
            || pm.hasSystemFeature(PackageManager.FEATURE_LEANBACK);
    }

    public static boolean isGoogleTV(Context context) {
        return context.getPackageManager()
            .hasSystemFeature(PackageManager.FEATURE_LEANBACK);
    }

    public static float getScreenDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }
}
```

### TV Layout (D-Pad Navigation)

File: `src/nnads/android/res/layout/activity_tv.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/tv_background">

    <!-- TV Toolbar -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="80dp"
        android:orientation="horizontal"
        android:padding="20dp"
        android:nextFocusRight="@+id/tv_search_bar">
        
        <Button
            android:id="@+id/tv_menu_button"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:text="☰"
            android:textSize="32sp"
            android:nextFocusDown="@+id/tv_webview" />

    </LinearLayout>

    <!-- WebView with large touch targets -->
    <FrameLayout
        android:id="@+id/tv_webview_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:nextFocusUp="@+id/tv_menu_button">

        <WebView
            android:id="@+id/tv_webview"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

        <!-- Media Controls (Picture-in-Picture) -->
        <FrameLayout
            android:id="@+id/tv_pip_container"
            android:layout_width="400dp"
            android:layout_height="225dp"
            android:layout_gravity="bottom|end"
            android:layout_marginBottom="20dp"
            android:layout_marginEnd="20dp"
            android:background="@drawable/rounded_background">

            <VideoView
                android:id="@+id/tv_pip_video"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

        </FrameLayout>

    </FrameLayout>

</LinearLayout>
```

### TV Activity

File: `src/nnads/android/TVBrowserActivity.java`:

```java
package com.nnads.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.leanback.app.BrowseSupportFragment;

public class TVBrowserActivity extends Activity {
    private FrameLayout webViewContainer;
    private int focusMode = 0; // 0=navigation, 1=text-input

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        
        if (TVUtils.isAndroidTV(this)) {
            setupTVUI();
        } else {
            setupPhoneUI();
        }
    }

    private void setupTVUI() {
        webViewContainer = findViewById(R.id.tv_webview_container);
        // Initialize TV-specific controls
        Toast.makeText(this, "TV Mode Activated", Toast.LENGTH_SHORT).show();
    }

    private void setupPhoneUI() {
        // Use standard phone layout
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // D-Pad Navigation
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            focusMode = 0;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            focusMode = 0;
            return true;
        }
        // Select button (Enter)
        if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_BUTTON_A) {
            handleSelect();
            return true;
        }
        // Back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        // Media controls
        if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
            handleMediaPlayPause();
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }

    private void handleSelect() {
        Toast.makeText(this, "Selected", Toast.LENGTH_SHORT).show();
    }

    private void handleMediaPlayPause() {
        Toast.makeText(this, "Play/Pause", Toast.LENGTH_SHORT).show();
    }
}
```

---

## 4. Ad-Blocking Configuration

### Filter Lists

File: `src/nnads/android/adblock/FilterLists.kt`:

```kotlin
package com.nnads.android.adblock

import java.net.URL

object FilterLists {
    data class FilterList(
        val name: String,
        val url: String,
        val enabled: Boolean = true,
        val language: String = "en"
    )

    val DEFAULT_LISTS = listOf(
        FilterList(
            "EasyList",
            "https://easylist.to/easylist/easylist.txt",
            enabled = true
        ),
        FilterList(
            "EasyPrivacy",
            "https://easylist.to/easylist/easyprivacy.txt",
            enabled = true
        ),
        FilterList(
            "Arabic Ads (ABP)",
            "https://www.fanboy.co.nz/arabicadblockplus.txt",
            enabled = true,
            language = "ar"
        )
    )

    suspend fun downloadFilterList(list: FilterList): String? {
        return try {
            URL(list.url).readText()
        } catch (e: Exception) {
            null
        }
    }
}
```

### Local Filter Database

File: `src/nnads/android/adblock/FilterDatabase.kt`:

```kotlin
package com.nnads.android.adblock

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Entity(tableName = "filter_lists")
data class FilterListEntity(
    @PrimaryKey val id: String,
    val name: String,
    val rules: String,  // Serialized rule set
    val lastUpdated: Long = System.currentTimeMillis(),
    val enabled: Boolean = true
)

@Dao
interface FilterListDao {
    @Insert
    suspend fun insertFilterList(list: FilterListEntity)

    @Query("SELECT * FROM filter_lists WHERE enabled = 1")
    suspend fun getEnabledLists(): List<FilterListEntity>

    @Query("UPDATE filter_lists SET rules = :rules, lastUpdated = :time WHERE id = :id")
    suspend fun updateRules(id: String, rules: String, time: Long)
}

@Database(entities = [FilterListEntity::class], version = 1)
abstract class FilterDatabase {
    abstract fun filterListDao(): FilterListDao
}
```

---

## 5. Media Playback Configuration

### ExoPlayer Setup

File: `src/nnads/android/media/MediaPlayerService.kt`:

```kotlin
package com.nnads.android.media

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession

class MediaPlaybackService : Service() {
    private var mediaSession: MediaSession? = null
    private var player: ExoPlayer? = null

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!).build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        mediaSession?.release()
        player?.release()
        super.onDestroy()
    }
}
```

---

## 6. Session & History Persistence

### History Database

File: `src/nnads/android/history/BrowsingHistory.kt`:

```kotlin
package com.nnads.android.history

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert

@Entity(tableName = "history")
data class HistoryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis(),
    val favicon: String? = null
)

@Dao
interface HistoryDao {
    @Insert
    suspend fun addHistory(entry: HistoryEntry)

    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentHistory(limit: Int = 100): List<HistoryEntry>

    @Query("DELETE FROM history WHERE timestamp < :cutoffTime")
    suspend fun deleteOldHistory(cutoffTime: Long)
}
```

---

## 7. Settings UI

### Preferences

File: `src/nnads/android/preferences/AppPreferences.kt`:

```kotlin
package com.nnads.android.preferences

import android.content.Context
import androidx.preference.PreferenceManager

object AppPreferences {
    const val PREF_AD_BLOCKING_ENABLED = "ad_blocking_enabled"
    const val PREF_TRACKING_BLOCKING_ENABLED = "tracking_blocking_enabled"
    const val PREF_DARK_MODE = "dark_mode"
    const val PREF_LANGUAGE = "language"

    fun getPreferences(context: Context) = 
        PreferenceManager.getDefaultSharedPreferences(context)

    fun isAdBlockingEnabled(context: Context) =
        getPreferences(context).getBoolean(PREF_AD_BLOCKING_ENABLED, true)

    fun setAdBlockingEnabled(context: Context, enabled: Boolean) =
        getPreferences(context).edit().putBoolean(PREF_AD_BLOCKING_ENABLED, enabled).apply()

    fun isDarkModeEnabled(context: Context) =
        getPreferences(context).getBoolean(PREF_DARK_MODE, false)
}
```

---

## Deployment Checklist

- [ ] Package ID changed to `com.nnads.browser`
- [ ] Brave services disabled (Sync, Rewards, Telemetry)
- [ ] TV detection implemented
- [ ] D-Pad navigation working
- [ ] Filter lists configured
- [ ] History database initialized
- [ ] Media playback service running
- [ ] Settings UI accessible
- [ ] Dark mode + RTL (Arabic) support added
- [ ] APK builds without errors

**Next**: Deploy and test on real devices.
