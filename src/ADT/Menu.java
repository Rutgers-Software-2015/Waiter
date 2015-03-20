package ADT;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class Menu {

	private Map<String,Float> itemsAndPrices;

	
	public Menu()
	{
		retrieveCurrentItems();
	}
	
	public void retrieveCurrentItems()
	{
		// Will eventually retrieve items from the database and map them
		itemsAndPrices = new Hashtable<String,Float>();
		itemsAndPrices.put("Sausage",(float)13.99);
		itemsAndPrices.put("Chicken",(float)11.99);
		itemsAndPrices.put("Sandwich",(float)6.99);
		itemsAndPrices.put("Pasta",(float)12.99);
		itemsAndPrices.put("Canoli",(float)5.99);
	}
	
	public boolean doesExist(String key)
	{
		retrieveCurrentItems();
		if(itemsAndPrices.containsKey(key)){
			if(itemsAndPrices.containsValue(itemsAndPrices.get(key))){
				return true;
			}
		}
		return false;
	}
	
	public Float getPrice(String key)
	{
		retrieveCurrentItems();
		return itemsAndPrices.get(key);
	}
	
	public LinkedList<String> getItemList()
	{
		retrieveCurrentItems();
		LinkedList<String> list = new LinkedList<String>();
		Set<String> set = itemsAndPrices.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			list.add(iterator.next());
		}
		return list;
	}

}