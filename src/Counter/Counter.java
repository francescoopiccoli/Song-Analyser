package Counter;

import java.io.IOException;

/**
 * The Counter interface is the interface used to use the Factory design pattern
 * @author picc
 *
 */
public interface Counter {
	
	public void readLyrics(String cleanMdfileName) throws IOException;
	
	public String[] lyricsSplitter(String lyrics);
}
