package Download;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the Downloader class. It implements Runnable.
 * It creates a Downloader object through the 
 * singleton design pattern.
 * It is divided in four methods.
 * 
 * @author picc
 *
 */
public class Downloader implements Runnable{
	
	private static Downloader downloader;
	String csvFile = "songs.csv"; 
	String line = "";
	String separator = ";"; 
	BufferedReader br;
	String urlString;
	public static ArrayList<String> songList = new ArrayList<>();
	public static ArrayList<String> htmlTitles = new ArrayList<>();
	static int nOfSongs;
	static int count;
	public  int i;
	DownloadBuilder db2 = new DownloadBuilder();
	Download d2;
	
	
	//singleton design pattern
	public static Downloader getInstance() throws FileNotFoundException, IOException, InterruptedException {
		
		if(downloader == null) {
			downloader = new Downloader();
		}
		return downloader;
	}
	
	
	/**
	 * the parseSongList method reads the songs.csv file, it parse it through regexes
	 * and in ends up forming all the urls for the songs which are added to a list.
	 * It also creates all the names for the html files which will be created. These names
	 * are added to another list.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void parseSongList() throws FileNotFoundException, IOException, InterruptedException {
		
		//read the file
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){ 
			
			while ((line = br.readLine()) != null) {
				
				String[] tokens = line.split(";"); 
				
				//eliminate a white space after ";" in case it was wrongly added
				if((tokens[1].charAt(0) + "").equals(" ")) {
					tokens[1] = tokens[1].substring(1, tokens[1].length());
				}
			
				//transform every song in a url
				
				String first = tokens[1] + " " +  tokens[0];
				first = first.replaceAll("&", "and");
				first = first.replaceAll("\\$|\\?\\!", "-");
				first = first.replaceAll("\\.|\\(|\\)|\\,", "");
				first = first.replaceAll("ò|ö|ó", "o");
				first = first.replaceAll("à|À|ä|Ä", "a");
				first = first.replaceAll("ì", "i");
				first = first.replaceAll("ü|Ü", "e");
				first = first.replaceAll("è|é|È|É|ë", "e");

				
				urlString = "https://genius.com/" + first.replace(" ", "-") + "-lyrics";
				urlString = urlString.replaceAll("'|,|\\?|\\!|’", "");
				
				//url are added to the songList
				songList.add(urlString);	
				
				//creates the names for the html files
            	String rawTitle = tokens[1] + " _ " +tokens[0];
            	String[] arr = rawTitle.split(" ");
            	String title = "";
            	
            	//camelcase
            	for(int i = 0; i< arr.length; i++) {
            		String k = arr[i];
            		k = Character.toUpperCase(k.charAt(0)) + k.substring(1);
            		title += k;
            	}
            	
            	title = title.replaceAll("'|\"|!|\\?|-|,|\\.", "");

            	title += ".html";
            	
            	//html file names are added to the htmlTitles list 
            	htmlTitles.add(title);
            	
            	nOfSongs = songList.size();
	            	 
			}
		}
	}
	
	/**
	 * the method "download" creates a downloadThread object for every song in the
	 * list. Since downloadThread extends Thread and this method calls the start
	 * method, a new thread is created for every song, in this way downloads occur 
	 * simultaneously.
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
 	
	public void download() throws IOException, UnsupportedEncodingException, InterruptedException {


		for(i = 0; i < songList.size(); i++) {
			
			//a downloadThread object is created through the builder design pattern
			d2 = db2.buildDownloadThread(songList.get(i), htmlTitles.get(i));
			
			//the downloadtThread object calls the start method
			db2.t.start();
			
			}
			
		//before starting to execute other commands/methods in the main class
		//all downloads must finish
		db2.t.join();
		
	}


	/**
	 * The run method calls the other method of this class
	 * The run method is executed when the start method is invoked
	 */
	@Override
	public void run() {
		try {
			parseSongList();
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		try {
			synchronized(this) {
			download();}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

