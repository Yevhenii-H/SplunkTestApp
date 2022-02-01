package com.example.splunktestapp

import android.app.Application
import com.splunk.mint.Mint

class SplunkTestApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // To see Splunk MINT logs in logcat console
        Mint.enableDebugLog()

        // To include information about the application environment for your mobile app
        // Mint.setApplicationEnvironment(Mint.appEnvironmentStaging)

        Mint.initAndStartSession(this, "880fa788")

        // Report Application Not Responding
        Mint.startANRMonitoring(5000, true)
    }
}
