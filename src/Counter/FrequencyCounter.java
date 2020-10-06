package Counter;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import Cleaner.HtmlCleaner;

/**
 * The FrequencyCounter class is divided in 3 methods plus the constructor.
 * The class reads the .clean.md files 
 * and through the use of maps and collection find the top 20 frequent words. It then writes
 * these words and the frequency in a new csv file
 * @author picc
 *
 */
 
public class FrequencyCounter implements Counter{
	
	List<String> ignoreWordsList;
	FileReader fr1, fr2;
	BufferedReader br;
	String line, wholeLyrics;
	static String[] wordsInLyrics;
	PrintWriter p;
	Map<String, Integer> frequencies;
	List<Integer> values;	
	public String wordCountFile;
	
	/**
	 * The FrequencyCounter constructor first reads all the words in the ignoreWords.csv
	 * file and puts them in a list. Then it reads the clean.md files, writes a new frequencycount.csv
	 * for every song
	 * and calls all the other methods of the class
	 * which perform the frequency computation.
	 * @throws IOException
	 */
	public FrequencyCounter() throws IOException {
		
		//reads the ignoreWords file and put each word in a list
		fr1 = new FileReader("ignoreWords.csv");
		br = new BufferedReader(fr1);
		ignoreWordsList = new ArrayList<String>();
		String upperLine = "";

		//to count in a case insensitive way it adds to the list also every word with the first letter in capital
		while ((line = br.readLine()) != null) {
			upperLine = line.toUpperCase();
			if(line.length()>= 2) {
			upperLine = upperLine.charAt(0) + line.substring(1, line.length());
			}
			ignoreWordsList.add(line); 
			ignoreWordsList.add(upperLine); 

		}
		
		//writes a new csv file for each song 
		for(int i = 0; i < HtmlCleaner.cleanMdList.size(); i++) {
			
			wordCountFile = HtmlCleaner.cleanMdList.get(i).replaceAll(".clean.md", "") + ".wordcount.csv";

			p = new PrintWriter(wordCountFile);
			
			readLyrics(HtmlCleaner.cleanMdList.get(i));
			wordsInLyrics = lyricsSplitter(wholeLyrics);
			wordCounter();
		}
	}
	

	 
	/**
	 * The readLyrics method is called in the constructor of this class.
	 * It reads for each song the clean.md file which contains the lyrics
	 * and puts the whole lyrics in a string
	 * @param cleanMdfileName
	 *  ,is a string which contains the name of the cleanMd File for the song
	 * @throws IOException
	 */
	
	public void readLyrics(String cleanMdfileName) throws IOException {
		
		//read the .clean.md file and put the whole lyrics in a string
		fr2 = new FileReader(cleanMdfileName);
		br = new BufferedReader(fr2);
		wholeLyrics = "";
		
		while ((line = br.readLine()) != null) {
			
			wholeLyrics += line + "\n"; 
		}
	}
	
	/**
	 * The lyricsSplitter method returns an array containing each word in the lyrics
	 * 
	 * @param lyrics
	 * @return
	 */
	public String[] lyricsSplitter(String lyrics) {
		
		//cleans the lyrics in order to count the words
		lyrics = lyrics.replaceAll("[,.!?\":\\(\\)“”1-9]", "");
		lyrics = lyrics.replaceAll("\\bx", "");
		lyrics = lyrics.replaceAll("’", "\\'");


		for(int i = 0; i < ignoreWordsList.size(); i++) {
			lyrics = lyrics.replaceAll("\\b" + ignoreWordsList.get(i) + "\\b[^\\'\\’]", "");
		}
		
		//split the wholeLyrics every times it finds a white space and put each word in an array of strings
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
	
	/**
	 * The wordCounter method performs the core operation of the class:
	 *it counts through a map the frequency of each word
	 * @throws FileNotFoundException
	 */
	
	public void wordCounter() throws FileNotFoundException {
		
		frequencies = new LinkedHashMap<String, Integer>();
	    Integer frequency = 0;
	    
		for (String word : wordsInLyrics) {
			
		    if (!word.isEmpty()) {
		    	
		        frequency = frequencies.get(word);
		    
		    
		        if (frequency == null) {
		            frequency = 0;
		        }
	
		        ++frequency;
		        frequencies.put(word, frequency);
		    }
		    
		    //a list which keeps only the values of the map
		    values = new ArrayList<Integer>(frequencies.values());
		    
		    //sets the list in ascending order
		    Collections.sort(values);
		}
		
		
		//makes a set with only the keys (that are strings) of the map
		//setted in descending order with respect to the corresponding values 
		//in order to save computional cost  and time cost 
		//it does it only for the top 20 elements
		Set<String> keys = new LinkedHashSet<String>();
		
		for (int i = values.size()-1; i > values.size()-21; i--) {
			 
			 for (Entry<String, Integer> entry : frequencies.entrySet()) {
				 
				 if(i< values.size() && i >= 0) {
					 
			        if (Objects.equals(values.get(i), entry.getValue())) {
			        	
			            keys.add(entry.getKey());
			        }
			      } 
			 }
		 }
		 
		int i = 1;
		
		//it writes the first 20 map entries onto the frequency file
		for(String k: keys) {
			 
			 if(i <= 20) {
				 p.write(frequencies.get(k) + ";" + k + "\n");
			 }
			 i++;
		 }	
		p.close();
	}
}
