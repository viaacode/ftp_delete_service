package be.viaa;

import java.util.Properties;

import be.viaa.amqp.AmqpPulseService;
import be.viaa.amqp.AmqpService;
import be.viaa.amqp.rabbitmq.RabbitMQService;
import be.viaa.delete.DeleteServiceConsumer;

/**
 * Contains the entry point of the application
 * 
 * @author Hannes Lowette
 *
 */
public class Application {

	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Starting application...");
		
		Properties properties = new Properties();
		properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));

		String host = properties.getProperty("host");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		
		try {
			AmqpService service = new RabbitMQService(host, username, password);
	//		AmqpService service = new RabbitMQService("localhost");
			AmqpPulseService poller = new AmqpPulseService(service);
	
			poller.addListener("delete_requests", new DeleteServiceConsumer());
			poller.start();
		} catch (Exception ex) {
			System.out.println("Could not connect to the MQ server: " + ex.getMessage());
		}
	}

}
