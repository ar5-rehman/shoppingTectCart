package com.clothing.shoppingcarts

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import android.os.StrictMode
import timber.log.Timber

@HiltAndroidApp
class ShoppingTechApp : Application(){

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeathOnNetwork()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            Timber.plant(Timber.DebugTree())
        }
    }

}
