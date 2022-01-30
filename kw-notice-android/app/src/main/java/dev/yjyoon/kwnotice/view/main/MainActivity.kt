package dev.yjyoon.kwnotice.view.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.sleep
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.databinding.ActivityMainBinding
import dev.yjyoon.kwnotice.view.notice.NoticeFragment
import dev.yjyoon.kwnotice.view.setting.SettingsFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val noticeFragment = NoticeFragment()
    private val settingsFragment = SettingsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        settingOnFirstLaunch()

        setTheme(R.style.Theme_KWNotice)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigationBar()
    }

    private fun settingOnFirstLaunch() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val first = prefs.getBoolean("first", true)

        if(first) {
            val topics = listOf("kw-home-new", "kw-home-edit", "sw-central-new")

            for(topic in topics) {
                prefs.edit().putBoolean(topic, true).apply()
                Firebase.messaging.subscribeToTopic(topic)
            }

            prefs.edit().putBoolean("first", false).apply()
        }
    }

    private fun initNavigationBar() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener {
                changeFragment(
                    when(it.itemId) {
                        R.id.notice -> { noticeFragment }
                        R.id.favorite -> { noticeFragment }
                        R.id.setting -> { settingsFragment }
                        else -> { noticeFragment }
                    }
                )
                true
            }
            selectedItemId = R.id.notice
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.frameLayout.id, fragment)
            .commit()
    }

}