package ai.yellow.demo

import android.content.Context
import android.content.SharedPreferences

private const val KEY_NAME = "demo_data"
private const val IS_INITIALISED = "is_initialised"

fun getSharedPrefReference(context: Context): SharedPreferences {
    return context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
}


fun initialiseInboxSdk(context: Context, initialised: Boolean) {
    val sharedPreferences: SharedPreferences = getSharedPrefReference(context)
    with(sharedPreferences.edit()) {
        putBoolean(IS_INITIALISED, initialised)
        apply()
    }

}

fun isSdkInitialised(context: Context): Boolean {
    val sharedPreferences: SharedPreferences = getSharedPrefReference(context)
    return sharedPreferences.getBoolean(IS_INITIALISED, false)
}
