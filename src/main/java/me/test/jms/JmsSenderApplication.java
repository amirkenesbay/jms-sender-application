package me.test.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import javax.jms.*;
import java.io.IOException;
import java.util.UUID;

@SpringBootApplication(exclude = JmxAutoConfiguration.class)
public class JmsSenderApplication {

	public static void main(String[] args) throws JMSException, IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(JmsSenderApplication.class, args);

		Session session = ctx.getBean(Session.class);
		Queue queue = ctx.getBean(Queue.class);

		MessageProducer producer = session.createProducer(queue);

		MapMessage msg = session.createMapMessage();

		UUID uuid = UUID.randomUUID();

		System.out.println(uuid);

		msg.setStringProperty(JmsMessageProperty.MESSAGE_TYPE, JmsMessageProperty.SEND_ACCRUAL_CONFIRM);
		msg.setLong(JmsMessageProperty.USER_ID, -1L);
		msg.setLong(JmsMessageProperty.CONFIRM_REGISTRY_ID, 48448575L);

		producer.send(msg);
	}
}

class JmsMessageProperty {
	public static final String USER_ID = "userId";
	public static final String REGISTRY_ID = "registryId";
	public static final String CONFIRM_REGISTRY_ID = "confirmRegistryId";
	public static final String DOCUMENT_ID = "documentId";
	public static final String IS_RETRY = "isRetry";
	public static final String MESSAGE_TYPE = "messageType";
	public static final String TOKEN = "token";
	public static final String ACCOUNT_ID = "accountId";
	public static final String OPERATION_TYPE = "operationType";
	public static final String SEND_CORRECTING_CONFIRM = "sendCorrectingConfirm";
	public static final String SEND_ACCRUAL_CONFIRM = "sendAccrualConfirm";
	public static final String PROCESSING_ACCRUAL_REGISTRY = "processingAccrualRegistry";
	public static final String PROCESSING_CORRECTING_REGISTRY = "processingCorrectingRegistry";
}
