package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientInfoBinding

class PatientInfoFragment : Fragment() {

    private lateinit var binding: FragmentPatientInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentManager = requireFragmentManager()

        binding.buttonBack.setOnClickListener {
            val patientFragment =PatientsFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, patientFragment)
                .commit()
        }

        return view
    }


}