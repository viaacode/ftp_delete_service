package be.viaa.delete;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTPClient;

import be.viaa.delete.model.File;
import be.viaa.delete.model.Host;

/**
 * TODO: Add some form of retry mechanism
 * 
 * @author Hannes Lowette
 *
 */
public class DeleteService {

	/**
	 * 
	 * @param file
	 * @param host
	 * @throws IOException
	 */
	public void delete(File file, Host host) throws Exception {
		System.out.println("attempting to delete file '" + file.getDirectory() + "/" + file.getName() + "'");
		
		FTPClient client = new FTPClient();

		try {
			/*
			 * Connect to the FTP server that hosts the source file
			 */
			client.connect(host.getHost(), host.getPort());
			if (!client.login(host.getUsername(), host.getPassword())) {
				throw new IOException("invalid credentials for the source FTP");
			}
	
			/*
			 * Send both of the FTP hosts to see if they are reachable
			 */
			if (!client.isConnected() || !client.isAvailable()) {
				throw new IOException("ftp connection not available");
			}
            
            /*
             * Check to see if the source directory exists
             */
			if (!client.changeWorkingDirectory(file.getDirectory())) {
				throw new FileNotFoundException("directory '" + file.getDirectory() + " does not exist.");
			}
			
			/*
			 * Check to see if the file exists
			 */
			if (!Arrays.asList(client.listNames()).contains(file.getName())) {
				throw new FileNotFoundException("could not find file " + file.getDirectory() + "/" + file.getName() + " on the FTP server");
			}
			
			/*
			 * Delete the file from the remote FTP server and return the OK status when successful
			 */
			client.deleteFile(file.getName());
			System.out.println("successfully deleted file '" + file.getDirectory() + "/" + file.getName() + "'");
		} catch (Exception ex) {
			System.out.println("could not delete file '" + file.getDirectory() + "/" + file.getName() + "' - " + ex.getMessage());
			throw ex;
		} finally {
			client.disconnect();
		}
	}

}
