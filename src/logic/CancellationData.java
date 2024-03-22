package logic;

import java.util.ArrayList;

public class CancellationData{
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
