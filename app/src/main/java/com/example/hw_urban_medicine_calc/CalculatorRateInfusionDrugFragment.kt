package com.example.hw_urban_medicine_calc

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculatorImtBinding
import com.example.hw_urban_medicine_calc.databinding.FragmentCalculatorRateInfusionDrugBinding

class CalculatorRateInfusionDrugFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorRateInfusionDrugBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var patientsList: List<Patient>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorRateInfusionDrugBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        dbHelper = DBHelper(requireContext(), null)
        patientsList = dbHelper.getInfo()?.let { getPatientsFromCursor(it) } ?: emptyList()

        val patientNames = patientsList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, patientNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPatients.adapter = adapter

        binding.buttonMakeCalculate.setOnClickListener {
            val selectedPatient = patientsList[binding.spinnerPatients.selectedItemPosition]
            val weight = binding.editTextWeight.text.toString().toDoubleOrNull()
            val dose = binding.editTextDose.text.toString().toDoubleOrNull()
            val count = binding.editTextCount.text.toString().toDoubleOrNull()
            val volume = binding.editTextVolume.text.toString().toDoubleOrNull()

            if (weight != null && dose != null && count != null && volume != null) {
                val result = calculateRateInfusionDrug(weight, dose, count, volume)
                binding.textViewResult.text = result.toString()

                selectedPatient.rateInfusionDrug = result.toString()
                dbHelper.updateName(selectedPatient)
            } else {
                Toast.makeText(requireContext(), "Введите данные", Toast.LENGTH_SHORT).show()
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

    private fun calculateRateInfusionDrug(weight: Double, dose: Double, count: Double, volume: Double): Double {
        val res = weight * dose / (count * (1000  / volume)) * 60
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