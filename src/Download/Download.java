package Download;

import java.util.ArrayList;
import java.util.List;

/**
 * The Download class is the starting point for the builder design pattern
 * @author picc
 *
 */
public class Download {

	/**
	 * A list of objects
	 */
	private List<Object> items = new ArrayList<Object>();
	
	/**
	 * Allows to add an object to the list
	 * @param item
	 * , the object which will be added
	 */
	public void addItem(Object item){ 
		items.add(item);
	}
}
