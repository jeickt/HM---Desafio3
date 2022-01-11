package br.com.southsystem.desafio_3.rabbitmq;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {
	
	private static final String EXCHANGE_NAME = "amq.direct";
	public static final String QUEUE_QUANTITY = "PRODUCT_CHANGED";
	
	private AmqpAdmin amqpAdmin;
	
	public RabbitMQConnection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
	}
	
	private Queue queue(String comunications) {
		return new Queue(comunications, true, false, false);
	}
	
	private DirectExchange exchange() {
		return new DirectExchange(EXCHANGE_NAME);
	}

	private Binding relationship(Queue queue, DirectExchange exchange) {
		return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
	}
	
	@PostConstruct
	private void add() {
		Queue queueQuantity = this.queue(QUEUE_QUANTITY);
		DirectExchange exchange = this.exchange();
		Binding relationship = this.relationship(queueQuantity, exchange);
		// Criando a fila no RabbitMQ
		this.amqpAdmin.declareQueue(queueQuantity);
		this.amqpAdmin.declareExchange(exchange);
		this.amqpAdmin.declareBinding(relationship);
	}
}
