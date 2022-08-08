package com.clothing.shoppingcarts.di

import android.content.Context
import android.content.SharedPreferences

/**
 * This class will help saving data into share preferences
 * here two type share pref
 * 1. is clear when user logout this share pref clear user related data
 * 2. will never clear this is global lavel settings like user selected language
 */
class SharePreferencesManager constructor(context: Context) {

    companion object {
        private const val SHARED_PREF_NAME = "cherie_pref"
        private const val SHARED_PREF_NAME_NO_CLEAR_DATA = "cherie_pref_clear_data"
        private const val TOKEN = "token"
        private const val USER_ID = "userId"
        private const val USER_NAME = "userName"
        private const val IS_TWO_WAY_AUTH = "isTwoWayAuth"
        private const val KYC_STEPS = "kycSteps"
        private const val MAPPED_WITH = "mapped_with"
        private const val NOTICE_NOTIFICATION = "notice_notification"
    }
    /**
     * user related sharepref logout time clear user data
     * */
    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Global lavel sharepref this share preference will never clear
     *
     */
    private val sharedPreferencesNoClearData by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(SHARED_PREF_NAME_NO_CLEAR_DATA, Context.MODE_PRIVATE)
    }

    /**
     * SharedPreferences editor creation
     *
     * @param body
     */
    private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.body()
        editor.apply()
    }

    /**
     * only user related share pref will clear
     *
     */
    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    /**
     * user token it will get when user will login
     *
     */
    var token: String?
        get() = sharedPreferences.getString(TOKEN, "")
        set(value) = sharedPreferences.put { putString(TOKEN, value) }

    /**
     * base on token we can check is user is login or not
     *
     * */
    val isLoggedIn: Boolean
        get() = !token.isNullOrBlank()

    /**
     * for storing & retriving userid id will get login time
     */
    var userId: String
        get() = sharedPreferences.getString(USER_ID, "") ?: ""
        set(value) = sharedPreferences.put { putString(USER_ID, value) }

    /**
     * user getting & setting using this property
     */
    var userName: String
        get() = sharedPreferences.getString(USER_NAME, "") ?: ""
        set(value) = sharedPreferences.put { putString(USER_NAME, value) }

    /**
     * for check two factor authentication on or not
     * */
    var isTwoWayAuth: Boolean
        get() = sharedPreferences.getBoolean(IS_TWO_WAY_AUTH, false)
        set(value) = sharedPreferences.put { putBoolean(IS_TWO_WAY_AUTH, value) }

    /**
     * for phone Code Number remember
     * */
    var kycSteps: Int
        get() = sharedPreferencesNoClearData.getInt(KYC_STEPS, 0)
        set(value) = sharedPreferencesNoClearData.put { putInt(KYC_STEPS, value) }

    /**
     * Passcode login with email & mobile
     * */
    var passcodeMappedWith: String
        get() = sharedPreferencesNoClearData.getString(MAPPED_WITH, "") ?: ""
        set(value) = sharedPreferencesNoClearData.put { putString(MAPPED_WITH, value) }

    var noticeNotification: Boolean
        get() = sharedPreferencesNoClearData.getBoolean(NOTICE_NOTIFICATION, true)
        set(value) = sharedPreferencesNoClearData.put { putBoolean(NOTICE_NOTIFICATION, value) }
}