package com.example.hw_urban_medicine_calc

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculatorImtBinding
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculatorRateOfIntravenousDripAdministrationDrugBinding

class CalculatorRateOfIntravenousDripAdministrationDrugFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorRateOfIntravenousDripAdministrationDrugBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var patientsList: List<Patient>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorRateOfIntravenousDripAdministrationDrugBinding.inflate(inflater, container, false)
        val view = binding.root

        dbHelper = DBHelper(requireContext(), null)
        patientsList = dbHelper.getInfo()?.let { getPatientsFromCursor(it) } ?: emptyList()

        val patientNames = patientsList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, patientNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPatients.adapter = adapter

        binding.buttonMakeCalculate.setOnClickListener {
            val selectedPatient = patientsList[binding.spinnerPatients.selectedItemPosition]
            val volume = binding.editTextVolume.text.toString().toDoubleOrNull()
            val times = binding.editTextTime.text.toString().toDoubleOrNull()

            if (volume != null && times != null) {
                val result = calculateRateOfIntravenousDripAdministrationDrug(volume, times)
                binding.textViewResult.text = result.toString()

                selectedPatient.rateOfIntravenousDripAdministrationDrug = result.toString()
                dbHelper.updateName(selectedPatient)
            } else {
                Toast.makeText(requireContext(), "Введите объем раствора(мл) и время(мин)", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonBack.setOnClickListener {
            val fragmentManager = requireFragmentManager()
            val calculateFragment = CalculateFragment()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, calculateFragment)
                .commit()
        }

        return view
    }
    private fun calculateRateOfIntravenousDripAdministrationDrug(volume: Double, times: Double): Double {
        val res = (volume * 20) / times
        return res
    }

    @SuppressLint("Range")
    private fun getPatientsFromCursor(cursor: Cursor): List<Patient> {
        val patients = mutableListOf<Patient>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID))
            val name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))
            val age = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_AGE))
            val itm = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_IMT))
            val rateInfusionDrug = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_RID))
            val potassiumDeficiency = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PD))
            val rateOfIntravenousDripAdministrationDrug = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ROIDAD))

            val patient = Patient(id, name, age, itm, rateInfusionDrug, potassiumDeficiency, rateOfIntravenousDripAdministrationDrug)
            patients.add(patient)
        }
        cursor.close()
        return patients
    }

}