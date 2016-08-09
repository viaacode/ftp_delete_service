package be.viaa.amqp;

import java.io.IOException;

/**
 * Abstract AMQP service representation
 * 
 * @author Hannes Lowette
 *
 */
public interface AmqpService {
	
	/**
	 * Initializes the queue
	 */
	void initialize() throws Exception;

	/**
	 * 
	 * @param queue
	 * @param consumer
	 */
	void read(String queue, AmqpConsumer consumer) throws IOException;

	/**
	 * 
	 * @param queue
	 * @param data
	 */
	void write(String queue, byte[] data) throws IOException;

}
