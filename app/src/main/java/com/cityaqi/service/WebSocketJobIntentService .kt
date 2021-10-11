package com.cityaqi.service

import android.app.Service
import androidx.core.app.JobIntentService


import android.content.Intent
import android.os.IBinder

class WebSocketJobIntentService  : Service() {

    lateinit var webSocketClientAIQ :WebSocketClientAIQ
    // This method is called when service starts instead of onHandleIntent


    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        webSocketClientAIQ  = WebSocketClientAIQ(baseContext)
        webSocketClientAIQ.createWebSocketClient()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        webSocketClientAIQ!!.close()
    }


}