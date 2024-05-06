package com.example.hw_urban_medicine_calc

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientsBinding
import java.io.Serializable

class PatientsFragment : Fragment(), PatientsAdapter.PatientItemClickListener {

    private lateinit var binding: FragmentPatientsBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var adapter: PatientsAdapter
    var patientList = mutableListOf<Patient>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientsBinding.inflate(inflater, container, false)
        val view = binding.root

        dbHelper = DBHelper(requireContext(), null)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PatientsAdapter(patientList, this)
        binding.recyclerView.adapter = adapter

        readDataFromDatabase()

        binding.buttonAddPatient.setOnClickListener {
            val patientAddFragment = PatientAddFragment()

            val bundle = Bundle()
            bundle.putSerializable("patientList", patientList as Serializable)
            patientAddFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, patientAddFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
    @SuppressLint("Range")
    private fun readDataFromDatabase() {
        val cursor = dbHelper.getInfo()
        patientList.clear()
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(DBHelper.KEY_ID))
                val name = it.getString(it.getColumnIndex(DBHelper.KEY_NAME))
                val age = it.getInt(it.getColumnIndex(DBHelper.KEY_AGE))
                val imt = it.getString(it.getColumnIndex(DBHelper.KEY_IMT))
                val rid = it.getString(it.getColumnIndex(DBHelper.KEY_RID))
                val pd = it.getString(it.getColumnIndex(DBHelper.KEY_PD))
                val roidad = it.getString(it.getColumnIndex(DBHelper.KEY_ROIDAD))
                patientList.add(Patient(id, name, age, imt, rid, pd, roidad))
            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onPatientItemClick(patient: Patient, position: Int) {
        val patientInfoFragment = PatientInfoFragment()

        val bundle = Bundle()
        bundle.putSerializable("patient", patient)
        patientInfoFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, patientInfoFragment)
            .addToBackStack(null)
            .commit()
    }
}