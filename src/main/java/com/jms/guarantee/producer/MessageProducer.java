package com.jms.guarantee.producer;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class MessageProducer {
    public static void main(String[] args) throws Exception {
        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/claimQueue");
        try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
             JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)) {
            JMSProducer producer = jmsContext.createProducer();
            producer.send(queue, "Message 1");
            producer.send(queue, "Message 2");
            jmsContext.commit();
//            jmsContext.rollback();
            System.out.println("Message sent");

        }
    }
}
