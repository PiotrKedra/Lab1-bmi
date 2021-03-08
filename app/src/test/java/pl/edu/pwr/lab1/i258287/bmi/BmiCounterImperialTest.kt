package pl.edu.pwr.lab1.i258287.bmi

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class BmiCounterImperialTest: StringSpec() {
    init {
        "For mass or height equals 0 should throw exception"{
            shouldThrow<IllegalArgumentException> { BmiService.countBmiImperial("0", "0", "0", "0") }
        }

        "For mass or height equals empty string should throw exception"{
            shouldThrow<IllegalArgumentException> { BmiService.countBmiImperial("", "", "", "") }
        }

        "For mass equals 11st, 11lb and height 6ft, 1in bmi should be 21,8"{
            BmiService.countBmiImperial("11", "11", "6", "1") shouldBe 21.8f
        }
    }
}