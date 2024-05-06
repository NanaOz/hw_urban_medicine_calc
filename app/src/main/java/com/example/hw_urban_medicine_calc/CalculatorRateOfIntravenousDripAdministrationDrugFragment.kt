package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculatorImtBinding
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculatorRateOfIntravenousDripAdministrationDrugBinding

class CalculatorRateOfIntravenousDripAdministrationDrugFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorRateOfIntravenousDripAdministrationDrugBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorRateOfIntravenousDripAdministrationDrugBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentManager = requireFragmentManager()

        binding.buttonBack.setOnClickListener {
            val calculateFragment = CalculateFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, calculateFragment)
                .commit()
        }

        return view


    }


}