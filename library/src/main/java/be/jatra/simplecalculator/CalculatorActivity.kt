package be.jatra.simplecalculator

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast

import me.grantland.widget.AutofitHelper

class CalculatorActivity : AbstractActivity(), CalculatorCallback {

    private var mClearButton: TextView? = null
    private var mResult: TextView? = null
    private var mFormula: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_calculator)
        setContentView(R.layout.activity_calculator_port)

        mClearButton = findViewById(R.id.btn_clear)
        mResult = findViewById(R.id.result)
        mFormula = findViewById(R.id.formula)

        mClearButton!!.setOnLongClickListener {
            mCalc!!.handleReset()
            true
        }
        mResult!!.setOnLongClickListener { view -> resultLongPressed(view) }
        mFormula!!.setOnLongClickListener { view -> formulaLongPressed(view) }

        mCalc = CalculatorImpl(this)
        AutofitHelper.create(mResult!!)
        AutofitHelper.create(mFormula!!)

        //set value from incoming intent
        val amount = intent.getStringExtra(IntentConstants.AMOUNT.name)
        if (null != amount) {
//            mFormula!!.text = amount
//            mResult!!.text = amount
            mCalc!!.setValue(amount)
            setValue(amount)
            mCalc!!.setLastKey(Constants.DIGIT)
//            setValueDouble(amount.toDouble())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Config.newInstance(applicationContext).isFirstRun = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item.itemId == R.id.menu_ok) {
            Toast.makeText(this, "... op OK geklikt...", Toast.LENGTH_LONG).show()
            //create intent & finish
            val intent = Intent()
            intent.putExtra(IntentConstants.AMOUNT.name, mResult!!.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    fun plusClicked(v: View) {
        mCalc!!.handleOperation(Constants.PLUS)
    }

    fun minusClicked(v: View) {
        mCalc!!.handleOperation(Constants.MINUS)
    }

    fun multiplyClicked(v: View) {
        mCalc!!.handleOperation(Constants.MULTIPLY)
    }

    fun divideClicked(v: View) {
        mCalc!!.handleOperation(Constants.DIVIDE)
    }

    fun moduloClicked(v: View) {
        mCalc!!.handleOperation(Constants.MODULO)
    }

    fun powerClicked(v: View) {
        mCalc!!.handleOperation(Constants.POWER)
    }

    fun rootClicked(v: View) {
        mCalc!!.handleOperation(Constants.ROOT)
    }

    fun clearClicked(v: View) {
        mCalc!!.handleClear()
    }

    fun clearAllClicked(v: View) {
        mCalc!!.handleClearAll()
    }

    fun equalsClicked(v: View) {
        mCalc!!.handleEquals()
    }

    fun numpadClick(view: View) {
        numpadClicked(view.id)
    }

    fun numpadClicked(id: Int) {
        mCalc!!.numpadClicked(id)
    }

    fun formulaLongPressed(v: View): Boolean {
        return copyToClipboard(false)
    }

    fun resultLongPressed(v: View): Boolean {
        return copyToClipboard(true)
    }

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = mFormula!!.text.toString().trim { it <= ' ' }
        if (copyResult) {
            value = mResult!!.text.toString().trim { it <= ' ' }
        }

        if (value.isEmpty())
            return false

        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(resources.getString(be.jatra.simplecalculator.R.string.app_name), value)
        clipboard.primaryClip = clip
        Utils.showToast(applicationContext, be.jatra.simplecalculator.R.string.copied_to_clipboard)
        return true
    }

    override fun setValue(value: String) {
        mResult!!.text = value
    }

    // used only by Robolectric
    override fun setValueDouble(d: Double) {
        mCalc!!.setValue(Formatter.doubleToString(d))
        mCalc!!.setLastKey(Constants.DIGIT)
    }

    override fun setFormula(value: String) {
        mFormula!!.text = value
    }

    companion object {

        private var mCalc: CalculatorImpl? = null
    }
}
