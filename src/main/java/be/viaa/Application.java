package be.viaa;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import be.viaa.amqp.AmqpBatchService;
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
		
		/*
		 * Read the properties file
		 */
		String propertiesFile = args.length == 2 && args[0].equals("-p") ? args[1] : "./application.properties";
		Properties properties = new Properties();
		properties.load(new FileReader(new File(propertiesFile)));
		String host = properties.getProperty("mq.rabbit.host");
		String username = properties.getProperty("mq.rabbit.username");
		String password = properties.getProperty("mq.rabbit.password");
		
		/*
		 * If there is no host specified, exit the program
		 */
		if (host == null || host.equals("")) {
			throw new IOException("no host specified");
		}
		
		try {
			AmqpService service = new RabbitMQService(host, username, password);
			AmqpBatchService poller = new AmqpBatchService(service);

			service.createIfNotExists("delete_requests");
			service.createIfNotExists("delete_responses");
			
			poller.addListener("delete_requests", new DeleteServiceConsumer());
			poller.start();
		} catch (Exception ex) {
			System.out.println("Could not connect to the MQ server: " + ex.getMessage());
		}
	}

}