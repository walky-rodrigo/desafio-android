package preferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

/**
 * Always call the [init] before start to use it.
 */
class DesafioPreferences @Inject constructor(private val context: Application) {

    private lateinit var preferences: SharedPreferences

    init {
        init()
    }

    fun init() {
        if (!::preferences.isInitialized) {
            preferences = context.getSharedPreferences(KEY_APP_PREF, Context.MODE_PRIVATE)
        }
    }

    fun getLong(key: String, default: Long): Long? {
        return preferences.getLong(key, default)
    }

    fun putLong(key: String, value: Long) {
        preferences.edit {
            putLong(key, value)
            apply()
        }
    }

    fun remove(key: String) {
        preferences.edit {
            remove(key)
        }
    }

    companion object {
        const val KEY_APP_PREF = "app_pref"
    }

}