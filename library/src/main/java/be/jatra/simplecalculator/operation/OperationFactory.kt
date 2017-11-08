package be.jatra.simplecalculator.operation

import be.jatra.simplecalculator.Constants

object OperationFactory {

    fun forId(id: String?, baseValue: Double, secondValue: Double): Operation? {
        when (id) {
            Constants.PLUS -> return PlusOperation(baseValue, secondValue)
            Constants.MINUS -> return MinusOperation(baseValue, secondValue)
            Constants.DIVIDE -> return DivideOperation(baseValue, secondValue)
            Constants.MULTIPLY -> return MultiplyOperation(baseValue, secondValue)
            Constants.MODULO -> return ModuloOperation(baseValue, secondValue)
            Constants.POWER -> return PowerOperation(baseValue, secondValue)
            Constants.ROOT -> return RootOperation(baseValue)
            else -> return null
        }
    }
}
