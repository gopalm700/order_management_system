package com.bnr.notification.messaging

import com.bnr.notification.dto.QueueMessage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class QueueConsumer {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun receiveMessage(message: String) {
        val mapper = ObjectMapper().registerModule(KotlinModule()).enable(SerializationFeature.INDENT_OUTPUT)
        try {
            val queueMessage = mapper.readValue(message, QueueMessage::class.java)
            queueMessage?.run {
                logger.info("\n${mapper.writeValueAsString(this)}")
            }
        } catch (e: Exception) {
            logger.error("", e)
        }
    }
}
