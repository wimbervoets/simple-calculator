package be.jatra.simplecalculator.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import be.jatra.simplecalculator.IntentConstants


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.calculator).setOnClickListener {
            val intent = intent(this@MainActivity, "1117")
            startActivityForResult(intent, IntentRequestCodes.CALCULATOR.value)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == IntentRequestCodes.CALCULATOR.value) {
            if (resultCode == Activity.RESULT_OK) {
                val textView: TextView = findViewById(R.id.result)
                textView.text = data.extras!!.getString(IntentConstants.AMOUNT.name)
            }
        }
    }

    companion object {
        fun intent(context: Context, amount: String): Intent {
            val intent = Intent(context, be.jatra.simplecalculator.CalculatorActivity::class.java)
            intent.putExtra(IntentConstants.AMOUNT.name, amount)
            return intent
        }
    }
}
