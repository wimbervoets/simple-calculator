package be.jatra.simplecalculator

interface CalculatorCallback {
    fun setValue(value: String)

    fun setValueDouble(d: Double)

    fun setFormula(value: String)
}
