package br.com.cadastroit.rabbitmq.connectors.test;

import org.junit.jupiter.api.Test;

import br.com.cadastroit.services.rabbitmq.connectors.RabbitMQConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class RabbitMQConnectionTestPRD {

	private String[] connections = new String[] {"[OCI-PRD;172.16.2.18;5672;guest;guest]"};
	
	@Test
	void sendMessagesNumber() {
		try {
			RabbitMQConnection rabbitMQConnection = RabbitMQConnection.builder().connections(this.connections).build();
			rabbitMQConnection.buildConnectionFactory();
			Long loteId = new Long(3816473);
			rabbitMQConnection.sendObjectMessage("OCI-PRD", "DIRECT-SEFAZ", "consulta-lote", loteId);
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
}
