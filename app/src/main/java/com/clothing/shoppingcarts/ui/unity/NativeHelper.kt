package com.clothing.shoppingcarts.ui.unity

import com.unity3d.player.UnityPlayer

interface NativeBridge {
    fun getData(key: String, specification: String): String

    fun onMessage(command: String, data: String?)
}

object NativeHelper {
    private var bridge: NativeBridge? = null

    fun attack(bridge: NativeBridge) {
        this.bridge = bridge
    }

    fun detach() {
        this.bridge = null
    }

    fun sendUnityMessage(function: String, message: String = "") {
        UnityPlayer.UnitySendMessage("Director", function, message)
    }

    @JvmStatic
    fun sendCommand(command: String, data: String?) {
        this.bridge?.onMessage(command, data)
    }

    @JvmStatic
    fun getData(key: String, specification: String): String {
        return bridge?.getData(key, specification) ?: ""
    }
}