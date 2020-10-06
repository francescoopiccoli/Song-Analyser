package Download;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is used for the Builder Design Pattern
 * It has two methods for the creation of objects of different type
 * @author picc
 *
 */
public class DownloadBuilder {
	
	public Downloader d;
	public DownloadThread t;
	
	
	/**
	 * To create a downloader object and add it to the list
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Download buildDownloader() throws FileNotFoundException, IOException, InterruptedException {
	
		Download download = new Download();
		//singleton design pattern
		d = Downloader.getInstance();
		download.addItem(d);
		return download;
	}
	
	
	/**
	 * To create a DownloadThread object and add it to the list
	 * @param song
	 * , (url)
	 * @param title
	 * ,(html file name)
	 * @return
	 */
	public Download buildDownloadThread(String song, String title) {
		
		Download dt = new Download();
		t = new DownloadThread(song, title);
		dt.addItem(t);
		return dt;
	}
}
