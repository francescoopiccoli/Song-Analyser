package Counter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import Cleaner.HtmlCleaner;

/**
 * The EmotionWordCounter class extends HtmlCleaner since it uses its cleanMdList list.
 * It is divided in 3 methods plus the constructor. The class reads the .clean.md files 
 * and the emotionWord.csv file and counts the frequency of each emotion word.

 * @author picc
 *
 */

public class EmotionWordCounter implements Counter{
	
	/**
	 * 
	 */
	
	List<String> emotionWordList;
	FileReader fr1, fr2;
	BufferedReader br;
	String line, wholeLyrics;
	static String[] wordsInLyrics;
	PrintWriter pw;		
	Map<String, Integer> frequencies;
	
	
	/**
	 * The constructor reads the emotionWord.csv file and puts each element/word
	 * in an list. Then it writes a new csv file for each song, this file contains
	 * the emotion words found and their frequency. Then it calls the other methods 
	 * which perform the core computations
	 * @throws IOException
	 */
	public EmotionWordCounter() throws IOException {
		
		//reads the emotion word csv file and store each element/word in an arraylist
		fr1 = new FileReader("emotionWords.csv");
		br = new BufferedReader(fr1);
		emotionWordList = new ArrayList<String>(); 
 
		while ((line = br.readLine()) != null) {
			emotionWordList.add(line);
		} 
		
		
		for(int i = 0; i < HtmlCleaner.cleanMdList.size(); i++) {
					
			String wordEmotionCountFile = HtmlCleaner.cleanMdList.get(i).replaceAll(".clean.md", "") + ".emotioncount.csv";
			pw = new PrintWriter(wordEmotionCountFile);
			
			readLyrics(HtmlCleaner.cleanMdList.get(i));
			wordsInLyrics = lyricsSplitter(wholeLyrics);
			counter();

		}	
	}
	
	/**
	 * The counter method performs the core operation. It creates a map
	 * which contains each emotion word found and its frequency
	 * the two "for" check if the word is in both the arrays/arraylists.
	 */

	
	public void counter() {
		
		//creates a map which store every word that is in both
		//the arraylist and the respective value(frequence)
		
		frequencies = new LinkedHashMap<String, Integer>();
	    Integer frequency = 0;
	    
		for (String word1 : emotionWordList) {
			 
			for(String word2 : wordsInLyrics) {
				
				if(word1.equalsIgnoreCase(word2)) {
				    
					if (!word1.isEmpty()) {
				    	
				        frequency = frequencies.get(word1);
				    
				        if (frequency == null) {
				            frequency = 0;
				        }
			
				        ++frequency;
				        frequencies.put(word1, frequency);
				    }
				}
			}
		}
		
		
		 Iterator it = frequencies.entrySet().iterator();
		 
		 //writes each pair of the map in a file
		 
		 while (it.hasNext()) {
			 Map.Entry pair = (Map.Entry)it.next();
			 pw.write(pair.getValue() + ";" + pair.getKey() + "\n");
		    }
		 
		 pw.close();
	}
	
	
	/**
	 * The readLyrics method is called in the constructor of this class.
	 * It reads for each song the clean.md file which contains the lyrics
	 * and puts the whole lyrics in a string
	 * @param s
	 * @throws IOException
	 */
	
	public void readLyrics(String s) throws IOException {
			
			//read the .clean.md file and put the whole lyrics in a string
			fr2 = new FileReader(s);
			br = new BufferedReader(fr2);
			wholeLyrics = "";
			
			while ((line = br.readLine()) != null) {
				
				wholeLyrics += line + "\n";
			}
		}
	
	
	/**
	 * The lyricsSplitter method returns an array containing each word in the lyrics
	 * @param lyrics
	 * @return
	 */
	public String[] lyricsSplitter(String lyrics) {
		//split the wholeLyrics every times it finds a white space and put each word in an array of strings
		lyrics = lyrics.replaceAll("[,.!?\":]", "");

		wordsInLyrics = lyrics.split(" |\n|-");
		
		//the next piece of code allows the counter to count in a case insenstive way
		for(int j = 0; j < wordsInLyrics.length-1; j++) {
			
			for(int k = 1; k < wordsInLyrics.length; k++) {
				
				if(wordsInLyrics[j].equalsIgnoreCase(wordsInLyrics[k])) {
					
					wordsInLyrics[j] = wordsInLyrics[k];
				}
			}
		}
		
		return wordsInLyrics;
	}
	
}
