package Logger;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * the Log class  has only one method,
 * the constructor. It sets up the file containing the logs
 * which is created in the main class.
 * @author picc
 *
 */
public class Log {
	
	public static Logger logger;
	FileHandler filehandler;
	public File f;
	/**
	 * see class description
	 * @param fileName
	 * , a string which will give the name to the file
	 * @throws SecurityException
	 * @throws IOException
	 */
	
	public Log(String fileName) throws SecurityException, IOException {
		
		f = new File(fileName);
		if(!f.exists()) {
			f.createNewFile();
		}
		
		filehandler = new FileHandler(fileName, true);
		logger = Logger.getLogger("test");
		logger.addHandler(filehandler);
		SimpleFormatter formatter = new SimpleFormatter();
		filehandler.setFormatter(formatter);
		
		
	}

}
