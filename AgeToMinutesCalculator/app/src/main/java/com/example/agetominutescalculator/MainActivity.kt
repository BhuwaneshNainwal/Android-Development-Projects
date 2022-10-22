package com.example.agetominutescalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.time.Month
import java.util.*

class MainActivity : AppCompatActivity() {

    var tvSelectedDate: TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the button that would be used to select date
        val datePicker : Button = findViewById(R.id.buttonDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        datePicker.setOnClickListener(){
            clickDatePicker()
        }
    }

    fun clickDatePicker()
    {
        val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)

        // DatePickerDialog()
        DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "Date Picker Works", Toast.LENGTH_LONG).show()

            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            tvSelectedDate?.setText(selectedDate)
        },
                year,
                month,
                day
                ).show()
        Toast.makeText(this, "btndatePicker pressed", Toast.LENGTH_LONG).show()
    }
}