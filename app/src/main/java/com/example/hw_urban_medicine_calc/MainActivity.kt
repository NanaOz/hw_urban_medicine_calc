package com.example.hw_urban_medicine_calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var calculateFragment: CalculateFragment
    private lateinit var patientsFragment: PatientsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculateFragment = CalculateFragment()
        patientsFragment = PatientsFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, calculateFragment)
            .commit()

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_calculate -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, calculateFragment)
                        .commit()
                    true
                }
                R.id.menu_patients -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, patientsFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}