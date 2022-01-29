package dev.yjyoon.kwnotice.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import dev.yjyoon.kwnotice.R
import dev.yjyoon.kwnotice.databinding.ActivityMainBinding
import dev.yjyoon.kwnotice.view.notice.NoticeFragment
import dev.yjyoon.kwnotice.view.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val noticeFragment = NoticeFragment()
    private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.bottomNavigationView.run {
            setOnItemSelectedListener {
                changeFragment(
                    when(it.itemId) {
                        R.id.notice -> { noticeFragment }
                        R.id.favorite -> { noticeFragment }
                        R.id.setting -> { settingFragment }
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