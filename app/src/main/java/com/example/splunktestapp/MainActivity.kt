package com.example.splunktestapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.splunk.mint.Mint
import com.splunk.mint.MintLogLevel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add or remove extra data from crashing reports
        Mint.addExtraData("key", "value")
        Mint.removeExtraData("key")
        Mint.clearExtraData()

        // Add user information
        Mint.setUserIdentifier("userID")

        // Report LogCat output
        Mint.enableLogging(true)

        // Log the latest 100 messages with priority level "warning" and higher with tag "SplunkTestApp"
        Mint.setLogging(100, "SplunkTestApp:W")
    }

    fun crashTheApp(view: View) {
        var a: Int? = null
        var b = a!! + 1
    }

    override fun onStart() {
        super.onStart()
        // Handle session manually
        Mint.startSession(this.application)

        // Add breadcrumbs to the crush report
        Mint.leaveBreadcrumb("Main activity onStart")
    }

    override fun onStop() {
        super.onStop()
        Mint.closeSession(this.application)
        Mint.flush()
    }

    // Track processes such as registration, login, or a purchase
    private lateinit var txID: String

    fun startTransactionClick(view: View) {
        txID = Mint.transactionStart("transactionName")

        // We can add extra custom data to specific transactions as a data map or as a key-value pair
        // Mint.transactionStart("transactionName", "keyName", "key-value")
    }

    fun stopTransactionClick(view: View) {
        Mint.transactionStop(txID)
    }

    fun cancelTransactionClick(view: View) {
        Mint.transactionCancel(txID, "Transaction is canceled by user")
    }

    // Report custom events
    fun logEventClick(view: View) {
        var data: HashMap<String, Any> = HashMap()
        data.put("key1", "value1")
        data.put("key2", "value2")
        Mint.logEvent("eventWithData", MintLogLevel.Info, data)
    }

    // Report handled exceptions
    fun logExceptionClick(view: View) {
        try {
            throw IllegalArgumentException()
        } catch (ex: java.lang.IllegalArgumentException) {
            Mint.logException(ex)
        }
    }

    // Create timers
    private lateinit var timerID: String

    fun timerStartClick(view: View) {
        timerID = Mint.timerStart("timerName")
    }

    fun timerStopClick(view: View) {
        Mint.timerStop(timerID)
    }

    // Report network calls manually
    fun logNetworkEvent(view: View) {
        Mint.logNetworkEvent(
            "http://splunk.com/",
            "HTTP/S",
            System.currentTimeMillis(),
            System.currentTimeMillis() + 500,
            404,
            2345,
            5435,
            null,
            null
        )
    }
}
