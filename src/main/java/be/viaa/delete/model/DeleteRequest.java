package be.viaa.delete.model;

import com.google.gson.annotations.SerializedName;

/**
 * An FXP request
 * 
 * @author Hannes Lowette
 *
 */
public class DeleteRequest {

	/**
	 * The name of the file
	 */
	@SerializedName("file_name")
	private String filename;
	
	/**
	 * The directory the file is located in
	 */
	@SerializedName("file_path")
	private String path;

	/**
	 * The remote location of the FTP server
	 */
	private String host;

	/**
	 * The username used to access the FTP server
	 */
	private String username;

	/**
	 * The password to authenticate on the server
	 */
	private String password;
	
	/**
	 * The correlation ID of the file
	 */
	@SerializedName("correlation_id")
	private String correlationId;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeleteMessage [filename=" + filename + ", path=" + path + ", host=" + host + ", username=" + username
				+ ", password=" + password + "]";
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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

}
