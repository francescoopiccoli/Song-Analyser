package Testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;

import org.junit.Test;
import org.xml.sax.SAXException;

import Cleaner.HtmlCleaner;
import Counter.Counter;
import Counter.CounterFactory;
import Counter.EmotionWordCounter;
import Counter.FrequencyCounter;
import Download.Downloader;
import Logger.Log;


/**
 * The TestJUnit class has only methods that will be tested by JUnit
 * @author picc
 *
 */
public class TestJUnit {
	
	
	/**
	 * Checks if the length of the list of lyrics file (...clean.md) is equal to
	 * the number of "...html" files
	 * @throws SAXException
	 * @throws IOException
	 */
	@Test
	public void listSize() throws SAXException, IOException {
		
		HtmlCleaner hc = new HtmlCleaner();
		assertEquals(hc.cleanMdList.size(), hc.k);
		
	}
	
	
	/**
	 * Checks if a Thread if the number of threads created is equal to the size
	 * of the list of songs.
	 * @throws IOException
	 */
	@Test
	public void CheckOfnumberOfThreadsForTheDownloads() throws IOException {
		
		Downloader d = new Downloader();
		assertEquals(d.i, d.songList.size());
		
	}	
	
	
	
	/**
	 * Checks if the CounterFactory class works correctly
	 * @throws IOException
	 */
	@Test
	public void FactoryCheck() throws IOException {
		
		CounterFactory cf = new CounterFactory();
		
		Counter fc1 = cf.getCounter("FrequencyCounter");
		assertTrue(fc1 instanceof FrequencyCounter);
		
		Counter fc2 = cf.getCounter("EmotionWordCounter");
		assertTrue(fc2 instanceof EmotionWordCounter);

	}
	
	
	/**
	 * Checks if a file (with the name equal to the String log parameter)
	 * is created, once that the log class has been instantiated.
	 * @throws SecurityException
	 * @throws IOException
	 */
	@Test
	public void LogCreationCheck() throws SecurityException, IOException {
		
		Log myLog = new Log("logFile.txt");
		assertTrue(myLog.f.exists());

 
	}
	
}	

