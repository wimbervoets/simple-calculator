package be.jatra.simplecalculator

import android.content.Context
import android.widget.Toast

object Utils {

    fun showToast(context: Context, resId: Int) {
        Toast.makeText(context, context.resources.getString(resId), Toast.LENGTH_SHORT).show()
    }
}
