package pl.edu.pwr.lab1.i258287.settings

import android.content.SharedPreferences
import pl.edu.pwr.lab1.i258287.settings.SettingConstants.DATA_TYPE
import pl.edu.pwr.lab1.i258287.settings.SettingConstants.IMPERIAL_DATA_TYPE
import pl.edu.pwr.lab1.i258287.settings.SettingConstants.IS_FIRST_RUN
import pl.edu.pwr.lab1.i258287.settings.SettingConstants.METRIC_DATA_TYPE

class Settings(private val settings: SharedPreferences) {

    init {
        val isFirstRun = settings.getBoolean(IS_FIRST_RUN, true)
        if (isFirstRun) {
            val editor = settings.edit()
            editor.putString(DATA_TYPE, METRIC_DATA_TYPE)
            editor.putBoolean(IS_FIRST_RUN, false)
            editor.apply()
        }
    }

    fun setMetricDataType(){
        setDataType(METRIC_DATA_TYPE)
    }

    fun setImperialDataType(){
        setDataType(IMPERIAL_DATA_TYPE)
    }

    private fun setDataType(dataType: String){
        val editor = settings.edit()
        editor.putString(DATA_TYPE, dataType)
        editor.apply()
    }

    fun getCurrentDataType(): String? {
        return settings.getString(DATA_TYPE, METRIC_DATA_TYPE)
    }
}