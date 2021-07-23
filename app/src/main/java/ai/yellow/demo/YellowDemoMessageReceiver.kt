package ai.yellow.demo

import com.yellowmessenger.YmMessageReceiver
import com.yellowmessenger.ui.xmpp.model.YmTicketCreateModel
import com.yellowmessenger.ui.xmpp.model.YmXMPPMessageModel

class YellowDemoMessageReceiver: YmMessageReceiver() {

    override fun onTicketCreateEventReceived(
        title: String?,
        body: String?,
        model: YmTicketCreateModel
    ) {
        val modifiedTitle = "Modified $title"
        val modifiedBody = "Modified $body"
        super.onTicketCreateEventReceived(modifiedTitle, modifiedBody, model)
    }

    override fun onTicketUpdateEventReceived(
        title: String?,
        body: String?,
        model: YmXMPPMessageModel
    ) {
        val modifiedTitle = "Modified $title"
        val modifiedBody = "Modified $body"
        super.onTicketUpdateEventReceived(modifiedTitle, modifiedBody, model)
    }
}