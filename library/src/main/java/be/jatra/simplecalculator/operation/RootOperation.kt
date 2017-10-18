package be.jatra.simplecalculator.operation

class RootOperation constructor(value: Double) : UnaryOperation(value), Operation {

    override val result: Double
        get() = Math.sqrt(value)
}
