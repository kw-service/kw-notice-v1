package dev.yjyoon.kwnotice.view.setting

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dev.yjyoon.kwnotice.R

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
        if(key == "kw-home-new" || key == "kw-home-edit" || key == "sw-central-new") {
            if(prefs!!.getBoolean(key, true)) {
                Firebase.messaging.subscribeToTopic(key)
                Log.d("fcm", "subscribed$key")
            }
            else {
                Firebase.messaging.unsubscribeFromTopic(key)
                Log.d("fcm", "UN subscribed$key")
            }
        }
    }
}