package br.com.cadastroit.services.rabbitmq.model;

import java.io.Serializable;

import com.rabbitmq.client.Channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RabbitChannel implements Serializable {

	private static final long serialVersionUID = 5122852329186867331L;
	
	private Integer index;
	private Channel channel;

}
