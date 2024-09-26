package br.com.cadastroit.services.rabbitmq.model;

import java.io.Serializable;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RabbitDomain implements Serializable{

	private static final long serialVersionUID = -2234547884288767203L;
	
	private String connectionId;
	private Connection connection;
	private Channel channel;
	private String host;
	private Integer port;
	private String username;
	private String password;
}
