package be.viaa.delete;

import be.viaa.amqp.AmqpJsonConsumer;
import be.viaa.amqp.AmqpService;
import be.viaa.amqp.util.JsonConverter;
import be.viaa.delete.model.File;
import be.viaa.delete.model.Host;
import be.viaa.fxp.amqp.DeleteRequest;
import be.viaa.fxp.amqp.DeleteResponse;

/**
 * AMQP consumer for FXP messages
 * 
 * @author Hannes Lowette
 *
 */
public class DeleteServiceConsumer extends AmqpJsonConsumer<DeleteRequest> {

	/**
	 * The file transporter
	 */
	private final DeleteService transporter = new DeleteService();

	/**
	 * Constructor to identify the class used in the JSON parser
	 */
	public DeleteServiceConsumer() {
		super(DeleteRequest.class);
	}

	@Override
	public void accept(AmqpService service, DeleteRequest message) throws Exception {
		File file = new File(message.getPath(), message.getFilename());
		Host host = new Host(message.getHost(), message.getUsername(), message.getPassword());
		
		transporter.delete(file, host);
	}

	@Override
	public void success(AmqpService service, DeleteRequest request) throws Exception {
		DeleteResponse response = new DeleteResponse();
		
		response.setFilename(request.getFilename());
		response.setDirectory(request.getPath());
		response.setStatus("OK");
		response.setCorrelationId(request.getCorrelationId());
		
		service.write("delete_responses", JsonConverter.convert(response));
	}

	@Override
	public void exception(AmqpService service, Exception exception, DeleteRequest request) {
		DeleteResponse response = new DeleteResponse();

		response.setFilename(request.getFilename());
		response.setDirectory(request.getPath());
		response.setStatus("NOK");
		response.addMessage(exception);
		response.setCorrelationId(request.getCorrelationId());
		
		try {
			service.write("delete_responses", JsonConverter.convert(response));
		} catch (Exception ex) {
			// TODO: This exception needs to be monitored closely and logged pretty well, it means the queue
			// TODO: is unreachable and this needs to be reported to inform that the RabbitMQ is down
			ex.printStackTrace();
		}
	}	

}
