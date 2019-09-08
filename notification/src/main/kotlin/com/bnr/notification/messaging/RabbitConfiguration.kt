package com.bnr.notification.messaging

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class RabbitConfiguration{

    private val LISTENER_METHOD = "receiveMessage"

    @Value("\${queue.name}")
    private lateinit var queueName: String

    @Value("\${fanout.exchange}")
    private lateinit var fanoutExchange: String

    @Bean
    fun queue(): Queue {
        return Queue(queueName, true)
    }

    @Bean
    fun exchange(): FanoutExchange {
        return FanoutExchange(fanoutExchange)
    }

    @Bean
    fun binding(queue: Queue, exchange: FanoutExchange): Binding {
        return BindingBuilder.bind(queue).to(exchange)
    }

    @Bean
    fun container(connectionFactory: ConnectionFactory,
                  listenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.setConnectionFactory(connectionFactory)
        container.setQueueNames(queueName)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(consumer: QueueConsumer): MessageListenerAdapter {
        return MessageListenerAdapter(consumer, LISTENER_METHOD)
    }
}
