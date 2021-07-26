package ai.yellow.demo

/**
 * Add commented code in your project if you are using FCM
 * Client need to send device token to SDK if it is changes
 * Client need to handle video call notification if app is in foreground
 * */
class DemoFirebaseMessagingService { /*: FirebaseMessagingService() {
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
    }*/
}