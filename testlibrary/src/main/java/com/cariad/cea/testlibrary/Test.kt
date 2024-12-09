package com.cariad.cea.testlibrary

import android.content.Context
import android.os.UserManager

class Test {
    fun test(context: Context) {
        println("Test")
        UserManager.isDeviceInDemoMode(context)
    }
}