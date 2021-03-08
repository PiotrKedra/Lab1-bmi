package pl.edu.pwr.lab1.i258287.bmi

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class BmiCounterMetricTest: StringSpec() {
    init {
        "For mass or height equals 0 should throw exception"{
            shouldThrow<IllegalArgumentException> { BmiService.countBmiMetric(0f, 0f) }
        }

        "For mass and height equals 1 BMI should be 1"{
            BmiService.countBmiMetric(1f, 1f) shouldBe 1f
        }

        "For mass equals 75 and height 1.85 bmi should be 21.9"{
            BmiService.countBmiMetric(75f, 1.85f) shouldBe 21.9f
        }
    }
}