package pl.edu.pwr.lab1.i258287

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import pl.edu.pwr.lab1.i258287.BmiDescriptionSharedData.BMI
import pl.edu.pwr.lab1.i258287.BmiDescriptionSharedData.STATUS
import pl.edu.pwr.lab1.i258287.BmiDescriptionSharedData.STATUS_COLOR
import pl.edu.pwr.lab1.i258287.bmi.BmiError
import pl.edu.pwr.lab1.i258287.bmi.BmiErrorEnum
import pl.edu.pwr.lab1.i258287.bmi.BmiService
import pl.edu.pwr.lab1.i258287.bmi.BmiValidation
import pl.edu.pwr.lab1.i258287.settings.SettingConstants.MAIN_SETTINGS
import pl.edu.pwr.lab1.i258287.settings.SettingConstants.METRIC_DATA_TYPE
import pl.edu.pwr.lab1.i258287.settings.Settings


class MainActivity : AppCompatActivity() {

    private lateinit var massError: TextView
    private lateinit var heightError: TextView
    private lateinit var massInput: EditText
    private lateinit var heightInput: EditText
    private lateinit var bmiValue: TextView
    private lateinit var bmiBtn: Button
    private lateinit var massTitle: TextView
    private lateinit var heightTitle: TextView
    private lateinit var bmiStatus: TextView
    private lateinit var imperialMassContainer: ConstraintLayout
    private lateinit var imperialHeightContainer: ConstraintLayout
    private lateinit var poundInput: EditText
    private lateinit var inchInput: EditText

    private lateinit var settings: Settings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSharedPreferences()
        initLayoutElements()
        initListeners()
        displayMetricOrImperialForm()
    }

    private fun initSharedPreferences() {
        val sharedPreferences = getSharedPreferences(MAIN_SETTINGS, Context.MODE_PRIVATE)
        settings = Settings(sharedPreferences)
    }

    private fun initLayoutElements() {
        massError = findViewById(R.id.massError)
        heightError = findViewById(R.id.heightError)
        massInput = findViewById(R.id.massInput)
        heightInput = findViewById(R.id.heightInput)
        bmiValue = findViewById(R.id.bmiValue)
        bmiBtn = findViewById(R.id.bmiBtn)
        massTitle = findViewById(R.id.massTextView)
        heightTitle = findViewById(R.id.heightTextView)
        bmiStatus = findViewById(R.id.bmiStatus)
        imperialMassContainer = findViewById(R.id.imperialMassContainer)
        imperialHeightContainer = findViewById(R.id.imperialHeightContainer)
        poundInput = findViewById(R.id.poundInput)
        inchInput = findViewById(R.id.inchInput)
    }

    private fun initListeners() {
        massInput.addTextChangedListener { massError.setText(R.string.empty) }
        heightInput.addTextChangedListener { heightError.setText(R.string.empty) }
    }

    private fun displayMetricOrImperialForm() {
        if (settings.getCurrentDataType() == METRIC_DATA_TYPE)
            displayMetricForm()
        else
            displayImperialForm()
    }

    fun countBmi(view: View) {
        cleanErrors()
        if (settings.getCurrentDataType() == METRIC_DATA_TYPE)
            countBmiByMetric()
        else
            countBmiByImperial()
    }

    private fun cleanErrors() {
        massError.setText(R.string.empty)
        heightError.setText(R.string.empty)
    }

    private fun countBmiByMetric() {
        val mass = massInput.text.toString()
        val height = heightInput.text.toString()
        val validation = BmiValidation.validateMetric(mass, height)
        if (validation.isNotEmpty())
            displayErrors(validation)
        else
            displayBmiMetric(mass, height)
    }

    private fun displayErrors(validation: ArrayList<BmiError>) {
        for (error: BmiError in validation)
            if (BmiErrorEnum.MASS == error.type) {
                massError.text = getString(error.message)
            } else {
                heightError.text = getString(error.message)
            }
    }

    private fun displayBmiMetric(mass: String, height: String) {
        val bmi = BmiService.countBmiMetric(mass.toFloat(), height.toFloat())
        bmiValue.text = bmi.toString()
        displayStatus(bmi)
    }

    private fun displayStatus(bmi: Float) {
        val bmiStatusIds = BmiService.getBmiStatus(bmi)
        bmiStatus.text = getString(R.string.bmi_status)
            .plus(getString(bmiStatusIds.status))
        bmiStatus.setTextColor(ContextCompat.getColor(this, bmiStatusIds.color))
    }

    private fun countBmiByImperial() {
        val massSt = massInput.text.toString()
        val massLb = poundInput.text.toString()
        val heightFt = heightInput.text.toString()
        val heightIn = inchInput.text.toString()
        val validation = BmiValidation.validateImperial(massSt, massLb, heightFt, heightIn)
        if (validation.isNotEmpty())
            displayErrors(validation)
        else
            displayBmiImperial(massSt, massLb, heightFt, heightIn)
    }

    private fun displayBmiImperial(massSt: String, massLb: String, heightFt: String, heightIn: String) {
        val bmi = BmiService.countBmiImperial(massSt, massLb, heightFt, heightIn)
        bmiValue.text = bmi.toString()
        displayStatus(bmi)
    }

    fun showBmiDescriptionActivity(view: View){
        val intent = Intent(this, BmiDescriptionActivity::class.java)
        intent.putExtra(BMI, bmiValue.text)
        intent.putExtra(STATUS, bmiStatus.text)
        intent.putExtra(STATUS_COLOR, bmiStatus.currentTextColor)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.metric-> {
                settings.setMetricDataType()
                displayMetricForm()
                true
            }
            R.id.imperial -> {
                settings.setImperialDataType()
                displayImperialForm()
                true
            }
            R.id.about_author -> {
                val intent = Intent(this, AboutAuthorActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayMetricForm() {
        setMetricTitles()
        imperialMassContainer.visibility = INVISIBLE
        imperialHeightContainer.visibility = INVISIBLE
    }

    private fun setMetricTitles() {
        massTitle.text = getString(R.string.mass)
            .plus(getString(R.string.metric_mass))
        heightTitle.text = getString(R.string.height)
            .plus(getString(R.string.metric_height))
    }

    private fun displayImperialForm() {
        setImperialTitles()
        imperialMassContainer.visibility = VISIBLE
        imperialHeightContainer.visibility = VISIBLE
    }

    private fun setImperialTitles() {
        massTitle.text = getString(R.string.mass)
            .plus(getString(R.string.imperial_mass))
        heightTitle.text = getString(R.string.height)
            .plus(getString(R.string.imperial_height))
    }
}