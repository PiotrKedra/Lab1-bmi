package pl.edu.pwr.lab1.i258287.bmi

import pl.edu.pwr.lab1.i258287.R
import kotlin.math.roundToInt

object BmiService {

    private const val ZERO_FLOAT = 0F
    private const val FOOT_CONVERT = 0.3048F
    private const val INCHES_CONVERT = 0.0254F
    private const val STONES_CONVERT = 6.35029318F
    private const val POUNDS_CONVERT = 0.45359237F
    private const val BMI_UNDERWEIGHT = 18.5F
    private const val BMI_NORMAL = 25F
    private const val BMI_OVERWEIGHT = 30F

    fun countBmiImperial(massSt: String, massLb: String, heightFt: String, heightIn: String): Float {
        val mass = convertToKg(massSt, massLb)
        val height = convertToMeter(heightFt, heightIn)
        return countBmi(mass, height)
    }

    private fun convertToMeter(heightFt: String, heightIn: String): Float {
        val foot = if (heightFt.isEmpty()) ZERO_FLOAT else heightFt.toFloat()
        val inches = if (heightIn.isEmpty()) ZERO_FLOAT else heightIn.toFloat()
        return (foot*FOOT_CONVERT) + (inches*INCHES_CONVERT)
    }

    private fun convertToKg(massSt: String, massLb: String): Float {
        val stones = if (massSt.isEmpty()) ZERO_FLOAT else massSt.toFloat()
        val pounds = if (massLb.isEmpty()) ZERO_FLOAT else massLb.toFloat()
        return (stones*STONES_CONVERT) + (pounds*POUNDS_CONVERT)
    }

    fun countBmiMetric(mass: Float, height: Float): Float {
        return countBmi(mass, height)
    }

    private fun countBmi(massBmi: Float, heightBmi: Float) =
            roundBmi((massBmi / (heightBmi * heightBmi)))

    private fun roundBmi(bmi: Float): Float {
        return (bmi * 10).roundToInt() /10F
    }

    fun getBmiStatus(bmi: Float): BmiStatus {
        return when {
            bmi < BMI_UNDERWEIGHT -> BmiStatus(R.string.underweight, R.color.warning)
            bmi < BMI_NORMAL -> BmiStatus(R.string.normal, R.color.normal)
            bmi < BMI_OVERWEIGHT -> BmiStatus(R.string.overweight, R.color.warning)
            else -> BmiStatus(R.string.obesity, R.color.error)
        }
    }
}