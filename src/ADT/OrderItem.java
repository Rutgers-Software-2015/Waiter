package ADT;

public class OrderItem {

	private int seatNumber;
	private MenuItem item;
	
	public OrderItem(int seat, MenuItem i)
	{
		seatNumber = seat;
		item = i;
	}
	
	public int getSeatNumber()
	{
		return seatNumber;
	}
	
	public MenuItem getMenuItem()
	{
		return item;
	}
	
}
