package Exception;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import Logger.Log;


/**
 * The WrongFileNameException class extends FileNotFoundException which is a child of the exception
 * class. In case there is no webpage in the given domain with the given path, this exception is thrown.
 * This exception class is called in the DownloadThread class. 
 */

public class WrongFileNameException extends FileNotFoundException{
	/**
	 * 
	 * @param url
	 * , it tells the user which url/path was not found.
	 */
	
	public WrongFileNameException(URL url) {
		
		Log.logger.setLevel(Level.WARNING);
		Log.logger.warning("\nAn ERROR has occured!\n"
				+ "There is no webpage on the given domain with the path:\n"+ url +"\n"
				+ "Downloading the other files...\n\n");		
	}
}
