package dev.yjyoon.kwnotice.view.setting

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.util.Constants.Companion.FCM_TOPICS

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        if(key in FCM_TOPICS) {
            if(prefs!!.getBoolean(key, true)) {
                Firebase.messaging.subscribeToTopic(key!!)
            }
            else {
                Firebase.messaging.unsubscribeFromTopic(key!!)
            }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when(preference?.key) {
            "dev" -> { startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://github.com/yjyoon-dev/kw-notice"))) }
            "ver" -> { startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=dev.yjyoon.kwnotice"))) }
            else -> {}
        }

        return super.onPreferenceTreeClick(preference)
    }
}