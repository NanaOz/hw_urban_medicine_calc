package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientAddBinding

class PatientAddFragment : Fragment() {

    private lateinit var binding: FragmentPatientAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientAddBinding.inflate(inflater, container, false)
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