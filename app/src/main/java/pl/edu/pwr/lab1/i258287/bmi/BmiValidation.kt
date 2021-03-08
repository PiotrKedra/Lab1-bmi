package pl.edu.pwr.lab1.i258287.bmi

import pl.edu.pwr.lab1.i258287.R

object BmiValidation {

    private const val ZERO = "0"

    fun validateMetric(mass: String, height: String): ArrayList<BmiError> {
        val errors = ArrayList<BmiError>()
        if (mass.isEmpty())
            errors.add(
                BmiError(
                    BmiErrorEnum.MASS,
                    R.string.error_mass_empty
                )
            )
        else if (mass == ZERO)
            errors.add(
                BmiError(
                    BmiErrorEnum.MASS,
                    R.string.error_mass_zero
                )
            )

        if (height.isEmpty())
            errors.add(
                BmiError(
                    BmiErrorEnum.HEIGHT,
                    R.string.error_height_empty
                )
            )
        else if (height == ZERO)
            errors.add(
                BmiError(
                    BmiErrorEnum.HEIGHT,
                    R.string.error_height_zero
                )
            )
        return errors
    }

    fun validateImperial(massSt: String, massLb: String, heightFt: String, heightIn: String): ArrayList<BmiError> {
        val errors = ArrayList<BmiError>()
        if (massSt.isEmpty() && massLb.isEmpty())
            errors.add(
                BmiError(
                    BmiErrorEnum.MASS,
                    R.string.error_mass_empty
                )
            )
        else if (massSt == ZERO && massLb == ZERO)
            errors.add(
                BmiError(
                    BmiErrorEnum.MASS,
                    R.string.error_mass_zero
                )
            )
        else if((massSt.isEmpty() && massLb == ZERO) || (massSt == ZERO && massLb.isEmpty() ))
            errors.add(
                BmiError(
                    BmiErrorEnum.MASS,
                    R.string.error_mass_zero
                )
            )

        if (heightFt.isEmpty() && heightIn.isEmpty())
            errors.add(
                BmiError(
                    BmiErrorEnum.HEIGHT,
                    R.string.error_height_empty
                )
            )
        else if (heightFt == ZERO && heightIn == ZERO)
            errors.add(
                BmiError(
                    BmiErrorEnum.HEIGHT,
                    R.string.error_height_zero
                )
            )
        else if((heightFt.isEmpty() && heightIn == ZERO) || (heightFt == ZERO && heightIn.isEmpty()))
            errors.add(
                BmiError(
                    BmiErrorEnum.HEIGHT,
                    R.string.error_height_zero
                )
            )
        return errors
    }
}