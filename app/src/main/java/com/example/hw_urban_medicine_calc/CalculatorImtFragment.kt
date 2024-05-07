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


class CalculatorImtFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorImtBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var patientsList: List<Patient>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculatorImtBinding.inflate(inflater, container, false)
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
            val height = binding.editTextHeight.text.toString().toDoubleOrNull()

            if (weight != null && height != null) {
                val result = calculateImt(weight, height)
                binding.textViewResult.text = result.toString()

                selectedPatient.itm = result.toString()
                dbHelper.updateName(selectedPatient)
            } else {
                Toast.makeText(requireContext(), "Введите вес и рост", Toast.LENGTH_SHORT).show()
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
    private fun calculateImt(weight: Double, height: Double): Double {
        val heightInMeters = height / 100
        return weight / (heightInMeters * heightInMeters)
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