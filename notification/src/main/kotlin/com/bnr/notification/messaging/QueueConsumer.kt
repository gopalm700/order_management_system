package com.bnr.notification.messaging

import com.bnr.notification.dto.QueueMessage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component

class QueueConsumer {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val restTemplate = RestTemplate()

    fun receiveMessage(message: String) {
        val mapper = ObjectMapper().registerModule(KotlinModule()).enable(SerializationFeature.INDENT_OUTPUT)
        try {
            logger.info("***************************Recieved Message*********************************")
            val message = mapper.readValue(message, QueueMessage::class.java)
            message?.run {
                logger.info("\n${mapper.writeValueAsString(message)}")
                logger.info("****************************************************************************")
//                restTemplate.postForObject(
//                        "http://localhost:8080" + this.callback,
//                        OrderCallback(this.orderId, "DELIVERED"),
//                        Object::class.java
//                )
            }
        } catch (e: Exception) {
            logger.error("", e)
        }
    }
}
