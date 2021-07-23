package ai.yellow.demo

import android.os.Handler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yellowmessenger.YellowInbox


class DemoFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {
        val hasValidData = isSdkInitialised(this.applicationContext)
        if (hasValidData) {
            Handler(mainLooper).post {
                YellowInbox.setFirebaseDeviceToken(token)
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.data.containsKey("callJoinUrl") && !message.data["callJoinUrl"].isNullOrEmpty()) {
            YellowInbox.handleBackgroundNotification(applicationContext, message.data)
        }
    }

}