/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Arriety.message;

import com.rabbitmq.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;


public class MessageConsumerManager {
    private final static String QUEUE_NAME = "players_queue";
    private Connection connection;
    private Channel channel;

    private static MessageConsumerManager instance;

    private MessageConsumerManager(){}

    public static synchronized MessageConsumerManager getInstance() {
        if (instance == null) {
            instance = new MessageConsumerManager();
        }
        return instance;
    }

    public void consumeWith(MessageConsumerListener listener) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setPassword("guest");
        factory.setUsername("guest");

        connection = factory.newConnection();
        channel = connection.createChannel();
        System.out.println(connection);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            if(null == delivery.getBody() || delivery.getBody().length == 0) {
                return;
            }
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(message);
            listener.onMessage(jsonObject);
        };

        CancelCallback cancelCallback = consumerTag -> {};
        String consumerTag = channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        System.out.println("consumerTag: " + consumerTag);
    }
}

