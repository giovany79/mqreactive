package co.com.bancolombia.reactivemq;


import co.com.bancolombia.reactivemq.service.SendMessageService;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.jms.JMSException;
import java.io.File;
import java.io.IOException;
import java.net.URL;




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

        JmsFactoryFactory factory = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
        JmsConnectionFactory connectionFactoryIbm= factory.createConnectionFactory();

        connectionFactoryIbm.setStringProperty(WMQConstants.USERID,user);
        connectionFactoryIbm.setStringProperty(WMQConstants.PASSWORD,password );
        connectionFactoryIbm.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER,queueManager );
        connectionFactoryIbm.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);


        System.out.println(user);
        System.out.println(password);

        MQConnectionFactory mqConnection = (MQConnectionFactory) connectionFactoryIbm;
        mqConnection.setClientReconnectOptions(WMQConstants.WMQ_CLIENT_RECONNECT);
        URL ccdtUrl = new File(ccdtfile).toURI().toURL();
        mqConnection.setCCDTURL(ccdtUrl);
        mqConnection.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        mqConnection.setAppName(appname);
        return mqConnection;

    }

    @Bean
    public SendMessageService service(MQConnectionFactory connectionFactory){
        return new SendMessageService(connectionFactory);

    }


}
