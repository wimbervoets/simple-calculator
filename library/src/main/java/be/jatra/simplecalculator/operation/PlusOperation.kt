package be.jatra.simplecalculator.operation

class PlusOperation constructor(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override val result: Double
        get() = baseValue + secondValue
}
