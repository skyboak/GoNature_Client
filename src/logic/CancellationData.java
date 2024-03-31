package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class CancellationData implements Serializable{
	private ArrayList<CancellationDetail> Cancellations;
    private int[] dayCount;
    
	public CancellationData(ArrayList<CancellationDetail> cancellations, int[] dayCount) {
		super();
		Cancellations = cancellations;
		this.dayCount = dayCount;
	}
	public ArrayList<CancellationDetail> getCancellations() {
		return Cancellations;
	}
	public int[] getDayCount() {
		return dayCount;
	}
	
}
