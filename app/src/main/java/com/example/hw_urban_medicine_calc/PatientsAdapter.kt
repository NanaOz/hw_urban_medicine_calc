package com.example.hw_urban_medicine_calc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PatientsAdapter(private var patientList: List<Patient>, private var itemClickListener: PatientItemClickListener) : RecyclerView.Adapter<PatientsAdapter.PatientViewHolder>() {

    interface PatientItemClickListener{
        fun onPatientItemClick(patient: Patient, position: Int)
    }
    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val ageTextView: TextView = itemView.findViewById(R.id.textViewAge)

        init {
            itemView.setOnClickListener {
                itemClickListener.onPatientItemClick(patientList[adapterPosition], adapterPosition)
            }
        }
        fun bind(patient: Patient) {
            nameTextView.text = patient.name
            ageTextView.text = patient.age.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_patients, parent, false)
        return PatientViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val patient = patientList[position]
        holder.bind(patient)
    }

    override fun getItemCount(): Int {
        return patientList.size
    }
    fun updateList(newList: List<Patient>) {
        patientList = newList
        notifyDataSetChanged()
    }
}
