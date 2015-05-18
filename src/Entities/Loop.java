package Entities;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class Loop {

	private String type;
	private String targetCondition;
	private int countNumber;
	private Pattern pattern;
	private double timeout = 6.0;
	public boolean executeCondition(Region region) {
		try {
			Thread.sleep((long) (1000 * timeout / 2));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
			return false;
		}
		try {
		
			region.wait(pattern,timeout / 2);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			return false;
			//e.printStackTrace();
		}
		
		return true;
	}
	public int getCountNumber() {
		return countNumber;
	}
	public void setCountNumber(int countNumber) {
		this.countNumber = countNumber;
	}
	public Loop(String countNumber, String type, String targetCondition) {
		this.type = type;
		this.targetCondition = targetCondition;
		if(targetCondition != null)
			pattern = new Pattern(targetCondition);
		if(countNumber != null)
			this.countNumber = new Integer(countNumber);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
