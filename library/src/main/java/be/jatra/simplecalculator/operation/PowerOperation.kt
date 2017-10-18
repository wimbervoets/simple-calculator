package be.jatra.simplecalculator.operation

class PowerOperation constructor(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override val result: Double
        get() {
            var result = Math.pow(baseValue, secondValue)
            if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result))
                result = 0.0
            return result
        }
}
