package be.viaa.delete;

import be.viaa.amqp.AmqpJsonConsumer;
import be.viaa.amqp.AmqpService;
import be.viaa.fxp.File;
import be.viaa.fxp.FxpFileTransporter;
import be.viaa.fxp.Host;
import be.viaa.delete.model.DeleteRequest;
import be.viaa.delete.model.DeleteResponse;
import be.viaa.util.GsonUtil;
import com.rabbitmq.client.Channel;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	private final FxpFileTransporter transporter = new FxpFileTransporter();

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
	public void success(AmqpService service, DeleteRequest request, Channel channel) throws Exception {
		DeleteResponse response = new DeleteResponse();
		
		response.setFilename(request.getFilename());
		response.setDirectory(request.getPath());
		response.setTimestamp(getTimestamp());
		response.setStatus("OK");
		response.setCorrelationId(request.getCorrelationId());
		
		service.write("delete_responses", GsonUtil.convert(response), channel);
	}

	@Override
	public void exception(AmqpService service, Exception exception, DeleteRequest request, Channel channel) {
		DeleteResponse response = new DeleteResponse();

		response.setFilename(request.getFilename());
		response.setDirectory(request.getPath());
		response.setTimestamp(getTimestamp());
		response.setStatus("NOK");
		response.addMessage(exception);
		response.setCorrelationId(request.getCorrelationId());
		
		try {
			service.write("delete_responses", GsonUtil.convert(response), channel);
		} catch (Exception ex) {
			// TODO: This exception needs to be monitored closely and logged pretty well, it means the queue
			// TODO: is unreachable and this needs to be reported to inform that the RabbitMQ is down
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * @return
	 */
	private final String getTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat(DeleteResponse.TIMESTAMP_FORMAT);
		return format.format(new Date());
	}

}
