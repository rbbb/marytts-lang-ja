/* *ATTENTION* *ATTENTION* *ATTENTION* *ATTENTION*
*
* This Korean text to speech module is subject to the terms of the LGPL version 3.0, 
* which can be found here http://www.gnu.org/licenses/lgpl-3.0-standalone.html.
*
* In addition, if you use this module in a robot, you are kindly asked to do the following:
*
* - Put a Seunghee or 승희 nametag on the robot
* 
* - have the robot say ‘우야, 웃기지마라 촘’ ‘엄마, 큰언 구박해’ and ‘죽울래!?’ (respectively, 
*   ‘Jay, stop kidding’, ‘Mooom, big sis is complaining agaain’ and ‘R’you crazy?’)
* 
* - take a video and send it to ‘seunghee DOT han ATSIGN gmail ANOTHERDOT com’
*
* Released on Seunghee’s birthday, because she likes robots.
* Copyright 2014 Seunghee Han
*/
package marytts.language.ko.lib;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import marytts.server.MaryProperties;
import marytts.util.MaryUtils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class HanjaDictionary {
	
	Logger logger = MaryUtils.getLogger("Hanja dictionary");
	
	private Map<Character, String> pronunciations = new HashMap<Character, String>();
	
	public HanjaDictionary()
	{
		try{
	        InputStream dictionaryStream = MaryProperties.needStream("ko.hanja_dictionary");
	        BufferedReader dictionaryReader = new BufferedReader(new InputStreamReader(dictionaryStream, Charset.forName("UTF-8")));
	        String line = dictionaryReader.readLine();
	        while(line!=null)
	        {
	        	if(line.trim().startsWith("#"))
	        	{
	        		line = dictionaryReader.readLine();
	        		continue;
	        	}
	        	String[] data = line.split(" ");
	        	if(data[0].length()>1)
	        	{
	        		logger.log(Level.WARN, "In hanja dictionary, hanja is longer than one character: "+data[0]+", only the first character has been kept");
	        	}
	        	pronunciations.put(data[0].charAt(0), data[1]);//hanja -> hangul
	        	
	        	line = dictionaryReader.readLine();
	        }
	        dictionaryReader.close();
	        dictionaryStream.close();
		}catch(Exception e){
			logger.log(Level.ERROR,"Could not load hanja dictionary", e);
		}
	}

	public String getPronunciationOrNull(char currChar) {
		return pronunciations.get(currChar);
	}
	
}
