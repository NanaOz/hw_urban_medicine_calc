package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientAddBinding
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientsBinding

class PatientsFragment : Fragment() {

    private lateinit var binding: FragmentPatientsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientsBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentManager = requireFragmentManager()

        binding.buttonAddPatient.setOnClickListener {
            val patientAddFragment = PatientAddFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, patientAddFragment)
                .commit()
        }

        return view
    }

}