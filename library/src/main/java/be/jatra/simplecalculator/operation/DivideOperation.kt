package be.jatra.simplecalculator.operation

class DivideOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override val result: Double
        get() {
            var result = 0.0
            if (secondValue != 0.0) {
                result = baseValue / secondValue
            }
            return result
        }
}
