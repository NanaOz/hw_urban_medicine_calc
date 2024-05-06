package com.example.hw_urban_medicine_calc

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "PACIENTS_DATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "patients_table"
        val KEY_ID = "id"
        val KEY_NAME = "name"
        val KEY_AGE = "age"
        val KEY_IMT = "imt"
        val KEY_RID = "rid"
        val KEY_PD = "pd"
        val KEY_ROIDAD = "roidad"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            ("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, "
                    + KEY_NAME + " TEXT," + KEY_AGE + " TEXT," +  KEY_IMT + " TEXT," +  KEY_RID
                    + " TEXT," +  KEY_PD + " TEXT," +  KEY_ROIDAD + " TEXT)")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

//    fun addPatient(name: String, age: Int, imt: String, rid: String, pd: String, roidad: String){
//        val values = ContentValues()
//        values.put(KEY_NAME, name)
//        values.put(KEY_AGE, age)
//        values.put(KEY_IMT, imt)
//        values.put(KEY_RID, rid)
//        values.put(KEY_PD, pd)
//        values.put(KEY_ROIDAD, roidad)
//        val db = this.writableDatabase
//        db.insert(TABLE_NAME,null, values)
//        db.close()
//    }
fun addPatient(patient: Patient): Long {
    val values = ContentValues()
    values.put(KEY_NAME, patient.name)
    values.put(KEY_AGE, patient.age)
    values.put(KEY_IMT, patient.itm)
    values.put(KEY_RID, patient.rateInfusionDrug)
    values.put(KEY_PD, patient.potassiumDeficiency)
    values.put(KEY_ROIDAD, patient.rateOfIntravenousDripAdministrationDrug)

    val db = this.writableDatabase
    val id = db.insert(TABLE_NAME, null, values)
    db.close()

    return id
}
    fun getInfo(): Cursor? {
        val  db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun updateName(patient: Patient) {
        val values = ContentValues()
        values.put(KEY_NAME, patient.name)
        values.put(KEY_AGE, patient.age)
        values.put(KEY_IMT, patient.itm)
        values.put(KEY_RID, patient.rateInfusionDrug)
        values.put(KEY_PD, patient.potassiumDeficiency)
        values.put(KEY_ROIDAD, patient.rateOfIntravenousDripAdministrationDrug)

        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(patient.id.toString()))
        db.close()
    }
}