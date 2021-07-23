package ai.yellow.demo

import android.app.Application
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.yellowmessenger.YellowInbox
import com.yellowmessenger.YmAppProcessLifeCycleListener
import com.yellowmessenger.datalayer.vo.Status

class YellowInboxDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(YmAppProcessLifeCycleListener())
        //Step-1  initialise the SDK
        try {
            YellowInbox.init(
                context = applicationContext,
                apiKey = "f2b588eafc8d6e6a4d166ff534413b363ddde496d3068fe07ea67d5ee8ad33d7",
                userId = "sukrit.gupta@yellow.ai",
                botId = "x1609740331340"
            ).observeForever {
                when (it.status) {
                    Status.SUCCESS -> {
                        initialiseInboxSdk(applicationContext, true)
                        Log.d("Test App", "Success")
                    }
                    Status.ERROR -> {
                        initialiseInboxSdk(applicationContext, false)
                        Log.d("Test App", "Error : ${it.message}")
                    }
                    Status.LOADING -> {
                        //Nothing
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("Test App", e.toString())
        }

        //Step-2  set local listener to modify local notification (Optional)
        YellowInbox.setLocalReceiver(YellowDemoMessageReceiver())

        //Step-3  set firebase access token for Firebase notification (Optional)
        getAndSetFcmAccessToken()

    }

    private fun getAndSetFcmAccessToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            if (!token.isNullOrEmpty() && isSdkInitialised(applicationContext)) {
                YellowInbox.setFirebaseDeviceToken(token)
            }
        })
    }
}