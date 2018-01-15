package be.jatra.simplecalculator

import android.content.Context
import android.content.SharedPreferences

class Config(context: Context) {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(Constants.PREFS_KEY, Context.MODE_PRIVATE)

    var isFirstRun: Boolean
        get() = mPrefs.getBoolean(Constants.IS_FIRST_RUN, true)
        set(firstRun) = mPrefs.edit().putBoolean(Constants.IS_FIRST_RUN, firstRun).apply()

    var isDarkTheme: Boolean
        get() = mPrefs.getBoolean(Constants.IS_DARK_THEME, false)
        set(isDarkTheme) = mPrefs.edit().putBoolean(Constants.IS_DARK_THEME, isDarkTheme).apply()

    companion object {

        fun newInstance(context: Context): Config {
            return Config(context)
        }
    }
}
