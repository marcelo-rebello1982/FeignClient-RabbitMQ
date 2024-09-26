package br.com.cadastroit.services.rabbitmq.model;

import java.io.Serializable;

import com.rabbitmq.client.AMQP.Queue;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QueueInformation implements Serializable {

	private static final long serialVersionUID = 4733537510625828693L;

	private int consumerCount;
	private int messageCount;
	private String queueName;

	public QueueInformation(Queue.DeclareOk declareOK) {

		this.queueName = declareOK.getQueue();
		this.consumerCount = declareOK.getConsumerCount();
		this.messageCount = declareOK.getMessageCount();
	}

}
