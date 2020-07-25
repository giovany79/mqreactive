package co.com.bancolombia.reactivemq.service;

import com.ibm.mq.jms.MQConnectionFactory;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import javax.jms.*;

@Log4j2
@AllArgsConstructor
public class SendMessageService {

    MQConnectionFactory mqConnectionFactory;

    public Mono<String> send(String message) throws JMSException {
        System.out.println("Llego aqui");
        Connection producerConnection = mqConnectionFactory.createConnection();
        producerConnection.start();
        Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination producerDestination = producerSession.createQueue("Q1");
        MessageProducer producer = producerSession.createProducer(producerDestination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        final TextMessage producerMessage = producerSession.createTextMessage(message);
        producer.send(producerMessage);
        System.out.println("Message sent.");
        producer.close();
        producerSession.close();
        producerConnection.close();
        return Mono.just("Message sent.");
    }

}
