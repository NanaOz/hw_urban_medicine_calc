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

        val patient = arguments?.getSerializable("patient") as? Patient
        if (patient != null) {
            binding.textViewName.text = patient.name
            binding.textViewAge.text = patient.age.toString()
            binding.textViewIMT.text = patient.itm
            binding.textViewRateInfusionDrug.text = patient.rateInfusionDrug
            binding.textViewPotassiumDeficiency.text = patient.potassiumDeficiency
            binding.textViewRateOfIntravenousDripAdministrationDrug.text = patient.rateOfIntravenousDripAdministrationDrug
        }

        binding.buttonBack.setOnClickListener {
            val patientFragment =PatientsFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, patientFragment)
                .commit()
        }

        return view
    }


}