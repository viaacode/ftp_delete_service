package be.viaa.delete;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import be.viaa.amqp.AmqpBatchService;
import be.viaa.amqp.AmqpService;
import be.viaa.amqp.rabbitmq.RabbitMQService;

/**
 * Contains the entry point of the application
 * 
 * @author Hannes Lowette
 *
 */
public class DeleteApplication {
	
	/**
	 * The logger class for this application
	 */
	private static final Logger logger = LogManager.getLogger(DeleteApplication.class);

	/**
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		logger.info("Starting application...");
		
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
			AmqpBatchService batch = new AmqpBatchService(service);

			service.createIfNotExists("delete_requests");
			service.createIfNotExists("delete_responses");
			
			batch.addListener("delete_requests", new DeleteServiceConsumer());
			batch.start();
		} catch (Exception ex) {
			logger.fatal("Could not connect to the MQ server: " + ex.getMessage());
			logger.catching(ex);
		}
	}

}