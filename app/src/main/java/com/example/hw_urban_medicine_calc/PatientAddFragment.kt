package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientAddBinding

class PatientAddFragment : Fragment() {

    private lateinit var binding: FragmentPatientAddBinding
    private lateinit var dbHelper: DBHelper

    private var patientList = mutableListOf<Patient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientAddBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentManager = requireFragmentManager()

        dbHelper = DBHelper(requireContext(), null)
        patientList = (arguments?.getSerializable("patientList") as? ArrayList<Patient>)!!


        binding.buttonAddPatient.setOnClickListener {
            val name = binding.editTextNamePatient.text.toString()
            val ageStr = binding.editTextAge.text.toString()
            if (name.isNotEmpty() && ageStr.isNotEmpty()) {
                val age = ageStr.toInt()
                val newPatient = Patient( patientList.size , name, age, "0.0", "0.0", "0.0", "0.0")
                patientList.add(newPatient)

                dbHelper.addPatient( newPatient )

                Toast.makeText(requireContext(), "Добавлен пациент: ${patientList.size } ${name}", Toast.LENGTH_SHORT).show()
                val patientsFragment = PatientsFragment()
//                patientsFragment.patientList = patientList
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, patientsFragment)
                    .commit()

            } else {
                Toast.makeText(requireContext(), "Пожалуйста введите Имя и возраст", Toast.LENGTH_SHORT).show()
            }
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