package com.jms.guarantee.consumer;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class MessageConsumer {
    public static void main(String[] args) throws Exception {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/claimQueue");
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)) {
            JMSConsumer consumer = jmsContext.createConsumer(queue);
            TextMessage message = (TextMessage) consumer.receive();
            JMSConsumer consumer2 = jmsContext.createConsumer(queue);
            TextMessage message2 = (TextMessage) consumer2.receive();
            jmsContext.commit();
            System.out.println("message: " + message.getText());
            System.out.println("message2: " + message2.getText());
        }
    }
}
