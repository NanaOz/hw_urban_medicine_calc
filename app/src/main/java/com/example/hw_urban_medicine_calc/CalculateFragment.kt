package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculateBinding


class CalculateFragment : Fragment() {

    private lateinit var binding: FragmentCalculateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculateBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        val fragmentManager = requireFragmentManager()

        binding.buttonIMT.setOnClickListener {
            val calculatorImtFragment = CalculatorImtFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, calculatorImtFragment)
                .commit()
        }

        binding.buttonRateInfusionDrug.setOnClickListener {
            val calculatorRateInfusionDrugFragment = CalculatorRateInfusionDrugFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, calculatorRateInfusionDrugFragment)
                .commit()
        }

        binding.buttonPotassiumDeficiency.setOnClickListener {
            val calculatePotassiumDeficiencyFragment = CalculatorPotassiumDeficiencyFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, calculatePotassiumDeficiencyFragment)
                .commit()
        }

        binding.buttonRateOfIntravenousDripAdministrationDrug.setOnClickListener {
            val calculateRateOfIntravenousDripAdministrationDrug = CalculatorRateOfIntravenousDripAdministrationDrugFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, calculateRateOfIntravenousDripAdministrationDrug)
                .commit()
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> {
                requireActivity().finishAffinity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}