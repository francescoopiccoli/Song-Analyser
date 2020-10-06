package Counter;

import java.io.IOException;


/**
 * The CounterFactory class is part of the factory design pattern
 * It has only one method
 * @author picc
 *
 */
public class CounterFactory {
	
	
	/**
	 * The getCounter method returns an object according to the string parameter 
	 * @param counterType
	 * , string parameter
	 * @return
	 * @throws IOException
	 */
	public Counter getCounter(String counterType) throws IOException {
		
		if(counterType.equalsIgnoreCase("FrequencyCounter")) {
			return new FrequencyCounter();
		}
		
		else if (counterType.equalsIgnoreCase("EmotionWordCounter")) {
			return new EmotionWordCounter();
		}
		return null;
	}

}
