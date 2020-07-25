package co.com.bancolombia.reactivemq;


import co.com.bancolombia.reactivemq.service.SendMessageService;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.compat.jms.internal.JMSC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.JMSException;
import java.io.IOException;




@Configuration
public class AppConfig {

    @Value("${ibm.mq.user}")
    private String user;

    @Value("${ibm.mq.password}")
    private String password;

    @Value("${ibm.mq.queueManager}")
    private String queueManager;

    @Value("${app.ccdtfile}")
    private String ccdtfile;

    @Value("${app.appname}")
    private String appname;


    @Bean
    public MQConnectionFactory ccdtConnectionFactory() throws JMSException, IOException{


        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setStringProperty("XMSC_USERID","admin" );
        mqQueueConnectionFactory.setStringProperty("XMSC_PASSWORD","passw0rd" );

        mqQueueConnectionFactory.setHostName("localhost");
        mqQueueConnectionFactory.setChannel("DEV.ADMIN.SVRCONN");//communications link
        mqQueueConnectionFactory.setPort(1414);
        mqQueueConnectionFactory.setQueueManager("QM1");//service provider
        mqQueueConnectionFactory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);



        return mqQueueConnectionFactory;

    }

    @Bean
    public SendMessageService service(MQConnectionFactory connectionFactory){
        return new SendMessageService(connectionFactory);

    }


}
