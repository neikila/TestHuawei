package ru.neikila.testaddkotlin

import android.util.Log
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage

private val TAG = "Demo"

class DemoHmsMessageService : HmsMessageService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle HCM messages here.
        // Check if message contains a data payload.
        if (remoteMessage.getData().length > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData())
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Job.
            } else {
            }
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody())
        }
    }
}