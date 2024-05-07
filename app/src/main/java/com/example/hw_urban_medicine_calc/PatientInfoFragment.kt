package com.example.hw_urban_medicine_calc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.hw_urban_medicine_calc.databinding.FragmentPatientInfoBinding

class PatientInfoFragment : Fragment() {

    private lateinit var binding: FragmentPatientInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPatientInfoBinding.inflate(inflater, container, false)
        val view = binding.root

        val toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

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