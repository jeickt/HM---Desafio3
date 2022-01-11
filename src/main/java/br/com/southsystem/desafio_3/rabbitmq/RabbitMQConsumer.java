package br.com.southsystem.desafio_3.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.southsystem.desafio_3.model.entities.Product;

@Component
public class RabbitMQConsumer {
	
	@RabbitListener(queues=RabbitMQConnection.QUEUE_QUANTITY)
	private void consumer(Product product) {
		System.out.println(product);
	}

}
