package be.jatra.simplecalculator

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.view.View
import android.widget.RemoteViews

class CalculatorProvider : AppWidgetProvider(), CalculatorCallback {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        initVariables(context)

        mIntent = Intent(context, CalculatorProvider::class.java)
        setupIntent(Constants.DECIMAL, R.id.btn_decimal)
        setupIntent(Constants.ZERO, R.id.btn_0)
        setupIntent(Constants.ONE, R.id.btn_1)
        setupIntent(Constants.TWO, R.id.btn_2)
        setupIntent(Constants.THREE, R.id.btn_3)
        setupIntent(Constants.FOUR, R.id.btn_4)
        setupIntent(Constants.FIVE, R.id.btn_5)
        setupIntent(Constants.SIX, R.id.btn_6)
        setupIntent(Constants.SEVEN, R.id.btn_7)
        setupIntent(Constants.EIGHT, R.id.btn_8)
        setupIntent(Constants.NINE, R.id.btn_9)

        setupIntent(Constants.EQUALS, R.id.btn_equals)
        setupIntent(Constants.PLUS, R.id.btn_plus)
        setupIntent(Constants.MINUS, R.id.btn_minus)
        setupIntent(Constants.MULTIPLY, R.id.btn_multiply)
        setupIntent(Constants.DIVIDE, R.id.btn_divide)
        setupIntent(Constants.MODULO, R.id.btn_modulo)
        setupIntent(Constants.POWER, R.id.btn_power)
        setupIntent(Constants.ROOT, R.id.btn_root)
        setupIntent(Constants.CLEAR, R.id.btn_clear)
        setupIntent(Constants.RESET, R.id.btn_reset)

//        setupAppOpenIntent(R.id.formula)
//        setupAppOpenIntent(R.id.result)

        updateWidget()
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun setupIntent(action: String, id: Int) {
        mIntent!!.action = action
        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, mIntent, 0)
        mRemoteViews!!.setOnClickPendingIntent(id, pendingIntent)
    }

//    private fun setupAppOpenIntent(id: Int) {
//        val intent = Intent(mContext, be.jatra.simplecalculator.app.CalculatorActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0)
//        mRemoteViews!!.setOnClickPendingIntent(id, pendingIntent)
//    }

    private fun initVariables(context: Context) {
        mContext = context
        updateWidgetIds()
        mPrefs = initPrefs(mContext)
        val defaultColor = mContext!!.resources.getColor(R.color.text_grey)
        val newBgColor = mPrefs!!.getInt(Constants.WIDGET_BG_COLOR, defaultColor)
        val newTextColor = mPrefs!!.getInt(Constants.WIDGET_TEXT_COLOR, Color.WHITE)

        mRemoteViews = RemoteViews(mContext!!.packageName, R.layout.activity_calculator)
        mRemoteViews!!.setViewVisibility(R.id.btn_reset, View.VISIBLE)
        mRemoteViews!!.setInt(R.id.calculator_holder, "setBackgroundColor", newBgColor)

        updateTextColors(newTextColor)
        mWidgetManager = AppWidgetManager.getInstance(mContext)

        val displayValue = "0"
        mCalc = CalculatorImpl(this, displayValue)
    }

    private fun updateWidgetIds() {
        val component = ComponentName(mContext!!, CalculatorProvider::class.java)
        mWidgetManager = AppWidgetManager.getInstance(mContext)
        mWidgetIds = mWidgetManager!!.getAppWidgetIds(component)
    }

    private fun updateWidget() {
        for (widgetId in mWidgetIds!!) {
            mWidgetManager!!.updateAppWidget(widgetId, mRemoteViews)
        }
    }

    private fun initPrefs(context: Context?): SharedPreferences {
        return context!!.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)
    }

    private fun updateTextColors(color: Int) {
        val viewIds = intArrayOf(R.id.formula, R.id.result, R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_modulo, R.id.btn_power, R.id.btn_root, R.id.btn_clear, R.id.btn_reset, R.id.btn_divide, R.id.btn_multiply, R.id.btn_minus, R.id.btn_plus, R.id.btn_decimal, R.id.btn_equals)

        for (i in viewIds) {
            mRemoteViews!!.setInt(i, "setTextColor", color)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        when (action) {
            Constants.DECIMAL, Constants.ZERO, Constants.ONE, Constants.TWO, Constants.THREE, Constants.FOUR, Constants.FIVE, Constants.SIX, Constants.SEVEN, Constants.EIGHT, Constants.NINE, Constants.EQUALS, Constants.CLEAR, Constants.RESET, Constants.PLUS, Constants.MINUS, Constants.MULTIPLY, Constants.DIVIDE, Constants.MODULO, Constants.POWER, Constants.ROOT -> myAction(action, context)
            else -> super.onReceive(context, intent)
        }
    }

    private fun myAction(action: String, context: Context) {
        if (mCalc == null || mRemoteViews == null || mWidgetManager == null || mPrefs == null || mContext == null) {
            initVariables(context)
        }

        when (action) {
            Constants.DECIMAL -> mCalc!!.numpadClicked(R.id.btn_decimal)
            Constants.ZERO -> mCalc!!.numpadClicked(R.id.btn_0)
            Constants.ONE -> mCalc!!.numpadClicked(R.id.btn_1)
            Constants.TWO -> mCalc!!.numpadClicked(R.id.btn_2)
            Constants.THREE -> mCalc!!.numpadClicked(R.id.btn_3)
            Constants.FOUR -> mCalc!!.numpadClicked(R.id.btn_4)
            Constants.FIVE -> mCalc!!.numpadClicked(R.id.btn_5)
            Constants.SIX -> mCalc!!.numpadClicked(R.id.btn_6)
            Constants.SEVEN -> mCalc!!.numpadClicked(R.id.btn_7)
            Constants.EIGHT -> mCalc!!.numpadClicked(R.id.btn_8)
            Constants.NINE -> mCalc!!.numpadClicked(R.id.btn_9)
            Constants.EQUALS -> mCalc!!.handleEquals()
            Constants.CLEAR -> mCalc!!.handleClear()
            Constants.RESET -> mCalc!!.handleReset()
            Constants.PLUS, Constants.MINUS, Constants.MULTIPLY, Constants.DIVIDE, Constants.MODULO, Constants.POWER, Constants.ROOT -> mCalc!!.handleOperation(action)
            else -> {
            }
        }
    }

    override fun setValue(value: String) {
        mRemoteViews!!.setTextViewText(R.id.result, value)
        updateWidget()
    }

    override fun setValueDouble(d: Double) {

    }

    override fun setFormula(value: String) {
        mRemoteViews!!.setTextViewText(R.id.formula, value)
        updateWidget()
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        super.onDeleted(context, appWidgetIds)
        if (mContext != null)
            updateWidgetIds()
    }

    companion object {
        private var mRemoteViews: RemoteViews? = null
        private var mCalc: CalculatorImpl? = null
        private var mWidgetManager: AppWidgetManager? = null
        private var mIntent: Intent? = null
        private var mContext: Context? = null
        private var mPrefs: SharedPreferences? = null

        private var mWidgetIds: IntArray? = null
    }
}
