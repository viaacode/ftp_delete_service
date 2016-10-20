package be.viaa.delete.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Response when the FXP transfer has been carried out
 * 
 * @author Hannes Lowette
 *
 */
public class DeleteResponse {

	/**
	 * The format in which the timestamp is shown
	 */
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";
	
	/**
	 * The filename of the deleted file
	 */
	@SerializedName("file_name")
	private String filename;
	
	/**
	 * The directory of the file
	 */
	@SerializedName("file_path")
	private String directory;
	
	/**
	 * The correlation ID of the file
	 */
	@SerializedName("correlation_id")
	private String correlationId;
	
	/**
	 * The status of the deletion (OK/NOK)
	 */
	private String status;

	/**
	 * The timestamp at which the response is created
	 */
	private String timestamp;
	
	/**
	 * Collection of exceptions
	 */
	private List<String> messages;

	/**
	 * 
	 * @param messages
	 */
	public void setMessages(List<Exception> messages) {
		for (Exception exception : messages) {
			addMessage(exception);
		}
	}
	
	/**
	 * 
	 * @param exception
	 */
	public void addMessage(Exception exception) {
		if (messages == null) {
			messages = new ArrayList<>();
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exception.printStackTrace(new PrintStream(baos));
		messages.add(new String(baos.toByteArray(), StandardCharsets.UTF_8));
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param directory the directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
