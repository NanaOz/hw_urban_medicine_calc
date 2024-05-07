package com.example.hw_urban_medicine_calc

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientsBinding
import java.io.Serializable
import java.util.Locale

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

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)

        binding.buttonAddPatient.startAnimation(slideUp)
        binding.recyclerView.startAnimation(slideUp)
        binding.textViewSearch.startAnimation(slideUp)

        dbHelper = DBHelper(requireContext(), null)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = PatientsAdapter(patientList, this)
        binding.recyclerView.adapter = adapter

        readDataFromDatabase()

        val textViewSearch = binding.textViewSearch

        textViewSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().trim().toLowerCase(Locale.getDefault())
                val filteredList = patientList.filter { patient ->
                    patient.name.toLowerCase(Locale.getDefault()).contains(searchText)
                }
                adapter.updateList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedPatient = patientList[position]

                dbHelper.deletePatient(deletedPatient)
                patientList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

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