package Cleaner;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Download.Downloader;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The HtmlCleaner class has just 1 method; clean().
 * It reads for every song the html file containing the source code of the webpage
 * and through the use of regexes it cleans it and keeps only the lyrics of the song
 * which is then written on a new markdown file
 *  
 * @author picc
 *
 */
public class HtmlCleaner {
	
	 FileReader fr;
	 BufferedReader br; 
	 String line;
	 String wholeHTMLtext;
	 Pattern p;
	 Matcher m; 
	 PrintWriter writ;
	 public static List<String> cleanMdList = new ArrayList<String>();
	 String cleanMd;	
	 public int k = Downloader.htmlTitles.size();
	 
	 /**
	  * see the javadocumentaion for the HtmlCleaner class
	  * @throws SAXException
	  * @throws IOException
	  */
	 public void clean() throws SAXException, IOException {
		 
		 //for every HTML files downloaded, this method reads the whole html text
		for(int i = 0; i < Downloader.htmlTitles.size(); i++) {
			 
			wholeHTMLtext = "";
			
			try {
				//reads the html source code
				fr = new FileReader(Downloader.htmlTitles.get(i));
				br = new BufferedReader(fr);
				line = br.readLine(); 
				
				//creates the -.clean.md file name
				cleanMd = Downloader.htmlTitles.get(i)+ ".clean.md";
				
				//adds it to a list
				cleanMdList.add(cleanMd);
				
				//writes a file with that name
				writ = new PrintWriter(cleanMd);
				 
				//it writes the html source code on a string
				while (line != null) {
					line = br.readLine(); 
					wholeHTMLtext += line + "\n";

				}
				
				//then through regexes the string is cleaned and only the lyrics of the song is kept
				Pattern p = Pattern.compile("(?s)<div class=\\\"lyrics\\\">(.*)<!--\\/sse-->");
				Matcher m = p.matcher(wholeHTMLtext);
				wholeHTMLtext = "";
				
				while(m.find()) {
					wholeHTMLtext +=m.group(1);
				}
				
				wholeHTMLtext = wholeHTMLtext.replaceAll("\\<[^>]*>|\\s\\s+|\\[.*\\]|(?m)^[\\t]*\\r?\\n", "");
				wholeHTMLtext = wholeHTMLtext.replaceAll("\\n{2,}", "\n");

				//the lyrics (cleaned string) is finally written onto a new file
				writ.write(wholeHTMLtext);
				writ.close();
				br.close();

			} catch (FileNotFoundException e) {
				
				//if it could not read the file it is because the given song does not exist or it was 
				//written wrongly, so the file was not created
				k--;
				continue;
			} 
			
			
			
	 }
	}
}
