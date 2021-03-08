package pl.edu.pwr.lab1.i258287

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.edu.pwr.lab1.i258287.BmiDescriptionSharedData.BMI
import pl.edu.pwr.lab1.i258287.BmiDescriptionSharedData.STATUS
import pl.edu.pwr.lab1.i258287.BmiDescriptionSharedData.STATUS_COLOR


class BmiDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_description)

        val bmi = intent.getStringExtra(BMI)
        val status = intent.getStringExtra(STATUS)
        val statusColor = intent.getIntExtra(STATUS_COLOR, R.color.normal)

        val bmiValue = findViewById<TextView>(R.id.bmiValueDes)
        val bmiStatus = findViewById<TextView>(R.id.bmiStatusDes)
        bmiValue.text = bmi
        bmiStatus.text = status
        bmiStatus.setTextColor(statusColor)
    }
}