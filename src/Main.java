import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Cleaner.HtmlCleaner;
import Counter.Counter;
import Counter.CounterFactory;
import Counter.EmotionWordCounter;
import Counter.FrequencyCounter;
import Download.Download;
import Download.DownloadBuilder;
import Download.Downloader;
import Logger.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

public class Main {

	/**
	 * This is the main method from where all the operations start.
	 * Downloads are carried out simultaneously through multithreading while the 
	 * other operations happen sequentially.
	 * @param args
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, InterruptedException {
		
		//to keep track of the execution time
		long startTime = System.currentTimeMillis();
		
		//an instance of the class log to log operations
		Log myLog = new Log("logFile.txt");
		Log.logger.setLevel(Level.INFO);
		
		//an instance of the class Downloader is created using the Builder design pattern
		DownloadBuilder db = new DownloadBuilder();
		Download dd = db.buildDownloader();
	
		//a thread is created to execute the methods of the downloader class before all the 
		//other operations 
		Thread t1 = new Thread(db.d);
		t1.start();
		//to get the right songList.size(n) the main thread sleeps for 200 millisec
		Thread.sleep(200);
		myLog.logger.info("\nDownloading html files... (" + db.d.songList.size() +")\n");
		t1.join();

		//to make sure all downloads file have been written
		Thread.sleep(1500);
		
		//in case an exception is thrown, the level becomes Warning
		//so i had to set it back to info
		Log.logger.setLevel(Level.INFO);
		
		//an instance of the class htmlcleaner cleans the html files and keeps just
		//the lyrics of the song through the method clean
		HtmlCleaner h = new HtmlCleaner();
		h.clean();
		
		myLog.logger.info("\nCleaning html files... (" + h.cleanMdList.size()+ ")\n");
		Thread.sleep(150);

		//factory design pattern
		CounterFactory cf = new CounterFactory();
		
		//an instance of the class frequencycounter counts the word frequency
		Counter fc = cf.getCounter("FrequencyCounter");
		
		myLog.logger.info("\nWriting wordCountFiles... (" + h.cleanMdList.size()+ ")\n");

		//an instance of the class emotionwordcounter counts the emotion word frequency
		Counter ewc = cf.getCounter("EmotionWordCounter");
		
		myLog.logger.info("\nWriting emotionWordFiles... (" + h.cleanMdList.size()+ ")\n");

	    long endTime = System.currentTimeMillis();
		
		myLog.logger.info("\nTerminated. Time of execution: " + (((endTime-startTime) /1000) % 60 ) + " seconds"
				+ "\n\n\n\n");	
	}
}