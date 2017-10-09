package be.jatra.simplecalculator

import be.jatra.simplecalculator.operation.OperationFactory

class CalculatorImpl {
    private var displayedNumber: String? = null
//        private set
    private var displayedFormula: String? = null
//        private set
    private var mLastKey: String? = null
    private var mLastOperation: String? = null
    private var mCallback: CalculatorCallback? = null

    private var mIsFirstOperation: Boolean = false
    private var mResetValue: Boolean = false
    private var mBaseValue: Double = 0.toDouble()
    private var mSecondValue: Double = 0.toDouble()

    constructor(calculator: CalculatorCallback) {
        mCallback = calculator
        resetValues()
        setValue("0")
        setFormula("")
    }

    constructor(calculator: CalculatorCallback, value: String) {
        mCallback = calculator
        resetValues()
        displayedNumber = value
        setFormula("")
    }

    private fun resetValueIfNeeded() {
        if (mResetValue)
            displayedNumber = "0"

        mResetValue = false
    }

    private fun resetValues() {
        mBaseValue = 0.0
        mSecondValue = 0.0
        mResetValue = false
        mLastKey = ""
        mLastOperation = ""
        displayedNumber = ""
        displayedFormula = ""
        mIsFirstOperation = true
    }

    fun setValue(value: String) {
        mCallback!!.setValue(value)
        displayedNumber = value
    }

    private fun setFormula(value: String) {
        mCallback!!.setFormula(value)
        displayedFormula = value
    }

    private fun updateFormula() {
        val first = Formatter.doubleToString(mBaseValue)
        val second = Formatter.doubleToString(mSecondValue)
        val sign = getSign(mLastOperation)

        if (sign == "√") {
            setFormula(sign + first)
        } else if (!sign.isEmpty()) {
            setFormula(first + sign + second)
        }
    }

    fun setLastKey(mLastKey: String) {
        this.mLastKey = mLastKey
    }

    fun addDigit(number: Int) {
        val currentValue = displayedNumber
        val newValue = formatString(currentValue!! + number)
        setValue(newValue)
    }

    private fun formatString(str: String): String {
        // if the number contains a decimal, do not try removing the leading zero anymore, nor add group separator
        // it would prevent writing values like 1.02
        if (str.contains("."))
            return str

        val doubleValue = Formatter.stringToDouble(str)
        return Formatter.doubleToString(doubleValue)
    }

    private fun updateResult(value: Double) {
        setValue(Formatter.doubleToString(value))
        mBaseValue = value
    }

    val displayedNumberAsDouble: Double
        get() = Formatter.stringToDouble(displayedNumber)

    fun handleResult() {
        mSecondValue = displayedNumberAsDouble
        calculateResult()
        mBaseValue = displayedNumberAsDouble
    }

    fun calculateResult() {
        if (!mIsFirstOperation) {
            updateFormula()
        }

        val operation = OperationFactory.forId(mLastOperation, mBaseValue, mSecondValue)

        if (operation != null) {
            updateResult(operation.result)
        }

        mIsFirstOperation = false
    }

    fun handleOperation(operation: String) {
        if (mLastKey == Constants.DIGIT)
            handleResult()

        mResetValue = true
        mLastKey = operation
        mLastOperation = operation

        if (operation == Constants.ROOT)
            calculateResult()
    }

    fun handleClear() {
        val oldValue = displayedNumber
        var newValue = "0"
        val len = oldValue!!.length
        var minLen = 1
        if (oldValue.contains("-"))
            minLen++

        if (len > minLen)
            newValue = oldValue.substring(0, len - 1)

        newValue = newValue.replace("\\.$".toRegex(), "")
        newValue = formatString(newValue)
        setValue(newValue)
        mBaseValue = Formatter.stringToDouble(newValue)
    }

    fun handleClearAll() {
        handleReset()
    }

    fun handleReset() {
        resetValues()
        setValue("0")
        setFormula("")
    }

    fun handleEquals() {
        if (mLastKey == Constants.EQUALS)
            calculateResult()

        if (mLastKey != Constants.DIGIT)
            return

        mSecondValue = displayedNumberAsDouble
        calculateResult()
        mLastKey = Constants.EQUALS
    }

    fun decimalClicked() {
        var value = displayedNumber
        if (!value!!.contains("."))
            value += "."
        setValue(value)
    }

    fun zeroClicked() {
        val value = displayedNumber
        if (value != "0")
            addDigit(0)
    }

    private fun getSign(lastOperation: String?): String {
        when (lastOperation) {
            Constants.PLUS -> return "+"
            Constants.MINUS -> return "-"
            Constants.MULTIPLY -> return "*"
            Constants.DIVIDE -> return "/"
            Constants.MODULO -> return "%"
            Constants.POWER -> return "^"
            Constants.ROOT -> return "√"
        }
        return ""
    }

    fun numpadClicked(id: Int) {
        if (mLastKey == Constants.EQUALS)
            mLastOperation = Constants.EQUALS
        mLastKey = Constants.DIGIT
        resetValueIfNeeded()

        when (id) {
            R.id.btn_decimal -> decimalClicked()
            R.id.btn_0 -> zeroClicked()
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
            else -> {
            }
        }
    }
}
