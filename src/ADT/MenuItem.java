package ADT;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class MenuItem extends Menu{

	private String menuItem;
	private float itemPrice;
	private boolean isSet = false;
	
	public MenuItem()
	{
		super();
	}
	
	public MenuItem(String item)
	{
		super();
		setItem(item);
	}
	
	public boolean setItem(String item)
	{
		if(doesExist(item)){
			itemPrice = getPrice(item);
			menuItem = item;
			isSet = true;
			return true;
		}
		else{
			itemPrice = 0;
			menuItem = null;
			isSet = false;
			return false;
		}
	}
	
	public String getItem()
	{
		if(isSet){
			return menuItem;
		}
		return null;
	}
	

}
