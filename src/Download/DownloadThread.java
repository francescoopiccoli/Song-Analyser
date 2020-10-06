package Download;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;

import javax.net.ssl.SSLHandshakeException;

import Exception.NoConnectionException;
import Exception.WrongFileNameException;


/**
 * the DownloadThread class extends Thread. It gets a remote input (reads) from the internet
 * (html source code of the webpages of the songs) and for every song it writes the html code on a file
 * 
 * @author picc
 *
 */
public class DownloadThread extends Thread{
	
	private String song;
	private String title;
	
	/**
	 * the constructor sets up the parameters
	 * @param song
	 * , is a string which contains the url of the song to download.
	 * @param title
	 * , is a string which contains the name of the html file for the song.
	 * 
	 */
	
	public DownloadThread(String song, String title) {
		this.song = song;
		this.title = title;
	}

	
	/**
	 * the run method is executed once an instance of the "DownloadThread" class
	 * is instantiated and its "start" method is called.
	 * It connects to the given link (string: song), reads its source code and
	 * writes it on a new file named as the given htmltitle( string: title).
	 */
	
	@Override
	public void run() {
	
		
		URL url =null;
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36"); 		
        try { 
            url = new URL(this.song);
        }  
        
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
         
        InputStream inputstream =null;
        try {
        	
        	//reads the html content (of the webpage corresponding to the given url)
            //writes it on a file which will contain the whole html source code of the webpage
        	inputstream = url.openConnection().getInputStream();
        	
        	 BufferedReader reader = null;
             try {
             reader = new BufferedReader(new InputStreamReader(inputstream));}
             
             catch(Exception e) {
             	
             }
             String line = null;
             PrintWriter writer = null;
            
             try {
             	
            	synchronized(this) {
             	writer = new PrintWriter( this.title, "UTF-8");
             	
                 while( ( line = reader.readLine() ) != null )  {

                 	writer.println(line);	
                 }
            	}
                 
             } catch (IOException e) {
                e.printStackTrace();
             }
             
             try {
             	
                 reader.close();
                 
             } catch (IOException e) {
                 e.printStackTrace();
             }
             synchronized(this) {
             writer.close(); 
             }
        } catch (FileNotFoundException e1) {
				try {
					//customized exception in case the webpage is not found (url not valid)
					throw new WrongFileNameException(url);
					
				} catch (WrongFileNameException e) {
						e.printStackTrace();
				}
				
        }

        catch (UnknownHostException e2) {
            try {
            	//customized exception in case the domain name is wrong or there is no internet connection
				throw new NoConnectionException();
            	
			} catch (NoConnectionException e) {

				e.printStackTrace();
			}

        } catch (SSLHandshakeException e) {

			try {
            	
				throw new NoConnectionException();
				
            	
			} catch (NoConnectionException e1) {
				e1.printStackTrace();
			}
		
        } catch (IOException e) {
        	e.printStackTrace();
		}
        }
}