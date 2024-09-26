package br.com.cadastroit.services;

import org.junit.jupiter.api.Test;

import br.com.cadastroit.services.rabbitmq.connectors.RabbitMQConnection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class RabbitMQConnectionTestPRD {

	private String[] connections = new String[] {"[OCI-PRD;localhost;5672;guest;guest]"};
	
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
