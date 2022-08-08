package com.clothing.shoppingcarts.ui.unity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.unity3d.player.UnityPlayeru

@SuppressLint("Registered")
abstract class UnityPlayerActivity : AppCompatActivity() , NativeBridge {

    protected lateinit var mUnityPlayer: UnityPlayer

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        this.intent = intent
    }

    override fun onDestroy() {
        NativeHelper.detach()
        this.mUnityPlayer.quit()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        this.mUnityPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        this.mUnityPlayer.resume()
    }

//    override fun onStart() {
//        super.onStart()
//        this.mUnityPlayer.start()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        this.mUnityPlayer.stop()
//    }

    override fun onLowMemory() {
        super.onLowMemory()
        this.mUnityPlayer.lowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            this.mUnityPlayer.lowMemory()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        this.mUnityPlayer.configurationChanged(newConfig)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        this.mUnityPlayer.windowFocusChanged(hasFocus)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return if (event.action == KeyEvent.KEYCODE_SOFT_RIGHT) this.mUnityPlayer.injectEvent(event) else super.dispatchKeyEvent(
            event
        )
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return this.mUnityPlayer.injectEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return true
        }
        return this.mUnityPlayer.injectEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.mUnityPlayer.injectEvent(event)
    }

    override fun onGenericMotionEvent(event: MotionEvent): Boolean {
        return this.mUnityPlayer.injectEvent(event)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        this.mUnityPlayer = UnityPlayer(this)
        addUnityView()
        this.mUnityPlayer.requestFocus()

        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // Clear low profile flags to apply non-fullscreen mode before splash screen
        showSystemUi()
        addUiVisibilityChangeListener()

        NativeHelper.attack(this)
    }

    private fun getLowProfileFlag(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
        else
            View.SYSTEM_UI_FLAG_LOW_PROFILE
    }

    private fun showSystemUi() {
        mUnityPlayer.systemUiVisibility = mUnityPlayer.systemUiVisibility and getLowProfileFlag().inv()
    }

    private fun addUiVisibilityChangeListener() {
        mUnityPlayer.setOnSystemUiVisibilityChangeListener {
            // Whatever changes - force status/nav bar to be visible
            showSystemUi()
        }
    }

    abstract fun addUnityView()
}

fun Activity.killUnityProcessIfNeeded() {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager ?: return
    val pids = am.runningAppProcesses
    pids.firstOrNull {
        it.processName.contains(":unityplayer")
    }?.let {
        Process.killProcess(it.pid)
    }
}