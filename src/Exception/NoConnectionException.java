package Exception;

import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.logging.Level;

import Logger.Log;

/**
 * NoConnectionException class extends UnknownHostException, a child of the exception class.
 * It handles both the lack of internet connection problem and the problem of non existing domain
 * since UnknownHostException handles both the problems.
 * This is class is used in the downloadThread exception
 * @author picc
 *
 */
public class NoConnectionException extends UnknownHostException {
	
	//since UnknownHostException handles both lack of connection problems and 
	//wrong domain name problem, i created an exception class that warns the user
	//that, in case this exception is thrown, there is one of the two above listed problems 
	
	public NoConnectionException() {
	
		Log.logger.setLevel(Level.WARNING);
		Log.logger.warning("Make sure you are connected to the internet \nand make sure that"
				+ " the domain name is correct\n\n\n\n");
		System.exit(1);}

}
