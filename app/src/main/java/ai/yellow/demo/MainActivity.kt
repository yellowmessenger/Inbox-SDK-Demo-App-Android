package ai.yellow.demo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.yellowmessenger.YellowInbox
import com.yellowmessenger.datalayer.util.isNullOrEmpty
import com.yellowmessenger.datalayer.vo.Status
import com.yellowmessenger.ui.vo.YmAgentStatus

class MainActivity : AppCompatActivity() {
    lateinit var currentStatus: TextView
    lateinit var fetchButton: Button
    lateinit var available: Button
    lateinit var busy: Button
    lateinit var away: Button
    lateinit var overview: Button
    lateinit var myChat: Button
    lateinit var logout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        overview = findViewById<Button>(R.id.overview)
        myChat = findViewById<Button>(R.id.my_chats)
        currentStatus = findViewById<TextView>(R.id.status)
        fetchButton = findViewById<Button>(R.id.fetch_again)
        available = findViewById<Button>(R.id.available)
        busy = findViewById<Button>(R.id.busy)
        away = findViewById<Button>(R.id.away)
        logout = findViewById<Button>(R.id.logout)

        initListener()

        //Check if app launched from Notification click, if yes take user to appropriate view
        //This code will be required once client has integrated FCM and has sent Device token to SDK
        val data = getDataFromIntent()
        if (data.isNotEmpty()) {
            YellowInbox.startMyChatActivity(this)
            YellowInbox.handleBackgroundNotification(this, data)
        }

    }

    private fun initListener() {
        myChat.setOnClickListener {
            try {
                // Launch My Chat Activity
                YellowInbox.startMyChatActivity(this)
            } catch (e: Exception) {
                showMessage(e.toString())
            }
        }

        overview.setOnClickListener {
            try {
                // Launch Overview Activity
                YellowInbox.startOverviewActivity(this)
            } catch (e: Exception) {
                showMessage(e.toString())
            }
        }

        fetchButton.setOnClickListener {
            getCurrentStatus()
        }

        available.setOnClickListener {
            changeAgentStatusTo(YmAgentStatus.AVAILABLE)
        }

        busy.setOnClickListener {
            changeAgentStatusTo(YmAgentStatus.BUSY)
        }

        away.setOnClickListener {
            changeAgentStatusTo(YmAgentStatus.AWAY)
        }

        logout.setOnClickListener {
            YellowInbox.logout()
        }
    }

    private fun changeAgentStatusTo(status: YmAgentStatus) {
        try {
            YellowInbox.setAgentStatus(status).observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        setCurrentStatus(status)
                        showMessage("Status changes to ${status.name}")
                    }
                    Status.ERROR -> {
                        showMessage(it.message ?: "Error")
                    }
                    else -> {
                        showMessage("Loading...")
                    }
                }
            })
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    private fun getCurrentStatus() {
        try {
            YellowInbox.getAgentStatus().observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        val data = it.data
                        if (!isNullOrEmpty(data)) {
                            setCurrentStatus(data!!)
                        }
                    }
                    Status.ERROR -> {
                        showMessage(it.message ?: "Error")
                    }
                    else -> {
                        showMessage("Loading...")
                    }
                }
            })
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }

    private fun showMessage(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun setCurrentStatus(data: YmAgentStatus) {
        currentStatus.text = data.name
    }

    private fun getDataFromIntent(): MutableMap<String, Any?> {
        val data = mutableMapOf<String, Any?>()
        if (intent.extras != null) {
            for (key in intent.extras!!.keySet()) {
                val value = intent.extras!![key]
                data[key] = value
            }
            this.intent.putExtras(Bundle.EMPTY)
        }
        return data
    }
}