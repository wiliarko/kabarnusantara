package com.kabar.nusantara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAction(savedInstanceState)
    }

    fun initAction(savedInstanceState: Bundle?){
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadHomeFragment(savedInstanceState)
                }
                R.id.navigation_kontribusi-> {
                    loadHistoryFragment(savedInstanceState)
                }
                R.id.navigation_event-> {
                    loadTokoFragment(savedInstanceState)
                }
                R.id.navigation_redaksi-> {
                    loadAkunFragment(savedInstanceState)
                }
            }
            true
        }
        navigation.selectedItemId = R.id.navigation_home
    }
}
