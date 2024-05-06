package com.example.hw_urban_medicine_calc

import android.os.Parcelable
import java.io.Serializable

class Patient(
    var id: Int,
    var name: String,
    var age: Int,
    var itm: String,
    var rateInfusionDrug: String,
    var potassiumDeficiency: String,
    var rateOfIntravenousDripAdministrationDrug: String
): Serializable