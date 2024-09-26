package br.com.cadastroit.rabbitmq.connectors.test;

import org.junit.jupiter.api.Test;

import br.com.cadastroit.services.rabbitmq.connectors.RabbitMQConnection;
import br.com.cadastroit.services.rabbitmq.model.QueueInformation;
import br.com.cadastroit.services.rabbitmq.model.RabbitDomain;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class RabbitMQConnectionTest {

//	private String[] connections = new String[] {"[localhost;localhost;5672;guest;guest]","[OCI-PRD;172.16.2.18;5672;guest;guest]"};
	private String[] connections = new String[] {"[localhost;localhost;5672;guest;guest]", "[localhost;localhost;5672;guest;guest]"};
	
	@Test
	void rabbitMqConnectionAllTests() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			
			RabbitDomain rabbitDomain = rabbitMQConnection.rabbitDomain("localhost");
			rabbitDomain = rabbitMQConnection.createDirectExchangeForQueue("NFE-PRINT-DANFE-LEG", "DIRECT-DOCUMENT-LEG", "d-print-danfe-leg", "localhost");
			rabbitDomain = rabbitMQConnection.createDirectExchangeForQueue("NFE-PRINT-DANFE-LEG", "DIRECT-DOCUMENT-LEG", "d-print-danfe-leg", "localhost");
			rabbitMQConnection.closeConnection(rabbitDomain);
			
			rabbitDomain = rabbitMQConnection.rabbitDomain("localhost");
			rabbitDomain = rabbitMQConnection.createDirectExchangeForQueue("NFE-PRINT-DANFE-TERC", "DIRECT-DOCUMENT-TERC", "d-print-danfe-terc", "localhost");
			rabbitMQConnection.closeConnection(rabbitDomain);
			
//			rabbitMQConnection.createDirectExchangeForQueue("NFE-PRINT-DANFE-LEG", "DIRECT-DOCUMENT-LEG", "d-print-danfe-leg", "OCI-PRD");
			
//			rabbitMQConnection = null;
//			
//			RabbitDomain rabbitDomain = RabbitDomain.builder().connectionId("Teste").build();
//			rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
//			rabbitMQConnection.buildConnectionFactory();
//			rabbitMQConnection.sendTextMessage("localhost", "DIRECT-DOCUMENT-LEG", "d-print-danfe-leg", "Teste1");
//			rabbitMQConnection.sendObjectMessage("localhost", "DIRECT-DOCUMENT-LEG", "d-print-danfe-leg", rabbitDomain);
//			
//			log.info(rabbitMQConnection.countConsumersQueue("NFE-PRINT-DANFE-LEG", "localhost").toString());
//			log.info(rabbitMQConnection.countMessageQueue("NFE-PRINT-DANFE-LEG", "localhost").toString());
//			
//			QueueInformation queueInformation = rabbitMQConnection.queueExists("localhost", "NFE-PRINT-DANFE-LEG");
//			log.info(queueInformation.toString());
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	@Test
	void checkQueueInformation() throws Exception{
		RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
		rabbitMQConnection.buildConnectionFactory();
		try {
			QueueInformation queueInformationOCIPRD = rabbitMQConnection.queueExists("localhost", "NFE-PRINT-DANFE-TERC");
			log.info(queueInformationOCIPRD.toString());
		}catch(Exception ex) {
			log.info("NFE-PRINT-DANFE nao existe no host OCI-PRD, [error] = "+ex.getMessage());
		}
		
		try {
			QueueInformation queueInformationOCIPRD2 = rabbitMQConnection.queueExists("OCI-PRD", "NFE-PRINT");
			log.info(queueInformationOCIPRD2.toString());
		}catch(Exception ex) {
			log.info("NFE-PRINT nao existe no host OCI-PRD, [error] = "+ex.getMessage());
		}
	}
	
	
	@Test
	void createDirectExchangeForQueue() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			rabbitMQConnection.createDirectExchangeForQueue("NFE-PRINT-DANFE-LEG", "DIRECT-DOCUMENT-LEG", "d-print-danfe-leg", "localhost");
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	@Test
	void createDirectDelayedExchangeForQueue() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			rabbitMQConnection.createDirectExchangeDelayedMessageForQueue("QUEUE-DELAYED", "EXCHANGE-QUEUE-DELAYED", "exchange-queue-delayed-rk", "localhost");
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	@Test
	void sendAllTypeMessages() {
		sendMessagesTextPlain();
		sendMessagesObject();
		sendMessagesNumber();
	}
	
	void sendMessagesTextPlain() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			rabbitMQConnection.createDirectExchangeForQueue("QUEUE-TEXT-MESSAGE", "DIRECT-MESSAGE", "text-message", "localhost");
			rabbitMQConnection.sendTextMessage("localhost", "DIRECT-MESSAGE", "text-message", "Teste1");
			rabbitMQConnection.sendTextMessage("localhost", "DIRECT-MESSAGE", "text-message", "Teste2");
			rabbitMQConnection.sendTextMessage("localhost", "DIRECT-MESSAGE", "text-message", "Teste3");
			
			
			QueueInformation queueInformation = rabbitMQConnection.queueExists("localhost", "QUEUE-TEXT-MESSAGE");
			System.out.println(queueInformation.toString());
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	void sendMessagesObject() {
		try {
			RabbitDomain rabbitDomain = RabbitDomain.builder().connectionId("Teste").build();
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			rabbitMQConnection.createDirectExchangeForQueue("QUEUE-OBJECT-MESSAGE", "DIRECT-OBJECT", "object-message", "localhost");
			rabbitMQConnection.sendObjectMessage("localhost", "DIRECT-OBJECT", "object-message", rabbitDomain);
			
			QueueInformation queueInformation = rabbitMQConnection.queueExists("localhost", "QUEUE-OBJECT-MESSAGE");
			System.out.println(queueInformation.toString());
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	void sendMessagesNumber() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			rabbitMQConnection.createDirectExchangeForQueue("QUEUE-NUMBER-MESSAGE", "DIRECT-NUMBER", "number-message", "localhost");
			Long notafiscalId = new Long(1);
			rabbitMQConnection.sendObjectMessage("localhost", "DIRECT-NUMBER", "number-message", notafiscalId);
			
			QueueInformation queueInformation = rabbitMQConnection.queueExists("localhost", "QUEUE-NUMBER-MESSAGE");
			System.out.println(queueInformation.toString());
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	@Test
	void checkInformationAllQueues() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			
			QueueInformation queueInformation = rabbitMQConnection.queueExists("localhost", "QUEUE-NUMBER-MESSAGE");
			System.out.println(queueInformation.toString());
			
			queueInformation = rabbitMQConnection.queueExists("localhost", "QUEUE-OBJECT-MESSAGE");
			System.out.println(queueInformation.toString());
			
			rabbitMQConnection.queueExists("localhost", "QUEUE-OBJECT-MESSAGE");
			System.out.println(queueInformation.toString());
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	@Test
	void dropQueue() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			
			int messages = rabbitMQConnection.dropQueue("QUEUE-OBJECT-MESSAGE", "localhost");
			log.info("Queue dropped, messages those was erased => "+messages);
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
